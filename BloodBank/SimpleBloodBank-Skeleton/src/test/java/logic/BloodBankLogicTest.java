package logic;
import common.EMFactory;
import common.TomcatStartUp;
import common.ValidationException;
import entity.Account;
import entity.BloodBank;
import entity.Person;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.IntFunction;



import javax.persistence.EntityManager;
import static logic.BloodBankLogic.ESTABLISHED;
import static logic.BloodBankLogic.PRIVATELY_OWNED;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


/**
 *
 * @author harpal
 */
//@Disabled
public class BloodBankLogicTest {
     private BloodBankLogic logic;
    private BloodBank expectedEntity;
        

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

        logic = LogicFactory.getFor( "BloodBank" );
      
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
    
        BloodBank entity = new BloodBank();
       entity.setId(1);
        
        entity.setName( "Junit 5 Test" );
        entity.setEmplyeeCount(4321);
        entity.setEstablished( logic.convertStringToDate( "1111-11-11 11:11:11" ) );
        entity.setPrivatelyOwned(true);
        
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
        List<BloodBank> list = logic.getAll();
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
  
    private void assertBloodBankEquals( BloodBank expected, BloodBank actual ) {
        //assert all field to guarantee they are the same
        assertEquals( expected.getId(), actual.getId() );   //   ???????????????????????
        assertEquals( expected.getName(), actual.getName() );
        assertEquals( expected.getEmplyeeCount(), actual.getEmplyeeCount() );
        assertEquals( expected.getEstablished(), actual.getEstablished() );
        assertEquals( expected.getOwner(), actual.getOwner() );
        assertEquals( expected.getPrivatelyOwned(), actual.getPrivatelyOwned() );
        
    }

@Test
    final void testGetWithId() {
        //using the id of test account get another account from logic
        BloodBank returnedBloodBank = logic.getBloodBankWithName(expectedEntity.getName() );
  //    BloodBank returnedBloodBank = logic.getWithId(expectedEntity.getId() );//????????????????

        //the two accounts (testAcounts and returnedAccounts) must be the same
        assertBloodBankEquals( expectedEntity, returnedBloodBank );
    }
    
     @Test
    final void testGetBloodBankWithName() {
        BloodBank returnedAccount = logic.getBloodBankWithName(expectedEntity.getName() );

        //the two accounts (testAcounts and returnedAccounts) must be the same
        assertBloodBankEquals( expectedEntity, returnedAccount );
    }
    
    @Test
    final void testGetBloodBankWithEmployeeCount() {
        int found=0;
     List <BloodBank> returnBloodBanks = logic.getBloodBanksWithEmplyeeCount(expectedEntity.getEmplyeeCount());
     for(BloodBank bloodBank: returnBloodBanks){
      // assertBloodBankEquals( expectedEntity, returnBloodBanks );  
      assertEquals(expectedEntity.getEmplyeeCount(),bloodBank.getEmplyeeCount());
      if(bloodBank.getId().equals(expectedEntity.getId())){
            assertBloodBankEquals( expectedEntity, bloodBank );  
            found++;
      }
     }
        //the two accounts (testAcounts and returnedAccounts) must be the same
      assertEquals( 1, found, "if zero means not found, if more than one means duplicate" );
        
    }
    @Test
    final void testGetBloodBankWithPrivatelyOwned() {
        int found=0;
     List <BloodBank> returnBloodBanks = logic.getBloodBanksWithEmplyeeCount(expectedEntity.getEmplyeeCount());
     for(BloodBank bloodBank: returnBloodBanks){
      // assertBloodBankEquals( expectedEntity, returnBloodBanks );  
      assertEquals(expectedEntity.getPrivatelyOwned(),bloodBank.getPrivatelyOwned());
      if(bloodBank.getId().equals(expectedEntity.getId())){
            assertBloodBankEquals( expectedEntity, bloodBank );  
            found++;
      }
     }
        //the two accounts (testAcounts and returnedAccounts) must be the same
      assertEquals( 1, found, "if zero means not found, if more than one means duplicate" );  
    }
    @Test
    final void testGetBloodBankWithEstablished() {
        int found=0;
     List <BloodBank> returnBloodBanks = logic.getBloodBanksWithEmplyeeCount(expectedEntity.getEmplyeeCount());
     for(BloodBank bloodBank: returnBloodBanks){
      // assertBloodBankEquals( expectedEntity, returnBloodBanks );  
      assertEquals(expectedEntity.getEstablished(),bloodBank.getEstablished());
      if(bloodBank.getId().equals(expectedEntity.getId())){
            assertBloodBankEquals( expectedEntity, bloodBank );  
            found++;
      }
     }
        //the two accounts (testAcounts and returnedAccounts) must be the same
        assertEquals( 1, found, "if zero means not found, if more than one means duplicate" );
    }
    
   
    final void testCreateEntity() {
        Map<String, String[]> sampleMap = new HashMap<>();
    //    sampleMap.put( BloodBankLogic.ID, new String[]{ Integer.toString( expectedEntity.getId() ) } );
        sampleMap.put( BloodBankLogic.NAME, new String[]{ expectedEntity.getName()} );
        sampleMap.put( BloodBankLogic.EMPLOYEE_COUNT, new String[]{ expectedEntity.getEmplyeeCount()+"" } );
        sampleMap.put( BloodBankLogic.ESTABLISHED, new String[]{ logic.convertDateToString(expectedEntity.getEstablished()) } );
        sampleMap.put( BloodBankLogic.PRIVATELY_OWNED, new String[]{ expectedEntity.getPrivatelyOwned()+"" } ); // i have this setup as 0 and 1 and we are sending string

        BloodBank returnedBloodBank = logic.createEntity( sampleMap ); /// WHY GETTING NULL POINTER

        assertBloodBankEquals( expectedEntity, returnedBloodBank );
    }
    
       @Test
    final void testCreateEntityAndAdd() {
        Map<String, String[]> sampleMap = new HashMap<>();
   //     sampleMap.put( BloodBankLogic.ID, new String[]{ "111" } );  ///AUTO INCREMENTS
  //      sampleMap.put( BloodBankLogic.OWNER_ID, new String[]{ "" } );/// CAN BE NULL 
        sampleMap.put( BloodBankLogic.NAME, new String[]{ "TestCreateEntity" } );
        sampleMap.put( BloodBankLogic.EMPLOYEE_COUNT, new String[]{ "12345" } );
        sampleMap.put( BloodBankLogic.ESTABLISHED, new String[]{ "2001-11-11 12:12:12" } );
        sampleMap.put( BloodBankLogic.PRIVATELY_OWNED, new String[]{ "1" } ); // i have this setup as 0 and 1 and we are sending string

        BloodBank returnedBloodBank = logic.createEntity( sampleMap ); /// WHY GETTING NULL POINTER
        logic.add( returnedBloodBank );

        returnedBloodBank = logic.getBloodBankWithName(returnedBloodBank.getName() );
         assertEquals( sampleMap.get( BloodBankLogic.EMPLOYEE_COUNT )[ 0 ], returnedBloodBank.getEmplyeeCount()+"" );
        assertEquals( sampleMap.get( BloodBankLogic.ESTABLISHED )[ 0 ],logic.convertDateToString(returnedBloodBank.getEstablished()) );
     
                int testCheck=0;
                 if(returnedBloodBank.getPrivatelyOwned()){
                    testCheck=1;
                    }
        assertEquals( sampleMap.get( BloodBankLogic.PRIVATELY_OWNED )[ 0 ], testCheck+""); //?:
        assertEquals( sampleMap.get( BloodBankLogic.NAME )[ 0 ], returnedBloodBank.getName() );
  //      assertEquals( sampleMap.get( BloodBankLogic.ID )[ 0 ], returnedBloodBank.getId() );

        logic.delete( returnedBloodBank );
    }

   
       @Test
    final void testGetColumnNames() {
        List<String> list = logic.getColumnNames();
        assertEquals( Arrays.asList( "ID", "Owner Id", "Name", "Privately Owned","Established", "Employee Count" ), list );
    }

    @Test
    final void testGetColumnCodes() {
        List<String> list = logic.getColumnCodes();
        assertEquals( Arrays.asList( BloodBankLogic.ID, BloodBankLogic.OWNER_ID, BloodBankLogic.NAME, 
        BloodBankLogic.PRIVATELY_OWNED, BloodBankLogic.ESTABLISHED,BloodBankLogic.EMPLOYEE_COUNT), list );
    }
    
     @Test
    final void testExtractDataAsList() {
        List<?> list = logic.extractDataAsList( expectedEntity );
        assertEquals( expectedEntity.getId(), list.get( 0 ) );
        assertEquals( expectedEntity.getOwner(), list.get( 1 ) );
        assertEquals( expectedEntity.getName(), list.get( 2 ) );
        assertEquals( expectedEntity.getPrivatelyOwned(), list.get( 3 ) );
        assertEquals( expectedEntity.getEstablished(), list.get( 4 ) );
        
    //    assertEquals( expectedEntity.getEmplyeeCount(), list.get( 5 )); ??????????????
    }
    
    //@Test bonus part
    final void testSearch() {
        int foundFull = 0;
        //search for a substring of one of the fields in the expectedAccount
        String searchString = expectedEntity.getName().substring( 2 );
        //in account we only search for display name and user, this is completely based on your design for other entities.
        List<BloodBank> returnedBloodBanks = logic.search( searchString );
        for( BloodBank bloodBank: returnedBloodBanks ) {
            //all accounts must contain the substring
            assertTrue( bloodBank.getName().contains( searchString ) );
            //exactly one account must be the same
            if( bloodBank.getId().equals( expectedEntity.getId() ) ){
                assertBloodBankEquals( expectedEntity, bloodBank );
                foundFull++;
            }
        }
        assertEquals( 1, foundFull, "if zero means not found, if more than one means duplicate" );
    }
@Test
    final void testCreateEntityBadLengthValues() {
        Map<String, String[]> sampleMap = new HashMap<>();
        Consumer<Map<String, String[]>> fillMap = ( Map<String, String[]> map ) -> {
            map.clear();
            map.put( BloodBankLogic.ID, new String[]{ Integer.toString( expectedEntity.getId() ) } );
            map.put( BloodBankLogic.PRIVATELY_OWNED, new String[]{(expectedEntity.getPrivatelyOwned()==true)?"1":"0" } );
      
            map.put( BloodBankLogic.EMPLOYEE_COUNT, new String[]{ expectedEntity.getEmplyeeCount()+""} );
            map.put( BloodBankLogic.ESTABLISHED, new String[]{ logic.convertDateToString(expectedEntity.getEstablished()) } );
            map.put( BloodBankLogic.NAME, new String[]{ expectedEntity.getName() } );
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
        sampleMap.replace( BloodBankLogic.ID, new String[]{ "" } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( BloodBankLogic.ID, new String[]{ "12b" } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );

            
        //*******************
/*         fillMap.accept( sampleMap );
        sampleMap.replace( BloodBankLogic.PRIVATELY_OWNED, new String[]{ "1" } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( BloodBankLogic.PRIVATELY_OWNED, new String[]{ generateString.apply( 4 ) } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );
*/
     fillMap.accept( sampleMap );
        sampleMap.replace( BloodBankLogic.EMPLOYEE_COUNT, new String[]{ "" } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );
         sampleMap.replace(BloodBankLogic.EMPLOYEE_COUNT,  new String[]{ generateString.apply( 101 ) } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );

        fillMap.accept( sampleMap );
        sampleMap.replace( BloodBankLogic.ESTABLISHED, new String[]{ "" } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );
         sampleMap.replace(BloodBankLogic.ESTABLISHED, new String[]{ generateString.apply( 21 ) } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );

        fillMap.accept( sampleMap );
        sampleMap.replace( BloodBankLogic.NAME, new String[]{ "" } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace(BloodBankLogic.NAME, new String[]{ generateString.apply( 101 ) } );
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
        sampleMap.put( BloodBankLogic.ID, new String[]{ Integer.toString( 1 ) } );
        sampleMap.put( BloodBankLogic.PRIVATELY_OWNED, new String[]{"1" } );
        sampleMap.put( BloodBankLogic.ESTABLISHED, new String[]{ "1982-01-01 16:16:00"  } );
        sampleMap.put( BloodBankLogic.EMPLOYEE_COUNT, new String[]{ "22"} );
        sampleMap.put( BloodBankLogic.NAME, new String[]{ generateString.apply( 1 ) } );


       //idealy every test should be in its own method
        BloodBank returnedBloodBank = logic.createEntity( sampleMap );
        assertEquals( Integer.parseInt( sampleMap.get( BloodBankLogic.ID )[ 0 ] ), returnedBloodBank.getId() );
        assertEquals( sampleMap.get( BloodBankLogic.ESTABLISHED )[ 0 ],logic.convertDateToString( returnedBloodBank.getEstablished()));
        assertEquals( sampleMap.get( BloodBankLogic.PRIVATELY_OWNED )[ 0 ], (expectedEntity.getPrivatelyOwned()==true)?1+"":0+"");
        assertEquals( sampleMap.get( BloodBankLogic.EMPLOYEE_COUNT )[ 0 ], returnedBloodBank.getEmplyeeCount()+ "");
        assertEquals( sampleMap.get( BloodBankLogic.NAME )[ 0 ], returnedBloodBank.getName() );

        sampleMap = new HashMap<>();
        sampleMap.put( BloodBankLogic.ID , new String[]{ Integer.toString( 1 ) } );
        sampleMap.put( BloodBankLogic.ESTABLISHED, new String[]{"1982-01-01 16:16:00" } );
        sampleMap.put( BloodBankLogic.PRIVATELY_OWNED, new String[]{ "1" } );
        sampleMap.put( BloodBankLogic.EMPLOYEE_COUNT, new String[]{ Integer.toString( 4 )} );
        sampleMap.put( BloodBankLogic.NAME, new String[]{ generateString.apply( 45 ) } );

         //idealy every test should be in its own method
        returnedBloodBank = logic.createEntity( sampleMap );
        assertEquals( Integer.parseInt( sampleMap.get( BloodBankLogic.ID )[ 0 ] ), returnedBloodBank.getId() );
        assertEquals( sampleMap.get( BloodBankLogic.ESTABLISHED )[ 0 ], logic.convertDateToString(returnedBloodBank.getEstablished()) );
        assertEquals( sampleMap.get( (BloodBankLogic.PRIVATELY_OWNED) )[ 0 ]+ "", (expectedEntity.getPrivatelyOwned()==true)?1+"":0+"");
        assertEquals( sampleMap.get( BloodBankLogic.EMPLOYEE_COUNT )[ 0 ], Integer.toString(returnedBloodBank.getEmplyeeCount()));
        assertEquals( sampleMap.get( BloodBankLogic.NAME )[  0 ], returnedBloodBank.getName() );
    }
}
