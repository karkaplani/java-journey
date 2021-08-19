package dal;

import entity.Person;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mjuli
 */
public class PersonDAL extends GenericDAL<Person> {

    public PersonDAL() {
        super( Person.class );
    }

    @Override
    public List<Person> findAll() {
        //first argument is a name given to a named query defined in appropriate entity
        //second argument is map used for parameter substitution.
        //parameters are names starting with : in named queries, :[name]
        return findResults( "Person.findAll", null );
    }

    @Override
    public Person findById( int id ) {
        Map<String, Object> map = new HashMap<>();
        map.put( "id", id );
        //first argument is a name given to a named query defined in appropriate entity
        //second argument is map used for parameter substitution.
        //parameters are names starting with : in named queries, :[name]
        //in this case the parameter is named "id" and value for it is put in map
        return findResult( "Person.findById", map );
    }

    /**
     * retrieves the persons that have the same first name
     * @param firstName - the first names of the persons that you want to retrieve
     * @return a list of persons 
     */
    public List<Person> findByFirstName( String firstName ) {
        Map<String, Object> map = new HashMap<>();
        map.put( "firstName", firstName );
        return findResults( "Person.findByFirstName", map );
    }

    /**
     * retrieves the persons that have the same last name
     * @param lastName - the last names of the persons that you want to retrieve
     * @return a list of persons 
     */
    public List<Person> findByLastName( String lastName ) {
        Map<String, Object> map = new HashMap<>();
        map.put( "lastName", lastName );
        return findResults( "Person.findByLastName", map );
    }

    /**
     * retrieves the persons that have the same phone
     * @param phone - the phones of the persons that you want to retrieve
     * @return a list of persons 
     */
    public List<Person> findByPhone( String phone ) {
        Map<String, Object> map = new HashMap<>();
        map.put( "phone", phone );
        return findResults( "Person.findByPhone", map );
    }

    /**
     * retrieves the persons that have the same address
     * @param address - the address of the persons that you want to retrieve
     * @return a list of persons 
     */
    public List<Person> findByAddress( String address ) {
        Map<String, Object> map = new HashMap<>();
        map.put( "address", address );
        return findResults( "Person.findByAddress", map );
    }

    /**
     * retrieves the persons that have the same birth date
     * @param birth - the birth date of the persons that you want to retrieve
     * @return a list of persons 
     */
    public List<Person> findByBirth( Date birth ) {
        Map<String, Object> map = new HashMap<>();
        map.put( "birth", birth );
        return findResults( "Person.findByBirth", map );
    }

}
