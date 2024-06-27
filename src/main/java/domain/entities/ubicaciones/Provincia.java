package domain.entities.ubicaciones;

import domain.entities.actores.sectores.SectorProvincial;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "provincia")
public class Provincia {
    @Getter
    @Id
    public int id;

    @Getter
    @Column(name = "nombre")
    public String nombre;

    @Setter
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "pais_id", referencedColumnName = "id")
    private Pais pais;

    @Getter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sector_provincial_id", referencedColumnName = "id")
    private SectorProvincial sectorProvincial;

    public Provincia() {
        this.sectorProvincial = new SectorProvincial(nombre);
    }

    public Provincia(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.sectorProvincial = new SectorProvincial(nombre);
    }
}