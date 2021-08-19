/**
 * @name TestBloodBank.java
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
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

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

import bloodbank.entity.PrivateBloodBank;
import bloodbank.entity.PublicBloodBank;
import bloodbank.utility.MyConstants;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class TestBloodBank {
	
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
    public void test06_all_blood_bank_userrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(userAuth)
            .path(MyConstants.BLOOD_BANK_RESOURCE+"/all")
            .request()
            .get();
        assertThat(response.getStatus(), is(Status.UNAUTHORIZED.getStatusCode()));
        
    }
    
    @Test
    public void test10_all_private_blood_bank_adminrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(adminAuth)
            .path(MyConstants.PRIVATE_BLOOD_BANK_RESOURCE+"/all")
            .request()
            .get();
        assertThat(response.getStatus(), is(200));
        List<PrivateBloodBank> privateBloodBanks = response.readEntity(new GenericType<List<PrivateBloodBank>>(){});
        assertThat(privateBloodBanks, is(not(empty())));
        assertThat(privateBloodBanks.size(), is(1));
    }
    
    @Test
    public void test11_all_private_blood_bank_userrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(userAuth)
            .path(MyConstants.PRIVATE_BLOOD_BANK_RESOURCE+"/all")
            .request()
            .get();
        assertThat(response.getStatus(), is(Status.UNAUTHORIZED.getStatusCode()));
    }
    
    @Test
    public void test12_all_public_blood_bank_adminrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(adminAuth)
            .path(MyConstants.PUBLIC_BLOOD_BANK_RESOURCE+"/all")
            .request()
            .get();
        assertThat(response.getStatus(), is(200));
        List<PublicBloodBank> publicBloodBanks = response.readEntity(new GenericType<List<PublicBloodBank>>(){});
        assertThat(publicBloodBanks, is(not(empty())));
        assertThat(publicBloodBanks.size(), is(1));
    }
    
    @Test
    public void test13_all_public_blood_bank_userrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(userAuth)
            .path(MyConstants.PUBLIC_BLOOD_BANK_RESOURCE+"/all")
            .request()
            .get();
        assertThat(response.getStatus(), is(Status.UNAUTHORIZED.getStatusCode()));
    
    }
    
    @Test
    public void test20_add_private_bloodbank_adminrole() throws JsonMappingException, JsonProcessingException {
    	PrivateBloodBank privateBloodBank = new PrivateBloodBank();
    	privateBloodBank.setName("privateadmin");
        Response response = webTarget
            //.register(userAuth)
            .register(adminAuth)
            .path(MyConstants.PRIVATE_BLOOD_BANK_RESOURCE)
            .request()
            .post(Entity.json(privateBloodBank));
        assertThat(response.getStatus(), is(200));
        
    }
    
    @Test
    public void test21_add_private_bloodbank_userrole() throws JsonMappingException, JsonProcessingException {
    	PrivateBloodBank privateBloodBank = new PrivateBloodBank();
    	privateBloodBank.setName("privateuser");
        Response response = webTarget
            //.register(userAuth)
            .register(userAuth)
            .path(MyConstants.PRIVATE_BLOOD_BANK_RESOURCE)
            .request()
            .post(Entity.json(privateBloodBank));
        assertThat(response.getStatus(), is(Status.UNAUTHORIZED.getStatusCode()));
        
    }
    
    @Test
    public void test22_add_public_bloodbank_adminrole() throws JsonMappingException, JsonProcessingException {
    	PrivateBloodBank privateBloodBank = new PrivateBloodBank();
    	privateBloodBank.setName("publicadmin");
        Response response = webTarget
            //.register(userAuth)
            .register(adminAuth)
            .path(MyConstants.PUBLIC_BLOOD_BANK_RESOURCE)
            .request()
            .post(Entity.json(privateBloodBank));
        assertThat(response.getStatus(), is(200));
        
    }
    
    @Test
    public void test23_add_public_bloodbank_userrole() throws JsonMappingException, JsonProcessingException {
    	PublicBloodBank privateBloodBank = new PublicBloodBank();
    	privateBloodBank.setName("publicuser");
        Response response = webTarget
            //.register(userAuth)
            .register(userAuth)
            .path(MyConstants.PUBLIC_BLOOD_BANK_RESOURCE)
            .request()
            .post(Entity.json(privateBloodBank));
        assertThat(response.getStatus(), is(Status.UNAUTHORIZED.getStatusCode()));
        
    }
    @Test
    public void test30_delete_public_blood_bank_userrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(userAuth)
            .path(MyConstants.PUBLIC_BLOOD_BANK_RESOURCE+"/1")
            .request()
            .delete();
        assertThat(response.getStatus(), is(Status.UNAUTHORIZED.getStatusCode()));
    
    }
    @Test
    public void test31_delete_private_blood_bank_userrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(userAuth)
            .path(MyConstants.PRIVATE_BLOOD_BANK_RESOURCE+"/1")
            .request()
            .delete();
        assertThat(response.getStatus(), is(Status.UNAUTHORIZED.getStatusCode()));
    }
    @Test
    public void test32_delete_private_blood_bank_adminrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(adminAuth)
            .path(MyConstants.PRIVATE_BLOOD_BANK_RESOURCE).path("1")
            .request()
            .delete();
        assertThat(response.getStatus(), is(200));
    }
    
    
    

}
