package rest;

import entities.GroupMember;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

public class CoolJokesResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static CoolJoke j1,j2,j3,j4;
    
    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.CREATE);
        
        httpServer = startServer();
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }
    
    @AfterAll
    public static void closeTestServer(){
         EMF_Creator.endREST_TestWithDB();
         httpServer.shutdownNow();
    }
    
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        j1 = new CoolJoke("x", "D", "69");
        j2 = new CoolJoke("X", "d", "96");
        j3 = new CoolJoke("L", "o", "L");
        j4 = new CoolJoke("M", "a", "O");
        try {
            em.getTransaction().begin();
            em.createNamedQuery("CoolJoke.deleteAllRows").executeUpdate();
            em.persist(j1);
            em.persist(j2); 
            em.persist(j3);
            em.persist(j4);
            em.getTransaction().commit();
        } finally { 
            em.close();
        }
    }
    
    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/cooljoke").then().statusCode(200);
    }
   
    @Test
    public void testDummyMsg() throws Exception {
        given()
        .contentType("application/json")
        .get("/cooljoke/").then()
        .assertThat()
        .statusCode(HttpStatus.OK_200.getStatusCode())
        .body("msg", equalTo("Hello World"));   
    }
    
    @Test
    public void testGetAllCoolJokes() throws Exception {
        given()
        .contentType("application/json")
        .get("/cooljoke/all")
        .then()
        .log().body()
        .assertThat()
        .statusCode(HttpStatus.OK_200.getStatusCode())
        .body("[2].reference", hasItem("L"));   
    }
    
    @Test
    public void testGetCoolJokeByID() throws Exception {
        given()
        .contentType("application/json")
        .get("/cooljoke/id/"+j1.getId()).then()
        .assertThat()
        .statusCode(HttpStatus.OK_200.getStatusCode())
        .body("name", equalTo(j1.getName()));  
    }
    
    @Test
    public void testGetRandomCoolJoke() throws Exception {
        given()
        .contentType("application/json")
        .get("/cooljoke/random").then()
        .assertThat()
        .statusCode(HttpStatus.OK_200.getStatusCode())
        .body("[1].id", equalTo(j2.getName()));  
    }
}