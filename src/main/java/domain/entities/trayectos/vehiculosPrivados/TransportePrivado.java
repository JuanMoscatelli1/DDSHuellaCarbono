package domain.entities.trayectos.vehiculosPrivados;

import db.EntidadPersistente;
import domain.entities.huellaDeCarbono.FactorDeEmision;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "transporte_privado")
@Setter
public class TransportePrivado extends EntidadPersistente {
    @Column(name = "nombre")
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_vehiculo")
    private TipoVehiculo tipoVehiculo;

    @Enumerated(EnumType.STRING)
    @Column(name = "clase_vehiculo")
    private CriterioDeVehiculo criterioDeVehiculo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_combustible")
    private TipoCombustible tipoCombustible;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "fe_id", referencedColumnName = "id")
    private FactorDeEmision factorDeEmision;

    public TransportePrivado() {
    }

    public TransportePrivado(String nombre, String criterio, String tipoVehiculo, String tipoCombustible) {
        this.nombre = nombre;
        this.criterioDeVehiculo = CriterioDeVehiculo.valueOf(criterio);
        this.tipoVehiculo = TipoVehiculo.valueOf(tipoVehiculo);
        if(tipoCombustible != null)
            this.tipoCombustible = TipoCombustible.valueOf(tipoCombustible);
        this.factorDeEmision = new FactorDeEmision(0.4, "lt");
    }

    public double getFactorDeEmision() {
        return factorDeEmision.getValor();
    }

    // ------------------- DTO -----------------------

    public TransportePrivadoDTO convertirADTO(){
        return new TransportePrivadoDTO(this);
    }

    public class TransportePrivadoDTO {
        @Getter
        private String nombre;
        private TipoVehiculo tipoVehiculo;
        private CriterioDeVehiculo criterioDeVehiculo;
        private TipoCombustible tipoCombustible;
        private FactorDeEmision factorDeEmision;

        public TransportePrivadoDTO(TransportePrivado transportePrivado) {
            nombre = transportePrivado.nombre;
            tipoVehiculo = transportePrivado.tipoVehiculo;
            criterioDeVehiculo = transportePrivado.criterioDeVehiculo;
            tipoCombustible = transportePrivado.tipoCombustible;
            factorDeEmision = transportePrivado.factorDeEmision;
        }

        public String getTipoVehiculo() {
            return tipoVehiculo.name();
        }
    }
}
