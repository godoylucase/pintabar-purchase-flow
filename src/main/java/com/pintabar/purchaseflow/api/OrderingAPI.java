package com.pintabar.purchaseflow.api;

import com.pintabar.commons.exceptions.AppException;
import com.pintabar.purchaseflow.ws.OrderingWS;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * @author Lucas.Godoy on 22/07/17.
 */
@Path("/ordering")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public interface OrderingAPI {

	@POST
	@Path("/checkin/user/{userUuid}/tableUnit/{tableUnitUuid}/")
	public Response userCheckin(
			@PathParam("userUuid") String userUuid,
			@PathParam("tableUnitUuid") String tableUnitUuid,
			@Context UriInfo uriInfo) throws AppException;

	@PUT
	@Path("/purchaseOrder/{purchaseOrderUuid}/addMenuItemInstances")
	public Response addMenuItemInstancesToPurchaseOrder(
			@PathParam("purchaseOrderUuid") String purchaseOrderUuid,
			OrderingWS orderingWS) throws AppException;

	@POST
	@Path("/purchaseOrder/{purchaseOrderUuid}")
	public Response checkoutPurchaseOrder(
			@PathParam("purchaseOrderUuid") String purchaseOrderUuid) throws AppException;

	@GET
	@Path("/business/{businessUuid}/menuInstance")
	public Response getMenuInstances(
			@PathParam("businessUuid") String businessUuid,
			@DefaultValue("false") @QueryParam("isDeleted") boolean isDeleted);
}
