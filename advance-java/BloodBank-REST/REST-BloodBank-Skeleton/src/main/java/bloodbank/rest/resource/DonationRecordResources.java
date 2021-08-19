package bloodbank.rest.resource;

import static bloodbank.utility.MyConstants.ADMIN_ROLE;
import static bloodbank.utility.MyConstants.DONATIONRECORD_RESOURCE_NAME;
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

import bloodbank.ejb.BloodDonationService;
import bloodbank.ejb.DonationRecordService;
import bloodbank.entity.BloodDonation;
import bloodbank.entity.DonationRecord;

@Path(DONATIONRECORD_RESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DonationRecordResources {

	private static final Logger LOG = LogManager.getLogger();

	@EJB
	protected DonationRecordService  service;

	@Inject
	protected SecurityContext sc;
	
	@GET
    @RolesAllowed({ADMIN_ROLE})
	public Response getBloodDonations() {
		LOG.debug("retrieving all donation records ...");
		List<DonationRecord> records = service.getAllDonationRecords();
		Response response = Response.ok(records).build();
		return response;
	}
	
	@GET
	@RolesAllowed({ADMIN_ROLE, USER_ROLE})
	@Path(RESOURCE_PATH_ID_PATH)
	public Response getDonationRecordById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
		LOG.debug("retrieving donation record by id ...");
		DonationRecord donationRecord = service.getDonationRecordId(id);
		if(donationRecord != null) {
			Response response = Response.ok(donationRecord).build();
			return response;
		}else {
			Response response = Response.status(Status.NO_CONTENT).build();
			return response;
		}
	}
	
	@POST
	@RolesAllowed({ADMIN_ROLE})
	public Response addDonationRecord(DonationRecord record) {
		Response response = null;
		DonationRecord newDonationRecordWithIdTimestamps = service.persistDonationRecord(record);
		// build a SecurityUser linked to the new person
		response = Response.ok(newDonationRecordWithIdTimestamps).build();
		return response;
	}
	
	@PUT
	@RolesAllowed({ADMIN_ROLE})
	@Path(RESOURCE_PATH_ID_PATH)
	public Response updateDonationRecordById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id, DonationRecord recordWithUpdates) {
		Response response = null;
		DonationRecord updatedDonationRecord = service.updateDonationRecordById(id, recordWithUpdates);
		response = Response.ok(updatedDonationRecord).build();
		return response;
	}
	
	@DELETE
	@RolesAllowed({ADMIN_ROLE})
	@Path(RESOURCE_PATH_ID_PATH)
	public Response deleteBloodDonationById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
		Response response = null;
		service.deleteDonationRecordById(id);;
		response = Response.ok().build();
		return response;
	}
	
}
