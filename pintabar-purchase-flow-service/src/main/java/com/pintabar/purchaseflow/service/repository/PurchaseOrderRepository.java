package com.pintabar.purchaseflow.service.repository;

import com.pintabar.purchaseflow.model.entity.PurchaseOrder;
import com.pintabar.purchaseflow.service.repository.custom.CustomPurchaseOrderRepository;
import com.pintabar.repositories.GenericJpaRepository;

import javax.transaction.Transactional;

/**
 * Created by lucasgodoy on 14/06/17.
 */
@Transactional
public interface PurchaseOrderRepository extends GenericJpaRepository<PurchaseOrder, Long>,
		CustomPurchaseOrderRepository {
}
