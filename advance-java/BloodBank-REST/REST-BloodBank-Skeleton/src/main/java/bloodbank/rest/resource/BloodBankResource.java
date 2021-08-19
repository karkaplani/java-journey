package bloodbank.rest.resource;

import static bloodbank.utility.MyConstants.ADMIN_ROLE;
import static bloodbank.utility.MyConstants.BLOODBANK_RESOURCE_NAME;
import static bloodbank.utility.MyConstants.RESOURCE_PATH_ID_ELEMENT;
import static bloodbank.utility.MyConstants.RESOURCE_PATH_ID_PATH;
import static bloodbank.utility.MyConstants.USER_ROLE;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bloodbank.ejb.AddressService;
import bloodbank.ejb.BankService;
import bloodbank.entity.Address;
import bloodbank.entity.BloodBank;

@Path(BLOODBANK_RESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BloodBankResource {

	private static final Logger LOG = LogManager.getLogger();

	@EJB
	protected BankService service;

	@Inject
	protected SecurityContext sc;
	
	@GET
    @RolesAllowed({ADMIN_ROLE})
	public Response getBloodBanks() {
		LOG.debug("retrieving all blood banks ...");
		List<BloodBank> bloodBanks = service.getAllBloodBanks();
		Response response = Response.ok(bloodBanks).build();
		return response;
	}
	
	@GET
	@RolesAllowed({ADMIN_ROLE, USER_ROLE})
	@Path(RESOURCE_PATH_ID_PATH)
	public Response getBloodBankById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
		LOG.debug("retrieving address by id ...");
		BloodBank bloodBank = service.getBloodBankId(id);
		if(bloodBank != null) {
			Response response = Response.ok(bloodBank).build();
			return response;
		}else {
			Response response = Response.status(Status.NO_CONTENT).build();
			return response;
		}
	}
	
	@POST
	@RolesAllowed({ADMIN_ROLE})
	public Response addBloodBank(BloodBank newBloodBank) {
		Response response = null;
		BloodBank newBloodBankWithIdTimestamps = service.persistBloodBank(newBloodBank);
		// build a SecurityUser linked to the new person
		response = Response.ok(newBloodBankWithIdTimestamps).build();
		return response;
	}
	
	@PUT
	@RolesAllowed({ADMIN_ROLE})
	@Path(RESOURCE_PATH_ID_PATH)
	public Response updateBloodBankById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id, BloodBank bloodBankWithUpdates) {
		Response response = null;
		BloodBank updatedBloodBank = service.updateBloodBankById(id, bloodBankWithUpdates);
		response = Response.ok(updatedBloodBank).build();
		return response;
	}
	
	@DELETE
	@RolesAllowed({ADMIN_ROLE})
	@Path(RESOURCE_PATH_ID_PATH)
	public Response deleteBloodBankById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
		Response response = null;
		service.deleteBloodBankById(id);
		response = Response.ok().build();
		return response;
	}
	
}
