package domain.repositories.daos;

import db.EntidadPersistente;

import java.util.List;

public class DAOMemoria<T> implements DAO<T> {
    private List<T> entidades;

    public DAOMemoria(List<T> entidades){
        this.entidades = entidades;
    }

    @Override
    public List<T> buscarTodos() {
        return (List<T>) this.entidades;
    }

    @Override
    public T buscar(String atributo, int id) {
        return null;
    }

    @Override
    public T buscar(int id) {
        return (T) this.entidades.stream()
                .filter(e -> ((EntidadPersistente) e).getId() == id)
                .findFirst().orElse(null);
    }

    @Override
    public List<T> buscarTodos(String atributo, int id) {
        return null;
    }

    @Override
    public void agregar(T unObjeto) {
        this.entidades.add(unObjeto);
    }

    @Override
    public void modificar(T unObjeto) {
        T entidadPersistente = buscar(((EntidadPersistente) unObjeto).getId());
        if(entidadPersistente != null) {
            eliminar(entidadPersistente);
            agregar(unObjeto);
        }
    }

    @Override
    public void eliminar(T unObjeto) {
        this.entidades.remove(unObjeto);
    }

}