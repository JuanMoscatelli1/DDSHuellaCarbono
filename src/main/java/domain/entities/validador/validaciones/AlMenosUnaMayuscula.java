package domain.entities.validador.validaciones;

import domain.entities.validador.Validacion;

public class AlMenosUnaMayuscula implements Validacion {
    @Override
    public String mensajeDeError() {
        return "La contraseña debe tener al menos una mayúcula.";
    }

    @Override
    public boolean esSegura(String contrasena) {
        for(char caracter : contrasena.toCharArray())
            if (Character.isUpperCase(caracter))
                return true;
        return false;
    }
}
