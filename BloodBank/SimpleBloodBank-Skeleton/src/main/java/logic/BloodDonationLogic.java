package logic;
import common.ValidationException;
import entity.BloodDonation;
import dal.BloodDonationDAL;
import entity.BloodGroup;
import entity.RhesusFactor;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 *
 * @author Abdullah Ilgun
 * This class is the logic class for adding and saving the blood donation values 
 * with bunch of getters as well as a create entity method. Used in blood donation
 * table view and create blood donation classes to create an object of it
 */
public class BloodDonationLogic extends GenericLogic<BloodDonation, BloodDonationDAL>{
    
    //Static variables to use them again instead of writing over and over
    
    public static final String ID = "id";
    public static final String BANK_ID = "bank_id";
    public static final String MILILITERS = "mililiters";
    public static final String BLOOD_GROUP = "blood_group";
    public static final String FACTOR = "rhesus_factor";
    public static final String CREATED = "created";
    
    BloodDonationLogic() {
        super(new BloodDonationDAL());
    }
    
    @Override
    public List<BloodDonation> getAll() {
        return get(() -> dal().findAll());
    }
    
    @Override
    public BloodDonation getWithId(int id) {
        return get(() -> dal().findById(id));
    }
    
    public List<BloodDonation> getBloodDonationWithMilliliters(int mililiters) {
        return get(() -> dal().findByMililiters(mililiters));
    }
    
    public List<BloodDonation> getBloodDonationWithBloodGroup(BloodGroup bloodGroup) {
        return get(() -> dal().findByBloodGroup(bloodGroup));
    }
    
    public List<BloodDonation> getBloodDonationWithCreated(Date created) {
        return get(() -> dal().findByCreated(created));
    }
    
    public List<BloodDonation> getBloodDonationsWithRhd(RhesusFactor rhd) {
        return get(() -> dal().findByRhd(rhd));
    }
    
    public List<BloodDonation> getBloodDonationsWithBloodBank(int bankId) {
        return get(() -> dal().findByBloodBank(bankId));
    }
    
    /**
     * An object o blood donation class is created and, the values needed are set to that 
     * object using its setters. Some of the values are validated before setting, and finally
     * after all the values are set, it returns that blood donation object. The method is used
     * in create blood donation class to create a new entity.
     * @param parameterMap
     * @return entity
     */
    @Override
    public BloodDonation createEntity(Map<String,String[]> parameterMap) {
        
        Objects.requireNonNull( parameterMap, "parameterMap cannot be null" );
        BloodDonation entity = new BloodDonation();
        
        if(parameterMap.containsKey(ID)){ 
            try {
                entity.setId(Integer.parseInt(parameterMap.get(ID)[0])); //donation_id is set here
            } catch(java.lang.NumberFormatException ex) {
                throw new ValidationException( ex );
            }
        }
        
        BloodGroup bloodGroup = BloodGroup.valueOf(parameterMap.get(BLOOD_GROUP)[0]);
        entity.setBloodGroup(bloodGroup);
        
        int mililiters = Integer.parseInt(parameterMap.get(MILILITERS)[0]);
        entity.setMilliliters(mililiters);
        
        RhesusFactor rhd = RhesusFactor.getRhesusFactor(parameterMap.get(FACTOR)[0]); 
        entity.setRhd(rhd);
        
        //Date created = new Date();
        //String dateString = convertDateToString(created);
        
        String createdString = parameterMap.get( CREATED )[ 0 ];
        createdString = createdString.replace("T", " 00:00:00");
        
        //created = convertStringToDate(parameterMap.get(CREATED)[0]);
        entity.setCreated(convertStringToDate(createdString));  
        
        return entity; 
    }
    
    @Override
    public List<String> getColumnNames() {
        return Arrays.asList("donation_id", "bank_id", "milliliters", "blood_group", "rhd", "created");
    }
    
    @Override
    public List<String> getColumnCodes() {
        return Arrays.asList(ID, BANK_ID, MILILITERS, BLOOD_GROUP, FACTOR, CREATED);
    }
    
    /**
     * The data to display in the table is set here. Firstly, the blood bank id is checked 
     * and set if it's null or not as it's the only value nullable, then a list if populated and
     * returned with all the values.
     * @param e
     * @return 
     */
    @Override
    public List<?> extractDataAsList(BloodDonation e) {

        Integer bankID;
        
        if(e.getBloodBank() != null) {
            bankID = e.getBloodBank().getId();
        } else {
            bankID = null;
        }
        
        return Arrays.asList(e.getId(), bankID, e.getMilliliters(), e.getBloodGroup(), e.getRhd(), e.getCreated());
    }
}
