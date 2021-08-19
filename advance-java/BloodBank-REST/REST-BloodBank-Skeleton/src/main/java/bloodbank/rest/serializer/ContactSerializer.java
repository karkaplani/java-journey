/**
 * @name ContactSerializer.java
 * @author Samarth Sharma, Masoumeh Mirzaeepour Gelvarzkhah, Abdullah Zeki Ilgun, Dishaben Patel
 * @since 2021-08-10
 */
package bloodbank.rest.serializer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import bloodbank.entity.Contact;

public class ContactSerializer extends StdSerializer<Set<Contact>> {

	public ContactSerializer() {
        this(null);
    }

    public ContactSerializer(Class<Set<Contact>> t) {
        super(t);
    }

	@Override
	public void serialize(Set<Contact> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		// TODO Auto-generated method stub
		Set<Contact> contacts = new HashSet<Contact>();
		if(value==null||value.isEmpty()) {
			gen.writeObject(contacts);
		} else {
			for(Contact c :value) {
				Contact n = new Contact();
				n.setContactType(c.getContactType());
				n.setEmail(c.getEmail());
				contacts.add(n);
			}
			gen.writeObject(contacts);
		}
		
	}

}
