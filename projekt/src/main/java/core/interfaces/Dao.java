package core.interfaces;

import javax.persistence.PersistenceException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface Dao<T> {

    T get(long id);

    List<T> getAll();

    void save(T t) throws PersistenceException;

    void update(T t, Map<String, String> params) throws ParseException;

    void delete(long id);

    void open();

    void close();
}
