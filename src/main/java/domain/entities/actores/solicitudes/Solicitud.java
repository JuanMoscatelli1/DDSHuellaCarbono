package domain.entities.actores.solicitudes;

import db.EntidadPersistente;
import domain.entities.actores.miembros.Miembro;
import domain.entities.actores.organizaciones.Sector;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static domain.entities.actores.solicitudes.Estado.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "solicitud")
public class Solicitud extends EntidadPersistente {
    @Getter
    @Setter
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "miembro_id", referencedColumnName = "id")
    private Miembro miembro;

    @Getter
    @ManyToOne
    @JoinColumn(name = "sector_id", referencedColumnName = "id")
    private Sector sector;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitud_id", referencedColumnName = "id")
    private List<EstadoSolicitud> estados;

    public Solicitud() {

    }

    public Solicitud(Sector sector) {
        this.sector = sector;
        this.estados = new ArrayList<>();
        agregarEstado(new EstadoSolicitud(PENDIENTE));
    }

    public void enviar() {
        sector.getOrganizacion().recibirSolicitud(this);
    }

    public void agregarEstado(EstadoSolicitud estado) {
        estados.add(estado);
    }

    public Estado estadoActual() {
        return estados.get(estados.size() - 1).getEstado();
    }

    public SolicitudDTO convertirADTO() {
        return new SolicitudDTO(this);
    }

    public class SolicitudDTO {
        @Getter
        private int id;
        private Miembro miembro;
        private Sector sector;
        private List<EstadoSolicitud> estados;

        public SolicitudDTO(Solicitud solicitud) {
            this.id = solicitud.id;
            this.miembro = solicitud.miembro;
            this.sector = solicitud.sector;
            this.estados = solicitud.estados;
        }

        public Miembro.MiembroDTO getMiembro() {
            return miembro.convertirADTO();
        }

        public Sector.SectorDTO getSector() {
            return sector.convertirADTO();
        }

        public List<EstadoSolicitud.EstadoSolicitudDTO> getEstados() {
            return estados.stream().map(EstadoSolicitud::convertirADTO).collect(Collectors.toList());
        }

        public EstadoSolicitud.EstadoSolicitudDTO getEstado() {
            return estados.get(estados.size() - 1).convertirADTO();
        }
    }
}
