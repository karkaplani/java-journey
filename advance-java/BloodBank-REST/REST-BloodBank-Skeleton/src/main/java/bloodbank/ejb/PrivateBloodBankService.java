/**
 * @name PrivateBloodBankService.java
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

import bloodbank.entity.PrivateBloodBank;

@Singleton
public class PrivateBloodBankService {

	private static final Logger LOG = LogManager.getLogger();

	@PersistenceContext(name = PU_NAME)
	protected EntityManager em;

	public List<PrivateBloodBank> getAllPrivateBloodBank() {
		return em.createNamedQuery(PrivateBloodBank.ALL_PRIVATE_BLOODBANKS_QUERY_NAME).getResultList();
	}

	public Response deletePrivateBloodBank(int id) {
		PrivateBloodBank privateBloodBank = em.find(PrivateBloodBank.class, id);
		if (privateBloodBank == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			em.getTransaction().begin();
			em.remove(privateBloodBank);
			em.getTransaction().commit();
			return Response.ok().build();
		}
	}

	public Response addPrivateBloodBank(PrivateBloodBank privateBloodBank) {
		em.getTransaction().begin();
		em.persist(privateBloodBank);
		em.getTransaction().commit();
		return Response.ok(em.find(PrivateBloodBank.class, privateBloodBank.getId())).build();
	}

	public Response getPrivateBloodBank(int id) {
		PrivateBloodBank privateBloodBank = em.find(PrivateBloodBank.class, id);
		return Response.status(privateBloodBank == null ? Status.NOT_FOUND : Status.OK).entity(privateBloodBank)
				.build();
	}
}
