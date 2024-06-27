package domain.entities.ubicaciones;

import db.EntidadPersistente;
import domain.entities.actores.sectores.SectorMunicipal;
import domain.entities.actores.organizaciones.Organizacion;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "direccion")
@Getter @Setter
public class Direccion extends EntidadPersistente {
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "localidad_id", referencedColumnName = "id")
    private Localidad localidad;

    @Column(name = "calle")
    private String calle;

    @Column(name = "altura")
    private String altura;

    public Direccion() {
    }

    public Direccion(Localidad localidad, String calle, String altura) {
        this.localidad = localidad;
        this.calle = calle;
        this.altura = altura;
    }

    public int getLocalidadId() {
        return localidad.getId();
    }

    public void agregarASectorMunicipal(Organizacion organizacion) {
        getSectorMunicipal().agregarOrganizacion(organizacion);
    }

    public Municipio getMunicipio() {
        return localidad.getMunicipio();
    }

    public SectorMunicipal getSectorMunicipal() {
        return localidad.getMunicipio().getSectorMunicipal();
    }

    public DireccionDTO convertirADTO() {
        return new DireccionDTO(this);
    }

    public class DireccionDTO {
        public String calle;
        public String altura;
        public Localidad localidad;

        public DireccionDTO(Direccion direccion) {
            calle = direccion.calle;
            altura = direccion.altura;
            localidad = direccion.localidad;
        }

        public String getDireccion() {
            return calle + " " + altura;
        }

        public String getLocalidad() {
            return localidad.getNombre();
        }

        public String getMunicipio() {
            return localidad.getMunicipio().getNombre();
        }

        public String getProvincia() {
            return localidad.getMunicipio().getProvincia().getNombre();
        }
    }
}