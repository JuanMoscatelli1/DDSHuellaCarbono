package domain.entities.huellaDeCarbono;

import db.EntidadPersistente;
import domain.entities.importacionDeDatos.actividades.Periodicidad;
import domain.entities.trayectos.tramo.Tramo;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "calculador_huella")
public class CalculadorHuella extends EntidadPersistente {
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "calculador_id", referencedColumnName = "id")
    private List<HuellaDeCarbono> huellasDeCarbono;

    public CalculadorHuella() {
        this.huellasDeCarbono = new ArrayList<>();
    }

    public double calcularHuella(Integer anio, Integer mes) {
        Optional<HuellaDeCarbono> huella;
        huella = mes == null ? anual(anio) : mensual(anio, mes);
        return Reporte.round(huella.map(HuellaDeCarbono::getValor).orElse(0.0), 2);
    }

    public double calcularHuellaDesde(LocalDate fecha) {
        return huellasDeCarbono.stream()
                .filter(h -> h.getFecha().isAfter(fecha))
                .mapToDouble(HuellaDeCarbono::getValor)
                .sum();
    }

    public Map<String, Double> evolucionAnual(LocalDate fecha) {
        return Reporte.evolucionAnual(this.huellasDeCarbono, fecha);
    }

    public Map<String, Double> evolucionMensual(LocalDate fecha) {
        return Reporte.evolucionMensual(this.huellasDeCarbono, fecha);
    }

    private Optional<HuellaDeCarbono> anual(int anio) {
        return huellasDeCarbono.stream()
                .filter(h -> h.esAnual() && h.esDelAnio(anio))
                .findFirst();
    }

    private Optional<HuellaDeCarbono> mensual(int anio, int mes) {
        return huellasDeCarbono.stream()
                .filter(h -> h.esMensual() && h.esDelMes(anio, mes))
                .findFirst();
    }

    public void agregarHuella(HuellaDeCarbono huellaDeCarbono) {
        Optional<HuellaDeCarbono> optionalHuellaDeCarbono = Optional.empty();

        switch (huellaDeCarbono.getPeriodicidad()) {
            case ANUAL:
                optionalHuellaDeCarbono = anual(huellaDeCarbono.getAnio());
                break;
            case MENSUAL:
                optionalHuellaDeCarbono = mensual(
                        huellaDeCarbono.getAnio(), huellaDeCarbono.getMes()
                );
                break;
        }

        if(optionalHuellaDeCarbono.isPresent())
            optionalHuellaDeCarbono.get().sumarValor(huellaDeCarbono.getValor());
        else
            huellasDeCarbono.add(huellaDeCarbono);
    }

    public HuellaDeCarbono agregarHuella(Tramo tramo) {
        Optional<HuellaDeCarbono> huellaMensual = mensual(tramo.getAnio(), tramo.getMes());
        HuellaDeCarbono huellaDeCarbono;
        if(!huellaMensual.isPresent()) {
            huellaDeCarbono = new HuellaDeCarbono(tramo.getFecha(), Periodicidad.MENSUAL);
            huellasDeCarbono.add(huellaDeCarbono);
        }
        else huellaDeCarbono = huellaMensual.get();
        huellaDeCarbono.sumarValor(tramo.calcularHuella());
        return huellaDeCarbono;
    }
}
