/**
 * @name PhoneService.java
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

import bloodbank.entity.Contact;
import bloodbank.entity.Phone;
@Singleton
public class PhoneService {
    
    private static final Logger LOG = LogManager.getLogger();
    
    @PersistenceContext(name = PU_NAME)
    protected EntityManager em;
    
    public List<Phone> getAllPhone() {
    	return em.createNamedQuery(Phone.ALL_PHONES_QUERY_NAME).getResultList();
    }
    
    public Response deletePhone(int id) {
		Phone phone = em.find(Phone.class, id);
		if(phone == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			em.getTransaction().begin();
			List<Contact> contacts = em.createQuery("select c from Contact c left JOIN FETCH c.phone where c.phone.id="+id).getResultList();
			contacts.forEach(e->em.remove(e));
			em.remove(phone);
			em.getTransaction().commit();
			return Response.ok().build();
		}
	}
    
    public Response addPhone(Phone newPhone) {
		em.getTransaction().begin();
		em.persist(newPhone);
		em.getTransaction().commit();
		return Response.ok(em.find(Phone.class, newPhone.getId())).build();
	}
	
	public Response getPhone(int id) {
		Phone phone = em.find(Phone.class, id);
		return Response.status(phone == null? Status.NOT_FOUND:Status.OK).entity(phone).build();
	}

}
