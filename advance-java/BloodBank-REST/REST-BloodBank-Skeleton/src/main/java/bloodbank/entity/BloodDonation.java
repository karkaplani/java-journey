/**
 * @name BloodDonation.java
 * @author Samarth Sharma, Masoumeh Mirzaeepour Gelvarzkhah, Abdullah Zeki Ilgun, Dishaben Patel
 * @since 2021-08-10
 */
package bloodbank.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.Hibernate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import bloodbank.rest.serializer.BloodBankSerializer;

/**
 * The persistent class for the blood_donation database table.
 */
@Entity
@Table( name = "blood_donation")
@NamedQuery( name = "BloodDonation.findAll", query = "SELECT b FROM BloodDonation b left JOIN FETCH b.bank")
@AttributeOverride( name = "id", column = @Column( name = "donation_id"))
public class BloodDonation extends PojoBase implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String FIND_ALL_QUERY = "BloodDonation.findAll";
	@ManyToOne( optional = false, cascade = { CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
	@JoinColumn( name = "bank_id", referencedColumnName = "bank_id")
	private BloodBank bank;

	@OneToOne( fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.REFRESH}, optional = true, mappedBy = "donation")
//	@JoinColumn( name = "donation_id", referencedColumnName = "donation_id", nullable = true, insertable = false, updatable = false)
	private DonationRecord record;

	@Basic( optional = false)
	@Column( nullable = false)
	private int milliliters;

	@Embedded
	private BloodType bloodType;

	@JsonSerialize(using = BloodBankSerializer.class)
	public BloodBank getBank() {
		return bank;
	}

	public void setBank( BloodBank bank) {
		this.bank = bank;
	}

	public DonationRecord getRecord() {
		return record;
	}

	public void setRecord( DonationRecord record) {
		this.record = record;
	}

	public int getMilliliters() {
		return milliliters;
	}

	public void setMilliliters( int milliliters) {
		this.milliliters = milliliters;
	}

	public BloodType getBloodType() {
		return bloodType;
	}

	public void setBloodType( BloodType bloodType) {
		this.bloodType = bloodType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		return prime + Objects.hash( getBank().getId(), getBloodType(), getMilliliters());
	}

	@Override
	public boolean equals( Object obj) {
		if ( obj == null)
			return false;
		if ( this == obj)
			return true;
		if ( !( getClass() == obj.getClass() || Hibernate.getClass( obj) == getClass()))
			return false;
		BloodDonation other = (BloodDonation) obj;
		return Objects.equals( getBank().getId(), other.getBank().getId())
				&& Objects.equals( getBloodType(), other.getBloodType()) && getMilliliters() == other.getMilliliters();
	}
}