package domain;

import domain.entities.notificaciones.Recordatorio;
import domain.entities.notificaciones.adapters.AdapterJavaxEmail;
import domain.entities.notificaciones.adapters.AdapterTwilioWhatsapp;
import domain.entities.actores.organizaciones.Contacto;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static domain.entities.notificaciones.ConfiguracionCron.CADA_MEDIO_MINUTO;

public class NotificacionesTest {

    @Test
    public void init() {
        Contacto contacto1 = new Contacto("Franco", "Magne", "fmagnecolque@frba.utn.edu.ar", "54", "1134401999");
        Contacto contacto2 = new Contacto("Franco", "Pasqualino", "fpasqualino@frba.utn.edu.ar", "54", "1130851933");
        Contacto contacto3 = new Contacto("Juan", "Moscatelli", "jmoscatelli@frba.utn.edu.ar", "54", "1165334565");
        Contacto contacto4 = new Contacto("Agustina", "Razanov", "arazanov@frba.utn.edu.ar", "54", "1173686223");

        String urlGuia = "https://europa.eu/youth/get-involved/sustainable-development/how-reduce-my-carbon-footprint_es";
        String cuerpo =
                "¡Hola! ¡Esperamos que te encuentres bien!\n\n" +
                "A continuación te brindamos el siguiente enlace para que puedas chequear " +
                "las últimas recomendaciones para mejorar el nivel de tu huella de carbono.\n\n" +
                urlGuia +
                "\n\n¡Saludos!";

        List<Contacto> contactos = new ArrayList<Contacto>() {{
            add(contacto1);
            add(contacto2);
            add(contacto3);
            add(contacto4);
        }};

        Recordatorio recordatorio = new Recordatorio();
        recordatorio.setContactos(contactos);
        recordatorio.setAdapterEmail(new AdapterJavaxEmail());
        recordatorio.setAdapterWhatsapp(new AdapterTwilioWhatsapp());
        recordatorio.setCuerpoMensaje(cuerpo);
        recordatorio.iniciarRecordatorio(CADA_MEDIO_MINUTO);
    }
}
