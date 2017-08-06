package com.pintabar.purchaseflow.repositories;

import com.pintabar.purchaseflow.repositories.custom.CustomPurchaseOrderRepository;
import com.pintabar.purchaseflow.entities.PurchaseOrder;
import com.pintabar.repositories.GenericJpaRepository;

import javax.transaction.Transactional;

/**
 * Created by lucasgodoy on 14/06/17.
 */
@Transactional
public interface PurchaseOrderRepository extends GenericJpaRepository<PurchaseOrder, Long>,
		CustomPurchaseOrderRepository {
}
