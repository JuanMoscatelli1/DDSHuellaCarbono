package domain.entities.validador.validaciones;

import domain.entities.validador.Validacion;

public class AlMenosUnaMinuscula implements Validacion {
    @Override
    public String mensajeDeError() {
        return "La contraseña debe tener al menos una minúscula.";
    }

    @Override
    public boolean esSegura(String contrasena) {
        for(char caracter : contrasena.toCharArray())
            if (Character.isLowerCase(caracter))
                return true;
        return false;
    }
}

