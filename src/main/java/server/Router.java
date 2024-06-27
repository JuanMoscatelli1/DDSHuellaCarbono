package server;

import controllers.*;
import domain.entities.helpersUsuario.PermisoHelper;
import domain.entities.usuarios.Rol;
import middlewares.AuthMiddleware;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import utils.BooleanHelper;
import utils.HandlebarsTemplateEngineBuilder;

public class Router {
    private static HandlebarsTemplateEngine engine;

    private static void initEngine() {
        Router.engine = HandlebarsTemplateEngineBuilder
                .create()
                .withDefaultHelpers()
                .withHelper("isTrue", BooleanHelper.isTrue)
                .build();
    }

    public static void init() {
        Router.initEngine();
        Spark.staticFileLocation("/public");
        Router.configure();
    }

    private static void configure(){
        IndexController indexController = new IndexController();

        Spark.path("/", () ->
                Spark.get("", indexController::pantallaDeInicio, engine)
        );

        LoginController loginController = new LoginController();

        Spark.path("/login", () -> {
            Spark.get("", loginController::pantallaDeLogin, engine);
            Spark.post("", loginController::login);
            Spark.post("/logout", loginController::logout);
        });

        Spark.path("/logout", () -> Spark.get("", loginController::logout));

        CrearUsuarioController crearUsuarioController = new CrearUsuarioController();

        //ya no debería ir no?
        Spark.path("/crearUsuario", () ->
                Spark.get("", crearUsuarioController::pantallaDeRegistro, engine)
        );

        // ---------------------------------------------------------------------//

        ReporteController reporteController = new ReporteController();

        //---------------------------- MIEMBROS ------------------------------//

        MiembroController miembroController = new MiembroController();

        Spark.path("/miembro", () -> {

            Spark.before("/*", AuthMiddleware::verificarSesion);

            Spark.before("/*", ((request, response) -> {
                if(!PermisoHelper.usuarioTienePermiso(request, Rol.MIEMBRO)) {
                    response.redirect("/prohibido");
                    Spark.halt();
                }
            }));

            Spark.get("/registrarTrayecto", miembroController::registrarTrayecto, engine);
            Spark.post("/registrarTramo", miembroController::registrarTramo, engine);
            Spark.get("/registrarTramo", miembroController::registrarTramo, engine);
            Spark.post("/agregarTramo", miembroController::agregarTramo);
            Spark.post("/agregarTrayecto", miembroController::agregarTrayecto);

            Spark.get("/calcularHuella", miembroController::calcularHuella,engine);
            Spark.post("/calcularHuella", miembroController::vistaHuella,engine);
            Spark.get("/vincularOrganizacion", miembroController::vincularOrganizacion,engine);
            Spark.post("/vincularOrganizacion", miembroController::vincularOrganizacionSeleccionada);
            Spark.get("/perfil", miembroController::miPerfil,engine);
            Spark.get("/miOrganizacion/:id", miembroController::miOrganizacion,engine);
            Spark.get("/prohibido", miembroController::mostrarProhibido, engine);
        });

        //------------------------------- ORGANIZACION ---------------------------//

        OrganizacionController organizacionController = new OrganizacionController();

        Spark.path("/organizacion", () -> {

            Spark.before("/*", AuthMiddleware::verificarSesion);

            Spark.before("/*", ((request, response) -> {
                if(!PermisoHelper.usuarioTienePermiso(request, Rol.ORGANIZACION)){
                    response.redirect("/prohibido");
                    Spark.halt();
                }
            }));

            Spark.get("/miembros", organizacionController::miembros, engine);
            Spark.get("/solicitudes", organizacionController::solicitudes, engine);
            Spark.post("/vincular/:id", organizacionController::vincular);

            Spark.get("/guia", organizacionController::guiaDeRecomendaciones, engine);

            Spark.get("/registrarMediciones", organizacionController::mediciones, engine);
            Spark.get("/vistaMediciones", organizacionController::vistaMediciones, engine);
            Spark.get("/mediciones", organizacionController::cargaMediciones, engine);
            Spark.post("/subirArchivo", organizacionController::subirArchivo);

            Spark.get("/calcularHuella", organizacionController::calcularHuella,engine);
            Spark.post("/calcularHuella", organizacionController::vistaHuella,engine);

            Spark.get("/reporte", organizacionController::reportes, engine);
            Spark.post("/visualizarReportes", organizacionController::visualizarReportes, engine);

            Spark.get("/miOrganizacion", organizacionController::miOrganizacion, engine);
            Spark.get("/prohibido", organizacionController::mostrarProhibido, engine);
        });

        // --------------------------------- AGENTE SECTORIAL ----------------------------- //

        AgenteController agenteController = new AgenteController();

        Spark.path("/agente", () ->{
            Spark.before("/*", AuthMiddleware::verificarSesion);

            Spark.before("/*", ((request, response) -> {
                if(!PermisoHelper.usuarioTienePermiso(request, Rol.AGENTE_SECTORIAL)) {
                    response.redirect("/prohibido");
                    Spark.halt();
                }
            }));

            Spark.get("/guia", agenteController::guiaDeRecomendaciones, engine);
            Spark.get("/reporte", agenteController::reportes, engine);
            Spark.post("/visualizarReportes", agenteController::visualizarReportes, engine);
            Spark.get("/prohibido", agenteController::mostrarProhibido, engine);
        });

//        AdminController adminController = new AdminController();
//
//        Spark.path("/admin", () -> {
//            Spark.before("/*", AuthMiddleware::verificarSesion);
//
//            Spark.before("/*", ((request, response) -> {
//                if (!PermisoHelper.usuarioTienePermiso(request, Rol.ADMINISTRADOR)) {
//                    response.redirect("/prohibido");
//                    Spark.halt();
//                }
//            }));
//            Spark.get("/menu", adminController::mostrarMenu, engine);
//            Spark.get("/organizaciones", adminController::mostrarOrganizaciones, engine);
//        });
    }
}