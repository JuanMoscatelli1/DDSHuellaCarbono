package domain;

import domain.entities.validador.Validacion;
import domain.entities.validador.Validador;
import domain.entities.validador.validaciones.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Objects;

public class ValidadorTest {
    private Validador validador = new Validador();
    private String contrasena;

    @Before
    public void inicializar() {
        validador.agregarValidaciones(new HashMap<String, Validacion>() {{
            put("mayus", new AlMenosUnaMayuscula());
            put("minus", new AlMenosUnaMinuscula());
            put("nro", new AlMenosUnNumero());
            put("top", new NoEstaEnElTop10000());
            put("long", new LongitudMayorIgualAOcho());
        }});
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
        System.out.println(contrasena);
    }

    @After
    public void mensajeDeError() {
        String mensajeDeError = validador.mensajeDeError();
        if(!Objects.equals(mensajeDeError, ""))
            System.out.println(mensajeDeError + "\n");
    }


    @Test
    public void testNoTieneNumeros() {
        this.setContrasena("UTNfrbaDdS");
        Assert.assertFalse(validador.esSegura(contrasena));
    }

    @Test
    public void testNoTieneMinusculas() {
        this.setContrasena("UTNFRBA2022");
        Assert.assertFalse(validador.esSegura(contrasena));
    }

    @Test
    public void testNoTieneMayusculas() {
        this.setContrasena("utnfrba2022");
        Assert.assertFalse(validador.esSegura(contrasena));
    }

    @Test
    public void testEstaEnElTop() {
        this.setContrasena("1Qaz2wSx");
        Assert.assertFalse(validador.esSegura(contrasena));
    }

    @Test
    public void testLongitudMenorAOcho() {
        this.setContrasena("UTNba22");
        Assert.assertFalse(validador.esSegura(contrasena));
    }

    @Test
    public void testSegura() {
        this.setContrasena("UTNfrba2022");
        Assert.assertTrue(validador.esSegura(contrasena));
        System.out.println("Contraseña segura.");
    }

    @Test
    public void desactivarTop() {
        validador.eliminarValidacion("top");
        Assert.assertTrue(validador.esSegura("1Qaz2wSx"));
        System.out.println("Contraseña segura.");
    }

    @Test
    public void estaEnElTop() {
        Assert.assertFalse(validador.esSegura("1Qaz2wSx"));
    }
}
