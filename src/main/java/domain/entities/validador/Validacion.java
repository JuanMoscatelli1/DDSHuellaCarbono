package domain.entities.validador;

public interface Validacion {
    String mensajeDeError();
    boolean esSegura(String contrasena);
}