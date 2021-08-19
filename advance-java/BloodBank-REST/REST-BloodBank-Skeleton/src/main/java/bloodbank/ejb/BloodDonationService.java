/**
 * @name BloodDonationService.java
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

import bloodbank.entity.BloodDonation;

@Singleton
public class BloodDonationService {

	private static final Logger LOG = LogManager.getLogger();

	@PersistenceContext(name = PU_NAME)
	protected EntityManager em;

	public List<BloodDonation> getBloodDonation() {
		return em.createNamedQuery(BloodDonation.FIND_ALL_QUERY).getResultList();
	}

	public Response deleteBloodDonation(int id) {
		BloodDonation bloodDonation = em.find(BloodDonation.class, id);
		if (bloodDonation == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			em.getTransaction().begin();
			em.remove(bloodDonation);
			em.getTransaction().commit();
			return Response.ok().build();
		}
	}

	public Response addBloodDonation(BloodDonation newBloodDonation) {
		em.getTransaction().begin();
		em.persist(newBloodDonation);
		em.getTransaction().commit();
		return Response.ok(em.find(BloodDonation.class, newBloodDonation.getId())).build();
	}

	public Response getBlooadDonation(int id) {
		BloodDonation bloodDonation = em.find(BloodDonation.class, id);
		return Response.status(bloodDonation == null ? Status.NOT_FOUND : Status.OK).entity(bloodDonation)
				.build();
	}
}
