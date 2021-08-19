package dal;

import entity.Account;
import entity.BloodBank;
import entity.BloodDonation;
import entity.Person;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author harpa
 */
public class BloodBankDal extends GenericDAL<BloodBank> {


  public BloodBankDal(){  
      super( BloodBank.class );
  }

    @Override
    public List<BloodBank> findAll() {
        return findResults( "BloodBank.findAll", null );
    }

    @Override
    public BloodBank findById(int id) {
        Map<String, Object> map = new HashMap<>();
        map.put( "id", id );
        //first argument is a name given to a named query defined in appropriate entity
        //second argument is map used for parameter substitution.
        //parameters are names starting with : in named queries, :[name]
        //in this case the parameter is named "id" and value for it is put in map
        return findResult( "BloodBank.findByBankId", map );
    }
    public BloodBank  findByName(String name) {
          Map<String, Object> map = new HashMap<>();
          map.put( "name", name );    
          return findResult( "BloodBank.findByName", map );           
      }
    // findByPrivatelyOwned(privatelyOwned : boolean) : List<BloodBank>
    public List<BloodBank> findByPrivatelyOwned(boolean privatelyOwned){
          Map<String, Object> map = new HashMap<>();
          map.put( "privatelyOwned", privatelyOwned );    
          return findResults( "BloodBank.findByPrivatelyOwned" , map ); 
    }

    // +findByEstablished(established : Date) : List<BloodBank>
    public List<BloodBank> findByEstablished(Date established  ){
          Map<String, Object> map = new HashMap<>();
          map.put( "established", established  );    
          return findResults( "BloodBank.findByEstablished" , map ); 
    }

   // +findByEmplyeeCount(emplyeeCount : int) : List<BloodBank>

    public List<BloodBank> findByEmplyeeCount(int employeeCount  ){
         Map<String, Object> map = new HashMap<>();
         map.put( "emplyeeCount", employeeCount  );    
         return findResults( "BloodBank.findByEmplyeeCount" , map );
    }
  // +findByOwner(ownerId : int) : BloodBank

    public BloodBank findByOwner(int ownerId){
        Map<String, Object> map = new HashMap<>();
        map.put( "ownerId", ownerId);    
        return findResult( "BloodBank.findByOwner", map ); 
      }
//+findContaining(search : String) : List<BloodBank>
//     public List<BloodBank> findContaining(String search ){
//       return null;
//       // dont have a named query for that do we have to make one ??????
//  }
  
}// end of class BloodBankDal
