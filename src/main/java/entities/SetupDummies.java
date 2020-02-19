package entities;

import facades.CarFacade;
import facades.GroupMemberFacade;
import facades.JokeFacade;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

public class SetupDummies {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/CA1",
            "dev",
            "ax2",
            EMF_Creator.Strategy.DROP_AND_CREATE);

    private static final GroupMemberFacade GROUP_FACADE = GroupMemberFacade.getFacadeExample(EMF);
    private static final JokeFacade JOKE_FACADE = JokeFacade.getFacadeExample(EMF);
    private static final CarFacade CAR_FACADE = CarFacade.getCarFacade(EMF);

    public static void main(String[] args) {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("GroupMember.deleteAllRows").executeUpdate();
            em.createNamedQuery("Joke.deleteAllRows").executeUpdate();
            em.createNamedQuery("Car.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        addGroupMembers();
        addJokes();
        addCars();
    }

    private static void addGroupMembers() {
        GroupMember gm1 = new GroupMember("Andreas", "cph-ap294", "Green");
        GroupMember gm2 = new GroupMember("Cahit", "cph-cb342", "Green");
        GroupMember gm3 = new GroupMember("Marcus", "cph-mj734", "Yellow");
        GroupMember gm4 = new GroupMember("Michael", "cph-mk548", "Red");
        GROUP_FACADE.addGroupMember(gm1);
        GROUP_FACADE.addGroupMember(gm2);
        GROUP_FACADE.addGroupMember(gm3);
        GROUP_FACADE.addGroupMember(gm4);
    }
    
    private static void addJokes() {
        Joke joke1 = new Joke("Why did the chicken cross the road? To get to the other side!", "", "old", "unknown");
        Joke joke2 = new Joke("Knock Knock\nWho is there\nBad\nBad who?\nThis is a bad joke", "", "bad", "Michael");
        Joke joke3 = new Joke("I just wrote a book on reverse psychology. Do not read it.", "", "wordplay", "Andreas");
        Joke joke4 = new Joke("What did Batman say to Robin before they got in the car? \"Robin, get in the car.\"", "", "bad", "Marcus");
        Joke joke5 = new Joke("Why shouldn't you write with a broken pencil? Because it's pointless!", "", "wordplay", "Cahit");
        JOKE_FACADE.addJoke(joke1);
        JOKE_FACADE.addJoke(joke2);
        JOKE_FACADE.addJoke(joke3);
        JOKE_FACADE.addJoke(joke4);
        JOKE_FACADE.addJoke(joke5);
    }
    
    private static void addCars() {
        Car car1 = new Car(1983, "Volvo","242 Turbo Evo", 85500, "Red");
        Car car2 = new Car(1995, "Nissan", "Skyline R33 GTR", 120000, "Midnight Purple");
        Car car3 = new Car(2020, "Ford","F-150 Raptor",450000, "Blue");
        Car car4 = new Car(2020, "Jeep", "Cherokee Trackhawk", 63000, "Bourdeaux");
        Car car5 = new Car(1995, "Volvo", "850 t5-r", 98500, "Yellow");
        Car car6 = new Car(1986, "BMW", "325i E30", 8000, "White");
        Car car7 = new Car(1990, "BMW", "850i V12", 785000, "Black");
        Car car8 = new Car(1990, "Toyota", "Supra MK3", 266000, "Perl Blue");
        CAR_FACADE.addCar(car1);
        CAR_FACADE.addCar(car2);
        CAR_FACADE.addCar(car3);
        CAR_FACADE.addCar(car4);
        CAR_FACADE.addCar(car5);
        CAR_FACADE.addCar(car6);
        CAR_FACADE.addCar(car7);
        CAR_FACADE.addCar(car8);
    }
}