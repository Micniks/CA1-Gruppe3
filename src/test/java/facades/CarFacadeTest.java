package facades;

import dto.CarDTO;
import dto.JokeDTO;
import entities.Car;
import entities.Joke;
import java.util.List;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CarFacadeTest {

    private static EntityManagerFactory emf;
    private static CarFacade facade;
    private static Car car1, car2, car3, car4, car5;
    private static Car[] carArray;

    public CarFacadeTest() {
    }

    //@BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/startcode_test",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
        facade = CarFacade.getCarFacade(emf);
    }

    @BeforeAll
    public static void setUpClassV2() {
        emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST, EMF_Creator.Strategy.DROP_AND_CREATE);
        facade = CarFacade.getCarFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
        emptyDatabase();
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Car.deleteAllRows").executeUpdate();
            car1 = new Car(1983, "Volvo", "242 Turbo Evo", 85500, "Red");
            car2 = new Car(1995, "Nissan", "Skyline R33 GTR", 120000, "Midnight Purple");
            car3 = new Car(2020, "Nissan", "F-150 Raptor", 450000, "Blue");
            car4 = new Car(2020, "Jeep", "Cherokee Trackhawk", 63000, "Bourdeaux");
            car5 = new Car(1986, "BMW", "325i E30", 5000, "White");
            em.persist(car1);
            em.persist(car2);
            em.persist(car3);
            em.persist(car4);
            em.persist(car5);
            em.getTransaction().commit();

        } finally {
            em.close();
        }

        carArray = new Car[]{car1, car2, car3, car4, car5};
    }

    private static void emptyDatabase() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Car.deleteAllRows").executeUpdate();
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
    public void testGetCarCount() {
        long result = facade.getCarCount();
        long expectedResult = carArray.length;

        assertEquals(expectedResult, result);
    }

    @Test
    public void test_GetCarCount_Empty() {
        emptyDatabase();

        long result = facade.getCarCount();
        long expectedResult = 0;

        assertEquals(expectedResult, result);

    }

    @Test
    public void testGetAllCars() {
        List<CarDTO> result = facade.getAllCars();

        for (CarDTO carDTO : result) {
            boolean foundMatchingCar = false;

            for (Car car : carArray) {
                if (car.getId() == carDTO.getId() && car.getMake().equals(carDTO.getMake())) {
                    foundMatchingCar = true;
                }
            }
            assertTrue(foundMatchingCar);
        }

        assertEquals(carArray.length, result.size());
        assertTrue(carArray.length > 0);
    }

    @Test
    public void test_GetAllCars_Empty() {
        emptyDatabase();
        List<CarDTO> result = facade.getAllCars();
        int expectedSize = 0;
        assertNotNull(result);
        assertEquals(expectedSize, result.size());

    }

    @Test
    public void testGetCarByID() {
        for (Car expectedCar : carArray) {
            long id = expectedCar.getId();
            Car result = facade.getCarId(id);

            assertEquals(expectedCar.getId(), result.getId());
            assertTrue(expectedCar.getMake().equals(result.getMake()));
        }

        assertTrue(carArray.length > 0);
    }

    @Test
    public void testAddCar() {

        long newCarCount = facade.getCarCount() + 1;

        Car car = new Car(2015, "VW", "Phaeton 4,2 V8", 900000, "Black");
        Car result = facade.addCar(car);

        assertEquals(car.getId(), result.getId());
        assertEquals(newCarCount, facade.getAllCars().size());

    }

    @Test
    public void testGetCarByMake() {
        Car expectedCar = car1;
        List<CarDTO> result = facade.getCarMake(expectedCar.getMake());
        int expectedResultSize = 1;
        assertEquals(expectedResultSize, result.size());
        assertEquals(expectedCar.getId(), result.get(0).getId());
        assertTrue(expectedCar.getMake().equals(result.get(0).getMake()));

    }

    @Test
    public void testGetCarByMake_Multiple() {
        Car expectedCar1 = car2;
        Car expectedCar2 = car3;
        if (expectedCar1.getMake() != expectedCar2.getMake()) {
            fail("Not the same maker");
        }
        List<CarDTO> result = facade.getCarMake(expectedCar1.getMake());
        int expectedResultSize = 2;
        assertEquals(expectedResultSize, result.size());
        for (CarDTO carDTO : result) {
            assertTrue(expectedCar1.getId() == carDTO.getId() || expectedCar2.getId() == carDTO.getId());
            assertTrue(expectedCar1.getMake().equals(carDTO.getMake()));
            assertTrue(expectedCar2.getMake().equals(carDTO.getMake()));
        }

    }
    
    @Test
    public void testGetCarByMake_NoResult() {
        String make = "No such Make";
        for (Car car : carArray) {
            if(car.getMake().equals(make)){
                fail("A car with the noSuchMake string exist");
            }
        }
        List<CarDTO> result = facade.getCarMake(make);
        int expectedResultSize = 0;
        assertEquals(expectedResultSize, result.size());
    }
}
