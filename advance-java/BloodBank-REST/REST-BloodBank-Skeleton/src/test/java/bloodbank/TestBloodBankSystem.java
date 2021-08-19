/**
 * File: OrderSystemTestSuite.java
 * Course materials (21S) CST 8277
 * Teddy Yap
 * (Original Author) Mike Norman
 *
 * @date 2020 10
 *
 * @author Samarth Sharma, Masoumeh Mirzaeepour Gelvarzkhah, Abdullah Zeki Ilgun, Dishaben Patel
 */

package bloodbank;

import static bloodbank.utility.MyConstants.APPLICATION_API_VERSION;
import static bloodbank.utility.MyConstants.DEFAULT_ADMIN_USER;
import static bloodbank.utility.MyConstants.DEFAULT_ADMIN_USER_PASSWORD;
import static bloodbank.utility.MyConstants.DEFAULT_USER_PASSWORD;
import static bloodbank.utility.MyConstants.DEFAULT_USER_PREFIX;
import static bloodbank.utility.MyConstants.PERSON_RESOURCE_NAME;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
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
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import bloodbank.entity.Address;
import bloodbank.entity.BloodBank;
import bloodbank.entity.BloodDonation;
import bloodbank.entity.DonationRecord;
import bloodbank.entity.Person;
import bloodbank.entity.Phone;
import bloodbank.entity.PrivateBloodBank;
import bloodbank.entity.PublicBloodBank;
import bloodbank.utility.MyConstants;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class TestBloodBankSystem {
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
        uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        adminAuth = HttpAuthenticationFeature.basic(DEFAULT_ADMIN_USER, DEFAULT_ADMIN_USER_PASSWORD);
        userAuth = HttpAuthenticationFeature.basic("cst8288", DEFAULT_USER_PASSWORD);
    }

    protected WebTarget webTarget;
    @BeforeEach
    public void setUp() {
        Client client = ClientBuilder.newClient(
            new ClientConfig().register(MyObjectMapperProvider.class).register(new LoggingFeature()));
        webTarget = client.target(uri);
    }

    
    
    @Test
    public void test01_all_customers_userrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(userAuth)
            .path(PERSON_RESOURCE_NAME+"/all")
            .request()
            .get();
        assertThat(response.getStatus(), is(401));
    }
//    
    @Test
    public void test02_all_associate_phone_adminrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(adminAuth)
            .path(MyConstants.PERSON_RESOURCE_NAME+"/associate/1/1/2")
            .request()
            .get();
        assertThat(response.getStatus(), is(200));
//        List<Address> address = response.readEntity(new GenericType<List<Address>>(){});
//        assertThat(address, is(not(empty())));
    }
    
    @Test
    public void test03_all_address_userrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(userAuth)
            .path(MyConstants.ADDRESS_RESOURCE+"/all")
            .request()
            .get();
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
            //.register(userAuth)
            .register(adminAuth)
            .path(MyConstants.PERSON_RESOURCE_NAME+"/1/address")
            .request()
            .put(Entity.json(address));
        assertThat(response.getStatus(), is(200));
    }
    
    @Test
    public void test05_all_blood_donation_userrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(userAuth)
            .path(MyConstants.BLOOD_DONATION_RESOURCE+"/all")
            .request()
            .get();
        assertThat(response.getStatus(), is(403));
    }
    
   
    
    @Test
    public void test06_all_blood_bank_userrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(userAuth)
            .path(MyConstants.BLOOD_BANK_RESOURCE+"/all")
            .request()
            .get();
        assertThat(response.getStatus(), is(403));
        
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
    
    @Test
    public void test08_all_donation_record_userrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(userAuth)
            .path(MyConstants.DONATION_RECORD_RESOURCE+"/all")
            .request()
            .get();
        assertThat(response.getStatus(), is(403));
    }
    
 
    
    @Test
    public void test09_all_phone_userrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(userAuth)
            .path(MyConstants.PHONE_RESOURCE+"/all")
            .request()
            .get();
        assertThat(response.getStatus(), is(403));
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
        assertThat(response.getStatus(), is(403));
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
        assertThat(response.getStatus(), is(403));
    
    }
    
  
    
    @Test
    public void test14_add_customers_userrole() throws JsonMappingException, JsonProcessingException {
    	Person person = new Person();
    	person.setFirstName("Test1");
    	person.setLastName("Test1");
        Response response = webTarget
            //.register(userAuth)
            .register(userAuth)
            .path(PERSON_RESOURCE_NAME)
            .request()
            .post(Entity.json(person));
        assertThat(response.getStatus(), is(403));
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
            //.register(userAuth)
            .register(adminAuth)
            .path(MyConstants.ADDRESS_RESOURCE)
            .request()
            .post(Entity.json(address));
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
            //.register(userAuth)
            .register(userAuth)
            .path(MyConstants.ADDRESS_RESOURCE)
            .request()
            .post(Entity.json(address));
        assertThat(response.getStatus(), is(403));
    }
   
//    
    @Test
    public void test17_add_customers_adminrole() throws JsonMappingException, JsonProcessingException {
    	Person person = new Person();
    	person.setFirstName("Test");
    	person.setLastName("Test");
        Response response = webTarget
            //.register(userAuth)
            .register(adminAuth)
            .path(PERSON_RESOURCE_NAME)
            .request()
            .post(Entity.json(person));
        assertThat(response.getStatus(), is(200));
        
    }
    @Test
    public void test18_add_phone_adminrole() throws JsonMappingException, JsonProcessingException {
    	Phone phone = new Phone();
    	phone.setAreaCode("1");
    	phone.setCountryCode("2");
    	phone.setNumber("123");
        Response response = webTarget
            //.register(userAuth)
            .register(adminAuth)
            .path(MyConstants.PHONE_RESOURCE)
            .request()
            .post(Entity.json(phone));
        assertThat(response.getStatus(), is(200));
        
    }
    
    @Test
    public void test19_add_phone_userrole() throws JsonMappingException, JsonProcessingException {
    	Phone phone = new Phone();
    	phone.setAreaCode("1");
    	phone.setCountryCode("2");
    	phone.setNumber("123");
        Response response = webTarget
            //.register(userAuth)
            .register(userAuth)
            .path(MyConstants.PHONE_RESOURCE)
            .request()
            .post(Entity.json(phone));
        assertThat(response.getStatus(), is(403));
        
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
        assertThat(response.getStatus(), is(403));
        
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
        assertThat(response.getStatus(), is(403));
        
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
    public void test25_delete_donation_record_userrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(userAuth)
            .path(MyConstants.DONATION_RECORD_RESOURCE+"/1")
            .request()
            .delete();
        assertThat(response.getStatus(), is(403));
    }
    
    @Test
    public void test26_delete_phone_adminrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(adminAuth)
            .path(MyConstants.PHONE_RESOURCE+"/2")
            .request()
            .delete();
        assertThat(response.getStatus(), is(200));
    }
//    
    @Test
    public void test27_delete_phone_userrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(userAuth)
            .path(MyConstants.PHONE_RESOURCE+"/1")
            .request()
            .delete();
        assertThat(response.getStatus(), is(403));
    }
    
    @Test
    public void test28_delete_blood_donation_userrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(userAuth)
            .path(MyConstants.BLOOD_DONATION_RESOURCE+"/1")
            .request()
            .delete();
        assertThat(response.getStatus(), is(403));
    }
    
    @Test
    public void test29_delete_blood_donation_adminrole() throws JsonMappingException, JsonProcessingException {
 
        Response response = webTarget
            //.register(userAuth)
            .register(adminAuth)
            .path(MyConstants.BLOOD_DONATION_RESOURCE+"/1")
            .request()
            .delete();
        assertThat(response.getStatus(), is(200));
    }
    
   
    
    @Test
    public void test30_delete_public_blood_bank_userrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(userAuth)
            .path(MyConstants.PUBLIC_BLOOD_BANK_RESOURCE+"/1")
            .request()
            .delete();
        assertThat(response.getStatus(), is(403));
    
    }
    
    @Test
    public void test31_delete_private_blood_bank_userrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(userAuth)
            .path(MyConstants.PRIVATE_BLOOD_BANK_RESOURCE+"/1")
            .request()
            .delete();
        assertThat(response.getStatus(), is(403));
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