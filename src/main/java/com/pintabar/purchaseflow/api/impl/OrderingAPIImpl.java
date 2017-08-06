package com.pintabar.purchaseflow.api.impl;

import com.pintabar.commons.exceptions.AppException;
import com.pintabar.commons.exceptions.ErrorCode;
import com.pintabar.purchaseflow.dto.PurchaseOrderDTO;
import com.pintabar.purchaseflow.services.PurchaseFlowService;
import com.pintabar.purchaseflow.ws.OrderingWS;
import com.pintabar.purchaseflow.ws.PurchaseOrderResumeWS;
import com.pintabar.purchaseflow.api.OrderingAPI;

import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by lucasgodoy on 13/06/17.
 */
@Component
public class OrderingAPIImpl implements OrderingAPI {

	private final PurchaseFlowService purchaseFlowService;

	public OrderingAPIImpl(PurchaseFlowService purchaseFlowService) {
		this.purchaseFlowService = purchaseFlowService;
	}

	public Response userCheckin(String userUuid, String tableUnitUuid, UriInfo uriInfo) throws AppException {
		PurchaseOrderDTO purchaseOrder = purchaseFlowService.checkInUserToTable(userUuid, tableUnitUuid)
				.orElseThrow(() -> new AppException(ErrorCode.INTERNAL_ERROR));
		return Response.status(Response.Status.CREATED)
				.entity(purchaseOrder)
				.build();
	}

	public Response addMenuItemInstancesToPurchaseOrder(String purchaseOrderUuid, OrderingWS orderingWS)
			throws AppException {
		PurchaseOrderDTO purchaseOrderDTO =	purchaseFlowService.addItemsToPurchaseOrder(purchaseOrderUuid, orderingWS);
		return Response.status(Response.Status.OK)
				.entity(purchaseOrderDTO)
				.build();
	}

	@Override
	public Response checkoutPurchaseOrder(String purchaseOrderUuid) throws AppException {
		PurchaseOrderDTO purchaseOrderDTO = purchaseFlowService.checkoutPurchaseOrder(purchaseOrderUuid)
				.orElseThrow(() -> new AppException(ErrorCode.INTERNAL_ERROR));

		PurchaseOrderResumeWS purchaseOrderResumeWS = purchaseFlowService.buildPurchaseOrderResume(purchaseOrderDTO);

		return Response.status(Response.Status.OK)
				.entity(purchaseOrderResumeWS)
				.build();	}

	public Response getMenuInstances(String businessUuid, boolean isDeleted) {
		return Response.ok(purchaseFlowService.getMenuInstances(businessUuid, isDeleted)).build();
	}
}
