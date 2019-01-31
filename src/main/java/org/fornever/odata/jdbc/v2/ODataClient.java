package org.fornever.odata.jdbc.v2;

import java.io.InputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.ep.EntityProviderException;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;

/**
 * OData V2 Client
 * 
 * @author Theo Sun <theo.sun@outlook.com>
 *
 */
public class ODataClient {

	public static final String APPLICATION_JSON = "application/json";
	public static final String APPLICATION_XML = "application/xml";
	public static final String APPLICATION_ATOM_XML = "application/atom+xml";
	public static final String METADATA = "$metadata";
	public static final String SEPARATOR = "/";
	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String CSRF_TOKEN_HEADER = "X-CSRF-Token";
	public static final String CSRF_TOKEN_FETCH = "Fetch";

	private String serviceRoot;

	private String csrfToken = "fetch";

	private String username;

	private String password;

	private Edm serviceEdm;

	public ODataClient(String serviceRoot, String username, String password)
			throws EntityProviderException, UnirestException {
		super();
		this.serviceRoot = serviceRoot;
		this.username = username;
		this.password = password;
		this.retriveEdm(serviceRoot);
	}

	private GetRequest get(String uri) {
		return Unirest.get(uri).header(CSRF_TOKEN_HEADER, csrfToken).header(AUTHORIZATION_HEADER,
				getAuthorizationHeader());
	}

	public ODataClient(String serviceRoot) throws EntityProviderException, UnirestException {
		super();
		this.serviceRoot = serviceRoot;
		this.retriveEdm(serviceRoot);
	}

	private String getAuthorizationHeader() {
		if (this.username == null || this.username.isEmpty()) {
			return "";
		}
		String temp = new StringBuilder(this.username).append(":").append(this.password).toString();
		String result = "Basic " + new String(Base64.encodeBase64(temp.getBytes()));
		return result;
	}

	public String getPassword() {
		return password;
	}

	public Edm getServiceEdm() {
		return serviceEdm;
	}

	public String getServiceUri() {
		return serviceRoot;
	}

	public String getUsername() {
		return username;
	}

	private void retriveEdm(String serviceUri) throws UnirestException, EntityProviderException {
		HttpResponse<InputStream> response = get(serviceRoot + SEPARATOR + METADATA).asBinary();
		if (response.getHeaders().containsKey(CSRF_TOKEN_HEADER)) {
			String csrf = response.getHeaders().getFirst(CSRF_TOKEN_HEADER);
			if (!csrf.equals("Required") && !csrf.isEmpty()) {
				this.csrfToken = csrf;
			}
		}
		this.serviceEdm = EntityProvider.readMetadata(response.getRawBody(), true);
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setServiceEdm(Edm serviceEdm) {
		this.serviceEdm = serviceEdm;
	}

	public void setServiceUri(String serviceUri) {
		this.serviceRoot = serviceUri;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
