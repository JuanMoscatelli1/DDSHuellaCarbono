package domain.entities.importacionDeDatos;

import domain.entities.huellaDeCarbono.FactorDeEmision;
import domain.entities.importacionDeDatos.actividades.Actividad;
import domain.entities.actores.organizaciones.Organizacion;
import domain.entities.importacionDeDatos.consumos.Consumo;
import domain.entities.importacionDeDatos.consumos.ConsumoLogistica;
import domain.entities.importacionDeDatos.consumos.ConsumoSimple;
import domain.entities.importacionDeDatos.consumos.TipoDeConsumo;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import spark.Request;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static domain.entities.importacionDeDatos.actividades.TipoDeActividad.LOGISTICA_DE_PRODUCTOS_Y_RESIDUOS;

public class LectorExcel {
    private static List<FactorDeEmision> factoresDeEmision;

    public static void setFactoresDeEmision() {
        for(TipoDeConsumo tipoDeConsumo : TipoDeConsumo.values()) {
            if (
                    tipoDeConsumo != TipoDeConsumo.DISTANCIA &&
                    tipoDeConsumo != TipoDeConsumo.PESO &&
                    tipoDeConsumo != TipoDeConsumo.CATEGORIA &&
                    tipoDeConsumo != TipoDeConsumo.MEDIO_DE_TRANSPORTE
            ) factoresDeEmision.add(new FactorDeEmision(tipoDeConsumo, 0.1));
        }
    }

    public static void setFactoresDeEmision(List<FactorDeEmision> factoresDeEmision) {
        LectorExcel.factoresDeEmision = factoresDeEmision;
    }

    public void cargarExcel(Organizacion organizacion, String path) {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(path);
        leerArchivo(organizacion, in);
    }

    public static void cargarExcel(Organizacion organizacion, Request request) {
        try {
            request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
            InputStream in = request.raw().getPart("uploaded_file").getInputStream();
            leerArchivo(organizacion, in);
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
    }

    public static void leerArchivo(Organizacion organizacion, InputStream in) {
        assert in != null;
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(in);
            Iterator<Row> iteradorFilas = workbook.getSheetAt(0).iterator();
            iteradorFilas.next(); // encabezados
            while(iteradorFilas.hasNext()) organizacion.agregarActividad(leerFila(iteradorFilas));
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Actividad leerFila(Iterator<Row> iteradorFilas) {
        Iterator<Cell> iteradorCeldas = iteradorFilas.next().cellIterator();
        String tipoDeActividad = iteradorCeldas.next().getStringCellValue();

        Consumo consumo;

        if (tipoDeActividad.equals(LOGISTICA_DE_PRODUCTOS_Y_RESIDUOS.toString())) {
            consumo = new ConsumoLogistica();
            for (int i = 0; i < 3; i++) {
                ((ConsumoLogistica) consumo).agregarConsumo(leerConsumo(iteradorCeldas));
                iteradorCeldas = iteradorFilas.next().cellIterator();
                iteradorCeldas.next();
            }
            ((ConsumoLogistica) consumo).agregarConsumo(leerConsumo(iteradorCeldas));
        }
        else
            consumo = leerConsumo(iteradorCeldas);

        consumo.setFactorDeEmision(getFactorDeEmision(consumo.getTipoDeConsumo()));

        return new Actividad(
                tipoDeActividad,
                consumo,
                iteradorCeldas.next().getStringCellValue(),
                leerPerDeImputacion(iteradorCeldas.next())
        );
    }

    private static ConsumoSimple leerConsumo(Iterator<Cell> iteradorCeldas) {
        TipoDeConsumo tipoDeConsumo = TipoDeConsumo.valueOf(iteradorCeldas.next().getStringCellValue());

        Cell celdaValor = iteradorCeldas.next();
        celdaValor.setCellType(CellType.STRING);
        String valorConsumo = celdaValor.getStringCellValue();

        return new ConsumoSimple(tipoDeConsumo, valorConsumo);
    }

    private static LocalDate leerPerDeImputacion(Cell celda) {
        switch (celda.getCellType()) {
            case STRING:
                return YearMonth.parse(celda.getStringCellValue(), DateTimeFormatter.ofPattern("MM/yyyy")).atDay(1);
            case NUMERIC:
                return LocalDate.now().withYear((int) celda.getNumericCellValue());
        }
        return null;
    }

    private static FactorDeEmision getFactorDeEmision(TipoDeConsumo tipoDeConsumo) {
        return factoresDeEmision.stream()
                .filter(f -> f.getTipoDeConsumo() == tipoDeConsumo).findFirst().orElse(null);
    }
}
