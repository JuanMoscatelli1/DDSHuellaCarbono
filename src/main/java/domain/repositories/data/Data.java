package domain.repositories.data;

import db.EntidadPersistente;
import domain.entities.actores.miembros.Miembro;
import domain.entities.actores.organizaciones.Contacto;
import domain.entities.actores.organizaciones.Organizacion;
import domain.entities.actores.organizaciones.Sector;
import domain.entities.actores.sectores.SectorMunicipal;
import domain.entities.actores.sectores.SectorProvincial;
import domain.entities.trayectos.tramo.Trayecto;
import domain.entities.trayectos.transportePublico.TransportePublico;
import domain.entities.trayectos.vehiculosPrivados.TransportePrivado;
import domain.entities.trayectos.tramo.Distancia;
import domain.entities.trayectos.tramo.TramoTransportePrivado;
import domain.entities.trayectos.tramo.TramoTransportePublico;
import domain.entities.ubicaciones.Direccion;
import domain.repositories.data.dataConcrets.*;

import java.util.*;

public class Data {
    private static Map<String, List<EntidadPersistente>> listadoDeDatos = new HashMap<>();

    public static void setInitialListadoDeDatos(){
        listadoDeDatos.put(Contacto.class.getName(), DataContacto.getList());
        listadoDeDatos.put(Direccion.class.getName(), DataDireccion.getList());
        listadoDeDatos.put(Distancia.class.getName(), DataDistancia.getList());
        listadoDeDatos.put(Miembro.class.getName(), DataMiembro.getList());
        listadoDeDatos.put(Organizacion.class.getName(), DataOrganizacion.getList());
        listadoDeDatos.put(Sector.class.getName(), DataSector.getList());
        listadoDeDatos.put(SectorMunicipal.class.getName(), DataSectorMunicipal.getList());
        listadoDeDatos.put(SectorProvincial.class.getName(), DataSectorProvincial.getList());
        listadoDeDatos.put(TramoTransportePrivado.class.getName(), DataTramoTransportePrivado.getList());
        listadoDeDatos.put(TramoTransportePublico.class.getName(), DataTramoTransportePublico.getList());
        listadoDeDatos.put(TransportePrivado.class.getName(), DataTransportePrivado.getList());
        listadoDeDatos.put(TransportePublico.class.getName(), DataTransportePublico.getList());
        listadoDeDatos.put(Trayecto.class.getName(), DataTrayecto.getList());
    }

    public static List<EntidadPersistente> getData(Class type){
       List<EntidadPersistente> entidades = new ArrayList<>();
       setInitialListadoDeDatos();
        if (listadoDeDatos.containsKey(type.getName())){
            //Le paso la key (el string) para que me devuelva la lista de entidades persistentes asociada
           entidades = listadoDeDatos.get(type.getName());
       }
        return entidades;
    }

}