package com.pintabar.purchaseflow.dto;

import com.pintabar.dto.BaseDTO;
import com.pintabar.dto.MenuItemInstanceDTO;
import com.pintabar.purchaseflow.dtoentityinterfaces.IPurchaseOrderDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

/**
 * Created by lucasgodoy on 14/06/17.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlRootElement
public class PurchaseOrderDetailDTO extends BaseDTO implements IPurchaseOrderDetail {
	private BigDecimal quantity = BigDecimal.ZERO;
	private String purchaseOrderUuid;
	private String userUuid;
	private MenuItemInstanceDTO menuItemInstance;
}
