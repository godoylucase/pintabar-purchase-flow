package com.pintabar.purchaseflow.service.impl;

import com.pintabar.commons.exceptions.ErrorCode;
import com.pintabar.commons.exceptions.general.DataNotFoundException;
import com.pintabar.commons.exceptions.purchaseorder.ClosedPurchaseOrderException;
import com.pintabar.commons.exceptions.purchaseorder.InvalidPurchaseOrderException;
import com.pintabar.commons.exceptions.tableunit.InvalidTableUnitException;
import com.pintabar.commons.exceptions.user.InvalidUserException;
import com.pintabar.commons.exceptions.user.UserWithOpenedOrderException;
import com.pintabar.purchaseflow.model.dto.PurchaseOrderDTO;
import com.pintabar.purchaseflow.model.dtomapper.PurchaseOrderDTOMapper;
import com.pintabar.purchaseflow.model.entity.PurchaseOrder;
import com.pintabar.purchaseflow.model.entity.PurchaseOrderDetail;
import com.pintabar.purchaseflow.model.entity.PurchaseOrderStatus;
import com.pintabar.purchaseflow.service.PurchaseFlowService;
import com.pintabar.purchaseflow.service.repository.PurchaseOrderRepository;
import com.pintabar.purchaseflow.service.validation.ValidationHandler;
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
	private final ValidationHandler validationHandler;

	public PurchaseFlowServiceImpl(PurchaseOrderRepository purchaseOrderRepository,
								   PurchaseOrderDTOMapper purchaseOrderDTOMapper, ValidationHandler validationHandler) {
		this.purchaseOrderRepository = purchaseOrderRepository;
		this.purchaseOrderDTOMapper = purchaseOrderDTOMapper;
		this.validationHandler = validationHandler;
	}


	@Override
	@Transactional
	public Optional<PurchaseOrderDTO> createPurchaseOrder(String userUuid, String businessUuid, String tableUnitUuid)
			throws DataNotFoundException, UserWithOpenedOrderException, InvalidUserException, InvalidTableUnitException {
		validationHandler.validateBeforePurchaseOrderCreation(userUuid, businessUuid, tableUnitUuid);
		PurchaseOrder purchaseOrder = createNewEmptyPurchaseOrder(userUuid, tableUnitUuid);
		return purchaseOrderDTOMapper.mapToDTO(purchaseOrder);
	}

	@Override
	@Transactional
	public Optional<PurchaseOrderDTO> addItemsToPurchaseOrder(String purchaseOrderUuid, Map<String, BigDecimal> purchaseOrderLinesMap)
			throws InvalidPurchaseOrderException, ClosedPurchaseOrderException, DataNotFoundException, InvalidUserException {
		PurchaseOrder purchaseOrder = purchaseOrderRepository.findByUuid(purchaseOrderUuid)
				.orElseThrow(() -> new DataNotFoundException(ErrorCode.PURCHASE_ORDER_NOT_FOUND));
		validationHandler.validateBeforeAddingItemsToPurchaseOrder(purchaseOrder);
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
		validationHandler.validateBeforeCheckingPurchaseOrderOut(purchaseOrder);
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
}
