package domain.entities.services.geodds;

import domain.entities.services.geodds.adapters.AdapterServicio;
import domain.entities.services.geodds.adapters.GeoDdsServiceAdapter;
import domain.entities.trayectos.tramo.Distancia;
import domain.entities.ubicaciones.*;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class ServicioDeUbicaciones {
    private static ServicioDeUbicaciones instancia = null;

    @Setter
    private AdapterServicio adapter = new GeoDdsServiceAdapter();

    private ServicioDeUbicaciones() {}

    public static ServicioDeUbicaciones getInstancia() {
        if (instancia == null) instancia = new ServicioDeUbicaciones();
        return instancia;
    }

    public List<Pais> paises() {
        List<Pais> listadoPais = new ArrayList<>();
        for(int offset = 1; listadoPais.size() == 50 * (offset-1); offset++)
            listadoPais.addAll(adapter.listadoDePaises(offset));
        return listadoPais;
    }

    public List<Provincia> provinciasPorPais(int paisId) {
        List<Provincia> listadoProvincias = new ArrayList<>();
        for(int offset = 1; listadoProvincias.size() == 50 * (offset-1); offset++)
            listadoProvincias.addAll(adapter.listadoDeProvincias(offset, paisId));
        return listadoProvincias;
    }

    public List<Municipio> municipiosPorProvincia(int provinciaId) {
        List<Municipio> listadoMunicipios = new ArrayList<>();
        for(int offset = 1; listadoMunicipios.size() == 50 * (offset-1); offset++)
            listadoMunicipios.addAll(adapter.listadoDeMunicipios(offset, provinciaId));
        return listadoMunicipios;
    }

    public List<Localidad> localidadesPorMunicipio(int municipioId) {
        List<Localidad> listadoLocalidades = new ArrayList<>();
        for(int offset = 1; listadoLocalidades.size() == 50 * (offset-1); offset ++)
            listadoLocalidades.addAll(adapter.listadoDeLocalidades(offset, municipioId));
        return listadoLocalidades;
    }

    public Distancia obtenerDistancia(Direccion direccionOrigen, Direccion direccionDestino) {
        return adapter.distancia(direccionOrigen, direccionDestino);
    }
}