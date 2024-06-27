package domain.repositories.daos;

import java.util.List;

public interface DAO<T> {
    void agregar(T unObjeto);
    void modificar(T unObjeto);
    void eliminar(T unObjeto);
    List<T> buscarTodos();
    T buscar(String atributo, int id);
    T buscar(int id);
    List<T> buscarTodos(String atributo, int id);
}
