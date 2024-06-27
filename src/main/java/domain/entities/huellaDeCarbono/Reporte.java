package domain.entities.huellaDeCarbono;

import domain.entities.actores.organizaciones.Organizacion;
import domain.entities.actores.organizaciones.TipoDeOrganizacion;
import domain.entities.actores.sectores.SectorMunicipal;
import domain.entities.actores.sectores.SectorProvincial;
import domain.entities.importacionDeDatos.actividades.Actividad;
import domain.entities.importacionDeDatos.actividades.TipoDeActividad;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reporte {

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static Map<String, Double> evolucionAnual(List<HuellaDeCarbono> huellasDeCarbono, LocalDate fecha) {
        Map<String, Double> evolucion = new HashMap<>();

        huellasDeCarbono.stream().filter(h -> h.getFecha().isAfter(fecha)).forEach(h -> {
                String anio = String.valueOf(h.getAnio());
                if(evolucion.containsKey(anio)) evolucion.merge(anio, h.getValor(), Double::sum);
                else evolucion.put(anio, h.getValor());
        });

        return evolucion;
    }

    public static Map<String, Double> evolucionMensual(List<HuellaDeCarbono> huellasDeCarbono, LocalDate fecha) {
        Map<String, Double> evolucion = new HashMap<>();
        int anio = fecha.getYear();

        huellasDeCarbono.stream()
                .filter(h -> h.esDelAnio(anio))
                .forEach(h -> evolucion.put(h.getFecha().format(DateTimeFormatter.ofPattern("yyyy/MM")), h.getValor()));

        return evolucion;
    }

    public static Map<String, Double> composicionOrganizacion(List<Actividad> actividades, double total, LocalDate fecha) {
        Map<String, Double> composicion = new HashMap<>();

        for(TipoDeActividad tipoDeActividad : TipoDeActividad.values()) {
            double valor = actividades.stream()
                    .filter(a -> a.esDelTipo(tipoDeActividad) && a.getPerDeImputacion().isAfter(fecha))
                    .mapToDouble(Actividad::getHuellaDeCarbono).sum();

            if(total == 0.0) composicion.put(tipoDeActividad.name(), 0.0);
            else composicion.put(tipoDeActividad.name(), round(valor/total * 100, 2));
        }

        double valor = Double.max(round(100.0 - composicion.values().stream().mapToDouble(Double::valueOf).sum(), 2), 0.0);
        composicion.put("TRAYECTO", valor);
        return composicion;
    }

    public static Map<String, Double> composicionMunicipio(List<Organizacion> organizaciones, double total, LocalDate fecha) {
        Map<String, Double> composicion = new HashMap<>();

        for(TipoDeOrganizacion tipoDeOrganizacion : TipoDeOrganizacion.values()) {
            double valor = organizaciones.stream()
                    .filter(o -> o.esDelTipo(tipoDeOrganizacion))
                    .mapToDouble(o -> o.getCalculadorHuella().calcularHuellaDesde(fecha))
                    .sum();

            if(total == 0.0) composicion.put(tipoDeOrganizacion.name(), 0.0);
            else composicion.put(tipoDeOrganizacion.name(), round(valor/total * 100, 2));
        }

        return composicion;
    }

    public static Map<String, Double> composicionProvincia(List<SectorMunicipal> sectores, double total, LocalDate fecha) {
        Map<String, Double> composicion = new HashMap<>();

        for(SectorMunicipal sectorMunicipal : sectores) {
            if(total == 0.0) composicion.put(sectorMunicipal.getNombre(), 0.0);
            else composicion.put(
                    sectorMunicipal.getNombre(),
                    round(sectorMunicipal.getCalculadorHuella().calcularHuellaDesde(fecha)/total * 100, 2)
            );
        }

        return composicion;
    }

    public static Map<String, Double> composicionPais(List<SectorProvincial> sectores, LocalDate fecha) {
        Map<String, Double> composicion = new HashMap<>();
        double total = sectores.stream()
                .mapToDouble(s -> s.getCalculadorHuella().calcularHuellaDesde(fecha))
                .sum();

        for (SectorProvincial sectorProvincial : sectores) {
            if(total == 0.0) composicion.put(sectorProvincial.getNombre(), 0.0);
            else composicion.put(
                    sectorProvincial.getNombre(),
                    round(sectorProvincial.getCalculadorHuella().calcularHuellaDesde(fecha) / total * 100, 2)
            );
        }

        return composicion;
    }
}
