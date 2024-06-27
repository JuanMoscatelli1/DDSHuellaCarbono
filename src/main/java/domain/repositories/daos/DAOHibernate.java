package domain.repositories.daos;

import db.EntityManagerHelper;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class DAOHibernate<T> implements DAO<T> {
    private final Class<T> type;

    public DAOHibernate(Class<T> type) {
        this.type = type;
    }

    @Override
    public List<T> buscarTodos() {
        CriteriaBuilder builder = EntityManagerHelper.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        Root<T> root = criteria.from(type);
        criteria.select(root);
        return EntityManagerHelper.getEntityManager().createQuery(criteria).getResultList();
    }

    @Override
    public T buscar(int id) {
        return EntityManagerHelper.getEntityManager().find(type, id);
    }

    @Override
    public List<T> buscarTodos(String atributo, int id) {
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + type.getName() + " where " + atributo + "=" + id)
                .getResultList();
    }

    @Override
    public T buscar(String atributo, int id) {
        return (T) EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + type.getName() + " where " + atributo + "=" + id)
                .getSingleResult();
    }

    @Override
    public void agregar(T unObjeto) {
        EntityManagerHelper.withTransaction(entityManager -> entityManager.persist(unObjeto));
    }

    @Override
    public void modificar(T unObjeto) {
        EntityManagerHelper.withTransaction(entityManager -> entityManager.merge(unObjeto));
    }

    @Override
    public void eliminar(T unObjeto) {
        EntityManagerHelper.withTransaction(entityManager -> entityManager.remove(unObjeto));
    }
}