package domain.entities.actores.solicitudes;

import db.EntidadPersistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Setter
@Entity
@Table(name = "estado_solicitud")
public class EstadoSolicitud extends EntidadPersistente {
    @Getter
    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private Estado estado;

    @Column(name = "fecha")
    private LocalDate fecha;

    public EstadoSolicitud(Estado estado) {
        this.estado = estado;
        this.fecha = LocalDate.now();
    }

    public EstadoSolicitud() {

    }

    public EstadoSolicitudDTO convertirADTO() {
        return new EstadoSolicitudDTO(this);
    }

    public class EstadoSolicitudDTO {
        @Getter
        private int id;
        private Estado estado;
        private LocalDate fecha;

        public EstadoSolicitudDTO(EstadoSolicitud estadoSolicitud) {
            this.id = estadoSolicitud.id;
            this.estado = estadoSolicitud.estado;
            this.fecha = estadoSolicitud.fecha;
        }

        public String getEstado() {
            return estado.name();
        }

        public String getFecha() {
            return fecha.format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }
}
