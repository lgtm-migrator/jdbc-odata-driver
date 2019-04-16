package org.fornever.odata.jdbc.types;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * OData V2 Query Parameter
 *
 */
public class ODataV2Param {

	private Integer top = 0;
	private Integer skip = 0;
	private String search = "";
	private List<ODataV2OrderBy> orderby = new LinkedList<>();
	private List<String> expand = new LinkedList<String>();
	private String inlinecount = "";
	private List<String> select = new LinkedList<String>();
	private ODataV2Filter filter = new ODataV2Filter();

	public Map<String, Object> toMap() {
		Map<String, Object> rt = new HashMap<String, Object>();

		if (this.top > 0) {
			rt.put("$top", this.top);
		}

		if (this.skip > 0) {
			rt.put("$skip", this.skip);
		}

		if (this.search != null && !this.search.isEmpty()) {
			rt.put("$search", this.search);
		}

		if (!this.filter.isEmpty()) {
			rt.put("$filter", this.filter.toString());
		}

		if (this.inlinecount != null && !this.inlinecount.isEmpty()) {
			rt.put("$inlinecount", inlinecount);
		}

		if (!this.expand.isEmpty()) {
			rt.put("$expand", String.join(",", this.expand));
		}

		if (!this.select.isEmpty()) {
			rt.put("$select", String.join(",", this.select));
		}

		if (!this.orderby.isEmpty()) {
			List<String> os = new LinkedList<String>();
			for (ODataV2OrderBy o : this.orderby) {
				os.add(o.toString());
			}
			rt.put("$orderby", String.join(",", os));

		}

		return rt;
	}

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

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getInlinecount() {
		return inlinecount;
	}

	public void setInlinecount(String inlinecount) {
		this.inlinecount = inlinecount;
	}

	public List<String> getExpand() {
		return expand;
	}

	public void setExpand(List<String> expand) {
		this.expand = expand;
	}

	public List<String> getSelect() {
		return select;
	}

	public void setSelect(List<String> select) {
		this.select = select;
	}

	public ODataV2Filter getFilter() {
		return filter;
	}

	public void setFilter(ODataV2Filter filter) {
		this.filter = filter;
	}

	public List<ODataV2OrderBy> getOrderby() {
		return orderby;
	}

	public void setOrderby(List<ODataV2OrderBy> orderby) {
		this.orderby = orderby;
	}

}
