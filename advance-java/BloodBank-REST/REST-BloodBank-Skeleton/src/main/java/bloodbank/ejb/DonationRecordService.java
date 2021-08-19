/**
 * @name DonationRecordService.java
 * @author Samarth Sharma, Masoumeh Mirzaeepour Gelvarzkhah, Abdullah Zeki Ilgun, Dishaben Patel
 * @since 2021-08-10
 */
package bloodbank.ejb;

import static bloodbank.utility.MyConstants.PU_NAME;

import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bloodbank.entity.DonationRecord;
@Singleton
public class DonationRecordService {
    
    private static final Logger LOG = LogManager.getLogger();
    
    @PersistenceContext(name = PU_NAME)
    protected EntityManager em;
    
    public List<DonationRecord> getAllDonationRecords() {
    	return em.createNamedQuery(DonationRecord.ALL_RECORDS_QUERY_NAME).getResultList();
    }
    
    public Response deleteDonationRecords(int id) {
		DonationRecord donationRecord = em.find(DonationRecord.class, id);
		if(donationRecord == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			em.getTransaction().begin();
			em.remove(donationRecord);
			em.getTransaction().commit();
			return Response.ok().build();
		}
	}
    
    public Response addDonationRecord(DonationRecord newDonationRecord) {
		em.getTransaction().begin();
		em.persist(newDonationRecord);
		em.getTransaction().commit();
		return Response.ok(em.find(DonationRecord.class, newDonationRecord.getId())).build();
	}
	
	public Response getDonationRecord(int id) {
		DonationRecord donationRecord = em.find(DonationRecord.class, id);
		return Response.status(donationRecord == null? Status.NOT_FOUND:Status.OK).entity(donationRecord).build();
	}
    

}
