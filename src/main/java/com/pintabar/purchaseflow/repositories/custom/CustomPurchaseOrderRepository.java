package com.pintabar.purchaseflow.repositories.custom;


import com.pintabar.purchaseflow.entities.PurchaseOrder;
import com.pintabar.purchaseflow.entities.PurchaseOrderStatus;

import java.util.EnumSet;
import java.util.List;

/**
 * Created by lucasgodoy on 22/06/17.
 */
public interface CustomPurchaseOrderRepository {

	List<PurchaseOrder> findPurchaseOrdersByUserIdAndStatus(Long userId, EnumSet<PurchaseOrderStatus> purchaseOrderStatus);

}
