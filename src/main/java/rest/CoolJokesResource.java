package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import utils.EMF_Creator;
import facades.GroupMemberFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("cooljoke")
public class CoolJokesResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/CA1",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
    
    private static final GroupMemberFacade FACADE =  GroupMemberFacade.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }
    
//    @Path("/all")
//    @GET
//    @Produces({MediaType.APPLICATION_JSON})
//    public String getAllCoolJokes() {
//        List<CoolJoke> list = FACADE.getAllCoolJokes();
//        return GSON.toJson(list);
//    }
//    
//    @Path("/id/{id}")
//    @GET
//    @Produces({MediaType.APPLICATION_JSON})
//    public String getJokeByID(@PathParam("id") int id) {
//        CoolJoke joke = FACADE.getCoolJokeID(id);
//        return GSON.toJson(joke);
//    }
//    
//    @Path("/random")
//    @GET
//    @Produces({MediaType.APPLICATION_JSON})
//    public String getRandomJoke() {
//        CoolJoke joke = getRandomJoke();
//        return GSON.toJson(joke);
//    }
}