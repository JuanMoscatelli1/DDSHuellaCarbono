package domain.entities.huellaDeCarbono;

import db.EntidadPersistente;
import domain.entities.importacionDeDatos.actividades.Periodicidad;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "huella_de_carbono")
@Getter
public class HuellaDeCarbono extends EntidadPersistente {
    @Setter
    @Column(name = "valor")
    protected double valor;

    @Column(name = "unidad")
    private String unidad;

    @Column
    protected LocalDate fecha;

    @Column
    @Enumerated(EnumType.STRING)
    private Periodicidad periodicidad;

    public HuellaDeCarbono(LocalDate fecha, Periodicidad periodicidad) {
        this.valor = 0.0;
        this.unidad = "kgCO2eq";
        this.fecha = fecha;
        this.periodicidad = periodicidad;
    }

    public HuellaDeCarbono() {

    }

    public HuellaDeCarbono copia() {
        HuellaDeCarbono huellaDeCarbono = new HuellaDeCarbono(fecha, periodicidad);
        huellaDeCarbono.sumarValor(valor);
        return huellaDeCarbono;
    }

    public void sumarValor(double valor) {
        this.valor += valor;
    }

    public void convertirUnidad(String unidadAConvertir) {
        unidad = unidadAConvertir;
        switch (unidadAConvertir) {
            case "tnCO2eq":
                valor /= 1000;
                break;
            case "gCO2eq":
                valor *= 1000;
                break;
        }
    }

    public int getAnio() {
        return fecha.getYear();
    }

    public int getMes() {
        return fecha.getMonthValue();
    }

    public boolean esDelAnio(int anio) {
        return getAnio() == anio;
    }

    public boolean esDelMes(int anio, int mes) {
        return esDelAnio(anio) && getMes() == mes;
    }

    public boolean esAnual() {
        return periodicidad.equals(Periodicidad.ANUAL);
    }

    public boolean esMensual() {
        return periodicidad.equals(Periodicidad.MENSUAL);
    }

    public HuellaDeCarbonoDTO convertirADTO(){
        return new HuellaDeCarbonoDTO(this);
    }

    public class HuellaDeCarbonoDTO {
        public double valor;
        public String unidad;

        public HuellaDeCarbonoDTO(HuellaDeCarbono huellaDeCarbono){
            valor = huellaDeCarbono.valor;
            unidad = huellaDeCarbono.unidad;
        }
    }
}
