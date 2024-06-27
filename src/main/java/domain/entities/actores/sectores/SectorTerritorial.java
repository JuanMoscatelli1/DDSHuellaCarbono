package domain.entities.actores.sectores;

import db.EntidadPersistente;
import domain.entities.huellaDeCarbono.CalculadorHuella;
import domain.entities.huellaDeCarbono.HuellaDeCarbono;
import domain.entities.usuarios.Usuario;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Map;

@Entity
@Table(name = "sector_territorial")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class SectorTerritorial extends EntidadPersistente {
    @Getter
    @Column
    private String nombre;

    @Getter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "calculador_id", referencedColumnName = "id")
    private CalculadorHuella calculadorHuella;

    @Setter
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    public SectorTerritorial() {

    }

    public SectorTerritorial(String nombre) {
        this.nombre = nombre;
        this.calculadorHuella = new CalculadorHuella();
    }

    public void agregarHuella(HuellaDeCarbono huellaDeCarbono) {
        calculadorHuella.agregarHuella(huellaDeCarbono);
    }

    public abstract Map<String, Double> composicion(LocalDate fecha);
}
