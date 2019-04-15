package org.fornever.odata.jdbc.v2;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.olingo.odata2.api.ep.EntityProviderException;

import kong.unirest.UnirestException;

public class ODataDriver implements Driver {

	/**
	 * schema/protocol
	 */
	private static String schema = "jdbc:odata";

	static {
		try {
			java.sql.DriverManager.registerDriver(new ODataDriver());
		} catch (SQLException E) {
			throw new RuntimeException("Can't register driver!");
		}
	}

	/**
	 * parse query string
	 * 
	 * @param query string
	 * @return
	 */
	private Map<String, String> parseQueryParam(String query) {
		Pattern pat = Pattern.compile("([^&=]+)=([^&]*)");
		Matcher matcher = pat.matcher(query);
		Map<String, String> map = new HashMap<>();
		while (matcher.find()) {
			map.put(matcher.group(1), matcher.group(2));
		}
		return map;
	}

	@Override
	public Connection connect(String url, Properties info) throws SQLException {
		// remove prefix 'jdbc:'
		URI u = URI.create(url.substring(5));
		String user = "";
		String password = "";
		if (u.getUserInfo() != null) {
			String userInfo = u.getUserInfo();
			if (!userInfo.isEmpty()) {
				String[] parts = userInfo.split(":");
				switch (parts.length) {
				case 1:
					user = parts[0];
					break;
				case 2:
					user = parts[0];
					password = parts[1];
					break;
				default:
					throw new SQLException(String
							.format("Provide user information but more than one ':' in the string '%s'", userInfo));
				}
			}

		}

		Map<String, String> query = this.parseQueryParam(u.getQuery());
		Integer port = u.getPort();

		String endpoint = String.format("%s%s%s%s",
				query.getOrDefault("useHttp", "false").equals("true") ? "http://" : "https://", u.getHost(),
				port > 0 ? String.format(":%d", port) : "", u.getPath());

		try {
			ODataClient client = new ODataClient(endpoint, user, password);
			return new ODataConnection(client);
		} catch (EntityProviderException e) {
			throw new SQLException(e);
		} catch (UnirestException e) {
			throw new SQLException(e);
		}
	}

	@Override
	public boolean acceptsURL(String url) throws SQLException {
		if (url != null && url.startsWith(schema)) {
			return true;
		}
		return false;
	}

	@Override
	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMajorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMinorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean jdbcCompliant() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

}
