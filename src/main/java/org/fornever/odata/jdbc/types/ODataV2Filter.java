package org.fornever.odata.jdbc.types;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ODataV2Filter {

	private Map<String, List<ODataV2FilterExpr>> filters = new LinkedHashMap<String, List<ODataV2FilterExpr>>();

	public ODataV2Filter addFilter(ODataV2FilterExpr expr) {
		if (!this.filters.containsKey(expr.getField())) {
			this.filters.put(expr.getField(), new LinkedList<ODataV2FilterExpr>());
		}
		// TODO do check here
		this.filters.get(expr.getField()).add(expr);
		return this;
	}

	public Boolean isEmpty() {
		return this.filters.isEmpty();
	}

	public String toString() {
		String rt = null;
		// TODO toString
		return rt;
	}

}
