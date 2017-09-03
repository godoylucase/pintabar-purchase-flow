package com.pintabar.purchaseflow.app.api.impl;

import com.pintabar.commons.exceptions.general.DataNotFoundException;
import com.pintabar.commons.exceptions.general.InvalidEntityException;
import com.pintabar.commons.exceptions.purchaseorder.ClosedPurchaseOrderException;
import com.pintabar.commons.exceptions.user.UserWithOpenedOrderException;
import com.pintabar.purchaseflow.api.PurchaseOrderFlowAPI;
import com.pintabar.purchaseflow.model.dto.PurchaseOrderDTO;
import com.pintabar.purchaseflow.presentationlayer.factory.WSObjectFactory;
import com.pintabar.purchaseflow.presentationlayer.ws.PurchaseOrderAddItemsWS;
import com.pintabar.purchaseflow.presentationlayer.ws.PurchaseOrderCreateWS;
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

	public Response createPurchaseOrder(PurchaseOrderCreateWS poCreate, UriInfo uriInfo)
			throws DataNotFoundException, UserWithOpenedOrderException, InvalidEntityException {
		PurchaseOrderDTO purchaseOrder =
				purchaseFlowService.createPurchaseOrder(poCreate.getUserUuid(), poCreate.getBusinessUuid(), poCreate.getTableUnitUuid())
						.orElseThrow(IllegalStateException::new);
		return Response.status(Response.Status.CREATED)
				.entity(purchaseOrder)
				.build();
	}

	public Response addMenuItemInstancesToPurchaseOrder(String purchaseOrderUuid, PurchaseOrderAddItemsWS purchaseOrderAddItemsWS)
			throws InvalidEntityException, DataNotFoundException, ClosedPurchaseOrderException {
		PurchaseOrderDTO purchaseOrderDTO = purchaseFlowService.addItemsToPurchaseOrder(purchaseOrderUuid, purchaseOrderAddItemsWS.getPurchaseOrderLinesMap())
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

}
