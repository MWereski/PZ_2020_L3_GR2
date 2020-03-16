package user.models;

import core.interfaces.Dao;
import org.mindrot.jbcrypt.BCrypt;
import task.models.Task;

import javax.persistence.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserDAO implements Dao<User> {

    @PersistenceContext
    private static EntityManagerFactory entityManagerFactory;
    @PersistenceContext
    public static EntityManager entityManager;
    private static final String PERSISTENCE_NAME = "CONNECTION";

    // TODO getUserByUsername method
    /**
     * @param id = id of user that you want to get
     * @return user's instance or null, when did't find
     */
    @Override
    public User get(long id) {
        open();
        entityManager.getTransaction().begin();
        User user = entityManager.find(User.class, id);
        entityManager.getTransaction().commit();
        close();
        return user;
    }

    /**
     * @return list of all records from user table in database
     */
    @Override
    public List<User> getAll() {
        open();
        entityManager.getTransaction().begin();
        List<User> results = entityManager.createQuery("select a from User a", User.class).getResultList();
        close();
        return results;
    }

    /**
     * @param user - User class instance that you want to keep in your database
     */
    @Override
    public void save(User user) throws PersistenceException {
        open();
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        close();
    }

    public void addPermission(User user, Permission p){
        open();
        user.getPermissions().add(p);
        entityManager.getTransaction().begin();
        entityManager.merge(user);
        entityManager.getTransaction().commit();
        close();
    }

    public void addTask(User user, Task task){
        open();
        user.getTasks().add(task);
        entityManager.getTransaction().begin();
        entityManager.merge(user);
        entityManager.getTransaction().commit();
        close();
    }

    /**
     * @param user   - user object that u want to update
     * @param params - String map with params for update
     *               for example - updating username require map with key: username and value: someValue
     *               for example to set admin or leader value param leader: true is needed.
     */
    @Override
    public void update(User user, Map<String, String> params) {
        // TODO - REFACTOR DUE TO PERMISSIONS
        open();
        entityManager.getTransaction().begin();
        for (Map.Entry<String, String> mapElement : params.entrySet()) {
            String key = mapElement.getKey().toLowerCase();
            String value = mapElement.getValue();
            for (Method method : User.class.getMethods()) {
                String methodName = method.getName().toLowerCase();
                if (methodName.contains("set" + key)) {
                    try {
                        if (methodName.equals("setadmin") || methodName.equals("setleader")) {
                            method.invoke(user, value.equals("true"));
                        } else {
                            method.invoke(user, value);
                        }

                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        entityManager.merge(user);
        entityManager.getTransaction().commit();
        close();
    }

    /**
     * @param id - id of user that u want to delete
     */
    @Override
    public void delete(long id) {
        open();
        entityManager.getTransaction().begin();
        User toRemove = entityManager.find(User.class, id);
        entityManager.remove(toRemove);
        entityManager.getTransaction().commit();
        close();
    }

    /**
     * @param username - users username
     * @param password - users password
     * @return user instance if credentials are right, and null if not
     */
    public Optional<User> validate(String username, String password) {
        entityManager.getTransaction().begin();
        int user_id = entityManager.createQuery("select a from User a where a.username = " + username).getFirstResult();
        User user = get(user_id);
        if (BCrypt.checkpw(password, user.getPassword())) {
            return Optional.of(user).or(null);
        }
        return Optional.empty();
    }

    /**
     * Opens database connection
     */
    @Override
    public void open() {
        entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_NAME);
        entityManager = entityManagerFactory.createEntityManager();
    }

    /**
     * closes database connection
     */
    @Override
    public void close() {
        entityManager.close();
        entityManagerFactory.close();
    }
}
