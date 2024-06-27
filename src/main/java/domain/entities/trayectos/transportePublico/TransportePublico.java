package domain.entities.trayectos.transportePublico;

import db.EntidadPersistente;
import domain.entities.huellaDeCarbono.FactorDeEmision;
import lombok.Getter;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "transporte_publico")
public class TransportePublico extends EntidadPersistente {
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_tp")
    private TipoDeTransportePublico tipoDeTransportePublico;

    @Column(name = "linea")
    private String linea;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "medio_tp_id", referencedColumnName = "id")
    private List<Parada> recorrido;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "fe_id", referencedColumnName = "id")
    private FactorDeEmision factorDeEmision;

    public TransportePublico() {
    }

    public TransportePublico(
            String tipoDeTransportePublico,
            String linea,
            List<Parada> recorrido
    ) {
        this.tipoDeTransportePublico = TipoDeTransportePublico.valueOf(tipoDeTransportePublico);
        this.linea = linea;
        this.recorrido = recorrido;
        this.linkearParadas();
        this.factorDeEmision = new FactorDeEmision(0.4, "lt");
    }

    private void linkearParadas() {
        Iterator<Parada> iteradorRecorrido = recorrido.iterator();
        Parada paradaActual = iteradorRecorrido.next(), paradaSiguiente;

        while(iteradorRecorrido.hasNext()) {
            paradaSiguiente = iteradorRecorrido.next();
            paradaActual.setParadaSiguiente(paradaSiguiente);
            paradaActual = paradaSiguiente;
        }
    }

    public boolean estaAntesDe(Parada unaParada, Parada otraParada) {
        return recorrido.indexOf(unaParada) < recorrido.indexOf(otraParada);
    }

    public double getFactorDeEmision() {
        return factorDeEmision.getValor();
    }

    // ---------------- DTO ----------------

    public TransportePublicoDTO convertirADTO(){
        return new TransportePublicoDTO(this);
    }

    public static class TransportePublicoDTO {
        @Getter
        public int id;
        public TipoDeTransportePublico tipoDeTransportePublico;
        @Getter
        public String linea;
        public List<Parada> recorrido;
        public FactorDeEmision factorDeEmision;

        public TransportePublicoDTO(TransportePublico transportePublico){
            id = transportePublico.id;
            tipoDeTransportePublico = transportePublico.tipoDeTransportePublico;
            linea = transportePublico.linea;
            recorrido = transportePublico.recorrido;
            factorDeEmision = transportePublico.factorDeEmision;
        }

        public String getNombre() {
            return getTipoDeTransportePublico() + " " + linea;
        }

        public String getTipoDeTransportePublico() {
            return tipoDeTransportePublico.name();
        }

        public List<Parada.ParadaDTO> getRecorrido() {
            return recorrido.stream().map(Parada::convertirADTO).collect(Collectors.toList());
        }
    }

}