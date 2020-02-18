/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.GroupMemberFacade;
import facades.JokeFacade;
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
    private static final GroupMemberFacade GROUP_FACADE = GroupMemberFacade.getFacadeExample(EMF);
    private static final JokeFacade JOKE_FACADE = JokeFacade.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("GroupMember.deleteAllRows").executeUpdate();
            em.createNamedQuery("Joke.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        addGroupMembers();
        addJokes();
        
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

}
