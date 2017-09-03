package com.pintabar.purchaseflow.service;


import com.pintabar.commons.exceptions.general.DataNotFoundException;
import com.pintabar.commons.exceptions.general.InvalidEntityException;
import com.pintabar.commons.exceptions.purchaseorder.ClosedPurchaseOrderException;
import com.pintabar.commons.exceptions.user.UserWithOpenedOrderException;
import com.pintabar.purchaseflow.model.dto.PurchaseOrderDTO;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

/**
 * Created by lucasgodoy on 14/06/17.
 */
public interface PurchaseFlowService {

	Optional<PurchaseOrderDTO> createPurchaseOrder(String userUuid, String businessUuid, String tableUnitUuid)
			throws DataNotFoundException, UserWithOpenedOrderException, InvalidEntityException;

	Optional<PurchaseOrderDTO> addItemsToPurchaseOrder(String purchaseOrderUuid, Map<String, BigDecimal> purchaseOrderLinesMap)
			throws InvalidEntityException, ClosedPurchaseOrderException, DataNotFoundException;

	Optional<PurchaseOrderDTO> checkoutPurchaseOrder(String purchaseOrderUuid) throws DataNotFoundException, ClosedPurchaseOrderException;

}
