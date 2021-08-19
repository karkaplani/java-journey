/**
 * @name PublicBloodBank.java
 * @author Samarth Sharma, Masoumeh Mirzaeepour Gelvarzkhah, Abdullah Zeki Ilgun, Dishaben Patel
 * @since 2021-08-10
 */
package bloodbank.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue( "0")
public class PublicBloodBank extends BloodBank {

	private static final long serialVersionUID = 1L;

	public PublicBloodBank() {
		super(true);
	}
}
