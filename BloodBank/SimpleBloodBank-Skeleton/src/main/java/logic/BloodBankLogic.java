package logic;

import common.ValidationException;
import dal.BloodBankDal;
import entity.BloodBank;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.ObjIntConsumer;


/**
 *
 * @author harpa
 */
public class BloodBankLogic extends GenericLogic<BloodBank, BloodBankDal> {
   
public static final String OWNER_ID = "owner";
public static final String PRIVATELY_OWNED = "privately_owned";
public static final String ESTABLISHED  = "established";
public static final String NAME  = "name";
public static final String EMPLOYEE_COUNT  = "employee_count";
public static final String ID  = "bank_id";
 
  public BloodBankLogic() {
      super(new BloodBankDal());
  }
 
   //  getAll() : List<BloodBank>
    @Override
    public List<BloodBank> getAll() {
     //  dal() from GenericLogic Class and findAll from BloodBankDal Class
     return get( () -> dal().findAll() );
    }
    
    // +getWithId(id : int) : BloodBank
    @Override
    public BloodBank getWithId(int id) {
      return get( () -> dal().findById( id ) );
    }
    
  
  // +getBloodBankWithName(name : String) : BloodBank
public BloodBank getBloodBankWithName(String name ){
    return get( () -> dal().findByName( name ) );
    
}

// +getBloodBankWithPrivatelyOwned(privatelyOwned : boolean) : List<BloodBank>
public List<BloodBank> getBloodBankWithPrivatelyOwned(Boolean privatelyOwned ){
   return get( () -> dal().findByPrivatelyOwned( privatelyOwned ) );
    
}
// +getBloodBankWithEstablished(established : Date) : List<BloodBank>

public List<BloodBank> getBloodBankWithEstablished(Date established){
  return get( () -> dal().findByEstablished( established ) );
    
}

// +getBloodBanksWithOwner(ownerId : int) : BloodBank
public BloodBank getBloodBanksWithOwner(int ownerId ){
    return get( () -> dal().findByOwner( ownerId ) );
    
}
   // +getBloodBanksWithEmplyeeCount(count : int) : List<BloodBank>

public List<BloodBank> getBloodBanksWithEmplyeeCount(int count){
   return get( () -> dal().findByEmplyeeCount(count ) );
   
}

//+getColumnNames() : List<String>
    @Override
    
    public List<String> getColumnNames() {
      return Arrays.asList( "ID", "Owner Id", "Name", "Privately Owned","Established", "Employee Count");
      
    }
// +getColumnCodes() : List<String>
    @Override
    public List<String> getColumnCodes() {
        return Arrays.asList( ID, OWNER_ID, NAME, PRIVATELY_OWNED , ESTABLISHED, EMPLOYEE_COUNT);
   
    }
    
//+extractDataAsList(e : BloodBank) : List<?>
    @Override
    public List<?> extractDataAsList(BloodBank e) {
  
    return Arrays.asList( e.getId(), e.getOwner(), e.getName(), e.getPrivatelyOwned(), e.getEstablished(), String.valueOf(e.getEmplyeeCount()) );
    }

  // +createEntity(parameterMap : Map<String, String[]>) : BloodBank
    @Override
    public BloodBank createEntity(Map<String, String[]> parameterMap) {
      //do not create any logic classes in this method.
      System.out.println(parameterMap);

//        return new AccountBuilder().SetData( parameterMap ).build();
        Objects.requireNonNull( parameterMap, "parameterMap cannot be null" );
        //same as if condition below
//        if (parameterMap == null) {
//            throw new NullPointerException("parameterMap cannot be null");
//        }

        //create a new Entity object
        BloodBank entity = new BloodBank();

        //ID is generated, so if it exists add it to the entity object
        //otherwise it does not matter as mysql will create an if for it.
        //the only time that we will have id is for update behaviour.
   // bank_id is the primary key
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
     
        String name = null;
        if( parameterMap.containsKey( NAME ) ){
            name = parameterMap.get( NAME )[ 0 ];
            validator.accept( name, 100 );
        }
        
//        String ownerId = parameterMap.get(OWNER_ID  )[ 0 ]; // sure if we need this
        String privatelyOwned = parameterMap.get(PRIVATELY_OWNED   )[ 0 ];
        
        String established = parameterMap.get( ESTABLISHED )[ 0 ];
        String employeeCount = parameterMap.get( EMPLOYEE_COUNT )[ 0 ];

        //setting privately owned to true or false
        boolean privatelyOwned1= false;
          if(Integer.parseInt( privatelyOwned)==1){
        
                  privatelyOwned1=true;          
          }
        validator.accept(established, 20);
          // validating the employee count can be over 10 else will be long
        validator.accept( employeeCount, 10 );
         //set values on entity
        entity.setEmplyeeCount(Integer.parseInt(employeeCount));
        entity.setPrivatelyOwned(privatelyOwned1);
        entity.setName(name);
        established = established.replace("T", " ");
        entity.setEstablished(convertStringToDate(established));
 
        return entity;
    }

}
