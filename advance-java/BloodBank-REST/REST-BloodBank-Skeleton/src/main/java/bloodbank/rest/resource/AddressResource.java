/**
 * @name AddressResource.java
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
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
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

import bloodbank.ejb.AddressService;
import bloodbank.ejb.BloodBankService;
import bloodbank.entity.Address;
import bloodbank.utility.MyConstants;

@Path(MyConstants.ADDRESS_RESOURCE)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AddressResource {
	private static final Logger LOG = LogManager.getLogger();
	@EJB
	private AddressService addressService;

	@EJB
	private BloodBankService service ;
	
	@Inject
	protected SecurityContext sc;
	
	@POST
	@RolesAllowed({ ADMIN_ROLE })
	public Response addAddress(Address newAddress) {
		return addressService.addAddress(newAddress);

	}
	
	@Path("/all")
	@GET
	@RolesAllowed({ADMIN_ROLE})
	public Response allAddress() {
		List<Address> allAddress = addressService.getAllAddress();
		return Response.ok(allAddress).build();
	}
	
	@GET
	@Path( RESOURCE_PATH_ID_PATH)
	@RolesAllowed({ADMIN_ROLE})
	public Response getAddressById(@PathParam( RESOURCE_PATH_ID_ELEMENT) int id) {
		return addressService.getAddress(id);
	}
	
	@DELETE
	@Path( RESOURCE_PATH_ID_PATH)
	@RolesAllowed({ADMIN_ROLE})
	public Response deleteAddress(@PathParam( RESOURCE_PATH_ID_ELEMENT) int id) {
		return addressService.deleteAddress(id);
	}

}
