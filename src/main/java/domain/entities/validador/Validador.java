package domain.entities.validador;

import java.util.*;
import java.util.stream.Collectors;

public class Validador {
    private final HashMap<String, Validacion> validaciones;
    private String mensajeDeError;

    public Validador() {
        this.validaciones = new HashMap<>();
    }

    public String mensajeDeError() {
        return this.mensajeDeError;
    }

    public boolean esSegura(String contrasena) {
        List<Validacion> validacionesFallidas = this.validaciones.values().stream()
                .filter(v -> !v.esSegura(contrasena))
                .collect(Collectors.toList());

        this.mensajeDeError =  validacionesFallidas.stream()
                .map(Validacion::mensajeDeError)
                .collect(Collectors.joining("\n"));

        return validacionesFallidas.isEmpty();
    }

    public void agregarValidaciones(HashMap<String, Validacion> validaciones) {
        this.validaciones.putAll(validaciones);
    }
    public void agregarValidacion(String clave, Validacion valor) {
        this.validaciones.put(clave, valor);
    }
    public void eliminarValidacion(String clave) { this.validaciones.remove(clave); }
}