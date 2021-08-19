/**
 * @name BloodResource.java
 * @author Samarth Sharma, Masoumeh Mirzaeepour Gelvarzkhah, Abdullah Zeki Ilgun, Dishaben Patel
 * @since 2021-08-10
 */
package bloodbank.rest.resource;

import static bloodbank.utility.MyConstants.ADMIN_ROLE;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bloodbank.ejb.BloodBankService;
import bloodbank.entity.BloodBank;
import bloodbank.utility.MyConstants;

@Path(MyConstants.BLOOD_BANK_RESOURCE)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BloodResource {
	private static final Logger LOG = LogManager.getLogger();
	@EJB
	protected BloodBankService service;

	@Inject
	protected SecurityContext sc;


	@GET
	@RolesAllowed({ ADMIN_ROLE })
	@Path("/all")
	public Response getBloodBank() {
		LOG.debug("retrieving all BloodBanks ...");
		List<BloodBank> bloodBanks = service.getBloodBank();
		Response response = Response.ok(bloodBanks).build();
		return response;
	}
	

}
