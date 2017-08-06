package com.pintabar.purchaseflow.model.dtomapper;

import com.pintabar.dtomappers.GenericDTOMapper;
import com.pintabar.purchaseflow.model.dto.PurchaseOrderDTO;
import com.pintabar.purchaseflow.model.entity.PurchaseOrder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by lucasgodoy on 14/06/17.
 */
@Component
public class PurchaseOrderDTOMapper implements GenericDTOMapper<PurchaseOrder, PurchaseOrderDTO> {

	private final PurchaseOrderDetailDTOMapper purchaseOrderDetailDTOMapper;

	public PurchaseOrderDTOMapper(PurchaseOrderDetailDTOMapper purchaseOrderDetailDTOMapper) {
		this.purchaseOrderDetailDTOMapper = purchaseOrderDetailDTOMapper;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Optional<PurchaseOrderDTO> mapToDTO(@Nullable PurchaseOrder entity) {
		PurchaseOrderDTO dto = null;
		if (entity != null) {
			dto = new PurchaseOrderDTO();
			dto.setUserUuid(entity.getUserUuid());
			dto.setTableUuid(entity.getTableUnitUuid());
			if (entity.getParentPurchaseOrder() != null) {
				dto.setParentPurchaseOrderUuid(entity.getParentPurchaseOrder().getUuid());
			}
			if (!entity.getSubPurchaseOrders().isEmpty()) {
				dto.getSubPurchaseOrders().addAll(
						entity.getSubPurchaseOrders()
								.stream()
								.map(subPO -> mapToDTO(subPO).orElse(null))
								.collect(Collectors.toList())
				);
			}
			dto.getPurchaseOrderDetails().addAll(
					entity.getDetails()
							.stream()
							.map(detail -> purchaseOrderDetailDTOMapper.mapToDTO(detail).orElse(null))
							.collect(Collectors.toList())
			);
			dto.setStatus(entity.getStatus());
			dto.setUuid(entity.getUuid());
			dto.setCreatedOn(entity.getCreatedOn());
			dto.setUpdatedOn(entity.getUpdatedOn());
		}
		return Optional.ofNullable(dto);
	}
}
