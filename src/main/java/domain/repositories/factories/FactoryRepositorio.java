package domain.repositories.factories;

import config.Config;
import domain.repositories.Repositorio;
import domain.repositories.daos.*;
import domain.repositories.data.Data;

import java.util.HashMap;

public class FactoryRepositorio {
    private static HashMap<String, Repositorio> repos;

    static {
        repos = new HashMap<>();
    }

    public static <T> Repositorio<T> get(Class<T> type) {
        String key = type.getName();

        if(repos.containsKey(key))
            return repos.get(key);

        Repositorio repo;

        if(Config.useDataBase)
            repo = new Repositorio<>(new DAOHibernate<>(type));
        else
            repo = new Repositorio<>(new DAOMemoria<>(Data.getData(type)));

        repos.put(type.getName(), repo);
        return repo;
    }
}