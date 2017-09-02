package com.pintabar.purchaseflow.service.validation;

import com.pintabar.businessmanagement.api.BusinessManagementAPI;
import com.pintabar.commons.exceptions.ErrorCode;
import com.pintabar.commons.exceptions.general.DataNotFoundException;
import com.pintabar.commons.exceptions.purchaseorder.ClosedPurchaseOrderException;
import com.pintabar.commons.exceptions.tableunit.InvalidTableUnitException;
import com.pintabar.commons.exceptions.user.InvalidUserException;
import com.pintabar.commons.exceptions.user.UserWithOpenedOrderException;
import com.pintabar.purchaseflow.model.entity.PurchaseOrder;
import com.pintabar.purchaseflow.model.entity.PurchaseOrderStatus;
import com.pintabar.purchaseflow.service.repository.PurchaseOrderRepository;
import com.pintabar.usermanagement.api.UserManagementAPI;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Lucas.Godoy on 2/09/17.
 */
@Component
public class ValidationHandler {

	private final UserManagementAPI userManagementAPIProxy;
	private final BusinessManagementAPI businessManagementAPIProxy;
	private final PurchaseOrderRepository purchaseOrderRepository;

	public ValidationHandler(UserManagementAPI userManagementAPIProxy, BusinessManagementAPI businessManagementAPIProxy,
							 PurchaseOrderRepository purchaseOrderRepository) {
		this.userManagementAPIProxy = userManagementAPIProxy;
		this.businessManagementAPIProxy = businessManagementAPIProxy;
		this.purchaseOrderRepository = purchaseOrderRepository;
	}

	public void validateBeforePurchaseOrderCreation(String userUuid, String businessUuid, String tableUnitUuid)
			throws UserWithOpenedOrderException, DataNotFoundException, InvalidUserException, InvalidTableUnitException {
		validateUser(userUuid);
		validateTableUnit(businessUuid, tableUnitUuid);
		List<PurchaseOrder> userOpenedPurchaseOrders =
				purchaseOrderRepository.findPurchaseOrdersByUserUuidAndStatus(userUuid, PurchaseOrderStatus.OPEN_STATUSES);
		if (!userOpenedPurchaseOrders.isEmpty()) {
			throw new UserWithOpenedOrderException(ErrorCode.USER_ALREADY_HAS_OPENED_ORDERS);
		}
	}

	public void validateBeforeAddingItemsToPurchaseOrder(PurchaseOrder purchaseOrder)
			throws ClosedPurchaseOrderException, DataNotFoundException, InvalidUserException {
		validateUser(purchaseOrder.getUserUuid());
		// TODO: validate user is checked in to table and business uudis
		//if (!purchaseOrder.getUuid().equals(orderingWS.getUserUuid())
		//		|| !purchaseOrder.getTableUnit().getBusiness().getUuid().equals(orderingWS.getBusinessUuid())) {
		//	throw new InvalidPurchaseOrderException(ErrorCode.PURCHASE_ORDER_INVALID_OWNER);
		//} else
		if (PurchaseOrderStatus.CLOSED_STATUSES.contains(purchaseOrder.getStatus())) {
			throw new ClosedPurchaseOrderException(ErrorCode.PURCHASE_ORDER_ALREADY_CLOSED);
		}
	}

	public void validateBeforeCheckingPurchaseOrderOut(PurchaseOrder purchaseOrder) throws ClosedPurchaseOrderException {
		if (PurchaseOrderStatus.CLOSED_STATUSES.contains(purchaseOrder.getStatus())) {
			throw new ClosedPurchaseOrderException(ErrorCode.PURCHASE_ORDER_ALREADY_CLOSED);
		}
	}

	private void validateUser(String userUuid) throws DataNotFoundException, InvalidUserException {
		if (!userManagementAPIProxy.validateUser(userUuid).readEntity(Boolean.class)) {
			throw new InvalidUserException(ErrorCode.USER_INVALID);
		}
	}

	private void validateTableUnit(String businessUuid, String tableUnitUuid) throws DataNotFoundException, InvalidTableUnitException {
		if (!businessManagementAPIProxy.validateTableUnit(businessUuid, tableUnitUuid).readEntity(Boolean.class)) {
			throw new InvalidTableUnitException(ErrorCode.TABLE_UNIT_INVALID);
		}
	}

}
