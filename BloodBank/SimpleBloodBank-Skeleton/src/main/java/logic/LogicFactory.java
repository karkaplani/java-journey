package logic;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class LogicFactory {

    private static final String PACKAGE = "logic.";
    private static final String SUFFIX = "Logic";

    private LogicFactory() {
      
    }

    //TODO this code is not complete, it is just here for sake of programe working. need to be changed ocmpletely
    public static < T> T getFor( String entityName ) {
 /*       if(entityName.equals("BloodBank")){
             return (T)new BloodBankLogic();
        } else if (entityName.equals("Person")){
            return (T)new AccountLogic(); // have to change to person logic
        }
        else {
    
             return (T)new AccountLogic();
        }*/
 try {
      T newInstance = getFor((Class<T>)Class.forName(PACKAGE + entityName + SUFFIX )); 
      return newInstance;
 } 
 catch (ClassNotFoundException cne){
     throw new IllegalArgumentException(cne + entityName);
 }

    } // end of getFor
    public static <R> R getFor(Class<R> type){
        try{
             Constructor<R> declaredConstructor = type.getDeclaredConstructor();
             R newInstance = declaredConstructor.newInstance();
             return newInstance;
             
        }catch(InstantiationException | IllegalAccessException | IllegalArgumentException |
InvocationTargetException | NoSuchMethodException | SecurityException ie) {
            throw new IllegalArgumentException(ie + "type not found");
            
        }
        
    }
}
