/**
 * @name PrivateBloodBank.java
 * @author Samarth Sharma, Masoumeh Mirzaeepour Gelvarzkhah, Abdullah Zeki Ilgun, Dishaben Patel
 * @since 2021-08-10
 */
package bloodbank.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;

@Entity
@DiscriminatorValue( "1")
public class PrivateBloodBank extends BloodBank {
	private static final long serialVersionUID = 1L;

	public PrivateBloodBank() {
		super(false);
	}
}
