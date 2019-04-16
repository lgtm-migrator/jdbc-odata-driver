package org.fornever.odata.jdbc.v2;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.fornever.odata.jdbc.types.ODataV2Param;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import kong.unirest.JsonNode;

public class ODataClientTests {

	private static final String INVOICES = "Invoices";
	private ODataClient client;

	@Before
	public void setUp() throws Exception {
		this.client = new ODataClient("https://services.odata.org/V2/Northwind/Northwind.svc");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEdm() throws EdmException {
		Edm edm = this.client.getServiceEdm();
		List<EdmEntitySet> entities = edm.getEntitySets();
		assert entities.size() > 0;
		EdmEntityType entityType = entities.get(0).getEntityType();
		assert entityType != null;
	}

	@Test
	public void testSinpleFetchData() {
		
		JsonNode data = this.client.fetchData(INVOICES, new ODataV2Param());
		
		assertTrue(data.getObject().getJSONObject("d").getJSONArray("results").length() > 0);
		
	}
	
	@Test
	public void testComplexFetchData() {
		
		ODataV2Param param = new ODataV2Param();
		
		param.setInlinecount("allpages");
		param.setTop(10);
		
		JsonNode data = this.client.fetchData(INVOICES, param);
		
		assertTrue(data.getObject().getJSONObject("d").getJSONArray("results").length() == 10);
		
		
	}

}
