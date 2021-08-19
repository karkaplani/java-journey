/**
 * @name TestDonationRecord.java
 * @author Samarth Sharma, Masoumeh Mirzaeepour Gelvarzkhah, Abdullah Zeki Ilgun, Dishaben Patel
 * @since 2021-08-10
 */
package bloodbank;

import static bloodbank.utility.MyConstants.APPLICATION_API_VERSION;
import static bloodbank.utility.MyConstants.DEFAULT_ADMIN_USER;
import static bloodbank.utility.MyConstants.DEFAULT_ADMIN_USER_PASSWORD;
import static bloodbank.utility.MyConstants.DEFAULT_USER_PASSWORD;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;

import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.io.STAXEventReader;
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

import bloodbank.entity.DonationRecord;
import bloodbank.utility.MyConstants;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class TestDonationRecord {
	
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
    public void test25_delete_donation_record_userrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(userAuth)
            .path(MyConstants.DONATION_RECORD_RESOURCE+"/1")
            .request()
            .delete();
        assertThat(response.getStatus(), is(Status.UNAUTHORIZED.getStatusCode()));
    }
    
	@Test
    public void test24_delete_donation_record_adminrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(adminAuth)
            .path(MyConstants.DONATION_RECORD_RESOURCE+"/1")
            .request()
            .delete();
        assertThat(response.getStatus(), is(200));
    }
	@Test
    public void test08_all_donation_record_userrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(userAuth)
            .path(MyConstants.DONATION_RECORD_RESOURCE+"/all")
            .request()
            .get();
        assertThat(response.getStatus(), is(Status.UNAUTHORIZED.getStatusCode()));
    }
	@Test
    public void test07_all_donation_record_adminrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(adminAuth)
            .path(MyConstants.DONATION_RECORD_RESOURCE+"/all")
            .request()
            .get();
        assertThat(response.getStatus(), is(200));
        List<DonationRecord> donationRecords = response.readEntity(new GenericType<List<DonationRecord>>(){});
        assertThat(donationRecords, is(not(empty())));
    }

}
