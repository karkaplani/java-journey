package logic;

import common.EMFactory;
import common.TomcatStartUp;
import entity.BloodBank;
import entity.BloodDonation;
import entity.BloodGroup;
import entity.RhesusFactor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class to test all the methods of blood donation logic by using JUnit5. 
 *
 * @author Abdullah Ilgun
 */
class BloodDonationTest {

    private BloodDonationLogic logic;
    private BloodDonation expectedEntity;
    private int bloodBankID = 1; //Used in test create entity and add to create a blood bank dependency 

    @BeforeAll
    final static void setUpBeforeClass() throws Exception {
        TomcatStartUp.createTomcat( "/SimpleBloodBank", "common.ServletListener", "simplebloodbank-PU-test" );
    }

    @AfterAll
    final static void tearDownAfterClass() throws Exception {
        TomcatStartUp.stopAndDestroyTomcat();
    }

    @BeforeEach
    final void setUp() throws Exception {

        logic = LogicFactory.getFor( "BloodDonation" );

        //get an instance of EntityManager
        EntityManager em = EMFactory.getEMF().createEntityManager();
        //start a Transaction
        em.getTransaction().begin();
        //check if the depdendecy exists on DB already
        //em.find takes two arguments, the class type of return result and the primery key.
        BloodBank bb = em.find( BloodBank.class, 1 );
        //if result is null create the entity and persist it
        if( bb == null ){
            //create object
            bb = new BloodBank();
            bb.setName( "JUNIT" );
            bb.setPrivatelyOwned( true );
            bb.setEstablished( logic.convertStringToDate( "1111-11-11 11:11:11" ) );
            bb.setEmplyeeCount( 111 );
            //persist the dependency first
            em.persist( bb );
        }

        //create the desired entity
        BloodDonation entity = new BloodDonation();
        entity.setMilliliters( 100 );
        entity.setBloodGroup( BloodGroup.AB );
        entity.setRhd( RhesusFactor.Negative );
        entity.setCreated( logic.convertStringToDate( "1111-11-11 11:11:11" ) );
        //add dependency to the desired entity
        entity.setBloodBank( bb );

        //add desired entity to hibernate, entity is now managed.
        //we use merge instead of add so we can get the managed entity.
        expectedEntity = em.merge( entity );
        //commit the changes
        em.getTransaction().commit();
        //close EntityManager
        em.close();
    }

    @AfterEach
    final void tearDown() throws Exception {
        if( expectedEntity != null ){
            logic.delete( expectedEntity );
        }
    }

    @Test
    final void testGetAll() {
        //get all the accounts from the DB
        List<BloodDonation> list = logic.getAll();
        //store the size of list, this way we know how many accounts exits in DB
        int originalSize = list.size();

        //make sure account was created successfully
        assertNotNull( expectedEntity );
        //delete the new account
        logic.delete( expectedEntity );

        //get all accounts again
        list = logic.getAll();
        //the new size of accounts must be one less
        assertEquals( originalSize - 1, list.size() );
    }
    
    private void assertBDEquals( BloodDonation expected, BloodDonation actual ) {
        assertEquals( expected.getId(), actual.getId() );
        assertEquals( expected.getBloodBank().getId(), actual.getBloodBank().getId() );
        assertEquals(expected.getMilliliters(), actual.getMilliliters());
        assertEquals(expected.getClass(), actual.getClass());
        assertEquals(expected.getRhd(), actual.getRhd());
        assertEquals(expected.getCreated(), actual.getCreated());
    }
    
    @Test
    final void testGetWithId() {
        //using the id of test account get another account from logic
        BloodDonation returnedBD = logic.getWithId( expectedEntity.getId() );

        //the two accounts (testAcounts and returnedAccounts) must be the same
        assertBDEquals( expectedEntity, returnedBD );
    }
    
    @Test
    final void testGetBloodDonationWithMilliliters() {
        
        int foundFull = 0;
        List<BloodDonation> returnedBD = logic.getBloodDonationWithMilliliters(expectedEntity.getMilliliters());
        
        for(BloodDonation bd: returnedBD) {
            //all accounts must have the same password
            assertEquals(expectedEntity.getMilliliters(), bd.getMilliliters());
            //exactly one account must be the same
            if( bd.getId().equals(expectedEntity.getId())){
                assertBDEquals( expectedEntity, bd );
                foundFull++;
            }
        }
        assertEquals( 0, foundFull, "if zero means not found, if more than one means duplicate" );
    }
    
    @Test
    final void testGetBloodDonationWithBloodGroup() {
        
        int foundFull = 0;
        List<BloodDonation> returnedBD = logic.getBloodDonationWithBloodGroup(expectedEntity.getBloodGroup());
        
        for(BloodDonation bd: returnedBD) {
            //all accounts must have the same password
            assertEquals(expectedEntity.getBloodGroup(), bd.getBloodGroup());
            //exactly one account must be the same
            if( bd.getId().equals(expectedEntity.getId())){
                assertBDEquals( expectedEntity, bd );
                foundFull++;
            }
        }
        assertEquals( 1, foundFull, "if zero means not found, if more than one means duplicate" );
    }
    
    @Test
    final void testGetBloodDonationWithCreated() {
        
        int foundFull = 0;
        List<BloodDonation> returnedBD = logic.getBloodDonationWithCreated(expectedEntity.getCreated());
        
        for(BloodDonation bd: returnedBD) {
            //all accounts must have the same password
            assertEquals(expectedEntity.getCreated(), bd.getCreated());
            //exactly one account must be the same
            if( bd.getId().equals(expectedEntity.getId())){
                assertBDEquals( expectedEntity, bd );
                foundFull++;
            }
        }
        assertEquals( 1, foundFull, "if zero means not found, if more than one means duplicate" );
    }
    
    @Test
    final void testGetBloodDonationsWithRhd() {
        
        int foundFull = 0;
        List<BloodDonation> returnedBD = logic.getBloodDonationsWithRhd(expectedEntity.getRhd());
        
        for(BloodDonation bd: returnedBD) {
            //all accounts must have the same password
            assertEquals(expectedEntity.getRhd(), bd.getRhd());
            //exactly one account must be the same
            if( bd.getId().equals(expectedEntity.getId())){
                assertBDEquals( expectedEntity, bd );
                foundFull++;
            }
        }
        assertEquals( 1, foundFull, "if zero means not found, if more than one means duplicate" );
    }
    
    @Test
    final void testGetBloodDonationsWithBloodBank() {
        
        int foundFull = 0;
        List<BloodDonation> returnedBD = logic.getBloodDonationsWithBloodBank(expectedEntity.getBloodBank().getId());
        
        for(BloodDonation bd: returnedBD) {
            //all accounts must have the same password
            assertEquals(expectedEntity.getBloodBank().getId(), bd.getBloodBank().getId());
            //exactly one account must be the same
            if( bd.getBloodBank().getId().equals(expectedEntity.getBloodBank().getId())){
                assertBDEquals( expectedEntity, bd );
                foundFull++;
            }
        }
        assertEquals( 1, foundFull, "if zero means not found, if more than one means duplicate" );
    }
    
    
    @Test
    final void testCreateEntityAndAdd() {
        Map<String, String[]> sampleMap = new HashMap<>();
//        sampleMap.put( BloodDonationLogic.ID, new String[]{ "Test Create Entity" } );
//        sampleMap.put( BloodDonationLogic.BANK_ID, new String[]{ "Test Create Entity" } );
        sampleMap.put( BloodDonationLogic.MILILITERS, new String[]{ Integer.toString(100) } );
        sampleMap.put( BloodDonationLogic.BLOOD_GROUP, new String[]{ BloodGroup.AB.toString() } );
        sampleMap.put( BloodDonationLogic.FACTOR, new String[]{ RhesusFactor.Negative.toString() } );
        sampleMap.put( BloodDonationLogic.CREATED, new String[]{ "1111-11-11 11:11:11" } ); 

        BloodDonation createdBD = logic.createEntity( sampleMap );
        //add bloodbanlk here as depedency
        createdBD.setBloodBank(EMFactory.getEMF().createEntityManager().find(BloodBank.class, bloodBankID));
        logic.add(createdBD);

        BloodDonation returnedBD = logic.getBloodDonationsWithBloodBank(createdBD.getBloodBank().getId()).get(0);

        assertEquals( createdBD.getBloodBank().getId(), returnedBD.getBloodBank().getId());
        assertEquals( createdBD.getMilliliters(), returnedBD.getMilliliters());
        assertEquals( createdBD.getBloodGroup(), returnedBD.getBloodGroup());
        assertEquals( createdBD.getRhd(), returnedBD.getRhd() );
        assertEquals( createdBD.getCreated(), returnedBD.getCreated() );

        logic.delete(createdBD);
    }
    
    @Test
    final void testCreateEntity() {
        Map<String, String[]> sampleMap = new HashMap<>();
        sampleMap.put( BloodDonationLogic.ID, new String[]{ Integer.toString( expectedEntity.getId() ) } );
        sampleMap.put( BloodDonationLogic.MILILITERS, new String[]{ Integer.toString( expectedEntity.getMilliliters() ) } );
        sampleMap.put( BloodDonationLogic.BLOOD_GROUP, new String[]{  expectedEntity.getBloodGroup().name()} );
        sampleMap.put( BloodDonationLogic.FACTOR, new String[]{  expectedEntity.getRhd().name()} );
        sampleMap.put( BloodDonationLogic.CREATED, new String[]{  logic.convertDateToString(expectedEntity.getCreated())  } ); //lgoc method use

        BloodDonation returnedBD = logic.createEntity( sampleMap );

        int bankID = expectedEntity.getBloodBank().getId(); 
        returnedBD.setBloodBank(EMFactory.getEMF().createEntityManager().find(BloodBank.class, bankID));

        assertBDEquals(expectedEntity, returnedBD);
    }
    
    @Test
    final void testCreateEntityNullAndEmptyValues() {
        Map<String, String[]> sampleMap = new HashMap<>();
        Consumer<Map<String, String[]>> fillMap = ( Map<String, String[]> map ) -> {
            map.clear();
            map.put( BloodDonationLogic.ID, new String[]{ Integer.toString( expectedEntity.getId() ) } );
            map.put( BloodDonationLogic.MILILITERS, new String[]{ Integer.toString( expectedEntity.getMilliliters() ) } );
            map.put( BloodDonationLogic.BLOOD_GROUP, new String[]{  expectedEntity.getBloodGroup().toString()  } );
            map.put( BloodDonationLogic.FACTOR, new String[]{  expectedEntity.getRhd().toString()  } );
            map.put( BloodDonationLogic.CREATED, new String[]{  logic.convertDateToString(expectedEntity.getCreated())  } );
        };

        //idealy every test should be in its own method
        fillMap.accept( sampleMap );
        sampleMap.replace( BloodDonationLogic.ID, null );
        assertThrows( NullPointerException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( BloodDonationLogic.ID, new String[]{} );
        assertThrows( IndexOutOfBoundsException.class, () -> logic.createEntity( sampleMap ) ); 
        
        fillMap.accept( sampleMap );
        sampleMap.replace( BloodDonationLogic.MILILITERS, null );
        assertThrows( NullPointerException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( BloodDonationLogic.MILILITERS, new String[]{} );
        assertThrows( IndexOutOfBoundsException.class, () -> logic.createEntity( sampleMap ) );  
        
        fillMap.accept( sampleMap );
        sampleMap.replace( BloodDonationLogic.BLOOD_GROUP, null );
        assertThrows( NullPointerException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( BloodDonationLogic.BLOOD_GROUP, new String[]{} );
        assertThrows( IndexOutOfBoundsException.class, () -> logic.createEntity( sampleMap ) );
        
        fillMap.accept( sampleMap );
        sampleMap.replace( BloodDonationLogic.FACTOR, null );
        assertThrows( NullPointerException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( BloodDonationLogic.FACTOR, new String[]{} );
        assertThrows( IndexOutOfBoundsException.class, () -> logic.createEntity( sampleMap ) );
        
        fillMap.accept( sampleMap );
        sampleMap.replace( BloodDonationLogic.CREATED, null );
        assertThrows( NullPointerException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( BloodDonationLogic.CREATED, new String[]{} );
        assertThrows( IndexOutOfBoundsException.class, () -> logic.createEntity( sampleMap ) );
    }
    
    @Test
    final void testCreateEntityBadValues() {
        Map<String, String[]> sampleMap = new HashMap<>();
        Consumer<Map<String, String[]>> fillMap = ( Map<String, String[]> map ) -> {
            map.clear();
            map.put( BloodDonationLogic.ID, new String[]{ Integer.toString( expectedEntity.getId() ) } );
            map.put( BloodDonationLogic.BANK_ID, new String[]{ Integer.toString( expectedEntity.getBloodBank().getId() ) } );
            map.put( BloodDonationLogic.MILILITERS, new String[]{ Integer.toString( expectedEntity.getMilliliters() ) } );
            map.put( BloodDonationLogic.BLOOD_GROUP, new String[]{  expectedEntity.getBloodGroup().toString()  } );
            map.put( BloodDonationLogic.FACTOR, new String[]{  expectedEntity.getRhd().toString()  } );
            map.put( BloodDonationLogic.CREATED, new String[]{  expectedEntity.getCreated().toString()  } );
        };
        
        fillMap.accept( sampleMap );
        sampleMap.replace( BloodDonationLogic.BLOOD_GROUP, new String[]{ "C" } );
        assertThrows( IllegalArgumentException.class, () -> logic.createEntity( sampleMap ) );
        
        fillMap.accept( sampleMap );
        sampleMap.replace( BloodDonationLogic.FACTOR, new String[]{ "=" } );
        assertThrows( IllegalArgumentException.class, () -> logic.createEntity( sampleMap ) );
    }
    
    @Test
    final void testGetColumnNames() {
        List<String> list = logic.getColumnNames();
        assertEquals( Arrays.asList("donation_id", "bank_id", "milliliters", "blood_group", "rhd", "created"), list );
    }

    @Test
    final void testGetColumnCodes() {
        List<String> list = logic.getColumnCodes();
        assertEquals( Arrays.asList( BloodDonationLogic.ID, BloodDonationLogic.BANK_ID, BloodDonationLogic.MILILITERS,
                                     BloodDonationLogic.BLOOD_GROUP, BloodDonationLogic.FACTOR, BloodDonationLogic.CREATED), list );
    }

    @Test
    final void testExtractDataAsList() {
        List<?> list = logic.extractDataAsList( expectedEntity );
        assertEquals( expectedEntity.getId(), list.get( 0 ) );
        assertEquals( expectedEntity.getBloodBank().getId(), list.get( 1 ) );
        assertEquals( expectedEntity.getMilliliters(), list.get( 2 ) );
        assertEquals( expectedEntity.getBloodGroup(), list.get( 3 ) );
        assertEquals( expectedEntity.getRhd(), list.get( 4 ) );
        assertEquals( expectedEntity.getCreated(), list.get( 5 ) );
    } 
}
