package com.pintabar.purchaseflow.presentationlayer.factory.impl;

import com.pintabar.purchaseflow.model.dto.PurchaseOrderDTO;
import com.pintabar.purchaseflow.presentationlayer.factory.WSObjectFactory;
import com.pintabar.purchaseflow.presentationlayer.ws.PurchaseOrderDetailWS;
import com.pintabar.purchaseflow.presentationlayer.ws.PurchaseOrderResumeWS;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lucas.Godoy on 20/08/17.
 */
@Component
public class DefaultWSObjectFactoryImpl implements WSObjectFactory {

	@Override
	public PurchaseOrderResumeWS buildPurchaseOrderResume(PurchaseOrderDTO purchaseOrderDTO) {
		PurchaseOrderResumeWS purchaseOrderResumeWS = new PurchaseOrderResumeWS();
		purchaseOrderResumeWS.setPurchaseOrderUuid(purchaseOrderDTO.getUuid());
		purchaseOrderResumeWS.setTableUnitUuid(purchaseOrderDTO.getTableUuid());
		purchaseOrderResumeWS.getPurchaseOrderDetailsWS().addAll(
				buildPurchaseOrderDetailsWS(purchaseOrderDTO));
		return purchaseOrderResumeWS;
	}

	private List<PurchaseOrderDetailWS> buildPurchaseOrderDetailsWS(PurchaseOrderDTO purchaseOrderDTO) {
		return purchaseOrderDTO.getPurchaseOrderDetails()
				.stream()
				.map(pod -> {
					PurchaseOrderDetailWS podws = new PurchaseOrderDetailWS();
					podws.setMenuItemInstanceUuid(pod.getMenuItemInstanceUuid());
					// TODO: get details from item instance to fill up this attribute
//					podws.setMenuItemInstanceName(pod.getMenuItemInstance().getMenuItem().getName());
					podws.setQuantity(pod.getQuantity());
					// TODO: get details from item instance to fill up this attribute
					//podws.setPrice(pod.getMenuItemInstance().getPrice());
					return podws;
				}).collect(Collectors.toList());
	}
}
