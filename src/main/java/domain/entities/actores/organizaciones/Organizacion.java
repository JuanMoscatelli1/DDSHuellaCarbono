package domain.entities.actores.organizaciones;

import db.EntidadPersistente;
import domain.entities.huellaDeCarbono.CalculadorHuella;
import domain.entities.actores.solicitudes.Estado;
import domain.entities.actores.solicitudes.EstadoSolicitud;
import domain.entities.actores.solicitudes.Solicitud;
import domain.entities.huellaDeCarbono.HuellaDeCarbono;
import domain.entities.actores.miembros.Miembro;
import domain.entities.importacionDeDatos.actividades.Actividad;
import domain.entities.huellaDeCarbono.Reporte;
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
@Table(name = "organizacion")
public class Organizacion extends EntidadPersistente {
    @Column(name = "razon_social")
    private String razonSocial;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_organizacion")
    private TipoDeOrganizacion tipoDeOrganizacion;

    @Column
    private String subclasificacion;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "direccion_id", referencedColumnName = "id")
    private Direccion direccion;

    @Getter
    @OneToMany(mappedBy = "organizacion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Sector> sectores;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacion_id", referencedColumnName = "id")
    private List<Solicitud> solicitudes;

    @Getter
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacion_id", referencedColumnName = "id")
    private List<Actividad> actividades;

    @Getter
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacion_id", referencedColumnName = "id")
    private Set<Contacto> contactos;

    @Setter
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    @Getter
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "calculador_id", referencedColumnName = "id")
    private CalculadorHuella calculadorHuella;

    public Organizacion() {

    }

    public Organizacion(String razonSocial, TipoDeOrganizacion tipoDeOrganizacion, String subclasificacion) {
        this.razonSocial = razonSocial;
        this.tipoDeOrganizacion = tipoDeOrganizacion;
        this.subclasificacion = subclasificacion;
        this.sectores = new ArrayList<>();
        this.solicitudes = new ArrayList<>();
        this.actividades = new ArrayList<>();
        this.contactos = new LinkedHashSet<>();
        this.calculadorHuella = new CalculadorHuella();
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
        this.direccion.agregarASectorMunicipal(this);
    }

    public boolean esDelTipo(TipoDeOrganizacion tipoDeOrganizacion) {
        return tipoDeOrganizacion.equals(this.tipoDeOrganizacion);
    }

    public List<Miembro> getMiembros() {
        return sectores.stream().flatMap(s -> s.getMiembros().stream()).collect(Collectors.toList());
    }

    public void agregarSector(Sector sector) {
        this.sectores.add(sector);
    }

    public void recibirSolicitud(Solicitud solicitud) {
        solicitudes.add(solicitud);
    }

    public void aceptarVinculacion(Solicitud solicitud) {
        solicitud.agregarEstado(new EstadoSolicitud(Estado.ACEPTADA));
        Sector sector = solicitud.getSector();
        Miembro miembro = solicitud.getMiembro();
        sector.agregarMiembro(miembro);
        miembro.agregarOrganizacion(this);
    }

    public void rechazarVinculacion(Solicitud solicitud) {
        solicitud.agregarEstado(new EstadoSolicitud(Estado.RECHAZADA));
    }

    public int cantidadSolicitudes() {
        return solicitudes.size();
    }

    public void agregarActividad(Actividad actividad) {
        actividades.add(actividad);
        HuellaDeCarbono huellaDeCarbono = new HuellaDeCarbono(
                actividad.getPerDeImputacion(), actividad.getPeriodicidad()
        );
        huellaDeCarbono.sumarValor(actividad.getHuellaDeCarbono());
        calculadorHuella.agregarHuella(huellaDeCarbono);
        direccion.getSectorMunicipal().agregarHuella(huellaDeCarbono.copia());
    }

    public void agregarTramo(Tramo tramo) {
        HuellaDeCarbono huellaDeCarbono = calculadorHuella.agregarHuella(tramo);
        direccion.getSectorMunicipal().agregarHuella(huellaDeCarbono.copia());
    }

    public void agregarContactos(Contacto ... contactos) {
        Collections.addAll(this.contactos, contactos);
    }

    public Map<String, Double> composicion(LocalDate fecha) {
        return Reporte.composicionOrganizacion(actividades, calculadorHuella.calcularHuellaDesde(fecha), fecha);
    }

    public OrganizacionDTO convertirADTO(){
        return new OrganizacionDTO(this);
    }

    public static class OrganizacionDTO {
        @Getter
        private int id;
        @Getter
        private String razonSocial;
        @Getter
        private TipoDeOrganizacion tipoDeOrganizacion;
        @Getter
        private String subclasificacion;
        private Direccion direccion;
        private List<Sector> sectores;
        private List<Solicitud> solicitudes;
        private List<Actividad> actividades;
        private Set<Contacto> contactos;

        public OrganizacionDTO(Organizacion organizacion) {
            id = organizacion.id;
            razonSocial = organizacion.razonSocial;
            tipoDeOrganizacion = organizacion.tipoDeOrganizacion;
            subclasificacion = organizacion.subclasificacion;
            direccion = organizacion.direccion;
            sectores = organizacion.sectores;
            solicitudes = organizacion.solicitudes;
            actividades = organizacion.actividades;
            contactos = organizacion.contactos;
        }

        public String getNombre() {
            return razonSocial;
        }

        public List<Actividad.ActividadDTO> getActividades() {
            return actividades.stream().map(Actividad::convertirADTO).collect(Collectors.toList());
        }

        public List<Actividad.ActividadDTO> getActividades(Integer anio, Integer mes) {
            if(mes != null)
                return actividades.stream()
                        .filter(a -> a.esDelMes(anio, mes) && a.esMensual())
                        .map(Actividad::convertirADTO)
                        .collect(Collectors.toList());
            else
                return actividades.stream()
                        .filter(a -> a.esDelAnio(anio) && a.esAnual())
                        .map(Actividad::convertirADTO)
                        .collect(Collectors.toList());
        }

        public Direccion.DireccionDTO getDireccion() {
            return direccion.convertirADTO();
        }

        public List<Contacto.ContactoDTO> getContactos() { return contactos.stream().map(Contacto::convertirADTO).collect(Collectors.toList());}

        public List<Sector.SectorDTO> getSectores() { return sectores.stream().map(Sector::convertirADTO).collect(Collectors.toList());}

        public Sector.SectorDTO getSector(Miembro miembro) {
            return sectores.stream().filter(s -> s.tieneAlMiembro(miembro)).findFirst().map(Sector::convertirADTO).orElse(null);
        }

        public List<Solicitud.SolicitudDTO> getSolicitudes() {
            return solicitudes.stream()
                    .filter(s -> !s.estadoActual().equals(Estado.PENDIENTE))
                    .map(Solicitud::convertirADTO).collect(Collectors.toList());
        }

        public List<Solicitud.SolicitudDTO> solicitudesPendientes() {
            return solicitudes.stream()
                    .filter(s -> s.estadoActual().equals(Estado.PENDIENTE))
                    .map(Solicitud::convertirADTO).collect(Collectors.toList());
        }

        public List<Miembro.MiembroDTO> getMiembros() {
            return getSectores().stream().flatMap(s -> s.getMiembros().stream()).collect(Collectors.toList());
        }
    }
}
