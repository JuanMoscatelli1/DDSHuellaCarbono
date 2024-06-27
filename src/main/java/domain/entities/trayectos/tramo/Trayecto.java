package domain.entities.trayectos.tramo;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("TRAYECTO")
public class Trayecto extends Tramo {
    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "trayecto_id", referencedColumnName = "id")
    private Set<Tramo> tramos;

    public Trayecto() {
        super();
        tramos = new HashSet<>();
        distancia = new Distancia(0.0);
    }

    public void agregarTramo(Tramo tramo) {
        tramos.add(tramo);
        tramo.setOrganizacion(organizacion);
        distancia.sumar(tramo.getDistancia().getValor());
    }

    @Override
    public double calcularHuella() {
        huellaDeCarbono = tramos.stream().mapToDouble(Tramo::calcularHuella).sum();
        return huellaDeCarbono;
    }

    @Override
    public Distancia calcularDistancia() {
        return distancia;
    }

    @Override
    public TramoDTO convertirADTO() {
        Tramo.TramoDTO tramo = new TramoDTO(this);
        tramo.setTramos(tramos);
        return tramo;
    }
}