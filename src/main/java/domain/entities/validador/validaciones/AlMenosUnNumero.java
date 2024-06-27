package domain.entities.validador.validaciones;

import domain.entities.validador.Validacion;

public class AlMenosUnNumero implements Validacion {
    @Override
    public String mensajeDeError() {
        return "La contraseña debe tener al menos un número.";
    }

    @Override
    public boolean esSegura(String contrasena) {
        for(char caracter : contrasena.toCharArray())
            if (Character.isDigit(caracter))
                return true;
        return false;
    }
}
