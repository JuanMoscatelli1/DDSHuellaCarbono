package controllers;

import domain.entities.actores.sectores.SectorProvincial;
import domain.entities.actores.sectores.SectorTerritorial;
import domain.repositories.Repositorio;
import domain.repositories.factories.FactoryRepositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.HashMap;

public class AgenteController {
    private Repositorio<SectorTerritorial> repoSector;

    public AgenteController(){
        this.repoSector = FactoryRepositorio.get(SectorTerritorial.class);
    }

    public ModelAndView guiaDeRecomendaciones(Request request, Response response) {
        return new ModelAndView(null, "/agentes/guiaDeRecomendaciones.hbs");
    }

    public SectorTerritorial buscarSectorTerritorial(Request request){
        return repoSector.buscar("usuario", request.session().attribute("id"));
    }

    public ModelAndView reportes(Request request, Response response){
        return new ModelAndView(null, "/agentes/reportes-agente.hbs");
    }

    public ModelAndView visualizarReportes(Request request, Response response) {
        int anio = new Integer(request.queryParams("anio"));
        int mes = new Integer(request.queryParams("mes"));
        LocalDate fecha = LocalDate.of(anio, mes, 1);

        SectorTerritorial sectorTerritorial = buscarSectorTerritorial(request);
        HashMap<String, Object> map = new HashMap<>();
        switch(request.queryParams("tipo_reporte")) {
            case "Composición huella de carbono":
                map.put("reporte", sectorTerritorial.composicion(fecha));
                map.put("columna1", "Componente");
                map.put("columna2", "Porcentaje huella");
                map.put("unidad", "%");
                break;
            case "Huella de carbono total":
                map.put("reporte", new HashMap<String, Double>() {{
                    put(sectorTerritorial.getNombre(), sectorTerritorial.getCalculadorHuella().calcularHuellaDesde(fecha));
                }});
                map.put("columna1", "Sector territorial");
                map.put("columna2", "Huella");
                map.put("unidad", "kgCO2eq");
                break;
            case "Evolución mensual":
                map.put("reporte", sectorTerritorial.getCalculadorHuella().evolucionMensual(fecha));
                map.put("columna1", "Fecha");
                map.put("columna2", "Huella");
                map.put("unidad", "kgCO2eq");
                break;
            case "Evolución anual":
                map.put("reporte", sectorTerritorial.getCalculadorHuella().evolucionAnual(fecha));
                map.put("columna1", "Año");
                map.put("columna2", "Huella");
                map.put("unidad", "kgCO2eq");
                break;
        }
        return new ModelAndView(map, "agentes/reportesVista.hbs");
    }

    public ModelAndView mostrarProhibido(Request request, Response response){
        return new ModelAndView(null, "agente/prohibido.hbs");
    }
}




