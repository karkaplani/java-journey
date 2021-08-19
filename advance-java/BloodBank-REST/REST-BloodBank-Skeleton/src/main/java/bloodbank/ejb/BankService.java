/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bloodbank.ejb;

import bloodbank.entity.Address;
import bloodbank.entity.BloodBank;
import bloodbank.entity.Person;
import bloodbank.entity.SecurityUser;
import static bloodbank.entity.SecurityUser.USER_FOR_OWNING_PERSON_QUERY;
import static bloodbank.utility.MyConstants.PARAM1;
import static bloodbank.utility.MyConstants.PU_NAME;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Ottawa
 */
 @Singleton
public class BankService implements Serializable{
     private static final long serialVersionUID = 1L;
     private static final Logger LOG = LogManager.getLogger();
    
    @PersistenceContext(name = PU_NAME)
    protected EntityManager em;
    @Inject
    protected Pbkdf2PasswordHash pbAndjPasswordHash;
    public List<BloodBank> getAllBloodBanks() {
    	//Added the following code
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<BloodBank> cq = cb.createQuery(BloodBank.class);
    	cq.select(cq.from(BloodBank.class));
    	return em.createQuery(cq).getResultList();
    	//return null;
    }
    public BloodBank getBloodBankId(int id) {
    	return em.find(BloodBank .class, id);
    	//return null;
    }
     @Transactional
    public BloodBank  persistBloodBank(BloodBank  newBloodBank ) {
    	em.persist(newBloodBank );
    	return newBloodBank ;
    	// return null;
    }
     @Transactional
    public BloodBank  updateBloodBankById(int id, BloodBank  bloodBankWithUpdates) {
       BloodBank bloodBankToBeUpdated = getBloodBankId(id);
        if (bloodBankToBeUpdated != null) {
            em.refresh(bloodBankToBeUpdated);
            em.merge(bloodBankWithUpdates);
            em.flush();
        }
        return bloodBankToBeUpdated;
    }
    @Transactional
    public void deleteBloodBankById(int id) {
        BloodBank bloodBank = getBloodBankId(id);
        if (bloodBank != null) {
            em.refresh(bloodBank);
  
            em.remove(bloodBank);
        }
    
    }
 }

    

