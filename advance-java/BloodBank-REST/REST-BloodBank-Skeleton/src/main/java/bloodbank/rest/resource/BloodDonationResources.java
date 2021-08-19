package bloodbank.rest.resource;

import static bloodbank.utility.MyConstants.ADMIN_ROLE;
import static bloodbank.utility.MyConstants.BLOODDONATION_RESOURCE_NAME;
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

import bloodbank.ejb.BankService;
import bloodbank.ejb.BloodDonationService;
import bloodbank.entity.BloodBank;
import bloodbank.entity.BloodDonation;

@Path(BLOODDONATION_RESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BloodDonationResources {

	private static final Logger LOG = LogManager.getLogger();

	@EJB
	protected BloodDonationService service;

	@Inject
	protected SecurityContext sc;
	
	@GET
    @RolesAllowed({ADMIN_ROLE})
	public Response getBloodDonations() {
		LOG.debug("retrieving all blood donations ...");
		List<BloodDonation> bloodDonations = service.getAllDonations();
		Response response = Response.ok(bloodDonations).build();
		return response;
	}
	
	@GET
	@RolesAllowed({ADMIN_ROLE, USER_ROLE})
	@Path(RESOURCE_PATH_ID_PATH)
	public Response getBloodDonationById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
		LOG.debug("retrieving donation by id ...");
		BloodDonation bloodDonation = service.getBloodDonationId(id);
		if(bloodDonation != null) {
			Response response = Response.ok(bloodDonation).build();
			return response;
		}else {
			Response response = Response.status(Status.NO_CONTENT).build();
			return response;
		}
	}
	
	@POST
	@RolesAllowed({ADMIN_ROLE})
	public Response addBloodDonation(BloodDonation bloodDonation) {
		Response response = null;
		BloodDonation newBloodDonationWithIdTimestamps = service.persistBloodDonation(bloodDonation);
		// build a SecurityUser linked to the new person
		response = Response.ok(newBloodDonationWithIdTimestamps).build();
		return response;
	}
	
	@PUT
	@RolesAllowed({ADMIN_ROLE})
	@Path(RESOURCE_PATH_ID_PATH)
	public Response updateBloodDonationById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id, BloodDonation bloodDonationWithUpdates) {
		Response response = null;
		BloodDonation updatedBloodDonation = service.updateBloodDonationById(id, bloodDonationWithUpdates);
		response = Response.ok(updatedBloodDonation).build();
		return response;
	}
	
	@DELETE
	@RolesAllowed({ADMIN_ROLE})
	@Path(RESOURCE_PATH_ID_PATH)
	public Response deleteBloodDonationById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
		Response response = null;
		service.deleteBloodDonationById(id);
		response = Response.ok().build();
		return response;
	}
	
}
