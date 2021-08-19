package logic;

import common.ValidationException;
import dal.PersonDAL;
import entity.Person;
import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.sql.Date;
import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.function.ObjIntConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mjuli
 */
public class PersonLogic extends GenericLogic<Person, PersonDAL> {

    /**
     * create static final variables with proper name of each column. this way you will never manually type it again,
     * instead always refer to these variables.
     *
     * by using the same name as column id and HTML element names we can make our code simpler. this is not recommended
     * for proper production project.
     */
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String PHONE = "phone";
    public static final String ADDRESS = "address";
    public static final String BIRTH = "birth";
    public static final String ID = "id";

    PersonLogic() {
        super( new PersonDAL() );
    }

   
    @Override
    public List<Person> getAll() {
        return get( () -> dal().findAll() );
    }

  
    @Override
    public Person getWithId( int id ) {
        return get( () -> dal().findById( id ) );
    }

    public List<Person> getPersonWithPhone( String phone ) {
        return get( () -> dal().findByPhone( phone ) );
    }

    public List<Person> getPersonWithFirstName( String firstName ) {
        return get( () -> dal().findByFirstName( firstName ) );
    }

    public List<Person> getPersonWithLastName( String lastName ) {
        return get( () -> dal().findByLastName( lastName ) );
    }

    public List<Person> getPersonsWithAddress( String address ) {
        return get( () -> dal().findByAddress( address ) );
    }

    public List<Person> getPersonsWithBirth( Date birth ) {
        return get( () -> dal().findByBirth(birth ));
    }

    @Override
    public Person createEntity( Map<String, String[]> parameterMap ) {
        //do not create any logic classes in this method.

        Objects.requireNonNull( parameterMap, "parameterMap cannot be null" );

        //create a new Entity object
        Person entity = new Person();

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


        String firstName = parameterMap.get( FIRST_NAME )[ 0 ];
        String lastName = parameterMap.get( LAST_NAME )[ 0 ];
        String phone = parameterMap.get( PHONE )[ 0 ];
        String address = parameterMap.get( ADDRESS )[ 0 ];
        String birth = parameterMap.get( BIRTH )[ 0 ];
        

        //validate the data
        validator.accept( firstName, 45 );
        validator.accept( lastName, 45 );
        validator.accept( address, 45 );
       

        //set values on entity
        entity.setFirstName( firstName );
        entity.setLastName( lastName );
        entity.setPhone( phone );
        entity.setAddress( address );
        birth = birth.replace("T", " ");
        entity.setBirth(convertStringToDate(birth));
//        
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
        return Arrays.asList( "ID", "First Name", "Last Name",  "Phone", "Address","Birth" );
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
        return Arrays.asList( ID, FIRST_NAME, LAST_NAME, PHONE, ADDRESS, BIRTH );
         
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
    @Override
    public List<?> extractDataAsList( Person e ) {
        return Arrays.asList( e.getId(), e.getFirstName(), e.getLastName(), e.getPhone(), e.getAddress(),  e.getBirth());
    }
}

