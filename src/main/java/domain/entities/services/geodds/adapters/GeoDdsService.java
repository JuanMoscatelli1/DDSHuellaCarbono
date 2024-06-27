package domain.entities.services.geodds.adapters;

import domain.entities.trayectos.tramo.Distancia;
import domain.entities.ubicaciones.Localidad;
import domain.entities.ubicaciones.Municipio;
import domain.entities.ubicaciones.Pais;
import domain.entities.ubicaciones.Provincia;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface GeoDdsService {
    @GET("distancia")
    Call<Distancia> distancia(
            @Query("localidadOrigenId") int localidadOrigenId,
            @Query("calleOrigen") String calleOrigen,
            @Query("alturaOrigen") String alturaOrigen,
            @Query("localidadDestinoId") int localidadDestinoId,
            @Query("calleDestino") String calleDestino,
            @Query("alturaDestino") String alturaDestino
    );

    @GET("provincias")
    Call<List<Provincia>> provincias(@Query("offset") int offset, @Query("paisId") int paisId);

    @GET("municipios")
    Call<List<Municipio>> municipios(@Query("offset") Integer offset, @Query("provinciaId") Integer provinciaId);

    @GET("localidades")
    Call<List<Localidad>> localidades(@Query("offset") int offset, @Query("municipioId") int municipioId);

    @GET("paises")
    Call<List<Pais>> paises(@Query("offset") int offset);
}