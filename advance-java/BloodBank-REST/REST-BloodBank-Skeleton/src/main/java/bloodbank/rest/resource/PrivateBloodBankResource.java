/**
 * @name PrivateBloodBankResource.java
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bloodbank.ejb.PrivateBloodBankService;
import bloodbank.entity.PrivateBloodBank;
import bloodbank.utility.MyConstants;

@Path(MyConstants.PRIVATE_BLOOD_BANK_RESOURCE)
@Consumes( MediaType.APPLICATION_JSON)
@Produces( MediaType.APPLICATION_JSON)
public class PrivateBloodBankResource {
	private static final Logger LOG = LogManager.getLogger();
	@EJB
	private PrivateBloodBankService privateBloodBankService;
	
	@POST
	@RolesAllowed({ ADMIN_ROLE })
	public Response addAddress(PrivateBloodBank newAddress) {
		return privateBloodBankService.addPrivateBloodBank(newAddress);
	}
	
	@Path("/all")
	@GET
	@RolesAllowed({ADMIN_ROLE})
	public Response allPrivateBloodBank() {
		List<PrivateBloodBank> allPrivateBloodBank = privateBloodBankService.getAllPrivateBloodBank();
		return Response.ok(allPrivateBloodBank).build();
	}
	
	@GET
	@Path( RESOURCE_PATH_ID_PATH)
	@RolesAllowed({ADMIN_ROLE})
	public Response getPrivateBloodBankById(@PathParam( RESOURCE_PATH_ID_ELEMENT) int id) {
		return privateBloodBankService.getPrivateBloodBank(id);
	}
	
	@DELETE()
	@Path( "/{id}")
	@RolesAllowed({ADMIN_ROLE})
	public Response deletePrivateBloodBank(@PathParam( "id") int id) {
		return privateBloodBankService.deletePrivateBloodBank(id);
	}

}
