/**
 * @name SecurityUserSerializer.java
 * @author Samarth Sharma, Masoumeh Mirzaeepour Gelvarzkhah, Abdullah Zeki Ilgun, Dishaben Patel
 * @since 2021-08-10
 */
package bloodbank.rest.serializer;

import java.io.IOException;
import java.io.Serializable;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import bloodbank.entity.SecurityUser;

public class SecurityUserSerializer extends StdSerializer<SecurityUser> implements Serializable {
	
	public SecurityUserSerializer() {
		this(null);
	}

	public SecurityUserSerializer(Class<SecurityUser> t) {
		super(t);
	}

	@Override
	public void serialize(SecurityUser value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		gen.writeNumberField( "id", value.getId());
		gen.writeStringField( "name", value.getName());
		gen.writeStringField( "username",  value.getUsername());
		gen.writeEndObject();
		
	}

}
