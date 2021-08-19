/**
 * @name Person_.java
 * @author Samarth Sharma, Masoumeh Mirzaeepour Gelvarzkhah, Abdullah Zeki Ilgun, Dishaben Patel
 * @since 2021-08-10
 */
package bloodbank.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2021-03-31T23:31:54.500-0400")
@StaticMetamodel(Person.class)
public class Person_ {
	public static volatile SingularAttribute<Person, String> firstName;
	public static volatile SingularAttribute<Person, String> lastName;
	public static volatile SetAttribute<Person, DonationRecord> donations;
	public static volatile SetAttribute<Person, Contact> contacts;
}
