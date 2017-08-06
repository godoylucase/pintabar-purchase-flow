package com.pintabar.purchaseflow.presentationlayer.ws;

import com.pintabar.ws.BaseWS;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

/**
 * @author Lucas.Godoy on 22/07/17.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class PurchaseOrderDetailWS implements BaseWS {
	private String menuItemInstanceUuid;
	private String menuItemInstanceName;
	private BigDecimal quantity = BigDecimal.ZERO;
	private BigDecimal price = BigDecimal.ZERO;
}
