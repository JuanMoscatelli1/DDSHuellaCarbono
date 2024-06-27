package domain.entities.trayectos.tramo;

import db.EntidadPersistente;
import domain.entities.actores.miembros.Miembro;
import domain.entities.actores.organizaciones.Organizacion;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "tramo")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Tramo extends EntidadPersistente {
    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "valor", column = @Column(name = "distancia_valor")),
            @AttributeOverride( name = "unidad", column = @Column(name = "distancia_unidad"))
    })
    protected Distancia distancia;

    @Column
    protected double huellaDeCarbono;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "organizacion_id", referencedColumnName = "id")
    protected Organizacion organizacion;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="tramo_miembro",
            joinColumns=@JoinColumn(name="tramo_id"),
            inverseJoinColumns=@JoinColumn(name="miembro_id")
    )
    private Set<Miembro> miembros;

    @Getter
    @Column
    private LocalDate fecha;

    public Tramo() {
        this.miembros = new HashSet<>();
        this.fecha = LocalDate.now();
    }

    public abstract double calcularHuella();
    public abstract Distancia calcularDistancia();
    public abstract TramoDTO convertirADTO();

    public int cantidadMiembros() {
        return miembros.size();
    }

    public boolean esDeLaOrganizacion(Organizacion organizacion) {
        return this.organizacion.equals(organizacion);
    }

    public boolean esDelAnio(int anio) {
        return anio == fecha.getYear();
    }

    public boolean esDelMes(int anio, int mes) {
        return esDelAnio(anio) && mes == fecha.getMonthValue();
    }

    public boolean esDeLaFecha(LocalDate fecha) {
        return esDelMes(fecha.getYear(), fecha.getMonthValue());
    }

    public int getAnio() {
        return fecha.getYear();
    }

    public int getMes() {
        return fecha.getMonthValue();
    }

    public void agregarMiembro(Miembro miembro) {
        miembros.add(miembro);
    }

    public class TramoDTO {
        public Distancia distancia;
        public double huellaDeCarbono;
        public Organizacion organizacion;
        public Set<Miembro> miembros;
        public LocalDate fecha;
        @Setter
        @Getter
        public String vehiculo;
        @Setter
        @Getter
        public String origen;
        @Setter
        @Getter
        public String destino;
        @Getter
        @Setter
        Set<Tramo> tramos;

        public TramoDTO(Tramo tramo) {
            this.distancia = tramo.distancia;
            this.huellaDeCarbono = tramo.huellaDeCarbono;
            this.organizacion = tramo.organizacion;
            this.miembros = tramo.miembros;
            this.fecha = tramo.fecha;
        }

        public List<TramoDTO> getTramos() {
            return tramos.stream().map(Tramo::convertirADTO).collect(Collectors.toList());
        }

        public Organizacion.OrganizacionDTO getOrganizacion() {
            return organizacion.convertirADTO();
        }

        public String getDistancia() {
            return String.format("%.2f %s", distancia.getValor(), distancia.getUnidad());
        }

        public String getHuellaDeCarbono() {
            return String.format("%.2f kgCO2eq", huellaDeCarbono);
        }

        public String getMiembros() {
            return miembros.stream()
                    .map(m -> m.convertirADTO().getNombre() + " " + m.convertirADTO().getApellido())
                    .collect(Collectors.joining(", "));
        }

        public String getHuellaOrgMod(){
            return String.format("%.2f kgCO2eq", huellaDeCarbono * miembros.size());
        }

        public String getFecha() {
            return fecha.format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }
}