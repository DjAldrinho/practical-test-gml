package dev.aldrinho.practicaltestgml.utils;

import dev.aldrinho.practicaltestgml.dto.ClientDto;
import jakarta.servlet.http.HttpServletResponse;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExcelUtil {

    public static void exportToExcel(HttpServletResponse response, List<ClientDto> listClients) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=clients_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        //Generate headers and value
        String[] csvHeader = {"ID", "sharedKey", "businessId", "email", "phone", "dataAdded"};
        String[] nameMapping = {"id", "sharedKey", "businessId", "email", "phone", "dataAddedFormatted"};

        csvWriter.writeHeader(csvHeader);

        //Writing the csv
        for (ClientDto clientDto : listClients) {
            csvWriter.write(clientDto, nameMapping);
        }

        csvWriter.close();
    }

}