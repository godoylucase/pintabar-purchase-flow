package com.pintabar.purchaseflow.entities;

import com.pintabar.entities.TableUnit;
import com.pintabar.entities.base.UUIDBaseEntity;
import com.pintabar.entities.user.User;
import com.pintabar.purchaseflow.dtoentityinterfaces.IPurchaseOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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

	@OneToOne
	@JoinColumn(name = "table_unit_id")
	private TableUnit tableUnit;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "purchaseOrder")
	private List<PurchaseOrderDetail> details = new ArrayList<>();

	public PurchaseOrder(User user, TableUnit tableUnit){
		this.user = user;
		this.tableUnit = tableUnit;
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
