package domain.repositories.data.dataConcrets;

import db.EntidadPersistente;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

//NO ELIMINAR LA INTERFAZ!!!!!!!!!!!!! La pienso utilizar mas adelante

public interface DataConcret {
   List<EntidadPersistente> getList();
   void addAll(List<EntidadPersistente> listClass);
}
