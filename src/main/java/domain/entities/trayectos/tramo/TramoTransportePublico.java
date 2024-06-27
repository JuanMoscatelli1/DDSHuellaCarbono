package domain.entities.trayectos.tramo;

import domain.entities.trayectos.transportePublico.Parada;
import domain.entities.trayectos.transportePublico.TransportePublico;

import javax.persistence.*;

@Entity
@DiscriminatorValue("TRANSPORTE PUBLICO")
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "id")) })
public class TramoTransportePublico extends Tramo {
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "transporte_publico_id", referencedColumnName = "id")
    private TransportePublico transportePublico;

    @ManyToOne
    @JoinColumn(name = "parada_origen_id", referencedColumnName = "id")
    private Parada paradaOrigen;

    @ManyToOne
    @JoinColumn(name = "parada_destino_id", referencedColumnName = "id")
    private Parada paradaDestino;

    public TramoTransportePublico(
            TransportePublico transportePublico,
            Parada paradaOrigen,
            Parada paradaDestino
    ) {
        super();
        this.transportePublico = transportePublico;
        this.paradaOrigen = paradaOrigen;
        this.paradaDestino = paradaDestino;
        this.distancia = calcularDistancia();
    }

    public TramoTransportePublico() {

    }

    @Override
    public Distancia calcularDistancia() {
        if(this.distancia != null) return this.distancia;

        double distancia;
        if (paradaOrigen.equals(paradaDestino))
            distancia = 0;
        else if (transportePublico.estaAntesDe(paradaOrigen, paradaDestino))
            distancia = paradaOrigen.distanciaHasta(paradaDestino);
        else
            distancia = paradaDestino.distanciaHasta(paradaOrigen);
        return new Distancia(distancia);
    }

    @Override
    public double calcularHuella() {
        huellaDeCarbono = transportePublico.getFactorDeEmision() * distancia.getValor();
        return huellaDeCarbono;
    }

    @Override
    public TramoDTO convertirADTO() {
        TramoDTO tramoDTO = new TramoDTO(this);
        tramoDTO.setVehiculo(transportePublico.convertirADTO().getTipoDeTransportePublico());
        tramoDTO.setOrigen(paradaOrigen.getNombre());
        tramoDTO.setDestino(paradaDestino.getNombre());
        return tramoDTO;
    }
}