package controllers;

import domain.entities.actores.miembros.Miembro;
import domain.entities.actores.organizaciones.Organizacion;
import domain.entities.actores.solicitudes.Solicitud;
import domain.entities.huellaDeCarbono.FactorDeEmision;
import domain.entities.importacionDeDatos.LectorExcel;
import domain.entities.trayectos.tramo.Tramo;
import domain.repositories.Repositorio;
import domain.repositories.factories.FactoryRepositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class OrganizacionController {
    private Repositorio<Miembro> repoMiembro;
    private Repositorio<Organizacion> repoOrganizacion;
    private Repositorio<FactorDeEmision> repoFactores;
    private Repositorio<Solicitud> repoSolicitud;
    private Repositorio<Tramo> repoTramo;

    public OrganizacionController() {
        repoMiembro = FactoryRepositorio.get(Miembro.class);
        repoOrganizacion = FactoryRepositorio.get(Organizacion.class);
        repoFactores = FactoryRepositorio.get(FactorDeEmision.class);
        repoSolicitud = FactoryRepositorio.get(Solicitud.class);
        repoTramo = FactoryRepositorio.get(Tramo.class);
    }

    public ModelAndView guiaDeRecomendaciones(Request request, Response response) {
        return new ModelAndView(null, "/organizaciones/guiaDeRecomendaciones.hbs");
    }

    public Organizacion buscarOrganizacion(Request request) {
        return repoOrganizacion.buscar("usuario", request.session().attribute("id"));
    }

    public ModelAndView miembros(Request request, Response response) {
        return new ModelAndView(new HashMap<String, Object>() {{
            put("sectores", buscarOrganizacion(request).convertirADTO().getSectores());
        }}, "organizaciones/miembros.hbs");
    }

    public ModelAndView solicitudes(Request request, Response response) {
        Organizacion.OrganizacionDTO organizacion = buscarOrganizacion(request).convertirADTO();

        Map<String, Object> map = new HashMap<>();
        map.put("pendientes", organizacion.solicitudesPendientes());
        map.put("solicitudes", organizacion.getSolicitudes());

        return new ModelAndView(map, "organizaciones/solicitudes.hbs");
    }

    public Response vincular(Request request, Response response) {
        Organizacion organizacion = buscarOrganizacion(request);
        Solicitud solicitud = repoSolicitud.buscar(new Integer(request.params("id")));

        if(request.queryParams("aceptar").equals("true"))
            organizacion.aceptarVinculacion(solicitud);
        if(request.queryParams("aceptar").equals("false"))
            organizacion.rechazarVinculacion(solicitud);

        repoOrganizacion.modificar(organizacion);

        response.redirect("/organizacion/solicitudes");
        return response;
    }

    public ModelAndView mediciones(Request request, Response response) {
        return new ModelAndView(null, "/organizaciones/medicionesPrincipal.hbs");
    }

    public ModelAndView vistaMediciones(Request request, Response response) {
        return new ModelAndView(new HashMap<String, Object>() {{
            put("actividades", buscarOrganizacion(request).convertirADTO().getActividades());
        }}, "/organizaciones/medicionesVista.hbs");
    }

    public ModelAndView cargaMediciones(Request request, Response response) {
        return new ModelAndView(null, "organizaciones/medicionesRegistro.hbs");
    }

    public Response subirArchivo(Request request, Response response) {
        Organizacion organizacion = buscarOrganizacion(request);
        LectorExcel.setFactoresDeEmision(repoFactores.buscarTodos());
        LectorExcel.cargarExcel(organizacion, request);
        repoOrganizacion.modificar(organizacion);
        response.redirect("/organizacion/registrarMediciones");
        return response;
    }

    public ModelAndView calcularHuella(Request request, Response response) {
        return new ModelAndView(null, "organizaciones/huellaCalculador.hbs");
    }

    public ModelAndView vistaHuella(Request request, Response response) {
        Organizacion organizacion = buscarOrganizacion(request);
        List<Tramo> tramos = repoTramo.buscarTodos("organizacion", organizacion.getId());

        HashMap<String, Object> map = new HashMap<>();

        Integer anio = null, mes = null;
        LocalDate fecha = LocalDate.now();

        switch(request.queryParams("tipo_huella")) {
            case "Mensual":
                mes = new Integer(request.queryParams("mes"));
                fecha = fecha.withMonth(mes);
            case "Anual":
                anio = new Integer(request.queryParams("anio"));
                fecha = fecha.withYear(anio);
        }

        map.put("huella", organizacion.getCalculadorHuella().calcularHuella(anio, mes));
        map.put("actividades", organizacion.convertirADTO().getActividades(anio, mes));
        LocalDate finalFecha = fecha;
        map.put("trayectos", tramos.stream()
                .filter(t -> t.esDeLaFecha(finalFecha) && t.convertirADTO().getVehiculo() != null)
                .map(Tramo::convertirADTO).collect(Collectors.toList())
        );

        return new ModelAndView(map, "organizaciones/huellaVista.hbs");
    }

    public ModelAndView reportes(Request request, Response response){
        return new ModelAndView(null, "/organizaciones/reportes.hbs");
    }


    public ModelAndView visualizarReportes(Request request, Response response) {
        Organizacion organizacion = buscarOrganizacion(request);
        int anio = new Integer(request.queryParams("anio"));
        int mes = new Integer(request.queryParams("mes"));
        LocalDate fecha = LocalDate.of(anio, mes, 1);

        HashMap<String, Object> map = new HashMap<>();

        switch(request.queryParams("tipo_reporte")){
            case "Composición por Actividad":
                map.put("reporte", organizacion.composicion(fecha));
                map.put("columna1", "Actividad");
                map.put("columna2", "Porcentaje huella");
                map.put("unidad", "%");
                break;
            case "Evolución mensual":
                map.put("reporte", organizacion.getCalculadorHuella().evolucionMensual(fecha));
                map.put("columna1", "Fecha");
                map.put("columna2", "Huella");
                map.put("unidad", "kgCO2eq");
                break;
            case "Evolución anual":
                map.put("reporte", organizacion.getCalculadorHuella().evolucionAnual(fecha));
                map.put("columna1", "Año");
                map.put("columna2", "Huella");
                map.put("unidad", "kgCO2eq");
                break;
        }
        return new ModelAndView(map, "organizaciones/reportesVista.hbs");
    }

    public ModelAndView miOrganizacion(Request request, Response response) {
        return new ModelAndView(new HashMap<String, Object>() {{
            put("organizacion", buscarOrganizacion(request).convertirADTO());
        }}, "organizaciones/perfil.hbs");
    }

    public ModelAndView mostrarProhibido(Request request, Response response){
        return new ModelAndView(null, "organizacion/prohibido.hbs");
    }
}
