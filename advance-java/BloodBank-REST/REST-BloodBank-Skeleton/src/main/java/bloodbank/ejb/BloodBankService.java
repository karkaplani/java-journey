/**
 * File: RecordService.java
 * Course materials (21S) CST 8277
 *
 * @author 
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * @author (original) Mike Norman
 * 
 * update by : Samarth Sharma, Masoumeh Mirzaeepour Gelvarzkhah, Abdullah Zeki Ilgun, Dishaben Patel
 *
 */

package bloodbank.ejb;

import static bloodbank.entity.SecurityRole.ROLE_BY_NAME_QUERY;
import static bloodbank.entity.SecurityUser.USER_FOR_OWNING_PERSON_QUERY;
import static bloodbank.utility.MyConstants.DEFAULT_KEY_SIZE;
import static bloodbank.utility.MyConstants.DEFAULT_PROPERTY_ALGORITHM;
import static bloodbank.utility.MyConstants.DEFAULT_PROPERTY_ITERATIONS;
import static bloodbank.utility.MyConstants.DEFAULT_SALT_SIZE;
import static bloodbank.utility.MyConstants.DEFAULT_USER_PASSWORD;
import static bloodbank.utility.MyConstants.DEFAULT_USER_PREFIX;
import static bloodbank.utility.MyConstants.PARAM1;
import static bloodbank.utility.MyConstants.PROPERTY_ALGORITHM;
import static bloodbank.utility.MyConstants.PROPERTY_ITERATIONS;
import static bloodbank.utility.MyConstants.PROPERTY_KEYSIZE;
import static bloodbank.utility.MyConstants.PROPERTY_SALTSIZE;
import static bloodbank.utility.MyConstants.PU_NAME;
import static bloodbank.utility.MyConstants.USER_ROLE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import bloodbank.entity.Address;
import bloodbank.entity.BloodBank;
import bloodbank.entity.Contact;
import bloodbank.entity.ContactPK;
import bloodbank.entity.DonationRecord;
import bloodbank.entity.Person;
import bloodbank.entity.Phone;
import bloodbank.entity.SecurityRole;
import bloodbank.entity.SecurityUser;

/**
 * Stateless Singleton ejb Bean - BloodBankService
 */
@Singleton
public class BloodBankService implements Serializable {
	private static final long serialVersionUID = 1L;

	@PersistenceContext(name = PU_NAME)
	protected EntityManager em;
	@Inject
	protected Pbkdf2PasswordHash pbAndjPasswordHash;

	public List<Person> getAllPeople() {
		List<Person> l = new ArrayList<Person>();
		try {
			l.addAll(em.createQuery("select p from Person p left JOIN FETCH p.donations").getResultList());
		} catch(Exception e) {
			e.printStackTrace();
		}
		return l;
//		return em.createNamedQuery(Person.ALL_PERSONS_QUERY_NAME).getResultList();
	}

	public Person getPersonId(int id) {
		return em.find(Person.class, id);
	}

	@Transactional
	public Person persistPerson(Person newPerson) {
		em.getTransaction().begin();
		em.persist(newPerson);
		em.getTransaction().commit();
		return em.find(Person.class, newPerson.getId());
	}

	@Transactional
	public void buildUserForNewPerson(Person newPerson) {
		newPerson = em.find(Person.class, newPerson.getId());
		SecurityUser userForNewPerson = new SecurityUser();
		userForNewPerson
				.setUsername(DEFAULT_USER_PREFIX + "_" + newPerson.getFirstName() + "." + newPerson.getLastName());
		Map<String, String> pbAndjProperties = new HashMap<>();
		pbAndjProperties.put(PROPERTY_ALGORITHM, DEFAULT_PROPERTY_ALGORITHM);
		pbAndjProperties.put(PROPERTY_ITERATIONS, DEFAULT_PROPERTY_ITERATIONS);
		pbAndjProperties.put(PROPERTY_SALTSIZE, DEFAULT_SALT_SIZE);
		pbAndjProperties.put(PROPERTY_KEYSIZE, DEFAULT_KEY_SIZE);
		pbAndjPasswordHash.initialize(pbAndjProperties);
		String pwHash = pbAndjPasswordHash.generate(DEFAULT_USER_PASSWORD.toCharArray());
		userForNewPerson.setPwHash(pwHash);
		userForNewPerson.setPerson(newPerson);
		SecurityRole userRole = em.createNamedQuery(ROLE_BY_NAME_QUERY, SecurityRole.class)
				.setParameter(PARAM1, USER_ROLE).getSingleResult();
		if(userForNewPerson.getRoles() == null) {
			userForNewPerson.setRoles(new HashSet<SecurityRole>());
		}
		userForNewPerson.getRoles().add(userRole);
		userRole.getUsers().add(userForNewPerson);
		em.persist(userForNewPerson);
	}

	@Transactional
	public Person setAddressFor(int id, Address newAddress) {
		Person person = em.find(Person.class, id);
		em.getTransaction().begin();
		em.persist(newAddress);
		
		person.getContacts().forEach(e -> {
			e.setAddress(newAddress);
		});
		em.getTransaction().commit();
		return person;
	}

	/**
	 * to update a person
	 * 
	 * @param id                - id of entity to update
	 * @param personWithUpdates - entity with updated information
	 * @return Entity with updated information
	 */
	@Transactional
	public Person updatePersonById(int id, Person personWithUpdates) {
		Person personToBeUpdated = getPersonId(id);
		if (personToBeUpdated != null) {
			em.refresh(personToBeUpdated);
			em.merge(personWithUpdates);
			em.flush();
		}
		return personToBeUpdated;
	}

	/**
	 * to delete a person by id
	 * 
	 * @param id - person id to delete
	 */
	@Transactional
	public void deletePersonById(int id) {
		Person person = getPersonId(id);
		if (person != null) {
			em.refresh(person);
			TypedQuery<SecurityUser> findUser = em.createNamedQuery(USER_FOR_OWNING_PERSON_QUERY, SecurityUser.class)
					.setParameter(PARAM1, person.getId());
			SecurityUser sUser = findUser.getSingleResult();
			em.remove(sUser);
			em.remove(person);
		}
	}

	public List<BloodBank> getBloodBank() {
		return em.createNamedQuery(BloodBank.ALL_BLOODBANKS_QUERY_NAME).getResultList();
	}

	public Response deleteBloodBank(int id) {
		BloodBank bloodBank = em.find(BloodBank.class, id);
		if (bloodBank == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			em.remove(bloodBank);
			return Response.accepted().build();
		}
	}

	public Response deleteAddress(int id) {
		Address address = em.find(Address.class, id);
		if (address == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			em.remove(address);
			return Response.accepted().build();
		}
	}

	public Response deletePhone(int id) {
		Phone phone = em.find(Phone.class, id);
		if (phone == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			em.remove(phone);
			return Response.accepted().build();
		}
	}

	public Response deleteContact(int id) {
		Contact contact = em.find(Contact.class, id);
		if (contact == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			em.remove(contact);
			return Response.accepted().build();
		}
	}

	public Response associatePhoneAndAddress(Integer personId, int phoneId, int addressId) {
		Person person = getPersonId(personId);
		if (person == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		Phone phone = em.find(Phone.class, phoneId);
		if (phone == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		Address address = em.find(Address.class, addressId);
		Contact contact = em.find(Contact.class, new ContactPK(personId, phoneId));
		if (contact == null) {
			contact = new Contact();
			contact.setId(new ContactPK(personId, phoneId));
			contact.setPhone(phone);
			contact.setOwner(person);
			contact.setContactType("default");
		}
		if(address != null) {			
			contact.setAddress(address);
		}
		em.getTransaction().begin();
		em.persist(contact);
		em.getTransaction().commit();
		return Response.ok(getPersonId(personId)).build();
	}
	
	public Set<DonationRecord> findForUser(int id){
		SecurityUser su = em.createQuery("select s from SecurityUser s left JOIN FETCH s.person where s.id = "+id, SecurityUser.class).getSingleResult();
		Person p = su.getPerson();
		return em.createQuery("select p from Person p left JOIN FETCH p.donations where p.id = "+p.getId(), Person.class).getSingleResult().getDonations(); 
	}
}