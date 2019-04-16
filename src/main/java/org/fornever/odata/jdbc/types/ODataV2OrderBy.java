package org.fornever.odata.jdbc.types;

import org.fornever.odata.jdbc.types.enums.ODataAscOrDesc;

public class ODataV2OrderBy {

	private String field;
	private ODataAscOrDesc order = ODataAscOrDesc.DESC;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public ODataV2OrderBy(String field) {
		this(field, ODataAscOrDesc.DESC);
	}

	public ODataAscOrDesc getOrder() {
		return order;
	}

	public void setOrder(ODataAscOrDesc order) {
		this.order = order;
	}

	public ODataV2OrderBy(String field, ODataAscOrDesc order) {
		super();
		this.field = field;
		this.order = order;
	}

	public String toString() {
		return String.format("%s %s", this.field, this.order);
	}

}
