package facades;

import dto.JokeDTO;
import utils.EMF_Creator;
import entities.Joke;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class JokeFacadeTest {

    private static EntityManagerFactory emf;
    private static JokeFacade facade;
    private static Joke joke1, joke2, joke3, joke4, joke5;
    private static Joke[] jokeArray;

    public JokeFacadeTest() {
    }

    //@BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/startcode_test",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
        facade = JokeFacade.getFacadeExample(emf);
    }

    /*   **** HINT **** 
        A better way to handle configuration values, compared to the UNUSED example above, is to store those values
        ONE COMMON place accessible from anywhere.
        The file config.properties and the corresponding helper class utils.Settings is added just to do that. 
        See below for how to use these files. This is our RECOMENDED strategy
     */
    @BeforeAll
    public static void setUpClassV2() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);
        facade = JokeFacade.getFacadeExample(emf);
    }

    @AfterAll
    public static void tearDownClass() {
        emptyDatabase();
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Joke.deleteAllRows").executeUpdate();
            joke1 = new Joke("Why did the chicken cross the road? To get to the other side!", "", "old", "unknown");
            joke2 = new Joke("Knock Knock\nWho is there\nBad\nBad who?\nThis is a bad joke", "", "bad", "Michael");
            joke3 = new Joke("I just wrote a book on reverse psychology. Do not read it.", "", "wordplay", "Andreas");
            joke4 = new Joke("What did Batman say to Robin before they got in the car? \"Robin, get in the car.\"", "", "bad", "Marcus");
            joke5 = new Joke("Why shouldn't you write with a broken pencil? Because it's pointless!", "", "wordplay", "Cahit");
            em.persist(joke1);
            em.persist(joke2);
            em.persist(joke3);
            em.persist(joke4);
            em.persist(joke5);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        jokeArray = new Joke[]{joke1, joke2, joke3, joke4, joke5};
    }

    private static void emptyDatabase() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Joke.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
        emptyDatabase();
    }

    @Test
    public void testAddJoke() {
        Joke newJoke = new Joke("Knock Knock", "", "not funny", "Michael");
        JokeDTO result = facade.addJoke(newJoke);
        assertEquals(newJoke.getId(), result.getId());
        assertTrue(newJoke.getValue().equals(result.getValue()));

        List<JokeDTO> dbResults = facade.getAllJokes();
        
        boolean foundMatchingJoke = false;
        for (JokeDTO joke : dbResults) {
            if (joke.getId() == result.getId() && joke.getValue().equals(result.getValue())) {
                foundMatchingJoke = true;
            }
        }
        assertTrue(foundMatchingJoke);
    }

    @Test
    public void testGetAllJokes() {
        List<JokeDTO> result = facade.getAllJokes();

        for (JokeDTO jokeDTO : result) {
            boolean foundMatchingJoke = false;

            for (Joke joke : jokeArray) {
                if (joke.getId() == jokeDTO.getId() && joke.getValue().equals(jokeDTO.getValue())) {
                    foundMatchingJoke = true;
                }
            }
            assertTrue(foundMatchingJoke);
        }

        assertEquals(jokeArray.length, result.size());
        assertTrue(jokeArray.length > 0);
    }

    @Test
    public void test_GetAllJokes_Empty() {
        emptyDatabase();
        List<JokeDTO> result = facade.getAllJokes();

        int expectedSize = 0;
        assertNotNull(result);
        assertEquals(expectedSize, result.size());
    }

    @Test
    public void testGetJokeByID() {
        for (Joke expectedJoke : jokeArray) {
            long id = expectedJoke.getId();
            JokeDTO result = facade.getJokeById(id);

            assertEquals(expectedJoke.getId(), result.getId());
            assertTrue(expectedJoke.getValue().equals(result.getValue()));
        }

        assertTrue(jokeArray.length > 0);
    }

    @Test
    public void test_negative_GetJokeByID_NoMatchingID() {

    }

    @Test
    public void testGetJokeCount() {
        long result = facade.getJokeCount();
        long expectedResult = jokeArray.length;

        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetJokeCountEmpty() {
        emptyDatabase();

        long result = facade.getJokeCount();
        long expectedResult = 0;

        assertEquals(expectedResult, result);

    }

    @Test
    public void testGetRandomJoke() {
        JokeDTO result = facade.getRandomJoke();

        assertNotNull(result);
        boolean foundMatchingJoke = false;

        for (Joke joke : jokeArray) {
            if (joke.getId() == result.getId() && joke.getValue().equals(result.getValue())) {
                foundMatchingJoke = true;
            }
        }
        assertTrue(foundMatchingJoke);

    }

    @Test
    public void testGetRandomJoke_FromList() {
        /*
        This test focus on testing if the random element of the method is 
        behaving within 2 given rules.
        1. The method may not give an OutOfBoundsException
        2. The random results should be evenly distributed within reason
        
        Because of the random nature of the method, and the fact that we are
        testing on multiple results being within expected parameters, there is
        a chance this test could fail, but this should be highly unlikely
         */

        //Making a fast list from the existing entity objects in the test-environment
        for (int i = 0; i < 100; i++) {
            List<JokeDTO> list = new ArrayList();
            list.add(new JokeDTO(joke1));
            list.add(new JokeDTO(joke2));
            list.add(new JokeDTO(joke3));
            list.add(new JokeDTO(joke4));
            list.add(new JokeDTO(joke5));

            //Try and see how evenly spread the results would be over multiple calls
            int[] results = new int[5];
            for (int j = 0; j < 1000000; j++) {

                // Calling the test-method here
                JokeDTO result = facade.getRandomJoke(list);

                //assert that a joke was returned
                assertNotNull(result);
                for (int k = 0; k < jokeArray.length; k++) {
                    if (result.getId() == jokeArray[k].getId() && result.getValue().equals(jokeArray[k].getValue())) {
                        results[k]++;
                    }
                }
            }

            //Assert that the results are distributed reasonably evenly
            int expectedResultAmount = 150000;
            assertTrue(results[0] > expectedResultAmount);
            assertTrue(results[1] > expectedResultAmount);
            assertTrue(results[2] > expectedResultAmount);
            assertTrue(results[3] > expectedResultAmount);
            assertTrue(results[4] > expectedResultAmount);
        }

    }

    @Test
    public void testGetRandomJoke_FromList_Empty() {
        List<JokeDTO> list = new ArrayList();

        JokeDTO result = facade.getRandomJoke(list);

        assertNull(result);
    }

}
