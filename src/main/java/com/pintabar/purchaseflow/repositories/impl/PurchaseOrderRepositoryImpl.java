package com.pintabar.purchaseflow.repositories.impl;


import com.pintabar.purchaseflow.entities.QPurchaseOrder;
import com.pintabar.purchaseflow.repositories.querydsl.PurchaseOrderPredicates;
import com.pintabar.purchaseflow.entities.PurchaseOrder;
import com.pintabar.purchaseflow.entities.PurchaseOrderStatus;
import com.pintabar.purchaseflow.repositories.custom.CustomPurchaseOrderRepository;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.EnumSet;
import java.util.List;

/**
 * Created by lucasgodoy on 22/06/17.
 */
@Component
public class PurchaseOrderRepositoryImpl implements CustomPurchaseOrderRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<PurchaseOrder> findPurchaseOrdersByUserIdAndStatus(Long userId, EnumSet<PurchaseOrderStatus> purchaseOrderStatus) {
		JPAQuery<PurchaseOrder> query = new JPAQuery<PurchaseOrder>(em);
		QPurchaseOrder qPurchaseOrder = QPurchaseOrder.purchaseOrder;

		Predicate predicate = PurchaseOrderPredicates.withUserAndStatus(userId, purchaseOrderStatus);

		return query.from(qPurchaseOrder).where(predicate).fetch();
	}
}
