package com.pintabar.purchaseflow.api;

import com.pintabar.commons.exceptions.general.DataNotFoundException;
import com.pintabar.commons.exceptions.general.InvalidEntityException;
import com.pintabar.commons.exceptions.purchaseorder.ClosedPurchaseOrderException;
import com.pintabar.commons.exceptions.user.UserWithOpenedOrderException;
import com.pintabar.purchaseflow.presentationlayer.ws.PurchaseOrderAddItemsWS;
import com.pintabar.purchaseflow.presentationlayer.ws.PurchaseOrderCreateWS;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * @author Lucas.Godoy on 22/07/17.
 */
@Path("/purchaseOrder")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public interface PurchaseOrderFlowAPI {

	@POST
	@Path("/user/{userUuid}/tableUnit/{tableUnitUuid}/")
	public Response createPurchaseOrder(
			PurchaseOrderCreateWS purchaseOrderCreateWS,
			@Context UriInfo uriInfo)
			throws DataNotFoundException, UserWithOpenedOrderException, InvalidEntityException;

	@PUT
	@Path("/{purchaseOrderUuid}/addMenuItemInstances")
	public Response addMenuItemInstancesToPurchaseOrder(
			@PathParam("purchaseOrderUuid") String purchaseOrderUuid,
			PurchaseOrderAddItemsWS purchaseOrderAddItemsWS)
			throws InvalidEntityException, DataNotFoundException, ClosedPurchaseOrderException;

	@POST
	@Path("/{purchaseOrderUuid}")
	public Response checkoutPurchaseOrder(
			@PathParam("purchaseOrderUuid") String purchaseOrderUuid)
			throws ClosedPurchaseOrderException, DataNotFoundException;

}
