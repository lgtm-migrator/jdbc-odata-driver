package org.fornever.odata.jdbc.types;

public class ODataV2FilterExpr {

	private String field;
	private String expr;
	private String value;

	public ODataV2FilterExpr(String field, String expr, String value) {
		super();
		this.field = field;
		this.expr = expr;
		this.value = value;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getExpr() {
		return expr;
	}

	public void setExpr(String expr) {
		this.expr = expr;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
