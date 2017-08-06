package com.pintabar.purchaseflow.presentationlayer.factory;

import com.pintabar.purchaseflow.model.dto.PurchaseOrderDTO;
import com.pintabar.purchaseflow.presentationlayer.ws.PurchaseOrderResumeWS;

/**
 * @author Lucas.Godoy on 20/08/17.
 */
public interface WSObjectFactory {

	PurchaseOrderResumeWS buildPurchaseOrderResume(PurchaseOrderDTO purchaseOrderDTO);

}
