package domain.entities.notificaciones.adapters;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import domain.entities.LectorProperties;
import domain.entities.actores.organizaciones.Contacto;

import java.util.List;
import java.util.Properties;

public class AdapterTwilioWhatsapp implements AdapterWhatsapp {
    // Find your Account Sid and Token at twilio.com/console
    private String ACCOUNT_SID;
    private String AUTH_TOKEN;

    @Override
    public void enviarNotificacion(String cuerpoMensaje, List<Contacto> contactos) {
        autorizarTokens();
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        for (Contacto contacto : contactos) {
            // Message message = Message.creator( PARA X NUMERO (DEL CONTACTO), DESDE ESTE NUMERO (POR DEFAULT. NO CAMBIAR) )
            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber(contacto.whatsapp()),
                    new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                    cuerpoMensaje
            ).create(); // DESDE ESTE NUMERO (POR DEFAULT. NO CAMBIAR)

            System.out.println("Whatsapp sent, response sid: " + message.getSid());
        }
    }

    public void autorizarTokens() {
        Properties properties = new LectorProperties()
                .leerProperties("notificaciones/twilio.properties");
        ACCOUNT_SID = properties.getProperty("SID");
        AUTH_TOKEN = properties.getProperty("token");
    }
}
