package com.pintabar.purchaseflow.app.api.impl;

import com.pintabar.commons.exceptions.general.DataNotFoundException;
import com.pintabar.commons.exceptions.purchaseorder.ClosedPurchaseOrderException;
import com.pintabar.commons.exceptions.purchaseorder.InvalidPurchaseOrderException;
import com.pintabar.commons.exceptions.user.InvalidUserException;
import com.pintabar.commons.exceptions.user.UserWithOpenedOrderException;
import com.pintabar.purchaseflow.api.PurchaseOrderFlowAPI;
import com.pintabar.purchaseflow.model.dto.PurchaseOrderDTO;
import com.pintabar.purchaseflow.presentationlayer.factory.WSObjectFactory;
import com.pintabar.purchaseflow.presentationlayer.ws.OrderingWS;
import com.pintabar.purchaseflow.presentationlayer.ws.PurchaseOrderResumeWS;
import com.pintabar.purchaseflow.service.PurchaseFlowService;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by lucasgodoy on 13/06/17.
 */
@Component
public class PurchaseOrderFlowAPIImpl implements PurchaseOrderFlowAPI {

	private final PurchaseFlowService purchaseFlowService;
	private final WSObjectFactory wsObjectFactory;

	public PurchaseOrderFlowAPIImpl(PurchaseFlowService purchaseFlowService, WSObjectFactory wsObjectFactory) {
		this.purchaseFlowService = purchaseFlowService;
		this.wsObjectFactory = wsObjectFactory;
	}

	public Response createPurchaseOrder(String userUuid, String tableUnitUuid, UriInfo uriInfo)
			throws DataNotFoundException, UserWithOpenedOrderException, InvalidUserException {
		PurchaseOrderDTO purchaseOrder = purchaseFlowService.createPurchaseOrder(userUuid, tableUnitUuid)
				.orElseThrow(IllegalStateException::new);
		return Response.status(Response.Status.CREATED)
				.entity(purchaseOrder)
				.build();
	}

	public Response addMenuItemInstancesToPurchaseOrder(String purchaseOrderUuid, OrderingWS orderingWS)
			throws InvalidPurchaseOrderException, DataNotFoundException, ClosedPurchaseOrderException {
		PurchaseOrderDTO purchaseOrderDTO = purchaseFlowService.addItemsToPurchaseOrder(purchaseOrderUuid, orderingWS.getPurchaseOrderLinesMap())
				.orElseThrow(IllegalStateException::new);
		return Response.status(Response.Status.OK)
				.entity(purchaseOrderDTO)
				.build();
	}

	@Override
	public Response checkoutPurchaseOrder(String purchaseOrderUuid)
			throws ClosedPurchaseOrderException, DataNotFoundException {
		PurchaseOrderDTO purchaseOrderDTO = purchaseFlowService.checkoutPurchaseOrder(purchaseOrderUuid)
				.orElseThrow(IllegalStateException::new);
		PurchaseOrderResumeWS purchaseOrderResumeWS = wsObjectFactory.buildPurchaseOrderResume(purchaseOrderDTO);
		return Response.status(Response.Status.OK)
				.entity(purchaseOrderResumeWS)
				.build();
	}

//	public Response getMenuInstances(String businessUuid, boolean isDeleted) {
//		return Response.ok(purchaseFlowService.getMenuInstances(businessUuid, isDeleted)).build();
//	}
}
