/**
 * @name DonationRecordResource.java
 * @author Samarth Sharma, Masoumeh Mirzaeepour Gelvarzkhah, Abdullah Zeki Ilgun, Dishaben Patel
 * @since 2021-08-10
 */
package bloodbank.rest.resource;

import static bloodbank.utility.MyConstants.ADMIN_ROLE;
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
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.soteria.WrappingCallerPrincipal;
import org.hibernate.Hibernate;

import bloodbank.ejb.BloodBankService;
import bloodbank.ejb.DonationRecordService;
import bloodbank.entity.DonationRecord;
import bloodbank.entity.Person;
import bloodbank.entity.SecurityUser;
import bloodbank.utility.MyConstants;

@Path(MyConstants.DONATION_RECORD_RESOURCE)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DonationRecordResource {
	private static final Logger LOG = LogManager.getLogger();
	@EJB
	private DonationRecordService donationRecordService;

	@EJB
	private BloodBankService service;

	@Inject
	protected SecurityContext sc;

	@POST
	@RolesAllowed({ ADMIN_ROLE })
	public Response addDonationRecord(DonationRecord newDonationRecord) {
		return donationRecordService.addDonationRecord(newDonationRecord);

	}

	@Path("/all")
	@GET
	@RolesAllowed({ ADMIN_ROLE })
	public Response allDonationRecord() {
		List<DonationRecord> allDonationRecord = donationRecordService.getAllDonationRecords();
		return Response.ok(allDonationRecord).build();
	}

	@GET
	@Path(RESOURCE_PATH_ID_PATH)
	@RolesAllowed({ ADMIN_ROLE })
	public Response getDonationRecordById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
		return donationRecordService.getDonationRecord(id);
	}

	@DELETE
	@Path(RESOURCE_PATH_ID_PATH)
	@RolesAllowed({ ADMIN_ROLE })
	public Response deleteDonationRecord(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
		return donationRecordService.deleteDonationRecords(id);
	}

	@GET
	@RolesAllowed({ ADMIN_ROLE, USER_ROLE })
	@Path("/for-user")
	public Response getDonationRecordByPerson() {
		Response response = null;
		Person person = null;
		WrappingCallerPrincipal wCallerPrincipal = (WrappingCallerPrincipal) sc.getCallerPrincipal();
		SecurityUser sUser = (SecurityUser) wCallerPrincipal.getWrapped();
		response = Response.status(Status.OK).entity(service.findForUser(sUser.getId())).build();

		return response;
	}

}
