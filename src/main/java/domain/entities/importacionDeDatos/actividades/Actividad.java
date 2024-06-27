package domain.entities.importacionDeDatos.actividades;

import db.EntidadPersistente;
import domain.entities.importacionDeDatos.consumos.Consumo;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "actividad")
@Getter
public class Actividad extends EntidadPersistente {
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_de_actividad")
    private TipoDeActividad tipoDeActividad;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "consumo_id", referencedColumnName = "id")
    private Consumo consumo;

    @Enumerated(EnumType.STRING)
    private Periodicidad periodicidad;

    @Column(name = "periodo_de_imputacion", columnDefinition = "DATE")
    private LocalDate perDeImputacion;

    @Column(name = "valor_hc")
    private double huellaDeCarbono;

    public Actividad() {

    }

    public Actividad(
            String tipoDeActividad,
            Consumo consumo,
            String periodicidad,
            LocalDate perDeImputacion
    ) {
        this.tipoDeActividad = TipoDeActividad.valueOf(tipoDeActividad);
        this.consumo = consumo;
        this.huellaDeCarbono = consumo.calcularHuella();
        this.periodicidad = Periodicidad.valueOf(periodicidad);
        this.perDeImputacion = perDeImputacion;
    }

    public boolean esAnual() {
        return periodicidad.equals(Periodicidad.ANUAL);
    }

    public boolean esMensual() {
        return periodicidad.equals(Periodicidad.MENSUAL);
    }

    public boolean esDelAnio(int anio) {
        return perDeImputacion.getYear() == anio;
    }

    public boolean esDelMes(int anio, int mes) {
        return esDelAnio(anio) && perDeImputacion.getMonthValue() == mes;
    }

    public boolean esDelTipo(TipoDeActividad tipoDeActividad) {
        return this.tipoDeActividad.equals(tipoDeActividad);
    }

    public ActividadDTO convertirADTO() {
        return new ActividadDTO(this);
    }

    public static class ActividadDTO {
        private TipoDeActividad tipoDeActividad;
        private Consumo consumo;
        private Periodicidad periodicidad;
        private LocalDate perDeImputacion;
        private double huellaDeCarbono;

        public ActividadDTO(Actividad actividad) {
            tipoDeActividad = actividad.tipoDeActividad;
            consumo = actividad.consumo;
            periodicidad = actividad.periodicidad;
            perDeImputacion = actividad.perDeImputacion;
            huellaDeCarbono = actividad.huellaDeCarbono;
        }

        public String getNombre() {
            return tipoDeActividad.name().replace('_', ' ');
        }

        public String getConsumo() {
            return consumo.getTipoDeConsumo().name().replace('_', ' ');
        }

        public String getPeriodicidad() {
            return periodicidad.name();
        }

        public String getPerDeImputacion() {
            switch (periodicidad) {
                case MENSUAL:
                    return perDeImputacion.format(DateTimeFormatter.ofPattern("MM/yyyy"));
                case ANUAL:
                    return perDeImputacion.format(DateTimeFormatter.ofPattern("yyyy"));
            }
            return null;
        }

        public String getHuellaDeCarbono() {
            return String.format("%.2f kgCO2eq", huellaDeCarbono);
        }
    }
}