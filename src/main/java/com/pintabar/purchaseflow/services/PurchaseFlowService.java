package com.pintabar.purchaseflow.services;


import com.pintabar.dto.MenuInstanceDTO;
import com.pintabar.purchaseflow.dto.PurchaseOrderDTO;
import com.pintabar.entities.TableUnit;
import com.pintabar.entities.user.User;
import com.pintabar.commons.exceptions.general.DataNotFoundException;
import com.pintabar.commons.exceptions.purchaseorder.ClosedPurchaseOrderException;
import com.pintabar.commons.exceptions.purchaseorder.InvalidPurchaseOrderException;
import com.pintabar.commons.exceptions.user.UserWithOpenedOrderException;
import com.pintabar.purchaseflow.ws.OrderingWS;
import com.pintabar.purchaseflow.ws.PurchaseOrderResumeWS;

import java.util.List;
import java.util.Optional;

/**
 * Created by lucasgodoy on 14/06/17.
 */
public interface PurchaseFlowService {

	Optional<PurchaseOrderDTO> checkInUserToTable(String userUuid, String tableUnitUuid)
			throws DataNotFoundException, UserWithOpenedOrderException;

	Optional<PurchaseOrderDTO> checkInUserToTable(User user, TableUnit tableDTO)
			throws UserWithOpenedOrderException;

	List<MenuInstanceDTO> getMenuInstances(String businessUuid);

	List<MenuInstanceDTO> getMenuInstances(String businessUuid, Boolean isDeleted);

	PurchaseOrderDTO addItemsToPurchaseOrder(String purchaseOrderUuid, OrderingWS orderingWS)
			throws InvalidPurchaseOrderException, ClosedPurchaseOrderException, DataNotFoundException;

	Optional<PurchaseOrderDTO> checkoutPurchaseOrder(String purchaseOrderUuid) throws DataNotFoundException, ClosedPurchaseOrderException;

	PurchaseOrderResumeWS buildPurchaseOrderResume(PurchaseOrderDTO purchaseOrderDTO);
}
