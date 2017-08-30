package com.pintabar.purchaseflow.service.repository.impl;


import com.pintabar.purchaseflow.model.entity.PurchaseOrder;
import com.pintabar.purchaseflow.model.entity.PurchaseOrderStatus;
import com.pintabar.purchaseflow.model.entity.QPurchaseOrder;
import com.pintabar.purchaseflow.service.repository.custom.CustomPurchaseOrderRepository;
import com.pintabar.purchaseflow.service.repository.querydsl.PurchaseOrderPredicates;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

/**
 * Created by lucasgodoy on 22/06/17.
 */
@Component
public class PurchaseOrderRepositoryImpl implements CustomPurchaseOrderRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<PurchaseOrder> findPurchaseOrdersByUserUuidAndStatus(String userUuid, EnumSet<PurchaseOrderStatus> purchaseOrderStatus) {
		JPAQuery<PurchaseOrder> query = new JPAQuery<PurchaseOrder>(em);
		QPurchaseOrder qPurchaseOrder = QPurchaseOrder.purchaseOrder;

		Predicate predicate = PurchaseOrderPredicates.withUserAndStatus(userUuid, purchaseOrderStatus);

		return query.from(qPurchaseOrder).where(predicate).fetch();
	}

	@Override
	public Optional<PurchaseOrder> findParentPurchaseOrderByTableUuid(String tableUuid) {
		JPAQuery<PurchaseOrder> query = new JPAQuery<>(em);
		QPurchaseOrder qPurchaseOrder = QPurchaseOrder.purchaseOrder;

		Predicate predicate = PurchaseOrderPredicates.parentFromTableUnit(tableUuid);

		return Optional.ofNullable(query.from(qPurchaseOrder).where(predicate).fetchOne());
	}
}
