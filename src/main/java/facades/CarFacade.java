package facades;

import dto.CarDTO;
import entities.Car;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class CarFacade {

    private static CarFacade instance;
    private static EntityManagerFactory emf;

    public CarFacade() {
    }

    public static CarFacade getCarFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CarFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public long getCarCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long carCount = (long) em.createQuery("SELECT COUNT(c) FROM Car c").getSingleResult();
            return carCount;
        } finally {
            em.close();
        }
    }

    public List<CarDTO> getAllCars() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Car> tq = em.createQuery("SELECT c FROM Car c ORDER BY c.model asc", Car.class);
            return CarDTO.convertList(tq.getResultList());
        } finally {
            em.close();
        }
    }

    public Car getCarId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Car> tq = em.createQuery("SELECT c FROM Car c WHERE c.id = :id", Car.class);
            tq.setParameter("id", id);
            Car result = tq.getSingleResult();
            return result;
        } catch (NoResultException ex) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<CarDTO> getCarMake(String make) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Car> tq = em.createQuery("SELECT c FROM Car c WHERE c.make = :make", Car.class);
            tq.setParameter("make", make);
            return CarDTO.convertList(tq.getResultList());
        } finally {
            em.close();
        }
    }

    public Car addCar(Car car) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(car);
            em.getTransaction().commit();
            return car;
        } finally {
            em.close();
        }
    }
}
