package org.fornever.odata.jdbc.v2;

import java.util.List;

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ODataClientTests {

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

}
