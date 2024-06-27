package domain.entities.importacionDeDatos.consumos;

import db.EntidadPersistente;
import domain.entities.huellaDeCarbono.FactorDeEmision;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "consumo")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Consumo extends EntidadPersistente {
    @Getter
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_de_consumo")
    protected TipoDeConsumo tipoDeConsumo;

    @Setter
    @ManyToOne
    @JoinColumn(name = "fe_id", referencedColumnName = "id")
    protected FactorDeEmision factorDeEmision;

    public abstract double calcularHuella();
}
