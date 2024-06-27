package domain.repositories.data.dataConcrets;

import db.EntidadPersistente;
import domain.entities.actores.organizaciones.Contacto;

import java.util.ArrayList;
import java.util.List;

public class DataContacto{
    private static List<EntidadPersistente> contactos = new ArrayList<>();

    public static List<EntidadPersistente> getList() {
        //Aca van los datos en concreto (guardarlos en la lista)
        Contacto contacto1 = new Contacto();
        contacto1.setNombre("Agustin");
        contacto1.setApellido("Ferre");
        contacto1.setMail("fagustin34@gmail.com");
        contacto1.setCodigoPais("+54");
        contacto1.setNroTelefono("15239547");
        contactos.add(contacto1);

        Contacto contacto2 = new Contacto();
        contacto2.setNombre("Jolee");
        contacto2.setApellido("Akrigg");
        contacto2.setMail("jakrigg3@gmail.com");
        contacto2.setCodigoPais("+54");
        contacto2.setNroTelefono("15839202");
        contactos.add(contacto2);

        Contacto contacto3 = new Contacto();
        contacto3.setNombre("Raquel");
        contacto3.setApellido("Deards");
        contacto3.setMail("rdeards0@gmail.com");
        contacto3.setCodigoPais("+54");
        contacto3.setNroTelefono("15757921");
        contactos.add(contacto3);

        Contacto contacto4 = new Contacto();
        contacto4.setNombre("Beitris");
        contacto4.setApellido("Lassey");
        contacto4.setMail("blassey1@gmail.com");
        contacto4.setCodigoPais("+54");
        contacto4.setNroTelefono("15163253");
        contactos.add(contacto4);

        Contacto contacto5 = new Contacto();
        contacto5.setNombre("Elianore");
        contacto5.setApellido("Waadenburg");
        contacto5.setMail("ewaadenburg2@gmail.com");
        contacto5.setCodigoPais("+54");
        contacto5.setNroTelefono("15251377");
        contactos.add(contacto5);

        Contacto contacto6 = new Contacto();
        contacto6.setNombre("Rudolf");
        contacto6.setApellido("Belfet");
        contacto6.setMail("rbelfet4@gmail.com");
        contacto6.setCodigoPais("+54");
        contacto6.setNroTelefono("15844015");
        contactos.add(contacto6);

        Contacto contacto7 = new Contacto();
        contacto7.setNombre("Demott");
        contacto7.setApellido("Croll");
        contacto7.setMail("dcroll6@gmail.com");
        contacto7.setCodigoPais("+54");
        contacto7.setNroTelefono("15767736");
        contactos.add(contacto7);

        Contacto contacto8 = new Contacto();
        contacto8.setNombre("Aurelia");
        contacto8.setApellido("Pettiford");
        contacto8.setMail("apettiford7@gmail.com");
        contacto8.setCodigoPais("+54");
        contacto8.setNroTelefono("15468397");
        contactos.add(contacto8);

        Contacto contacto9 = new Contacto();
        contacto9.setNombre("Elias");
        contacto9.setApellido("Hallshaw");
        contacto9.setMail("ehallshawb@gmail.com");
        contacto9.setCodigoPais("+54");
        contacto9.setNroTelefono("15490281");
        contactos.add(contacto9);

        Contacto contacto10 = new Contacto();
        contacto10.setNombre("Cello");
        contacto10.setApellido("Bortol");
        contacto10.setMail("cbortolle@gmail.com");
        contacto10.setCodigoPais("+54");
        contacto10.setNroTelefono("15896032");
        contactos.add(contacto10);

        return contactos;
    }

    public static void addAll(List<EntidadPersistente> listClass) {
        contactos.addAll(listClass);
    }
}
