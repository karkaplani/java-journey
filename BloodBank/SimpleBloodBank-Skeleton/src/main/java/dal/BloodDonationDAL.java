package dal;

import entity.BloodDonation;
import entity.BloodGroup;
import entity.RhesusFactor;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data access layer of the blood donation. Simply a blood donation
 * object is gotten by using that class's methods.
 * 
 * @author Abdullah Ilgun
 */
public class BloodDonationDAL extends GenericDAL<BloodDonation> {
    
    public BloodDonationDAL() {
        super(BloodDonation.class);
    }
    
    @Override
    public List<BloodDonation> findAll() {
        return findResults( "BloodDonation.findAll", null );
    }
    
    @Override
    public BloodDonation findById(int donationId) {
        Map<String, Object> map = new HashMap<>();
        map.put( "donationId", donationId);
        return findResult("BloodDonation.findByDonationId", map);
    }
    
    public List<BloodDonation> findByMililiters(int mililiters) {
        Map<String, Object> map = new HashMap<>();
        map.put("mililiters", mililiters);
        return findResults("BloodDonation.findByMililiters", map);
    }
    
    public List<BloodDonation> findByBloodGroup(BloodGroup bloodGroup) {
        Map<String, Object> map = new HashMap<>();
        map.put("bloodGroup", bloodGroup);
        return findResults("BloodDonation.findByBloodGroup", map);
    }
    
    public List<BloodDonation> findByRhd(RhesusFactor rhd) {
        Map<String, Object> map = new HashMap<>();
        map.put("rhd", rhd);
        return findResults("BloodDonation.findByRhd", map);
    }
    
    public List<BloodDonation> findByCreated(Date created) {
        Map<String, Object> map = new HashMap<>();
        map.put("created", created);
        return findResults("BloodDonation.findByCreated", map);
    }
    
    public List<BloodDonation> findByBloodBank(int bloodBankId) {
        Map<String, Object> map = new HashMap<>();
        map.put("bloodBankId", bloodBankId);
        return findResults("BloodDonation.findByBloodBank", map);
    }
}
