package domain.entities.actores.sectores;

import domain.entities.huellaDeCarbono.HuellaDeCarbono;
import domain.entities.actores.organizaciones.Organizacion;
import domain.entities.huellaDeCarbono.Reporte;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@DiscriminatorValue("SECTOR MUNICIPAL")
public class SectorMunicipal extends SectorTerritorial {
    @Setter
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private SectorProvincial sectorProvincial;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipio_id", referencedColumnName = "id")
    private List<Organizacion> organizaciones;

    public SectorMunicipal(String nombre) {
        super(nombre);
        this.organizaciones = new ArrayList<>();
    }

    public SectorMunicipal() {

    }

    public void agregarOrganizacion(Organizacion organizacion) {
        this.organizaciones.add(organizacion);
    }

    public void agregarHuella(HuellaDeCarbono huellaDeCarbono) {
        super.agregarHuella(huellaDeCarbono);
        sectorProvincial.agregarHuella(huellaDeCarbono.copia());
    }

    @Override
    public Map<String, Double> composicion(LocalDate fecha) {
        return Reporte.composicionMunicipio(
                organizaciones, getCalculadorHuella().calcularHuellaDesde(fecha), fecha
        );
    }
}