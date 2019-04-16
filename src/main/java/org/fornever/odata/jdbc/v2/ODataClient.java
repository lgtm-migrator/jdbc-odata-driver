package org.fornever.odata.jdbc.v2;

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.fornever.odata.jdbc.types.ODataV2Param;

import kong.unirest.Client;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import kong.unirest.UnirestInstance;

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

	private UnirestInstance client = Unirest.spawnInstance();

	private String serviceRoot;

	private String username;

	private String password;

	private Edm serviceEdm;

	public ODataClient(String serviceRoot, String username, String password)
			throws EntityProviderException, UnirestException {
		super();
		this.serviceRoot = serviceRoot;
		this.username = username;
		this.password = password;

		this.client.config().addDefaultHeader(CSRF_TOKEN_HEADER, CSRF_TOKEN_FETCH)
				.addDefaultHeader("Accept", APPLICATION_JSON).addDefaultHeader("Content-Type", APPLICATION_JSON)
				.setDefaultBasicAuth(username, password);

		this.retriveEdm(serviceRoot);

	}

	public ODataClient(String serviceRoot) throws EntityProviderException, UnirestException {
		this(serviceRoot, "", "");
	}

	/**
	 * fetch data by parameter
	 * 
	 * @param collectionName of entity
	 * @param param          of query
	 * @return
	 */
	public JsonNode fetchData(String collectionName, ODataV2Param param) {
		return this.client.get(String.format("%s%s%s", this.serviceRoot, SEPARATOR, collectionName))
				.queryString(param.toMap()).asJson().getBody();

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
		Unirest.get(serviceRoot + SEPARATOR + METADATA).header(CSRF_TOKEN_HEADER, CSRF_TOKEN_FETCH)
				.basicAuth(this.username, this.password).thenConsume(r -> {
					// set CSRF token
					if (r.getHeaders().containsKey(CSRF_TOKEN_HEADER)) {
						String csrf = r.getHeaders().getFirst(CSRF_TOKEN_HEADER);
						if (!csrf.equals("Required") && !csrf.isEmpty()) {
							this.client.config().setDefaultHeader(CSRF_TOKEN_HEADER, csrf);
						}
					}
					try {
						this.serviceEdm = EntityProvider.readMetadata(r.getContent(), true);
					} catch (EntityProviderException e) {
						e.printStackTrace();
					}
				});

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
