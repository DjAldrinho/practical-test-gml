package dev.aldrinho.practicaltestgml.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@RequiredArgsConstructor
public class ClientDto {
    private Long id;
    private String businessId;
    private String sharedKey;
    private String email;
    private String phone;
    @JsonIgnore
    private Date dataAdded;

    //Format the dataAdded Attribute
    public String getDataAddedFormatted() {
        if (dataAdded != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(dataAdded);
        }
        return null;
    }
}
