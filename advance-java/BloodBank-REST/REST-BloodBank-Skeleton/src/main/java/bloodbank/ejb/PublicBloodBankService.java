/**
 * @name PublicBloodBankService.java
 * @author Samarth Sharma, Masoumeh Mirzaeepour Gelvarzkhah, Abdullah Zeki Ilgun, Dishaben Patel
 * @since 2021-08-10
 */
package bloodbank.ejb;

import static bloodbank.utility.MyConstants.PU_NAME;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bloodbank.entity.BloodDonation;
import bloodbank.entity.PublicBloodBank;

@Singleton
public class PublicBloodBankService {

	private static final Logger LOG = LogManager.getLogger();

	@PersistenceContext(name = PU_NAME)
	protected EntityManager em;
	
	@EJB
	private DonationRecordService donationRecordService;

	public List<PublicBloodBank> getAllPublicBloodBank() {
		return em.createNamedQuery(PublicBloodBank.ALL_PUBLIC_BLOODBANKS_QUERY_NAME).getResultList();
	}

	public Response deletePublicBloodBank(int id) {
		PublicBloodBank publicBloodBank = em.find(PublicBloodBank.class, id);
		if (publicBloodBank == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			em.getTransaction().begin();
			List<BloodDonation> donations = em.createQuery("select d from BloodDonation d left JOIN FETCH d.bank where d.bank.id="+id).getResultList();
			donations.forEach(e->donationRecordService.deleteDonationRecords(e.getId()));
			em.remove(publicBloodBank);
			em.getTransaction().commit();
			return Response.ok().build();
		}
	}

	public Response addPublicBloodBank(PublicBloodBank publicBloodBank) {
		em.getTransaction().begin();
		em.persist(publicBloodBank);
		em.getTransaction().commit();
		return Response.ok(em.find(PublicBloodBank.class, publicBloodBank.getId())).build();
	}

	public Response getPublicBloodBank(int id) {
		PublicBloodBank publicBloodBank = em.find(PublicBloodBank.class, id);
		return Response.status(publicBloodBank == null ? Status.NOT_FOUND : Status.OK).entity(publicBloodBank)
				.build();
	}
}
