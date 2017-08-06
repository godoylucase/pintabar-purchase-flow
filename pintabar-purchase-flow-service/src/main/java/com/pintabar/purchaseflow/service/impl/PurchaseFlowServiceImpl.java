package com.pintabar.purchaseflow.service.impl;

import com.pintabar.commons.exceptions.ErrorCode;
import com.pintabar.commons.exceptions.general.DataNotFoundException;
import com.pintabar.commons.exceptions.purchaseorder.ClosedPurchaseOrderException;
import com.pintabar.commons.exceptions.purchaseorder.InvalidPurchaseOrderException;
import com.pintabar.commons.exceptions.user.InvalidUserException;
import com.pintabar.commons.exceptions.user.UserWithOpenedOrderException;
import com.pintabar.purchaseflow.model.dto.PurchaseOrderDTO;
import com.pintabar.purchaseflow.model.dtomapper.PurchaseOrderDTOMapper;
import com.pintabar.purchaseflow.model.entity.PurchaseOrder;
import com.pintabar.purchaseflow.model.entity.PurchaseOrderDetail;
import com.pintabar.purchaseflow.model.entity.PurchaseOrderStatus;
import com.pintabar.purchaseflow.service.PurchaseFlowService;
import com.pintabar.purchaseflow.service.repositories.PurchaseOrderRepository;
import com.pintabar.usermanagement.api.UserManagementAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by lucasgodoy on 14/06/17.
 */
@Component
public class PurchaseFlowServiceImpl implements PurchaseFlowService {

	private final PurchaseOrderRepository purchaseOrderRepository;
	private final PurchaseOrderDTOMapper purchaseOrderDTOMapper;

	@Autowired
	private UserManagementAPI userManagementAPIProxy;

	public PurchaseFlowServiceImpl(PurchaseOrderRepository purchaseOrderRepository, PurchaseOrderDTOMapper purchaseOrderDTOMapper) {
		this.purchaseOrderRepository = purchaseOrderRepository;
		this.purchaseOrderDTOMapper = purchaseOrderDTOMapper;
	}

	@Override
	@Transactional
	public Optional<PurchaseOrderDTO> createPurchaseOrder(String userUuid, String tableUnitUuid)
			throws DataNotFoundException, UserWithOpenedOrderException, InvalidUserException {
		validateBeforePurchaseOrderCreation(userUuid, tableUnitUuid);
		PurchaseOrder purchaseOrder = createNewEmptyPurchaseOrder(userUuid, tableUnitUuid);
		return purchaseOrderDTOMapper.mapToDTO(purchaseOrder);
	}

	// TODO: move out this services to business logic uMS
//	@Override
//	@Transactional
//	public List<MenuInstanceDTO> getMenuInstances(String businessUuid) {
//		return getMenuInstances(businessUuid, null);
//	}
//
//	@Override
//	@Transactional
//	public List<MenuInstanceDTO> getMenuInstances(String businessUuid, Boolean isDeleted) {
//		Preconditions.checkNotNull(businessUuid);
//		return menuInstanceRepository.findAllMenuInstancesByBusinessUuid(businessUuid, isDeleted)
//				.stream()
//				.map(menuInstance -> menuInstanceDTOMapper.mapToDTO(menuInstance).orElse(null))
//				.collect(Collectors.toList());
//	}

	@Override
	@Transactional
	public Optional<PurchaseOrderDTO> addItemsToPurchaseOrder(String purchaseOrderUuid, Map<String, BigDecimal> purchaseOrderLinesMap)
			throws InvalidPurchaseOrderException, ClosedPurchaseOrderException, DataNotFoundException {
		PurchaseOrder purchaseOrder = purchaseOrderRepository.findByUuid(purchaseOrderUuid)
				.orElseThrow(() -> new DataNotFoundException(ErrorCode.PURCHASE_ORDER_NOT_FOUND));
		validateBeforeAddingItemsToPurchaseOrder(purchaseOrder);
		// item detail rebuild from external resource and add them into purchase order
		purchaseOrder.getDetails()
				.addAll(processPurchaseOrderDetailsFromMap(purchaseOrderLinesMap, purchaseOrder));
		return purchaseOrderDTOMapper.mapToDTO(purchaseOrder);
	}

	@Override
	@Transactional
	public Optional<PurchaseOrderDTO> checkoutPurchaseOrder(String purchaseOrderUuid) throws DataNotFoundException, ClosedPurchaseOrderException {
		PurchaseOrder purchaseOrder = purchaseOrderRepository.findByUuid(purchaseOrderUuid)
				.orElseThrow(() -> new DataNotFoundException(ErrorCode.PURCHASE_ORDER_NOT_FOUND));
		validateBeforeCheckingPurchaseOrderOut(purchaseOrder);
		purchaseOrder.setStatus(PurchaseOrderStatus.CLOSED_TO_BE_PAID);
		return purchaseOrderDTOMapper.mapToDTO(purchaseOrder);
	}

	private List<PurchaseOrderDetail> processPurchaseOrderDetailsFromMap(Map<String, BigDecimal> purchaseOrderLinesMap,
																		 PurchaseOrder purchaseOrder) throws DataNotFoundException {
		try {
			return purchaseOrderLinesMap.entrySet().stream()
					.map(entry -> {
						// TODO: check menu item instance exist and if it is fully available
//						MenuItemInstance menuItemInstance = menuItemInstanceRepository.findByUuid(entry.getKey())
//								.orElseThrow(IllegalArgumentException::new);
//						if (!menuItemInstance.isFullAvailable()) {
//							throw new IllegalArgumentException();
//						}
						return new PurchaseOrderDetail(entry.getValue(), entry.getKey(), purchaseOrder);
					}).collect(Collectors.toList());
		} catch (IllegalArgumentException ex) {
			// temporal work around before implementing custom Function for above lambda expression
			throw new DataNotFoundException(ErrorCode.MENU_ITEM_NOT_FOUND);
		}
	}

	private PurchaseOrder createNewEmptyPurchaseOrder(String userUuid, String tableUnitUuid) {
		Optional<PurchaseOrder> parentPurchaseOrder = purchaseOrderRepository.findParentPurchaseOrderByTableUuid(tableUnitUuid);
		PurchaseOrder purchaseOrder = new PurchaseOrder(userUuid, tableUnitUuid, parentPurchaseOrder.orElse(null));
		return purchaseOrderRepository.save(purchaseOrder);
	}

	private void validateBeforePurchaseOrderCreation(String userUuid, String tableUnitUuid) throws UserWithOpenedOrderException,
			DataNotFoundException, InvalidUserException {
		if (userManagementAPIProxy.validateUser(userUuid).readEntity(Boolean.class)) {
			throw new InvalidUserException(ErrorCode.USER_INVALID);
		}

		// TODO: at this stage validate table against business microservice
		// TableUnit tableUnit = tableUnitRepository.findByUuid(tableUnitUuid)
		//		.orElseThrow(() -> new DataNotFoundException(ErrorCode.TABLE_UNIT_NOT_FOUND));

		List<PurchaseOrder> userOpenedPurchaseOrders =
				purchaseOrderRepository.findPurchaseOrdersByUserUuidAndStatus(userUuid, PurchaseOrderStatus.OPEN_STATUSES);
		if (!userOpenedPurchaseOrders.isEmpty()) {
			throw new UserWithOpenedOrderException(ErrorCode.USER_ALREADY_HAS_OPENED_ORDERS);
		}
	}

	private void validateBeforeAddingItemsToPurchaseOrder(PurchaseOrder purchaseOrder) throws ClosedPurchaseOrderException {
		// TODO: at this stage validate user against user microservice
		//		User user = userRepository.findByUuid(orderingWS.getUserUuid())
		//	.orElseThrow(() -> new DataNotFoundException(ErrorCode.USER_NOT_FOUND));

		// TODO: validate user is checked in to table and business uudis
		//if (!purchaseOrder.getUuid().equals(orderingWS.getUserUuid())
		//		|| !purchaseOrder.getTableUnit().getBusiness().getUuid().equals(orderingWS.getBusinessUuid())) {
		//	throw new InvalidPurchaseOrderException(ErrorCode.PURCHASE_ORDER_INVALID_OWNER);
		//} else
		if (PurchaseOrderStatus.CLOSED_STATUSES.contains(purchaseOrder.getStatus())) {
			throw new ClosedPurchaseOrderException(ErrorCode.PURCHASE_ORDER_ALREADY_CLOSED);
		}
	}

	private void validateBeforeCheckingPurchaseOrderOut(PurchaseOrder purchaseOrder) throws ClosedPurchaseOrderException {
		if (PurchaseOrderStatus.CLOSED_STATUSES.contains(purchaseOrder.getStatus())) {
			throw new ClosedPurchaseOrderException(ErrorCode.PURCHASE_ORDER_ALREADY_CLOSED);
		}
	}
}
