package als.endpoint;

import javax.management.Notification;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import als.domain.PersonDetails;
import handelers.DBwriter;

@ApplicationPath("/api")
public class API extends Application{
	@Path("/notifications")
	public class NotificationsResource {
	    @GET
	    @Path("/ping")
	    public Response ping() {
	        return Response.ok().entity("Service online").build();
	    }
	 
	    @GET
	    @Path("/get/{id}")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response getNotification(@PathParam("id") int id) {
	        return Response.ok()
	          .entity(DBwriter.getUser(id))
	          .build();
	    }
	 
	    @POST
//	    @Path("/register/")
	    @Path("/Id/{Id}/username/{username}/password/{password}/register")
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response postNotification(@PathParam("id") int id,
	    		@PathParam("username") String username,
	    		@PathParam("password") String password) {
    		
    		
    		DBwriter.writeNewUser(username, password, id, "", "");
	    	
	    	
	    	
	        return Response.status(201).build();
	    }
	}
}
