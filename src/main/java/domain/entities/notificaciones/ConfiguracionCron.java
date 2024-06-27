package domain.entities.notificaciones;

import lombok.Getter;

public enum ConfiguracionCron {
    CADA_MEDIO_MINUTO("0/30 0/1 * 1/1 * ? *"),
    CADA_MINUTO("0 0/1 * 1/1 * ? *"), // EVERY 1 MINUTE: 0 0/1 * 1/1 * ? *
    CADA_DIA("0 0 9 1/1 * ? *"), // EVERY DAY AT 09:00 AM: 0 0 9 1/1 * ? *
    CADA_LUNES("0 0 9 ? * MON *"), // EVERY MONDAY AT 09:00 AM: 0 0 9 ? * MON *
    CADA_MES_DIA_UNO("0 0 9 1 1/1 ? *"); // EVERY DAY 1 OF EVERY 1 MONTH AT 09:00 AM: 0 0 9 1 1/1 ? *

    @Getter private final String cronExpresion;

    ConfiguracionCron (String cronExpresion) {
        this.cronExpresion = cronExpresion;
    }
}