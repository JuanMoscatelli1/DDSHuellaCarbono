package domain.entities.actores.miembros;

import db.EntidadPersistente;
import domain.entities.huellaDeCarbono.CalculadorHuella;
import domain.entities.actores.solicitudes.Solicitud;
import domain.entities.actores.organizaciones.Organizacion;
import domain.entities.trayectos.tramo.Tramo;
import domain.entities.ubicaciones.Direccion;
import domain.entities.usuarios.Usuario;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "miembro")
public class Miembro extends EntidadPersistente {
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento")
    private TipoDeDocumento tipoDeDocumento;

    @Setter
    @Column(name = "numero_documento")
    private int numeroDocumento;

    @Setter
    @Getter
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "direccion_id", referencedColumnName = "id")
    private Direccion direccion;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Organizacion> organizaciones;

    @ManyToMany(mappedBy = "miembros")
    private Set<Tramo> tramos;

    @Setter
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    @Getter
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "calculador_id", referencedColumnName = "id")
    private CalculadorHuella calculadorHuella;

    public Miembro(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.organizaciones = new HashSet<>();
        this.calculadorHuella = new CalculadorHuella();
        this.tramos = new HashSet<>();
    }

    public Miembro() {

    }

    public void solicitarVinculacion(Solicitud solicitud) {
        solicitud.setMiembro(this);
        solicitud.enviar();
    }

    public void agregarOrganizacion(Organizacion organizacion) {
        organizaciones.add(organizacion);
    }

    public int cantidadOrganizaciones() {
        return organizaciones.size();
    }

    public void agregarTramo(Tramo tramo) {
        tramos.add(tramo);
        tramo.agregarMiembro(this);
        calculadorHuella.agregarHuella(tramo);
        tramo.getOrganizacion().agregarTramo(tramo);
    }

    public MiembroDTO convertirADTO(){
        return new MiembroDTO(this);
    }

    public static class MiembroDTO {
        @Getter
        private int id;
        @Getter
        private String nombre;
        @Getter
        private String apellido;
        private TipoDeDocumento tipoDeDocumento;
        @Getter
        private int numeroDocumento;
        private Direccion direccion;
        private Set<Organizacion> organizaciones;
        private Set<Tramo> tramos;

        public MiembroDTO(Miembro miembro) {
            id = miembro.id;
            nombre = miembro.nombre;
            apellido = miembro.apellido;
            tipoDeDocumento = miembro.tipoDeDocumento;
            numeroDocumento = miembro.numeroDocumento;
            direccion = miembro.direccion;
            organizaciones = miembro.organizaciones;
            tramos = miembro.tramos;
        }

        public String getTipoDocumento() {
            return tipoDeDocumento.name();
        }

        public Direccion.DireccionDTO getDireccion() {
            return direccion.convertirADTO();
        }

        public List<Organizacion.OrganizacionDTO> getOrganizaciones() {
            return organizaciones.stream().map(Organizacion::convertirADTO).collect(Collectors.toList());
        }

        public List<Tramo.TramoDTO> getTramos(Organizacion organizacion) {
            return tramos.stream()
                    .filter(t -> t.esDeLaOrganizacion(organizacion))
                    .map(Tramo::convertirADTO)
                    .collect(Collectors.toList());
        }

        public List<Tramo.TramoDTO> getTramos(LocalDate fecha) {
            return tramos.stream()
                    .filter(t -> t.getFecha().isAfter(fecha))
                    .map(Tramo::convertirADTO)
                    .collect(Collectors.toList());
        }
    }
}
