/**
 * @name PublicBloodBankResource.java
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

import bloodbank.ejb.PublicBloodBankService;
import bloodbank.entity.PublicBloodBank;
import bloodbank.utility.MyConstants;

@Path(MyConstants.PUBLIC_BLOOD_BANK_RESOURCE)
@Consumes( MediaType.APPLICATION_JSON)
@Produces( MediaType.APPLICATION_JSON)
public class PublicBloodBankResource {
	
	private static final Logger LOG = LogManager.getLogger();
	@EJB
	private PublicBloodBankService publicBloodBankService;
	
	@POST
	@RolesAllowed({ ADMIN_ROLE })
	public Response addAddress(PublicBloodBank newAddress) {
		return publicBloodBankService.addPublicBloodBank(newAddress);

	}
	
	@Path("/all")
	@GET
	@RolesAllowed({ADMIN_ROLE})
	public Response allPublicBloodBank() {
		List<PublicBloodBank> allPublicBloodBank = publicBloodBankService.getAllPublicBloodBank();
		return Response.ok(allPublicBloodBank).build();
	}
	
	@GET
	@Path( RESOURCE_PATH_ID_PATH)
	@RolesAllowed({ADMIN_ROLE})
	public Response getPublicBloodBankById(@PathParam( RESOURCE_PATH_ID_ELEMENT) int id) {
		return publicBloodBankService.getPublicBloodBank(id);
	}
	
	@DELETE
	@Path( RESOURCE_PATH_ID_PATH)
	@RolesAllowed({ADMIN_ROLE})
	public Response deletePublicBloodBank(@PathParam( RESOURCE_PATH_ID_ELEMENT) int id) {
		try {
		return publicBloodBankService.deletePublicBloodBank(id);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
