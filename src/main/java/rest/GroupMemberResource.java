package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.GroupMemberDTO;
import entities.GroupMember;
import utils.EMF_Creator;
import facades.GroupMemberFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("groupmember")
public class GroupMemberResource {

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
    
    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllGroupMembers() {
        List<GroupMemberDTO> list = FACADE.getAllGroupMembers();
        return GSON.toJson(list);
    }
}