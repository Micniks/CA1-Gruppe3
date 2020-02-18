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
    private GroupMemberFacade() {
    }

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
    public long getGroupMemberCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long membersCount = (long) em.createQuery("SELECT COUNT(m) FROM GroupMember m").getSingleResult();
            return membersCount;
        } finally {
            em.close();
        }
    }

    public List<GroupMemberDTO> getAllGroupMembers() {
        EntityManager em = emf.createEntityManager();
        try {
            //TypedQuery<GroupMember> tq = em.createQuery("SELECT m FROM Movie m order by m.name desc", GroupMember.class);
            TypedQuery<GroupMember> tq = em.createQuery("SELECT m FROM GroupMember m ORDER BY m.name asc", GroupMember.class);
            return GroupMemberDTO.convertList(tq.getResultList());
        } finally {
            em.close();
        }
    }

    public GroupMember getGroupMmeberId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<GroupMember> tq = em.createQuery("SELECT m FROM GroupMember m WHERE m.id = :id", GroupMember.class);
            tq.setParameter("id", id);
            GroupMember result = tq.getSingleResult();
            return result;
        } finally {
            em.close();
        }
    }

    public GroupMember addGroupMember(GroupMember member) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(member);
            em.getTransaction().commit();
            return member;
        } finally {
            em.close();
        }
    }

}
