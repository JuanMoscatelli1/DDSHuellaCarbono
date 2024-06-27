package domain.entities.services.geodds.adapters;

import domain.entities.LectorProperties;
import domain.entities.trayectos.tramo.Distancia;
import domain.entities.ubicaciones.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class GeoDdsServiceAdapter implements AdapterServicio {
    private final Retrofit retrofit;

    public GeoDdsServiceAdapter() {
        Properties lectorProperties = new LectorProperties()
                .leerProperties("token.properties");

        String token = lectorProperties.getProperty("token");

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
            Request newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + token)
                    .build();
            return chain.proceed(newRequest);
        }).build();

        String urlAPI = "https://ddstpa.com.ar/api/";
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(urlAPI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public Distancia distancia(Direccion direccionOrigen, Direccion direccionDestino) {
        try {
            Call<Distancia> requestDistancia = this.retrofit.create(GeoDdsService.class).distancia(
                    direccionOrigen.getLocalidadId(),
                    direccionOrigen.getCalle(),
                    direccionOrigen.getAltura(),
                    direccionDestino.getLocalidadId(),
                    direccionDestino.getCalle(),
                    direccionDestino.getAltura()
            );
            retrofit2.Response<Distancia> responseDistancia = requestDistancia.execute();
            return responseDistancia.body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Localidad> listadoDeLocalidades(int offset, int municipioId) {
        try {
            Call<List<Localidad>> requestLocalidades = this.retrofit.create(GeoDdsService.class).localidades(offset, municipioId);
            retrofit2.Response<List<Localidad>> responseLocalidades = requestLocalidades.execute();
            return responseLocalidades.body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Municipio> listadoDeMunicipios(int offset, int provinciaId) {
        try {
            Call<List<Municipio>> requestMunicipio = this.retrofit.create(GeoDdsService.class).municipios(offset, provinciaId);
            retrofit2.Response<List<Municipio>> responseMunicipio = requestMunicipio.execute();
            return responseMunicipio.body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Provincia> listadoDeProvincias(int offset, int paisId) {
        try {
            Call<List<Provincia>> requestProvincia = retrofit.create(GeoDdsService.class).provincias(offset, paisId);
            retrofit2.Response<List<Provincia>> responseProvincia = requestProvincia.execute();
            return responseProvincia.body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Pais> listadoDePaises(int offset) {
        try {
            Call<List<Pais>> requestPais = retrofit.create(GeoDdsService.class).paises(offset);
            retrofit2.Response<List<Pais>> responsePais = requestPais.execute();
            return responsePais.body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
