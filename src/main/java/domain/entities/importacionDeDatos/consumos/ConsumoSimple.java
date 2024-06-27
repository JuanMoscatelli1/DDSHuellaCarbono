package domain.entities.importacionDeDatos.consumos;

import domain.entities.huellaDeCarbono.FactorDeEmision;
import lombok.Getter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("simple")
@Getter
public class ConsumoSimple extends Consumo {
    @Column(name = "valor")
    private String valor;

    @Column(name = "unidad")
    private String unidad;

    public ConsumoSimple() {

    }

    public ConsumoSimple(TipoDeConsumo tipoDeConsumo, String valor) {
        this.tipoDeConsumo = tipoDeConsumo;
        this.valor = valor;
        this.unidad = tipoDeConsumo.getUnidad();
    }

    @Override
    public double calcularHuella() {
        try {
            double valor = Double.parseDouble(this.valor);
            switch (tipoDeConsumo) {
                case DISTANCIA: case PESO:
                    return valor;
                default:
                    return valor * factorDeEmision.getValor();
            }
        } catch (NumberFormatException e) {
           return 1.0;
        }
    }
}