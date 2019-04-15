package org.fornever.odata.jdbc.types;

public class ODataV2Param {

	private Integer top;
	private Integer skip;
	private String[] orderby;
	private String search;
	private String[] expand;
	private String inlinecount;
	private String[] select;
	private ODataV2Filter filter;

	public Integer getTop() {
		return top;
	}

	public void setTop(Integer top) {
		this.top = top;
	}

	public Integer getSkip() {
		return skip;
	}

	public void setSkip(Integer skip) {
		this.skip = skip;
	}

	public String[] getOrderby() {
		return orderby;
	}

	public void setOrderby(String[] orderby) {
		this.orderby = orderby;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String[] getExpand() {
		return expand;
	}

	public void setExpand(String[] expand) {
		this.expand = expand;
	}

	public String getInlinecount() {
		return inlinecount;
	}

	public void setInlinecount(String inlinecount) {
		this.inlinecount = inlinecount;
	}

	public String[] getSelect() {
		return select;
	}

	public void setSelect(String[] select) {
		this.select = select;
	}

	public ODataV2Filter getFilter() {
		return filter;
	}

	public void setFilter(ODataV2Filter filter) {
		this.filter = filter;
	}

}
