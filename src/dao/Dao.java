//Vsevolod Geraskin
//Assignment 3

package dao;


import java.io.Serializable;
import java.util.List;


public interface Dao<T,ID extends Serializable> {
    public List<T> read();
    public T read(ID id);
    public List<T> findBy(String first, String last, Object fvalue);
    public List<T> findBy(String first, Object value);
    public void save(T t);
    public void merge(T t);
    public void delete(T t);
    public void commit();
}
