package task.models;

import core.interfaces.Dao;
import user.models.Permission;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TaskDAO implements Dao<Task> {

    @PersistenceContext
    private static EntityManagerFactory entityManagerFactory;
    @PersistenceContext
    public static EntityManager entityManager;
    private static final String PERSISTENCE_NAME = "CONNECTION";

    // TODO - filter Tasks by Dates, are finished or not?
    /**
     *
     * @param id = id of task that you want to get
     * @return - task
     */
    @Override
    public Task get(long id) {
        open();
        entityManager.getTransaction().begin();
        Task task = entityManager.find(Task.class, id);
        entityManager.getTransaction().commit();
        close();
        return task;
    }

    /**
     *
     * @return list of all tasks
     */
    @Override
    public List<Task> getAll() {
        open();
        entityManager.getTransaction().begin();
        List<Task> results = entityManager.createQuery("select t from Task t", Task.class).getResultList();
        close();
        return results;
    }

    /**
     *
     * @param task - task object that you want to save
     *
     */
    @Override
    public void save(Task task) {
        open();
        entityManager.getTransaction().begin();
        entityManager.persist(task);
        entityManager.flush();
        entityManager.getTransaction().commit();
        close();
    }

    /**
     *
     * @param task - task to update
     * @param params - Map<String, String> of parameters
     *               Important! for date, date format in string required is dd/MM/yyyy
     * @throws ParseException - exception while parsing String to Date
     */
    @Override
    public void update(Task task, Map<String, String> params) throws ParseException {
        open();
        entityManager.getTransaction().begin();
        for (Map.Entry<String, String> mapElement : params.entrySet()) {
            String key = mapElement.getKey().toLowerCase();
            String value = mapElement.getValue();
            for (Method method : Permission.class.getMethods()) {
                String methodName = method.getName().toLowerCase();
                if (methodName.contains("set" + key)) {
                    try {
                        if (methodName.equals("setdatecreated") || methodName.equals("setdatetackled") || methodName.equals("setdatefinished")) {
                            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(value);
                        } else {
                            method.invoke(task, value);
                        }
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        entityManager.merge(task);
        entityManager.getTransaction().commit();
        close();
    }

    /**
     *
     * @param id - id of Task that you want to delete
     */
    @Override
    public void delete(long id) {
        open();
        entityManager.getTransaction().begin();
        Task toRemove = entityManager.find(Task.class, id);
        entityManager.remove(toRemove);
        entityManager.getTransaction().commit();
        close();
    }

    /**
     * opens database connection
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
