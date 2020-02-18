package facades;

import dto.GroupMemberDTO;
import entities.GroupMember;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class GroupMemberFacade {

    private static GroupMemberFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private GroupMemberFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static GroupMemberFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new GroupMemberFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    //TODO Remove/Change this before use
    public long getMovieCount(){
        EntityManager em = emf.createEntityManager();
        try{
            long movieCount = (long)em.createQuery("SELECT COUNT(m) FROM Movie m").getSingleResult();
            return movieCount;
        }finally{  
            em.close();
        }
    }
    
    public List<GroupMember> getAllMovies(){
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<GroupMember> tq = em.createQuery("SELECT m FROM Movie m order by m.name desc", GroupMember.class);
            return tq.getResultList();
        }finally{  
            em.close();
        }
    }
    
    public List<GroupMemberDTO> getMovieName(String name){
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<GroupMember> tq = em.createQuery("SELECT m FROM Movie m WHERE m.name = :name", GroupMember.class);
            tq.setParameter("name", name);
            List<GroupMember> movies = tq.getResultList();
            List<GroupMemberDTO> result = new ArrayList<GroupMemberDTO>();
            for (GroupMember movie : movies) {
                result.add(new GroupMemberDTO(movie));
            }
            return result;
        }finally{  
            em.close();
        }
    }
    
    public GroupMember getMovieID(Long id){
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<GroupMember> tq = em.createQuery("SELECT m FROM Movie m WHERE m.id = :id", GroupMember.class);
            tq.setParameter("id", id);
            GroupMember result = tq.getSingleResult();
            return result;
        }finally{  
            em.close();
        }
    }
    
    public GroupMember addMovie(GroupMember movie){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(movie);
            em.getTransaction().commit();
            return movie;
        }finally{  
            em.close();
        }
    }

}
