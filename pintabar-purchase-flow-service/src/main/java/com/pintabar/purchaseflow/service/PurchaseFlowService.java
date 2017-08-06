package com.pintabar.purchaseflow.service;


import com.pintabar.commons.exceptions.general.DataNotFoundException;
import com.pintabar.commons.exceptions.purchaseorder.ClosedPurchaseOrderException;
import com.pintabar.commons.exceptions.purchaseorder.InvalidPurchaseOrderException;
import com.pintabar.commons.exceptions.user.InvalidUserException;
import com.pintabar.commons.exceptions.user.UserWithOpenedOrderException;
import com.pintabar.purchaseflow.model.dto.PurchaseOrderDTO;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

/**
 * Created by lucasgodoy on 14/06/17.
 */
public interface PurchaseFlowService {

	Optional<PurchaseOrderDTO> createPurchaseOrder(String userUuid, String tableUnitUuid)
			throws DataNotFoundException, UserWithOpenedOrderException, InvalidUserException;

//	List<MenuInstanceDTO> getMenuInstances(String businessUuid);
//
//	List<MenuInstanceDTO> getMenuInstances(String businessUuid, Boolean isDeleted);

	Optional<PurchaseOrderDTO> addItemsToPurchaseOrder(String purchaseOrderUuid, Map<String, BigDecimal> purchaseOrderLinesMap)
			throws InvalidPurchaseOrderException, ClosedPurchaseOrderException, DataNotFoundException;

	Optional<PurchaseOrderDTO> checkoutPurchaseOrder(String purchaseOrderUuid) throws DataNotFoundException, ClosedPurchaseOrderException;

}
