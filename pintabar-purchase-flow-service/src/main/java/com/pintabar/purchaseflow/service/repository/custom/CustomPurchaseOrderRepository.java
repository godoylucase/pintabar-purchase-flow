package com.pintabar.purchaseflow.service.repository.custom;


import com.pintabar.purchaseflow.model.entity.PurchaseOrder;
import com.pintabar.purchaseflow.model.entity.PurchaseOrderStatus;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

/**
 * Created by lucasgodoy on 22/06/17.
 */
public interface CustomPurchaseOrderRepository {

	List<PurchaseOrder> findPurchaseOrdersByUserUuidAndStatus(String userUuid, EnumSet<PurchaseOrderStatus> purchaseOrderStatus);

	Optional<PurchaseOrder> findParentPurchaseOrderByTableUuid(String tableUuid);
}
