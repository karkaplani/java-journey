package logic;

import common.ValidationException;
import dal.DonationRecordDAL;
import entity.DonationRecord;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.ObjIntConsumer;

/**
 *
 * @author APZ
 */
public class DonationRecordLogic extends GenericLogic<DonationRecord, DonationRecordDAL> {

    public static final String ID = "id";
    public static final String TESTED = "tested";
    public static final String ADMINISTRATOR = "administrator";
    public static final String HOSPITAL = "hospital";
    public static final String PERSON_ID = "person_id";
    public static final String DONATION_ID = "donation_id";
    public static final String CREATED = "created";
    

    DonationRecordLogic() {
        super( new DonationRecordDAL() );
    }

    @Override
    public List<DonationRecord> getAll() {
        return get( () -> dal().findAll() );
    }

    @Override
    public DonationRecord getWithId( int id ) {
        return get( () -> dal().findById( id ) );
    }

    public DonationRecord getDonationRecordWithTested( boolean tested ) {
        return get( () -> dal().findByTested( tested ) );
    }

    public List<DonationRecord> getDonationRecordWithAdministrator( String administrator ) {
        return get( () -> dal().findByAdministrator( administrator ) );
    }
    
    public List<DonationRecord> getDonationRecordWithHospital( String username ) {
        return get( () -> dal().findByHospital( username ) );
    }
    
    public List<DonationRecord> getDonationRecordWithCreated( Date created) {
        return get( () -> dal().findByCreated( created ) );
    }

    public DonationRecord getDonationRecordWithPerson( int personId ) {
        return get( () -> dal().findByPerson( personId ) );
    }
    
    public DonationRecord getDonationRecordWithDonation( int donationId ) {
        return get( () -> dal().findByDonation( donationId ) );
    }
   

    @Override
    public DonationRecord createEntity( Map<String, String[]> parameterMap ) {
        //do not create any logic classes in this method.

//       return new DonationRecordBuilder().SetData( parameterMap ).build();
        Objects.requireNonNull( parameterMap, "parameterMap cannot be null" );
        //same as if condition below
//        if (parameterMap == null) {
//            throw new NullPointerException("parameterMap cannot be null");
//        }

        //create a new Entity object
        DonationRecord entity = new DonationRecord();

        //ID is generated, so if it exists add it to the entity object
        //otherwise it does not matter as mysql will create an if for it.
        //the only time that we will have id is for update behaviour.
        if( parameterMap.containsKey( ID ) ){
            try {
                entity.setId( Integer.parseInt( parameterMap.get( ID )[ 0 ] ) );
            } catch( java.lang.NumberFormatException ex ) {
                throw new ValidationException( ex );
            }
        }

        //before using the values in the map, make sure to do error checking.
        //simple lambda to validate a string, this can also be place in another
        //method to be shared amoung all logic classes.
        ObjIntConsumer< String> validator = ( value, length ) -> {
            if( value == null || value.trim().isEmpty() || value.length() > length ){
                String error = "";
                if( value == null || value.trim().isEmpty() ){
                    error = "value cannot be null or empty: " + value;
                }
                if( value.length() > length ){
                    error = "string length is " + value.length() + " > " + length;
                }
                throw new ValidationException( error );
            }
        };

        //extract the date from map first.
        //everything in the parameterMap is string so it must first be
        //converted to appropriate type. have in mind that values are
        //stored in an array of String; almost always the value is at
        //index zero unless you have used duplicated key/name somewhere.
        String displayname = null;
        if( parameterMap.containsKey( DONATION_ID ) ){
            displayname = parameterMap.get( ID )[ 0 ];
            validator.accept( displayname, 0);
        }
        String person = parameterMap.get( PERSON_ID )[ 0 ];
        String tested = parameterMap.get( TESTED )[ 0 ];
        String administrator = parameterMap.get( ADMINISTRATOR )[ 0 ];
        String hospital = parameterMap.get( HOSPITAL )[ 0 ];
        String created = parameterMap.get( CREATED )[ 0 ];
       String bloodDonation = parameterMap.get( DONATION_ID )[ 0 ];
 //       String id = parameterMap.get( ID )[ 0 ];
        boolean testYes= false;
          if(Integer.parseInt( tested)==1){
        
                  testYes=true;          
          }
   System.out.println("tttt"+ tested);
          validator.accept(created, 20);
       //validate the data
        validator.accept( person, 45 );
     //   validator.accept( tested, 1 );
        validator.accept( administrator, 100 );
        validator.accept( hospital, 100 );
        validator.accept( created, 20);
   //     validator.accept( bloodDonation, 1);
     //   validator.accept(id, 1);

        //set values on entity
   //     entity.setId( recordId );
     //   entity.setPerson(person);
     //   entity.setBloodDonation(bloodDonation);
        entity.setPerson(null);
        entity.setBloodDonation(null);
        entity.setTested( testYes );
        entity.setAdministrator( administrator );
        entity.setHospital( hospital );
        created = created.replace("T", " ");
        entity.setCreated(convertStringToDate(created));
 //     entity.setCreated( convertStringToDate(created) );

        return entity;
    }

   
    /**
     * this method is used to send a list of all names to be used form table column headers. by having all names in one
     * location there is less chance of mistakes.
     *
     * this list must be in the same order as getColumnCodes and extractDataAsList
     *
     * @return list of all column names to be displayed.
     */
    @Override
    public List<String> getColumnNames() {
        return Arrays.asList( "Person", "Donation ID", "Tested", "Administrator", "Hospital", "Created", "RecordId");
        
    }

    /**
     * this method returns a list of column names that match the official column names in the db. by having all names in
     * one location there is less chance of mistakes.
     *
     * this list must be in the same order as getColumnNames and extractDataAsList
     *
     * @return list of all column names in DB.
     */
    @Override
    public List<String> getColumnCodes() {
        return Arrays.asList( PERSON_ID, DONATION_ID, TESTED, ADMINISTRATOR, HOSPITAL, CREATED, ID );
    }

   
    /**
     * return the list of values of all columns (variables) in given entity.
     *
     * this list must be in the same order as getColumnNames and getColumnCodes
     *
     * @param e - given Entity to extract data from.
     *
     * @return list of extracted values
     */
  //  @Override
   // public List<?> extractDataAsList( DonationRecord e ) {
   //     return Arrays.asList( e.getId(), e.getPerson(), e.getBloodDonation(), e.getTested(), e.getAdministrator(), e.getHospital(), e.getCreated(), e.getPerson() );
   // }
     @Override
    public List<?> extractDataAsList( DonationRecord e ) {
        return Arrays.asList( e.getPerson(), e.getBloodDonation(), e.getTested(), e.getAdministrator(), e.getHospital(), e.getCreated(),e.getId()  );
    }
}
