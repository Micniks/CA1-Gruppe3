package entities;

import facades.CarFacade;
import facades.GroupMemberFacade;
import facades.JokeFacade;
import java.util.ArrayList;
import java.util.List;
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
        List<Joke> jokeList = new ArrayList<Joke>();
        jokeList.add(new Joke("Why did the chicken cross the road? To get to the other side!", "", "old", "unknown"));
        jokeList.add(new Joke("I just wrote a book on reverse psychology. Do not read it.", "", "wordplay", "Andreas"));
        jokeList.add(new Joke("What did Batman say to Robin before they got in the car? \"Robin, get in the car.\"", "", "Dad Joke", "Marcus"));
        jokeList.add(new Joke("Why shouldn't you write with a broken pencil? Because it's pointless!", "", "Dad Joke", "Cahit"));
        jokeList.add(new Joke("What has 8 legs, 8 arms, and 8 eyes? 8 pirates.", "https://www.rd.com/funny-stuff/pirate-jokes-pirate-puns/", "Pirate", "Michael"));
        jokeList.add(new Joke("Why does it take pirates so long to learn the alphabet? Because they can spend years at C.", "https://www.rd.com/funny-stuff/pirate-jokes-pirate-puns/", "Pirate", "Michael"));
        jokeList.add(new Joke("How much did the pirate pay for his peg and hook? An arm and a leg!", "https://bestlifeonline.com/pirate-jokes/", "Pirate", "Michael"));
        jokeList.add(new Joke("What is a horny pirate’s worst nightmare? A sunken chest with no booty.", "", "Pirate", "Michael"));
        jokeList.add(new Joke("What do ye call a pirate with two eyes and two legs? A rookie.", "https://blog.yellowoctopus.com.au/funny-pirate-jokes/", "Pirate", "Michael"));
        jokeList.add(new Joke("What did the buffalo say when his son left? Bison!", "https://inews.co.uk/light-relief/jokes/best-bad-jokes-497422 ", "Dad Joke", "Michael"));
        jokeList.add(new Joke("How do you make holy water? You boil the hell out of it!", "https://top-funny-jokes.com/bad-jokes/", "Dad Joke", "Michael"));
        jokeList.add(new Joke("It has four legs and it can fly, what is it? Two birds.	", "https://short-funny.com/bad-jokes.php", "Dad Joke", "Cahit"));
        jokeList.add(new Joke("What is blue and smells like red paint? Blue paint.", "https://short-funny.com/bad-jokes.php", "Dad Joke", "Cahit"));
        jokeList.add(new Joke("Why do cows wear bells? Their horns don’t work.", "https://short-funny.com/bad-jokes.php", "Dad Joke", "Cahit"));
        jokeList.add(new Joke("How can you open a banana? With a monkey!", "https://short-funny.com/bad-jokes.php", "Dad Joke", "Cahit"));
        jokeList.add(new Joke("You’ll never believe whom I saw yesterday! Everybody I laid my eyes on!", "https://short-funny.com/bad-jokes.php", "Dad Joke", "Cahit"));
        jokeList.add(new Joke("Why did the chicken cross the road? It just felt like it.", "https://short-funny.com/bad-jokes.php", "Dad Joke", "Cahit"));
        jokeList.add(new Joke("What does a farmer say when he's looking for his tractor? \"Where is my tractor?\"", "https://short-funny.com/bad-jokes.php", "Dad Joke", "Cahit"));
        jokeList.add(new Joke("Programming is like sex: One mistake and you have to support it for the rest of your life!", "https://www.boredpanda.com/funny-dad-jokes-puns/?utm_source=google&utm_medium=organic&utm_campaign=organic", "Programmer", "Marcus"));
        jokeList.add(new Joke("A SQL statement walks into a bar and sees two tables. It approaches, and asks \"may I join you?\"", "https://www.boredpanda.com/funny-dad-jokes-puns/?utm_source=google&utm_medium=organic&utm_campaign=organic", "Programmer", "Marcus"));
        jokeList.add(new Joke("I bought some shoes from a drug dealer. I don't know what he laced them with, but I was tripping all day!", "https://www.boredpanda.com/funny-dad-jokes-puns/?utm_source=google&utm_medium=organic&utm_campaign=organic", "Dad Joke", "Marcus"));
        jokeList.add(new Joke("What do you call someone with no body and no nose? Nobody knows.", "https://www.boredpanda.com/funny-dad-jokes-puns/?utm_source=google&utm_medium=organic&utm_campaign=organic", "Dad Joke", "Marcus"));
        jokeList.add(new Joke("What is the least spoken language in the world? Sign language.", "https://www.boredpanda.com/funny-dad-jokes-puns/?utm_source=google&utm_medium=organic&utm_campaign=organic", "Dad Joke", "Marcus"));
        jokeList.add(new Joke("A slice of apple pie is $2.50 in Jamaica and $3.00 in the Bahamas. These are the pie rates of the Caribbean.", "https://www.boredpanda.com/funny-dad-jokes-puns/?utm_source=google&utm_medium=organic&utm_campaign=organic", "Pirate", "Marcus"));
        jokeList.add(new Joke("Don't trust atoms. They make up everything!", "https://www.boredpanda.com/funny-dad-jokes-puns/?utm_source=google&utm_medium=organic&utm_campaign=organic", "Dad Joke", "Marcus"));
        for (Joke joke : jokeList) {
            JOKE_FACADE.addJoke(joke);
        }
    }

    private static void addCars() {
        List<Car> carList = new ArrayList<Car>();
        carList.add(new Car(1983, "Volvo", "242 Turbo Evo", 85500, "Red"));
        carList.add(new Car(1995, "Nissan", "Skyline R33 GTR", 120000, "Midnight Purple"));
        carList.add(new Car(2020, "Ford", "F-150 Raptor", 450000, "Blue"));
        carList.add(new Car(2020, "Jeep", "Cherokee Trackhawk", 63000, "Bourdeaux"));
        carList.add(new Car(1995, "Volvo", "850 t5-r", 98500, "Yellow"));
        carList.add(new Car(1986, "BMW", "325i E30", 8000, "White"));
        carList.add(new Car(1990, "BMW", "850i V12", 785000, "Black"));
        carList.add(new Car(1990, "Toyota", "Supra MK3", 266000, "Perl Blue"));
        carList.add(new Car(2010, "Toyota", "Yaris", 2000, "Blue"));
        carList.add(new Car(2017, "Mazda", "CX5", 150000, "Grey"));
        carList.add(new Car(2020, "Ford", "Fiesta MK8", 266000, "Gun Metal"));
        carList.add(new Car(2010, "Ferrari", "448", 500000, "Red"));
        carList.add(new Car(2005, "Skoda", "Octavia", 50, "Pink"));
        carList.add(new Car(2005, "Ford", "Fiesta MK5", 36000, "White"));
        carList.add(new Car(1948, "Citroen", "2CV", 6400, "Yellow"));
        carList.add(new Car(1960, "Morris", "Minor", 7400, "Racing Green"));
        carList.add(new Car(2016, "VW", "Up!", 8000, "Grey"));
        carList.add(new Car(2018, "VW", "Arteon", 480000, "Black"));
        carList.add(new Car(1997, "VW", "Golf GTI MK3", 2660, "Silver"));
        carList.add(new Car(2011, "Toyota", "Corolla", 20000, "Purple"));

        for (Car car : carList) {
            CAR_FACADE.addCar(car);
        }

    }
}
