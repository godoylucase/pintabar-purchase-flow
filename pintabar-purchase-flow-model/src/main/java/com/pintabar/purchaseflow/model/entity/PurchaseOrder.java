package com.pintabar.purchaseflow.model.entity;

import com.pintabar.entities.base.UUIDBaseEntity;
import com.pintabar.purchaseflow.model.dtoentityinterface.IPurchaseOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lucasgodoy on 13/06/17.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class PurchaseOrder extends UUIDBaseEntity implements IPurchaseOrder {

	@Enumerated(EnumType.STRING)
	private PurchaseOrderStatus status = PurchaseOrderStatus.OPENED;

	@Column(name = "table_unit_uuid")
	private String tableUnitUuid;

	@Column(name = "user_uuid")
	private String userUuid;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "purchaseOrder")
	private List<PurchaseOrderDetail> details = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parentPurchaseOrder")
	private List<PurchaseOrder> subPurchaseOrders = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "parent_purchase_order_id")
	private PurchaseOrder parentPurchaseOrder;

	public PurchaseOrder(String userUuid, String tableUnitUuid, PurchaseOrder parentPurchaseOrder) {
		this.userUuid = userUuid;
		this.tableUnitUuid = tableUnitUuid;
		this.parentPurchaseOrder = parentPurchaseOrder;
	}

	public void addDetail(PurchaseOrderDetail purchaseOrderDetail) {
		this.details.add(purchaseOrderDetail);
		purchaseOrderDetail.setPurchaseOrder(this);
	}

	public void removeDetail(PurchaseOrderDetail purchaseOrderDetail) {
		this.details = this.details.stream()
				.filter(detail -> !detail.getId().equals(purchaseOrderDetail.getId()))
				.collect(Collectors.toList());
		purchaseOrderDetail.setPurchaseOrder(null);
	}
}
