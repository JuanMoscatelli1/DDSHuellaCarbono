package domain.entities.importacionDeDatos.consumos;

import lombok.Getter;

public enum TipoDeConsumo {

    // Alcance 1

    GAS_NATURAL("m3"),
    DIESEL_GASOIL("lt"),
    KEROSENE("lt"),
    FUEL_OIL("lt"),
    NAFTA("lt"),
    CARBON("kg"),
    CARBON_DE_LENIA("kg"),
    LENIA("kg"),

    // Alcance 2

    ELECTRICIDAD("kWh"),

    // Alcance 3

    LOGISTICA("kgkm"),
    CATEGORIA(null),
    MEDIO_DE_TRANSPORTE(null),
    DISTANCIA("km"),
    PESO("kg");

    @Getter
    private final String unidad;
    TipoDeConsumo(String unidad) { this.unidad = unidad; }
}