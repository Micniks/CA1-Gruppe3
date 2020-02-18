/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.GroupMemberFacade;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author Bruger
 */
public class SetupDummies {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/CA1",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);

    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    private static final GroupMemberFacade FACADE = GroupMemberFacade.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("GroupMember.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        GroupMember gm1 = new GroupMember("Andreas", "cph-ap294", "Green");
        GroupMember gm2 = new GroupMember("Cahit", "cph-cb342", "Green");
        GroupMember gm3 = new GroupMember("Marcus", "cph-mj734", "Yellow");
        GroupMember gm4 = new GroupMember("Michael", "cph-mk548", "Red");
        FACADE.addMovie(gm1);
        FACADE.addMovie(gm2);
        FACADE.addMovie(gm3);
        FACADE.addMovie(gm4);
    }

}
