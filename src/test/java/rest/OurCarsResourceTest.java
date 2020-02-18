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

public class OurCarsResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
//    private static Car c1,c2,c3,c4;
    
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
//        c1 = new Car(2016, "Mercedes", "C300");
//        c2 = new Car(2019, "Audi", "Q7");
//        c3 = new Car(1970, "Chevrolet", "Chevelle");
//        c4 = new Car(1969, "Ford", "Mustang");
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Cars.deleteAllRows").executeUpdate();
//            em.persist(c1);
//            em.persist(c2); 
//            em.persist(c3);
//            em.persist(c4);
            em.getTransaction().commit();
        } finally { 
            em.close();
        }
    }
    
//    @Test
//    public void testServerIsUp() {
//        System.out.println("Testing is server UP");
//        given().when().get("/cars").then().statusCode(200);
//    }
//   
//    @Test
//    public void testDummyMsg() throws Exception {
//        given()
//        .contentType("application/json")
//        .get("/cars/").then()
//        .assertThat()
//        .statusCode(HttpStatus.OK_200.getStatusCode())
//        .body("msg", equalTo("Hello World"));   
//    }
//    
//    @Test
//    public void testGetAllCars() throws Exception {
//        given()
//        .contentType("application/json")
//        .get("/cars/all")
//        .then()
//        .log().body()
//        .assertThat()
//        .statusCode(HttpStatus.OK_200.getStatusCode())
//        .body("[2].year", equalTo(1970));   
//    }
//    
//    @Test
//    public void testGetCarByID() throws Exception {
//        given()
//        .contentType("application/json")
//        .get("/cars/id/"+c1.getId()).then()
//        .assertThat()
//        .statusCode(HttpStatus.OK_200.getStatusCode())
//        .body("name", equalTo(c1.getName()));  
//    }
}