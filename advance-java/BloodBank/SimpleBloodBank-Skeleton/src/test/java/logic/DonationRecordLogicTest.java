package logic;

import common.EMFactory;
import common.TomcatStartUp;
import common.ValidationException;
import entity.BloodDonation;
import entity.DonationRecord;
import entity.Person;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.IntFunction;
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
 *
 * @author AdamPZ
 */
class DonationRecordLogicTest {

    private BloodDonation bloodDonation;
	private DonationRecordLogic logic;
    private DonationRecord expectedEntity;
	private Person person;
	private String administrator;
	private String hospital;
	private Date created;
	private int record;

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

        logic = LogicFactory.getFor( "DonationRecord" );
        /* **********************************
         * ***********IMPORTANT**************
         * **********************************/
        //we only do this for the test.
        //always create Entity using logic.
        //we manually make the account to not rely on any logic functionality , just for testing

        //get an instance of EntityManager
        EntityManager em = EMFactory.getEMF().createEntityManager();
        //start a Transaction
        em.getTransaction().begin();
        
        

        DonationRecord entity = new DonationRecord();
        
        entity.setPerson( person );
        entity.setBloodDonation( bloodDonation );
        entity.setTested( true );
        entity.setAdministrator( administrator );
        entity.setHospital(hospital);
        entity.setCreated(created);
        entity.setId(record);

        //add an account to hibernate, account is now managed.
        //we use merge instead of add so we can get the updated generated ID.
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
        List<DonationRecord> list = logic.getAll();
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

    /**
     * helper method for testing all account fields
     *
     * @param expected
     * @param actual
     */

    
    private void assertDonationRecordEquals( DonationRecord expected, DonationRecord actual ) {
        //assert all field to guarantee they are the same
        assertEquals( expected.getId(), actual.getId() );
        assertEquals( expected.getPerson(), actual.getPerson() );
        assertEquals( expected.getBloodDonation(), actual.getBloodDonation() );
        assertEquals( expected.getTested(), actual.getTested() );
        assertEquals( expected.getAdministrator(), actual.getAdministrator() );
        assertEquals( expected.getHospital(), actual.getHospital() );
        assertEquals( expected.getCreated(), actual.getCreated() );
        
    }

   
    @Test
    final void testGetWithId() {
        //using the id of test account get another account from logic
    	DonationRecord returnedDonationRecord = logic.getWithId( expectedEntity.getId() );
    	
    	returnedDonationRecord.setBloodDonation(bloodDonation);
        //the two accounts (testAcounts and returnedAccounts) must be the same
        assertDonationRecordEquals( expectedEntity, returnedDonationRecord );
    }
    
    @Test
    final void testGetAccountWithTested() {
    	DonationRecord returnedDonationRecord = logic.getDonationRecordWithTested( (Boolean) expectedEntity.getTested() );

        //the two accounts (testAcounts and returnedAccounts) must be the same
        assertDonationRecordEquals( expectedEntity, returnedDonationRecord );
    }

    @Test
    final void testGetDonationRecordWithHospital() {
    	DonationRecord returnedDonationRecord = (DonationRecord) logic.getDonationRecordWithHospital(hospital);

        //the two accounts (testAcounts and returnedAccounts) must be the same
        assertDonationRecordEquals( expectedEntity, returnedDonationRecord );
    }
    
    @Test
    final void testGetDonationRecordWithAdministrator() {
    	DonationRecord returnedDonationRecord = (DonationRecord) logic.getDonationRecordWithAdministrator(administrator);

        //the two accounts (testAcounts and returnedAccounts) must be the same
        assertDonationRecordEquals( expectedEntity, returnedDonationRecord );
    }
    
    @Test
    final void testGetDonationRecordWithCreated() {
    	DonationRecord returnedDonationRecord = (DonationRecord) logic.getDonationRecordWithCreated(created);

        //the two accounts (testAcounts and returnedAccounts) must be the same
        assertDonationRecordEquals( expectedEntity, returnedDonationRecord );
    }


    @Test
    final void testCreateEntityAndAdd() {
        Map<String, String[]> sampleMap = new HashMap<>();
        sampleMap.put( DonationRecordLogic.TESTED, new String[]{ "Test Create Entity" } );
        sampleMap.put( DonationRecordLogic.ADMINISTRATOR, new String[]{ "test administrator" } );
        sampleMap.put( DonationRecordLogic.HOSPITAL, new String[]{ "test hospital" } );
        sampleMap.put( DonationRecordLogic.CREATED, new String[]{ "test create" } );

        DonationRecord returnedDonationRecord = logic.createEntity( sampleMap );
        logic.add( returnedDonationRecord);

        returnedDonationRecord = logic.getWithId(returnedDonationRecord.getId());

        assertEquals( sampleMap.get( DonationRecordLogic.TESTED )[ 0 ], returnedDonationRecord.getTested() );
        assertEquals( sampleMap.get( DonationRecordLogic.ADMINISTRATOR )[ 0 ], returnedDonationRecord.getAdministrator() );
        assertEquals( sampleMap.get( DonationRecordLogic.CREATED)[ 0 ], returnedDonationRecord.getCreated() );
        assertEquals( sampleMap.get( DonationRecordLogic.HOSPITAL )[ 0 ], returnedDonationRecord.getHospital() );

        logic.delete( returnedDonationRecord );
    }

    @Test
    final void testCreateEntity() {
        Map<String, String[]> sampleMap = new HashMap<>();
        sampleMap.put( DonationRecordLogic.ID, new String[]{ Integer.toString( expectedEntity.getId() ) } );
        sampleMap.put( DonationRecordLogic.TESTED, new String[]{ Boolean.toString( expectedEntity.getTested()) } );
        sampleMap.put( DonationRecordLogic.ADMINISTRATOR, new String[]{ (String) expectedEntity.getAdministrator() } );
        sampleMap.put( DonationRecordLogic.CREATED, new String[]{ logic.convertDateToString( expectedEntity.getCreated()) } );
        sampleMap.put( DonationRecordLogic.HOSPITAL, new String[]{ (String) expectedEntity.getHospital() } );

        DonationRecord returnedDonationRecord = logic.createEntity( sampleMap );

        assertDonationRecordEquals( expectedEntity, returnedDonationRecord);
    }

    @Test
    final void testCreateEntityNullAndEmptyValues() {
        Map<String, String[]> sampleMap = new HashMap<>();
        Consumer<Map<String, String[]>> fillMap = ( Map<String, String[]> map ) -> {
            map.clear();
            map.put( DonationRecordLogic.ID, new String[]{ Integer.toString( expectedEntity.getId() ) } );
            map.put( DonationRecordLogic.TESTED, new String[]{ Boolean.toString (expectedEntity.getTested()) } );
            map.put( DonationRecordLogic.ADMINISTRATOR, new String[]{ (String) expectedEntity.getAdministrator() } );
            map.put( DonationRecordLogic.CREATED, new String[]{ logic.convertDateToString(expectedEntity.getCreated()) } );
            map.put( DonationRecordLogic.HOSPITAL, new String[]{ (String) expectedEntity.getHospital() } );
        };

        //idealy every test should be in its own method
        fillMap.accept( sampleMap );
        sampleMap.replace( DonationRecordLogic.ID, null );
        assertThrows( NullPointerException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( DonationRecordLogic.ID, new String[]{} );
        assertThrows( IndexOutOfBoundsException.class, () -> logic.createEntity( sampleMap ) );

        fillMap.accept( sampleMap );
        sampleMap.replace( DonationRecordLogic.TESTED, null );
        assertThrows( NullPointerException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( DonationRecordLogic.TESTED, new String[]{} );
        assertThrows( IndexOutOfBoundsException.class, () -> logic.createEntity( sampleMap ) );

        fillMap.accept( sampleMap );
        //can be null
        sampleMap.replace( DonationRecordLogic.ADMINISTRATOR, new String[]{} );
        assertThrows( IndexOutOfBoundsException.class, () -> logic.createEntity( sampleMap ) );

        fillMap.accept( sampleMap );
        sampleMap.replace( DonationRecordLogic.CREATED, null );
        assertThrows( NullPointerException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( DonationRecordLogic.CREATED, new String[]{} );
        assertThrows( IndexOutOfBoundsException.class, () -> logic.createEntity( sampleMap ) );

        fillMap.accept( sampleMap );
        sampleMap.replace( DonationRecordLogic.HOSPITAL, null );
        assertThrows( NullPointerException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( DonationRecordLogic.HOSPITAL, new String[]{} );
        assertThrows( IndexOutOfBoundsException.class, () -> logic.createEntity( sampleMap ) );
    }

    @Test
    final void testCreateEntityBadLengthValues() {
        Map<String, String[]> sampleMap = new HashMap<>();
        Consumer<Map<String, String[]>> fillMap = ( Map<String, String[]> map ) -> {
            map.clear();
            map.put( DonationRecordLogic.ID, new String[]{ Integer.toString( expectedEntity.getId() ) } );
            map.put( DonationRecordLogic.TESTED, new String[]{ Boolean.toString (expectedEntity.getTested()) } );
            map.put( DonationRecordLogic.ADMINISTRATOR, new String[]{ (String) expectedEntity.getAdministrator() } );
            map.put( DonationRecordLogic.CREATED, new String[]{ logic.convertDateToString(expectedEntity.getCreated()) } );
            map.put( DonationRecordLogic.HOSPITAL, new String[]{ (String) expectedEntity.getHospital() } );
        };
        


        IntFunction<String> generateString = ( int length ) -> {
            //https://www.baeldung.com/java-random-string#java8-alphabetic
            //from 97 inclusive to 123 exclusive
            return new Random().ints( 'a', 'z' + 1 ).limit( length )
                    .collect( StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append )
                    .toString();
        };

        //idealy every test should be in its own method
        fillMap.accept( sampleMap );
        sampleMap.replace( DonationRecordLogic.ID, new String[]{ "" } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( DonationRecordLogic.ID, new String[]{ "12b" } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );

        fillMap.accept( sampleMap );
        sampleMap.replace( DonationRecordLogic.TESTED, new String[]{ "" } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( DonationRecordLogic.TESTED, new String[]{ generateString.apply( 46 ) } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );

        fillMap.accept( sampleMap );
        sampleMap.replace( DonationRecordLogic.ADMINISTRATOR, new String[]{ "" } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( DonationRecordLogic.ADMINISTRATOR, new String[]{ generateString.apply( 46 ) } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );

        fillMap.accept( sampleMap );
        sampleMap.replace( DonationRecordLogic.CREATED, new String[]{ "" } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( DonationRecordLogic.CREATED, new String[]{ generateString.apply( 46 ) } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );

        fillMap.accept( sampleMap );
        sampleMap.replace( DonationRecordLogic.HOSPITAL, new String[]{ "" } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( DonationRecordLogic.HOSPITAL, new String[]{ generateString.apply( 46 ) } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );
    }

    @Test
    final void testCreateEntityEdgeValues() {
        IntFunction<String> generateString = ( int length ) -> {
            //https://www.baeldung.com/java-random-string#java8-alphabetic
            return new Random().ints( 'a', 'z' + 1 ).limit( length )
                    .collect( StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append )
                    .toString();
        };

        Map<String, String[]> sampleMap = new HashMap<>();
        sampleMap.put( DonationRecordLogic.ID, new String[]{ Integer.toString( 1 ) } );
        sampleMap.put( DonationRecordLogic.TESTED, new String[]{ generateString.apply( 1 ) } );
        sampleMap.put( DonationRecordLogic.ADMINISTRATOR, new String[]{ generateString.apply( 1 ) } );
        sampleMap.put( DonationRecordLogic.CREATED, new String[]{ generateString.apply( 1 ) } );
        sampleMap.put( DonationRecordLogic.HOSPITAL, new String[]{ generateString.apply( 1 ) } );

        //idealy every test should be in its own method
        DonationRecord returnedDonationRecord = logic.createEntity( sampleMap );
        assertEquals( Integer.parseInt( sampleMap.get( DonationRecordLogic.ID )[ 0 ] ), returnedDonationRecord.getId() );
        assertEquals( sampleMap.get( DonationRecordLogic.TESTED )[ 0 ], returnedDonationRecord.getTested() );
        assertEquals( sampleMap.get( DonationRecordLogic.ADMINISTRATOR )[ 0 ], returnedDonationRecord.getAdministrator() );
        assertEquals( sampleMap.get( DonationRecordLogic.CREATED )[ 0 ], returnedDonationRecord.getCreated() );
        assertEquals( sampleMap.get( DonationRecordLogic.HOSPITAL )[ 0 ], returnedDonationRecord.getHospital() );

        sampleMap = new HashMap<>();
        sampleMap.put( DonationRecordLogic.ID, new String[]{ Integer.toString( 1 ) } );
        sampleMap.put( DonationRecordLogic.TESTED, new String[]{ generateString.apply( 45 ) } );
        sampleMap.put( DonationRecordLogic.ADMINISTRATOR, new String[]{ generateString.apply( 45 ) } );
        sampleMap.put( DonationRecordLogic.CREATED, new String[]{ generateString.apply( 45 ) } );
        sampleMap.put( DonationRecordLogic.HOSPITAL, new String[]{ generateString.apply( 45 ) } );

        //idealy every test should be in its own method
        returnedDonationRecord = logic.createEntity( sampleMap );
        assertEquals( Integer.parseInt( sampleMap.get( DonationRecordLogic.ID )[ 0 ] ), returnedDonationRecord.getId() );
        assertEquals( sampleMap.get( DonationRecordLogic.TESTED )[ 0 ], returnedDonationRecord.getTested() );
        assertEquals( sampleMap.get( DonationRecordLogic.ADMINISTRATOR )[ 0 ], returnedDonationRecord.getAdministrator() );
        assertEquals( sampleMap.get( DonationRecordLogic.CREATED )[ 0 ], returnedDonationRecord.getCreated() );
        assertEquals( sampleMap.get( DonationRecordLogic.HOSPITAL )[ 0 ], returnedDonationRecord.getHospital() );
    }

    @Test
    final void testGetColumnNames() {
        List<String> list = logic.getColumnNames();
        assertEquals( Arrays.asList( "ID", "Tested", "Administrator", "Created", "Hospital" ), list );
    }

    @Test
    final void testGetColumnCodes() {
        List<String> list = logic.getColumnCodes();
        assertEquals( Arrays.asList( DonationRecordLogic.ID, DonationRecordLogic.TESTED, DonationRecordLogic.ADMINISTRATOR, DonationRecordLogic.CREATED, DonationRecordLogic.HOSPITAL ), list );
    }

    @Test
    final void testExtractDataAsList() {
        List<?> list = logic.extractDataAsList( expectedEntity );
        assertEquals( expectedEntity.getId(), list.get( 0 ) );
        assertEquals( expectedEntity.getTested(), list.get( 1 ) );
        assertEquals( expectedEntity.getAdministrator(), list.get( 2 ) );
        assertEquals( expectedEntity.getCreated(), list.get( 3 ) );
        assertEquals( expectedEntity.getHospital(), list.get( 4 ) );
    }
}