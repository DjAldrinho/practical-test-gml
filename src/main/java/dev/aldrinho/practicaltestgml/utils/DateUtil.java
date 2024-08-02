package dev.aldrinho.practicaltestgml.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
public class DateUtil {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public static Date parseDate(String dateStr) {
        log.info("Convirtiendo fecha: {}", dateStr);
        if (StringUtils.hasText(dateStr)) {
            try {
                LocalDate localDate = LocalDate.parse(dateStr, DATE_FORMATTER);
                return java.sql.Date.valueOf(localDate);
            } catch (Exception e) {
                log.error("Error al convertir fecha: " + e.getMessage());
            }
        }
        return null;
    }
}
