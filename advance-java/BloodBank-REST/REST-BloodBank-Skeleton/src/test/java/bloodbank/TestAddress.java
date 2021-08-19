package bloodbank;

import static bloodbank.utility.MyConstants.APPLICATION_API_VERSION;
import static bloodbank.utility.MyConstants.DEFAULT_ADMIN_USER;
import static bloodbank.utility.MyConstants.DEFAULT_ADMIN_USER_PASSWORD;
import static bloodbank.utility.MyConstants.DEFAULT_USER_PASSWORD;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.lang.invoke.MethodHandles;
import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import bloodbank.entity.Address;
import bloodbank.utility.MyConstants;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class TestAddress {

	private static final Class<?> _thisClaz = MethodHandles.lookup().lookupClass();
	private static final Logger logger = LogManager.getLogger(_thisClaz);

	static final String APPLICATION_CONTEXT_ROOT = "REST-BloodBank-Skeleton";
	static final String HTTP_SCHEMA = "http";
	static final String HOST = "localhost";
	static final int PORT = 8080;

	// test fixture(s)
	static URI uri;
	static HttpAuthenticationFeature adminAuth;
	static HttpAuthenticationFeature userAuth;

	@BeforeAll
	public static void oneTimeSetUp() throws Exception {
		logger.debug("oneTimeSetUp");
		uri = UriBuilder.fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION).scheme(HTTP_SCHEMA).host(HOST)
				.port(PORT).build();
		adminAuth = HttpAuthenticationFeature.basic(DEFAULT_ADMIN_USER, DEFAULT_ADMIN_USER_PASSWORD);
		userAuth = HttpAuthenticationFeature.basic("cst8288", DEFAULT_USER_PASSWORD);
	}

	protected WebTarget webTarget;

	@BeforeEach
	public void setUp() {
		Client client = ClientBuilder
				.newClient(new ClientConfig().register(MyObjectMapperProvider.class).register(new LoggingFeature()));
		webTarget = client.target(uri);
	}

	@Test
	public void test03_all_address_userrole() throws JsonMappingException, JsonProcessingException {
		Response response = webTarget
				// .register(userAuth)
				.register(userAuth).path(MyConstants.ADDRESS_RESOURCE + "/all").request().get();
		assertThat(response.getStatus(), is(Status.UNAUTHORIZED.getStatusCode()));
	}

	@Test
	public void test04_add_address_to_customer_adminrole() throws JsonMappingException, JsonProcessingException {
		Address address = new Address();
		address.setCity("City");
		address.setCountry("Country");
		address.setStreet("Stress");
		address.setZipcode("2");
		address.setStreetNumber("1");
		address.setProvince("2");
		Response response = webTarget
				// .register(userAuth)
				.register(adminAuth).path(MyConstants.PERSON_RESOURCE_NAME + "/1/address").request()
				.put(Entity.json(address));
		assertThat(response.getStatus(), is(200));
	}

	@Test
	public void test15_add_address_adminrole() throws JsonMappingException, JsonProcessingException {
		Address address = new Address();
		address.setCity("City");
		address.setCountry("12");
		address.setStreet("12");
		address.setZipcode("12");
		address.setStreetNumber("12");
		address.setProvince("12");
		Response response = webTarget
				// .register(userAuth)
				.register(adminAuth).path(MyConstants.ADDRESS_RESOURCE).request().post(Entity.json(address));
		assertThat(response.getStatus(), is(200));

	}

	@Test
	public void test16_add_address_userrole() throws JsonMappingException, JsonProcessingException {
		Address address = new Address();
		address.setCity("City");
		address.setCountry("Country");
		address.setStreet("Stress");
		address.setZipcode("Zipcode");
		address.setStreetNumber("StreetNumber");
		address.setProvince("Province");
		Response response = webTarget
				// .register(userAuth)
				.register(userAuth).path(MyConstants.ADDRESS_RESOURCE).request().post(Entity.json(address));
		assertThat(response.getStatus(), is(Status.UNAUTHORIZED.getStatusCode()));
	}

}
