package org.fornever.odata.jdbc.v2;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;

public class ODataDriverTests {

	private String connectionURL = "jdbc:odata://user:pass@services.odata.org:443/V2/Northwind/Northwind.svc?useHttp=false";

	@Test
	public void testConnect() throws SQLException, ClassNotFoundException {
		Class.forName(ODataDriver.class.getName());
		Connection conn = DriverManager.getConnection(connectionURL);
		assertNotNull(conn);

	}

	@Test
	public void testAcceptsURL() throws ClassNotFoundException, SQLException {
		Class.forName(ODataDriver.class.getName());
		assertTrue(DriverManager.getDriver(connectionURL) instanceof ODataDriver);
	}

}
