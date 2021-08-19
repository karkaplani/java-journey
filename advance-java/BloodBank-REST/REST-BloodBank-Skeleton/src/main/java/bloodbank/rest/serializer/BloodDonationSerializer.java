/**
 * @name BloodDontationSerializer.java
 * @author Samarth Sharma, Masoumeh Mirzaeepour Gelvarzkhah, Abdullah Zeki Ilgun, Dishaben Patel
 * @since 2021-08-10
 */
package bloodbank.rest.serializer;

import java.io.IOException;
import java.io.Serializable;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import bloodbank.entity.BloodDonation;

public class BloodDonationSerializer extends StdSerializer<BloodDonation> implements Serializable {
	public BloodDonationSerializer() {
		this(null);
	}

	public BloodDonationSerializer(Class<BloodDonation> t) {
		super(t);
	}

	@Override
	public void serialize(BloodDonation value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		// TODO Auto-generated method stub
		gen.writeStartObject();
		gen.writeNumberField( "id", value.getId());
		gen.writeObjectField( "bloodType", value.getBloodType());
		gen.writeNumberField( "millimeters",  value.getMilliliters());
		gen.writeEndObject();
		
	}
}
