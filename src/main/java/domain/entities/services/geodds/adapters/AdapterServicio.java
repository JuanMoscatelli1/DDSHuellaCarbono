package domain.entities.services.geodds.adapters;

import domain.entities.trayectos.tramo.Distancia;
import domain.entities.ubicaciones.*;

import java.util.List;

public interface AdapterServicio {
    Distancia distancia(Direccion direccionOrigen, Direccion direccionDestino);
    List<Localidad> listadoDeLocalidades(int offset, int municipioId);
    List<Municipio> listadoDeMunicipios(int offset, int provinciaId);
    List<Provincia> listadoDeProvincias(int offset, int paisId);
    List<Pais> listadoDePaises(int offset);
}
