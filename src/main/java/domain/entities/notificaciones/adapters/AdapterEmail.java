package domain.entities.notificaciones.adapters;

import domain.entities.actores.organizaciones.Contacto;

import java.util.List;

public interface AdapterEmail {
    void enviarNotificacion(String cuerpoMensaje, List<Contacto> contactos);
}
