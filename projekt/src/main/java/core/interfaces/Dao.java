package core.interfaces;

import java.util.List;
import java.util.Map;

public interface Dao<T> {

    T get(long id);

    List<T> getAll();

    void save(T t);

    void update(T t, Map<String, String> params);

    void delete(long id);

    void open();

    void close();
}
