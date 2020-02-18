package facades;

import dto.JokeDTO;
import entities.Joke;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

public class JokeFacade {

    private final static Random RANDOM = new Random();
    private static JokeFacade instance;
    private static EntityManagerFactory emf;

    private JokeFacade() {
    }

    public static JokeFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new JokeFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public long getJokeCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long movieCount = (long) em.createQuery("SELECT COUNT(j) FROM Joke j").getSingleResult();
            return movieCount;
        } finally {
            em.close();
        }
    }

    public List<JokeDTO> getAllJokes() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Joke> tq = em.createQuery("SELECT j FROM Joke j order by j.value asc", Joke.class);
            return JokeDTO.convertList(tq.getResultList());
        } finally {
            em.close();
        }
    }

    public JokeDTO getJokeById(long id) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Joke> tq = em.createQuery("SELECT j FROM Joke j WHERE j.id = :id", Joke.class);
            tq.setParameter("id", id);
            Joke joke = tq.getSingleResult();
            JokeDTO result = new JokeDTO(joke);
            return result;
        } finally {
            em.close();
        }
    }

    public JokeDTO getRandomJoke() {
        List<JokeDTO> listOfJokes = getAllJokes();
        return getRandomJoke(listOfJokes);
    }

    protected JokeDTO getRandomJoke(List<JokeDTO> listOfJokes) {
        if (!listOfJokes.isEmpty()) {
            int randomNumber = RANDOM.nextInt(listOfJokes.size());
            return listOfJokes.get(randomNumber);
        } else {
            return null;
        }
    }

    public JokeDTO addJoke(Joke joke) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(joke);
            em.getTransaction().commit();
            JokeDTO result = new JokeDTO(joke);
            return result;
        } finally {
            em.close();
        }
    }
}
