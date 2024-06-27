package domain.entities.huellaDeCarbono;

import db.EntidadPersistente;
import domain.entities.importacionDeDatos.consumos.TipoDeConsumo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "factor_de_emision")
@Getter
public class FactorDeEmision extends EntidadPersistente {
    @Column(name = "valor")
    @Setter
    private double valor;

    @Column(name = "unidad")
    private String unidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_de_consumo")
    private TipoDeConsumo tipoDeConsumo;

    public FactorDeEmision() {

    }

    public FactorDeEmision(double valor, String unidad) {
        this.valor = valor;
        this.unidad = "kgCO2eq/" + unidad;
    }

    public FactorDeEmision(TipoDeConsumo tipoDeConsumo, double valor) {
        this.tipoDeConsumo = tipoDeConsumo;
        String unidad = tipoDeConsumo.getUnidad();
        if(unidad != null) this.unidad = "kgCO2eq/" + unidad;
        this.valor = valor;
    }

    public boolean matchea(TipoDeConsumo tipoDeConsumo) {
        return this.tipoDeConsumo.equals(tipoDeConsumo);
    }

    // -------------------- DTO ----------------

    public FactorDeEmisionDTO convertirADTO(){
        return new FactorDeEmisionDTO(this);
    }

    public class FactorDeEmisionDTO {
        public double valor;
        public String unidad;
        public TipoDeConsumo tipoDeConsumo;

        public FactorDeEmisionDTO(FactorDeEmision factorDeEmision){
            valor = factorDeEmision.valor;
            unidad = factorDeEmision.unidad;
            tipoDeConsumo = factorDeEmision.tipoDeConsumo;
        }
    }

}
