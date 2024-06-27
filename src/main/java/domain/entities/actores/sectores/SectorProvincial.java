package domain.entities.actores.sectores;

import domain.entities.huellaDeCarbono.Reporte;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@DiscriminatorValue("SECTOR PROVINCIAL")
public class SectorProvincial extends SectorTerritorial {
    @OneToMany(mappedBy = "sectorProvincial", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SectorMunicipal> sectoresMunicipales;

    public SectorProvincial(String nombre) {
        super(nombre);
        this.sectoresMunicipales = new ArrayList<>();
    }

    public SectorProvincial() {
        super();
    }

    @Override
    public Map<String, Double> composicion(LocalDate fecha) {
        return Reporte.composicionProvincia(
                sectoresMunicipales, getCalculadorHuella().calcularHuellaDesde(fecha), fecha
        );
    }

    public void agregarSectorMunicipal(SectorMunicipal sectorMunicipal) {
        sectoresMunicipales.add(sectorMunicipal);
        sectorMunicipal.setSectorProvincial(this);
    }
}