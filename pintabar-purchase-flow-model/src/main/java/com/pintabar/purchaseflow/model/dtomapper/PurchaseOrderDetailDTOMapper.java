package com.pintabar.purchaseflow.model.dtomapper;

import com.pintabar.dtomappers.GenericDTOMapper;
import com.pintabar.purchaseflow.model.dto.PurchaseOrderDetailDTO;
import com.pintabar.purchaseflow.model.entity.PurchaseOrderDetail;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by lucasgodoy on 15/06/17.
 */
@Component
public class PurchaseOrderDetailDTOMapper implements GenericDTOMapper<PurchaseOrderDetail, PurchaseOrderDetailDTO> {

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Optional<PurchaseOrderDetailDTO> mapToDTO(PurchaseOrderDetail entity) {
		PurchaseOrderDetailDTO dto = null;
		if (entity != null) {
			dto = new PurchaseOrderDetailDTO();
			dto.setMenuItemInstanceUuid(entity.getMenuItemInstanceUuid());
			dto.setQuantity(entity.getQuantity());
			if (entity.getPurchaseOrder() != null) {
				dto.setPurchaseOrderUuid(entity.getPurchaseOrder().getUuid());
			}
			dto.setId(entity.getId());
			dto.setCreatedOn(entity.getCreatedOn());
			dto.setUpdatedOn(entity.getUpdatedOn());
		}
		return Optional.ofNullable(dto);
	}
}
