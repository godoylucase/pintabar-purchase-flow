package com.pintabar.purchaseflow.model.dto;

import com.pintabar.dto.BaseDTO;
import com.pintabar.purchaseflow.model.dtoentityinterface.IPurchaseOrder;
import com.pintabar.purchaseflow.model.entity.PurchaseOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucasgodoy on 14/06/17.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlRootElement
public class PurchaseOrderDTO extends BaseDTO implements IPurchaseOrder {
	private PurchaseOrderStatus status = PurchaseOrderStatus.OPENED;
	private String tableUuid;
	private String userUuid;
	private List<PurchaseOrderDetailDTO> purchaseOrderDetails = new ArrayList<>();
	private List<PurchaseOrderDTO> subPurchaseOrders = new ArrayList<>();
	private String parentPurchaseOrderUuid;
}
