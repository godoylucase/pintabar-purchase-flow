package com.pintabar.purchaseflow.dtomappers;

import com.pintabar.purchaseflow.dto.PurchaseOrderDTO;
import com.pintabar.dtomappers.GenericDTOMapper;
import com.pintabar.purchaseflow.entities.PurchaseOrder;
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
			if (entity.getUser() != null) {
				dto.setUserUuid(entity.getUser().getUuid());
			}
			if (entity.getTableUnit() != null) {
				dto.setTableUuid(entity.getTableUnit().getUuid());
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
