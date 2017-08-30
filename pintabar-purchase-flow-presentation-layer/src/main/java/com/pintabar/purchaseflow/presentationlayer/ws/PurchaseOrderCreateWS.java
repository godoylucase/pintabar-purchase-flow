package com.pintabar.purchaseflow.presentationlayer.ws;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Lucas.Godoy on 30/08/17.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class PurchaseOrderCreateWS {
	private String businessUuid;
	private String userUuid;
	private String tableUnitUuid;
}
