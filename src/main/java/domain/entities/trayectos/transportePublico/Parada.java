package domain.entities.trayectos.transportePublico;

import db.EntidadPersistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "parada")
public class Parada extends EntidadPersistente {
    @Column(name = "nombre")
    @Getter
    private String nombre;

    @OneToOne
    private Parada paradaSiguiente;

    @OneToOne
    @Setter
    private Parada paradaAnterior;

    @Column(name = "distancia_parada_sgte")
    private double distanciaParadaSiguiente;

    public Parada() {
    }

    public Parada(String nombre, double distanciaParadaSiguiente) {
        this.nombre = nombre;
        this.distanciaParadaSiguiente = distanciaParadaSiguiente;
    }

    public void setParadaSiguiente(Parada paradaSiguiente) {
        this.paradaSiguiente = paradaSiguiente;
        paradaSiguiente.setParadaAnterior(this);
    }

    public double distanciaHasta(Parada paradaFinal) {
        if (this.equals(paradaFinal)) return 0;
        return distanciaParadaSiguiente + paradaSiguiente.distanciaHasta(paradaFinal);
    }

    // ----------------- DTO ------------------

    public ParadaDTO convertirADTO(){
        return new ParadaDTO(this);
    }

    public static class ParadaDTO {
        @Getter
        public int id;
        @Getter
        public String nombre;
        public Parada paradaSiguiente;
        public Parada paradaAnterior;
        public double distanciaSiguienteParada;

        public ParadaDTO(Parada parada){
            id = parada.id;
            nombre = parada.nombre;
            paradaSiguiente = parada.paradaSiguiente;
            paradaAnterior = parada.paradaAnterior;
            distanciaSiguienteParada = parada.distanciaParadaSiguiente;
        }
    }
}
