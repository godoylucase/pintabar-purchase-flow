package com.pintabar.purchaseflow.repositories.querydsl;

import com.pintabar.purchaseflow.entities.PurchaseOrderStatus;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.EnumSet;

/**
 * Created by lucasgodoy on 22/06/17.
 */
public class PurchaseOrderPredicates {

	public static Predicate withUserAndStatus(Long userId, EnumSet<PurchaseOrderStatus> purchaseOrderStatus) {
//		QPurchaseOrder qPurchaseOrder = QPurchaseOrder.purchaseOrder;
//
//		BooleanExpression predicate = qPurchaseOrder.user.id.eq(userId);
//
//		if (purchaseOrderStatus.size() > 1) {
//			predicate = predicate.and(qPurchaseOrder.status.in(purchaseOrderStatus));
//		} else {
//			predicate = predicate.and(qPurchaseOrder.status.eq(purchaseOrderStatus.iterator().next()));
//		}
//
//		return predicate;
		return null;
	}
}
