package com.pintabar.purchaseflow.presentationlayer.ws;

import com.pintabar.ws.BaseWS;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lucas.Godoy on 22/07/17.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class PurchaseOrderResumeWS implements BaseWS {
	private String purchaseOrderUuid;
	private String tableUnitUuid;
	private List<PurchaseOrderDetailWS> purchaseOrderDetailsWS = new ArrayList<>();
}
