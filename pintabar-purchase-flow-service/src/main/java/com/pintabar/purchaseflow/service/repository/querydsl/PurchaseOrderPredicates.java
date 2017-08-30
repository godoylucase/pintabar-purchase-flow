package com.pintabar.purchaseflow.service.repository.querydsl;

import com.pintabar.purchaseflow.model.entity.PurchaseOrderStatus;
import com.pintabar.purchaseflow.model.entity.QPurchaseOrder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.EnumSet;

/**
 * Created by lucasgodoy on 22/06/17.
 */
public class PurchaseOrderPredicates {

	public static Predicate withUserAndStatus(String userUuid, EnumSet<PurchaseOrderStatus> purchaseOrderStatus) {
		QPurchaseOrder qPurchaseOrder = QPurchaseOrder.purchaseOrder;

		BooleanExpression predicate = qPurchaseOrder.userUuid.eq(userUuid);

		if (purchaseOrderStatus.size() > 1) {
			predicate = predicate.and(qPurchaseOrder.status.in(purchaseOrderStatus));
		} else {
			predicate = predicate.and(qPurchaseOrder.status.eq(purchaseOrderStatus.iterator().next()));
		}

		return predicate;
	}

	public static Predicate parentFromTableUnit(String tableUuid) {
		QPurchaseOrder qPurchaseOrder = QPurchaseOrder.purchaseOrder;

		return qPurchaseOrder.tableUnitUuid.eq(tableUuid)
				.and(qPurchaseOrder.parentPurchaseOrder.isNull());
	}
}
