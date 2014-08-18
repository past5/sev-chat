//Vsevolod Geraskin
//Final Project
package dao.impl;


import org.stripesstuff.stripersist.Stripersist;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import dao.Dao;


public abstract class BaseDaoImpl<T,ID extends Serializable>
    implements Dao<T,ID>
{
    private Class<T> entityClass;
    private static final int MAX_RESULTS = 20;
    
    @SuppressWarnings("unchecked")
    public BaseDaoImpl() {
        entityClass = (Class<T>)
            ((ParameterizedType) getClass().getGenericSuperclass())
            .getActualTypeArguments()[0];
    }
    /* methods... */
    
    public List<T> read() {	
    	Query query = Stripersist.getEntityManager()
            .createQuery("from " + entityClass.getName() + " t order by t.id desc");
    	
    	query.setMaxResults(MAX_RESULTS);
    	
    	return (List<T>) getResults(query);
    }
    
    public T read(ID id) {
        return Stripersist.getEntityManager().find(entityClass, id);
    }
    
    public void merge(T object) {
    	Stripersist.getEntityManager().merge(object);  
    }
    public void save(T object) {
    	Stripersist.getEntityManager().persist(object);  
    }
    public void delete(T object) {
    	Stripersist.getEntityManager().remove(object);
    }
    public void commit() {
    	Stripersist.getEntityManager().getTransaction().commit();
    }
    
    public List<T> findBy(String first, String last, Object fvalue) {
        Query query = Stripersist.getEntityManager()
            .createQuery(getQuery(first, last, fvalue));
        
        query.setMaxResults(MAX_RESULTS);
        
        return (List<T>) getResults(query);
    }
    
    public List<T> findBy(String first, Object value) {
        Query query = Stripersist.getEntityManager()
            .createQuery(getQuery(first, value));
        
        return (List<T>) getResults(query);
    }

    private String getQuery(String first, String last, Object fvalue){
        String query =
            "from " + entityClass.getName() + " t " +
            "where (upper(t." + first + ") LIKE '" + fvalue.toString().toUpperCase() +
            "' and upper(t." + last + ") IS NOT NULL ) OR (upper(t." + first + 
            ") IS NOT NULL and upper(t." + last + ") LIKE '" + fvalue.toString().toUpperCase() + 
            "') OR upper(t." + last + ") IS NULL order by t.id desc";
            return query;
    }
    
    private String getQuery(String first, Object value){
        String query =
            "from " + entityClass.getName() + " t " +
            "where upper(t." + first + ") LIKE '" + value.toString().toUpperCase() + "%'";
            return query;
    }
    
    @SuppressWarnings("unchecked")
    private List<T> getResults(Query query) {
        try {
            return (List<T>) query.getResultList();
        }
        catch (NoResultException exc) {
            return null;
        }
    }


}
