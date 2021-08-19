/**
 * @name PhoneResource.java
 * @author Samarth Sharma, Masoumeh Mirzaeepour Gelvarzkhah, Abdullah Zeki Ilgun, Dishaben Patel
 * @since 2021-08-10
 */
package bloodbank.rest.resource;

import static bloodbank.utility.MyConstants.ADMIN_ROLE;
import static bloodbank.utility.MyConstants.RESOURCE_PATH_ID_ELEMENT;
import static bloodbank.utility.MyConstants.RESOURCE_PATH_ID_PATH;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import bloodbank.ejb.PhoneService;
import bloodbank.entity.Phone;
import bloodbank.utility.MyConstants;

@Path(MyConstants.PHONE_RESOURCE)
@Consumes( MediaType.APPLICATION_JSON)
@Produces( MediaType.APPLICATION_JSON)
public class PhoneResource {

	@EJB
	private PhoneService phoneService;
	
	@POST
	@RolesAllowed({ ADMIN_ROLE })
	public Response addPhone(Phone newPhone) {
		return phoneService.addPhone(newPhone);

	}
	
	@Path("/all")
	@GET
	@RolesAllowed({ADMIN_ROLE})
	public Response allPhone() {
		List<Phone> allPhone = phoneService.getAllPhone();
		return Response.ok(allPhone).build();
	}
	
	@GET
	@Path( RESOURCE_PATH_ID_PATH)
	@RolesAllowed({ADMIN_ROLE})
	public Response getPhoneById(@PathParam( RESOURCE_PATH_ID_ELEMENT) int id) {
		return phoneService.getPhone(id);
	}
	
	@DELETE
	@Path( RESOURCE_PATH_ID_PATH)
	@RolesAllowed({ADMIN_ROLE})
	public Response deletePhone(@PathParam( RESOURCE_PATH_ID_ELEMENT) int id) {
		return phoneService.deletePhone(id);
	}
	
}
