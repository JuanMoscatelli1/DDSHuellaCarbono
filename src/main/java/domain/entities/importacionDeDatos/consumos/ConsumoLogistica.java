package domain.entities.importacionDeDatos.consumos;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("logistica")
public class ConsumoLogistica extends Consumo {
    @Getter
    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "consumo_logistica_id", referencedColumnName = "id")
    private List<Consumo> consumos;

    @Column(name = "constante")
    private double constante;

    public ConsumoLogistica() {
        this.tipoDeConsumo = TipoDeConsumo.LOGISTICA;
        this.consumos = new ArrayList<>();
        this.constante = 1.0;
    }

    @Override
    public double calcularHuella() {
        double valorConsumo = consumos.stream()
                .mapToDouble(Consumo::calcularHuella)
                .reduce(1.0, (factor, valor) -> factor * valor);
        return valorConsumo * constante * factorDeEmision.getValor();
    }

    public void agregarConsumo(Consumo consumo) {
        consumos.add(consumo);
    }
}