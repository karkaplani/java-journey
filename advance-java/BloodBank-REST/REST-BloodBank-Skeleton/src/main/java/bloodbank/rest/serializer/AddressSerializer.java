/**
 * @name AddressSerializer.java
 * @author Samarth Sharma, Masoumeh Mirzaeepour Gelvarzkhah, Abdullah Zeki Ilgun, Dishaben Patel
 * @since 2021-08-10
 */
package bloodbank.rest.serializer;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import bloodbank.entity.Contact;
import bloodbank.entity.SecurityRole;

public class AddressSerializer extends StdSerializer<Set<Contact>> implements Serializable {
	public AddressSerializer() {
		this(null);
	}

	public AddressSerializer(Class<Set<Contact>> t) {
		super(t);
	}

	/**
	 * This is to prevent back and forth serialization between Many to Many
	 * relations.<br>
	 * This is done by setting the relation to null.
	 */
	@Override
	public void serialize(Set<Contact> originalRoles, JsonGenerator generator, SerializerProvider provider)
			throws IOException {

		Set<Contact> hollowContacts = new HashSet<>();
		for (Contact originalRole : originalRoles) {
			// create a 'hollow' copy of the original Security Roles entity
			originalRole.setAddress(null);
			hollowContacts.add(originalRole);
//	            newContact.setAddress(address);
		}
		generator.writeObject(hollowContacts);
	}
}
