package com.pintabar.purchaseflow.ws;

import com.google.common.collect.Maps;
import com.pintabar.ws.BaseWS;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by lucasgodoy on 23/06/17.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class OrderingWS implements BaseWS {
	private String businessUuid;
	private String userUuid;
	private Map<String, BigDecimal> purchaseOrderLinesMap = Maps.newHashMap();
}
