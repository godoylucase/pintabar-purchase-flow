package com.pintabar.purchaseflow.api;

import com.pintabar.commons.exceptions.AppException;
import com.pintabar.commons.exceptions.general.DataNotFoundException;
import com.pintabar.commons.exceptions.purchaseorder.ClosedPurchaseOrderException;
import com.pintabar.commons.exceptions.purchaseorder.InvalidPurchaseOrderException;
import com.pintabar.commons.exceptions.user.InvalidUserException;
import com.pintabar.commons.exceptions.user.UserWithOpenedOrderException;
import com.pintabar.purchaseflow.presentationlayer.ws.OrderingWS;

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
			@PathParam("userUuid") String userUuid,
			@PathParam("tableUnitUuid") String tableUnitUuid,
			@Context UriInfo uriInfo) throws DataNotFoundException, UserWithOpenedOrderException, InvalidUserException;

	@PUT
	@Path("/{purchaseOrderUuid}/addMenuItemInstances")
	public Response addMenuItemInstancesToPurchaseOrder(
			@PathParam("purchaseOrderUuid") String purchaseOrderUuid,
			OrderingWS orderingWS) throws InvalidPurchaseOrderException, DataNotFoundException, ClosedPurchaseOrderException;

	@POST
	@Path("/{purchaseOrderUuid}")
	public Response checkoutPurchaseOrder(
			@PathParam("purchaseOrderUuid") String purchaseOrderUuid) throws ClosedPurchaseOrderException, DataNotFoundException;

//	@GET
//	@Path("/business/{businessUuid}/menuInstance")
//	public Response getMenuInstances(
//			@PathParam("businessUuid") String businessUuid,
//			@DefaultValue("false") @QueryParam("isDeleted") boolean isDeleted);
}
