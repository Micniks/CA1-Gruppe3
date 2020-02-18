package rest;

import dto.JokeDTO;
import entities.Joke;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

public class JokeResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Joke joke1, joke2, joke3, joke4, joke5;

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
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        joke1 = new Joke("Why did the chicken cross the road? To get to the other side!", "", "old", "Unknown");
        joke2 = new Joke("Knock Knock\nWho is there\nBad\nBad who?\nThis is a bad joke", "", "bad", "Michael");
        joke3 = new Joke("I just wrote a book on reverse psychology. Do not read it.", "", "wordplay", "Andreas");
        joke4 = new Joke("What did Batman say to Robin before they got in the car? \"Robin, get in the car.\"", "", "bad", "Marcus");
        joke5 = new Joke("Why shouldn't you write with a broken pencil? Because it's pointless!", "", "wordplay", "Cahit");
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Joke.deleteAllRows").executeUpdate();
            em.persist(joke1);
            em.persist(joke2);
            em.persist(joke3);
            em.persist(joke4);
            em.persist(joke5);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/joke").then().statusCode(200);
    }

    //This test assumes the database contains two rows
    @Test
    public void testDummyMsg() throws Exception {
        given()
                .contentType("application/json")
                .get("/joke/").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("Jokes here!"));
    }

    @Test
    public void testCount() throws Exception {
        given()
                .contentType("application/json")
                .get("/joke/count").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("count", equalTo(5));
    }

    @Test
    public void testAll() throws Exception {
        given()
                .contentType("application/json")
                .get("/joke/all")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("[0].value", equalTo(joke3.getValue()))
                .body("[4].value", equalTo(joke5.getValue()));
    }

    @Test
    public void testID() throws Exception {
        given()
                .contentType("application/json")
                .get("joke/id/" + joke1.getId().toString())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("value", equalTo(joke1.getValue()));
    }

    @Test
    public void testRandom() throws Exception {
        Set results = new TreeSet<JokeDTO>();
        for (int i = 0; i < 1000; i++) {
            results.add(given()
                    .contentType("application/json")
                    .get("joke/random")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.OK_200.getStatusCode())
                    .extract()
                    .as(JokeDTO.class));
        }
        
        int expectedSize = 5;
        assertEquals(expectedSize, results.size());

    }
}
