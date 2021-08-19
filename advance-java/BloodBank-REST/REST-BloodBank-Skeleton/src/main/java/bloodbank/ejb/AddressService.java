/**
 * @name AddressService.java
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

import bloodbank.entity.Address;

@Singleton
public class AddressService {

	private static final Logger LOG = LogManager.getLogger();

	@PersistenceContext(name = PU_NAME)
	protected EntityManager em;

	public List<Address> getAllAddress() {
		return em.createNamedQuery(Address.ALL_ADDRESS_QUERY_NAME).getResultList();
	}

	public Response deleteAddress(int id) {
		LOG.debug("Finding address with id"+id);
		Address address = em.find(Address.class, id);
		if (address == null) {
			LOG.debug("Address not found");
			return Response.status(Status.NOT_FOUND).build();
		} else {
			LOG.debug("Address found. Deleting address ....");
			em.getTransaction().begin();
			em.remove(address);
			em.getTransaction().commit();
			return Response.ok().build();
		}
	}

	public Response addAddress(Address newAddress) {
		LOG.debug("saving address ....");
		em.getTransaction().begin();
		em.persist(newAddress);
		em.getTransaction().commit();
		return Response.ok(em.find(Address.class, newAddress.getId())).build();
	}

	public Response getAddress(int id) {
		LOG.debug("Finding address with id "+id);
		Address address = em.find(Address.class, id);
		return Response.status(address == null ? Status.NOT_FOUND : Status.OK).entity(address).build();
	}

}
