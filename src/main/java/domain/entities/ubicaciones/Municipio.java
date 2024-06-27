package domain.entities.ubicaciones;

import domain.entities.actores.sectores.SectorMunicipal;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "municipio")
public class Municipio {
    @Getter
    @Id
    public int id;

    @Getter
    @Column(name = "nombre")
    public String nombre;

    @Getter
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "provincia_id", referencedColumnName = "id")
    private Provincia provincia;

    @Getter
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "sector_municipal_id", referencedColumnName = "id")
    private SectorMunicipal sectorMunicipal;

    public Municipio() {
        this.sectorMunicipal = new SectorMunicipal(nombre);
    }

    public Municipio(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.sectorMunicipal = new SectorMunicipal(nombre);
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
        provincia.getSectorProvincial().agregarSectorMunicipal(sectorMunicipal);
    }
}