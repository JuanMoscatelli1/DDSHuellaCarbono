package domain.entities.validador.validaciones;

import domain.entities.validador.Validacion;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NoEstaEnElTop10000 implements Validacion {
    private final List<String> contrasenas;

    public NoEstaEnElTop10000() {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("validador/top10000.txt");
        assert in != null;
        Scanner scanner = new Scanner(in);

        this.contrasenas = new ArrayList<>();

        while(scanner.hasNextLine())
            this.contrasenas.add(scanner.nextLine());
        scanner.close();
    }

    @Override
    public String mensajeDeError() {
        return "La contraseña está entre las más usadas.";
    }

    @Override
    public boolean esSegura(String contrasena) {
        return !this.contrasenas.contains(contrasena.toLowerCase());
    }
}
