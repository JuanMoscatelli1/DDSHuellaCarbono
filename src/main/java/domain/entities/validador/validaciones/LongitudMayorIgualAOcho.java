package domain.entities.validador.validaciones;

import domain.entities.validador.Validacion;

public class LongitudMayorIgualAOcho implements Validacion {
    @Override
    public String mensajeDeError() {
        return "La contraseña debe tener al menos 8 caracteres.";
    }

    @Override
    public boolean esSegura(String contrasena) {
        return contrasena.length() >= 8;
    }
}
