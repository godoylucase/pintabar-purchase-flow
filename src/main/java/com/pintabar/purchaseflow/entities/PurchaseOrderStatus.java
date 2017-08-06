package com.pintabar.purchaseflow.entities;

import java.util.EnumSet;

/**
 * Created by lucasgodoy on 13/06/17.
 */
public enum PurchaseOrderStatus {
	OPENED, PROCESSING, READY, DELIVERED, CLOSED_CANCELLED, CLOSED_TO_BE_PAID, CLOSED_PAID;

	public static final EnumSet<PurchaseOrderStatus> ALL = EnumSet.allOf(PurchaseOrderStatus.class);

	public static final EnumSet<PurchaseOrderStatus> CLOSED_STATUSES = EnumSet.of(CLOSED_CANCELLED, CLOSED_TO_BE_PAID, CLOSED_PAID);
	public static final EnumSet<PurchaseOrderStatus> OPEN_STATUSES = EnumSet.of(OPENED, PROCESSING, READY, DELIVERED);
}

