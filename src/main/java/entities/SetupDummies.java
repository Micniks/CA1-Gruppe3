package entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.CarFacade;
import facades.GroupMemberFacade;
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

    private static final GroupMemberFacade FACADE = GroupMemberFacade.getFacadeExample(EMF);
    private static final CarFacade CARFACADE = CarFacade.getCarFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("GroupMember.deleteAllRows").executeUpdate();
            em.createNamedQuery("Car.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        GroupMember gm1 = new GroupMember("Andreas", "cph-ap294", "Green");
        GroupMember gm2 = new GroupMember("Cahit", "cph-cb342", "Green");
        GroupMember gm3 = new GroupMember("Marcus", "cph-mj734", "Yellow");
        GroupMember gm4 = new GroupMember("Michael", "cph-mk548", "Red");
        FACADE.addGroupMember(gm1);
        FACADE.addGroupMember(gm2);
        FACADE.addGroupMember(gm3);
        FACADE.addGroupMember(gm4);
        
        Car car1 = new Car(1983, "Volvo","242 Turbo Evo", 85500, "Red");
        Car car2 = new Car(1995, "Nissan", "Skyline R33 GTR", 120000, "Midnight Purple");
        Car car3 = new Car(2020, "Ford","F-150 Raptor",450000, "Blue");
        Car car4 = new Car(2020, "Jeep", "Cherokee Trackhawk", 63000, "Bourdeaux");
        Car car5 = new Car(1986, "BMW", "325i E30", 5000, "White");
        CARFACADE.addCar(car1);
        CARFACADE.addCar(car2);
        CARFACADE.addCar(car3);
        CARFACADE.addCar(car4);
        CARFACADE.addCar(car5);
    }
}