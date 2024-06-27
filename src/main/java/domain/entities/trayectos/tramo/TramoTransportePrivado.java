package domain.entities.trayectos.tramo;

import domain.entities.services.geodds.ServicioDeUbicaciones;
import domain.entities.trayectos.vehiculosPrivados.TransportePrivado;
import domain.entities.ubicaciones.Direccion;

import javax.persistence.*;

@Entity
@DiscriminatorValue("TRANSPORTE PRIVADO")
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "id")) })
public class TramoTransportePrivado extends Tramo {
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "transporte_privado_id", referencedColumnName = "id")
    private TransportePrivado transportePrivado;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "direccion_origen_id", referencedColumnName = "id")
    private Direccion direccionOrigen;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "direccion_destino_id", referencedColumnName = "id")
    private Direccion direccionDestino;

    @Transient
    private ServicioDeUbicaciones servicioDeUbicaciones;

    public TramoTransportePrivado(
            TransportePrivado transportePrivado,
            Direccion direccionOrigen,
            Direccion direccionDestino
    ) {
        super();
        this.transportePrivado = transportePrivado;
        this.direccionOrigen = direccionOrigen;
        this.direccionDestino = direccionDestino;
        this.servicioDeUbicaciones = ServicioDeUbicaciones.getInstancia();
        this.distancia = calcularDistancia();
    }

    public TramoTransportePrivado() {

    }

    @Override
    public Distancia calcularDistancia() {
        if(distancia != null) return distancia;
        return this.servicioDeUbicaciones.obtenerDistancia(direccionOrigen, direccionDestino);
    }

    @Override
    public double calcularHuella() {
        if(cantidadMiembros() == 0) return 0.0;
        if (distancia != null)
            huellaDeCarbono = transportePrivado.getFactorDeEmision() * distancia.getValor() / cantidadMiembros();
        else
            huellaDeCarbono = transportePrivado.getFactorDeEmision() / cantidadMiembros();
        return huellaDeCarbono;
    }

    @Override
    public TramoDTO convertirADTO() {
        TramoDTO tramoDTO = new TramoDTO(this);
        TransportePrivado.TransportePrivadoDTO transporte = transportePrivado.convertirADTO();
        if(transporte.getNombre() != null) tramoDTO.setVehiculo(transporte.getNombre());
        else tramoDTO.setVehiculo(transporte.getTipoVehiculo());
        tramoDTO.setOrigen(direccionOrigen.convertirADTO().getDireccion());
        tramoDTO.setDestino(direccionDestino.convertirADTO().getDireccion());
        return tramoDTO;
    }
}
