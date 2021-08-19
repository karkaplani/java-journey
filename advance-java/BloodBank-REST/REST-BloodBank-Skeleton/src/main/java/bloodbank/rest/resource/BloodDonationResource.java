
/**
 * @name BloodDonationResource.java
 * @author Samarth Sharma, Masoumeh Mirzaeepour Gelvarzkhah, Abdullah Zeki Ilgun, Dishaben Patel
 * @since 2021-08-10
 */package bloodbank.rest.resource;

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

import bloodbank.ejb.BloodDonationService;
import bloodbank.entity.BloodDonation;
import bloodbank.utility.MyConstants;

@Path(MyConstants.BLOOD_DONATION_RESOURCE)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BloodDonationResource {
	private static final Logger LOG = LogManager.getLogger();
	@EJB
	private BloodDonationService bloodDonationService;

	@POST
	@RolesAllowed({ ADMIN_ROLE })
	public Response addBloodDonation(BloodDonation newBloodDonation) {
		return bloodDonationService.addBloodDonation(newBloodDonation);

	}
	
	@Path("/all")
	@GET
	@RolesAllowed({ADMIN_ROLE})
	public Response allDonations() {
		List<BloodDonation> allDonations = bloodDonationService.getBloodDonation();
		return Response.ok(allDonations).build();
	}
	
	@GET
	@Path( RESOURCE_PATH_ID_PATH)
	@RolesAllowed({ADMIN_ROLE})
	public Response getDonationById(@PathParam( RESOURCE_PATH_ID_ELEMENT) int id) {
		return bloodDonationService.getBlooadDonation(id);
	}
	
	@DELETE
	@Path( RESOURCE_PATH_ID_PATH)
	@RolesAllowed({ADMIN_ROLE})
	public Response deleteDonation(@PathParam( RESOURCE_PATH_ID_ELEMENT) int id) {
		try {
			
			return bloodDonationService.deleteBloodDonation(id);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
