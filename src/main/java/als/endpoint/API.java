package als.endpoint;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import als.domain.PersonDetails;
import als.domain.Users;
import handelers.DBwriter;

@Path("/users")
public class API {

	@GET
	@Path("{id : \\d+}") //support digit only
	public Response getUserById(@PathParam("id") int id) {

	   return Response.status(200).entity("id : " + DBwriter.getUserByID(id)).build();

	}

	@GET
	@Path("/username/{username}")
	public Response getUserByUserName(@PathParam("username") String username) {
	   return Response.status(200)
		.entity("username : " + DBwriter.getUser(username)).build();

	}
	
	@GET
	@Path("/allUsers")
	public Response getAllUsers() {
	   return Response.status(200)
		.entity("users : " + DBwriter.getAllUsers()).build();

	}
	
	
	@POST
	@Path("/create")
	@Consumes("application/json")
	public Response createProductInJSON(Users u) {

		PersonDetails p = u.getDetails();
		
		
		DBwriter.writeNewPerson(p);
		DBwriter.writeNewUser(u.getUserName(), u.getPassword(), u.getUserId(), p.getFirstName(), p.getLastName());
		
		
		
		String result = "User created : " + u ;
		return Response.status(201).entity(result).build();
		
	}
	
	
	
}