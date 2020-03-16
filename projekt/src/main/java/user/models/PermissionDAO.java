package user.models;

import core.interfaces.Dao;

import javax.persistence.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class PermissionDAO implements Dao<Permission> {

    @PersistenceContext
    private static EntityManagerFactory entityManagerFactory;
    @PersistenceContext
    public static EntityManager entityManager;
    private static final String PERSISTENCE_NAME = "CONNECTION";

    // TODO: getPermByName method

    /**
     * @param id - id of Perm that you want to get
     * @return - permission
     */
    @Override
    public Permission get(long id) {
        open();
        entityManager.getTransaction().begin();
        Permission perm = entityManager.find(Permission.class, id);
        entityManager.getTransaction().commit();
        close();
        return perm;
    }

    /**
     * @return List of all permissions
     */
    @Override
    public List<Permission> getAll() {
        open();
        entityManager.getTransaction().begin();
        List<Permission> results = entityManager.createQuery("select p from Permission p", Permission.class).getResultList();
        close();
        return results;
    }

    /**
     * @param permission - Permission class instance that you want to keep in database
     * @throws PersistenceException - Occurrences when Permission with duplicate name is about to be saved
     */
    @Override
    public void save(Permission permission) throws PersistenceException {
        open();
        entityManager.getTransaction().begin();
        entityManager.persist(permission);
        entityManager.getTransaction().commit();
        close();
    }

    public void addUser(Permission perm, User user) {
        open();
        perm.getUsers().add(user);
        entityManager.getTransaction().begin();
        entityManager.merge(perm);
        entityManager.getTransaction().commit();
        close();
    }

    /**
     * @param permission - permission to update
     * @param params     - params map
     */
    @Override
    public void update(Permission permission, Map<String, String> params) {
        open();
        entityManager.getTransaction().begin();
        for (Map.Entry<String, String> mapElement : params.entrySet()) {
            String key = mapElement.getKey().toLowerCase();
            String value = mapElement.getValue();
            for (Method method : Permission.class.getMethods()) {
                String methodName = method.getName().toLowerCase();
                if (methodName.contains("set" + key)) {
                    try {
                        method.invoke(permission, value);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        entityManager.merge(permission);
        entityManager.getTransaction().commit();
        close();
    }

    /**
     * @param id - id of Permission that you want to delete
     */
    @Override
    public void delete(long id) {
        open();
        entityManager.getTransaction().begin();
        Permission toRemove = entityManager.find(Permission.class, id);
        entityManager.remove(toRemove);
        entityManager.getTransaction().commit();
        close();
    }

    /**
     * opens connection
     */
    @Override
    public void open() {
        entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_NAME);
        entityManager = entityManagerFactory.createEntityManager();
    }

    /**
     * closes connection
     */
    @Override
    public void close() {
        entityManager.close();
        entityManagerFactory.close();
    }
}
