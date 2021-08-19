/**
 * @name Person.java
 * @author Samarth Sharma, Masoumeh Mirzaeepour Gelvarzkhah, Abdullah Zeki Ilgun, Dishaben Patel
 * @since 2021-08-10
 */
package bloodbank.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.Hibernate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import bloodbank.rest.serializer.SecurityUserSerializer;

/**
 * The persistent class for the person database table.
 */
@Entity
@Table( name = "person")
@NamedQuery( name = Person.ALL_PERSONS_QUERY_NAME, query = "SELECT p FROM Person p left JOIN FETCH p.donations left join fetch p.contacts")
//@AttributeOverride( name = "id", column = @Column( name = "id"))
//no need for AttributeOverride as person is column is called id as well.
public class Person extends PojoBase implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String ALL_PERSONS_QUERY_NAME = "Person.findAll";

	@Basic( optional = false)
	@Column( name = "first_name", nullable = false, length = 50)
	private String firstName;

	@Basic( optional = false)
	@Column( name = "last_name", nullable = false, length = 50)
	private String lastName;

	@OneToMany( cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, mappedBy = "owner", fetch = FetchType.LAZY)
	private Set< DonationRecord> donations = new HashSet<>();

//	@JoinColumn( name = "person_id", referencedColumnName = "id", insertable = false, updatable = false)
	@OneToMany( cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, mappedBy = "owner", fetch = FetchType.LAZY)
	private Set< Contact> contacts = new HashSet<>();
	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "person")
	private SecurityUser securityUser;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName( String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName( String lastName) {
		this.lastName = lastName;
	}

	@JsonIgnore
	public Set< DonationRecord> getDonations() {
		return donations;
	}

	public void setDonations( Set< DonationRecord> donations) {
		this.donations = donations;
	}

	@JsonIgnore
	public Set< Contact> getContacts() {
		return contacts;
	}

	public void setContacts( Set< Contact> contacts) {
		this.contacts = contacts;
	}

	public void setFullName( String firstName, String lastName) {
		setFirstName( firstName);
		setLastName( lastName);
	}
	
	@JsonIgnore
	public SecurityUser getSecurityUser() {
		return securityUser;
	}

	public void setSecurityUser(SecurityUser securityUser) {
		this.securityUser = securityUser;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		return prime + Objects.hash( getFirstName(), getLastName());
	}

	@Override
	public boolean equals( Object obj) {
		if ( obj == null)
			return false;
		if ( this == obj)
			return true;
		if ( !( getClass() == obj.getClass() || Hibernate.getClass( obj) == getClass()))
			return false;
		Person other = (Person) obj;
		return Objects.equals( getFirstName(), other.getFirstName())
				&& Objects.equals( getLastName(), other.getLastName());
	}

}