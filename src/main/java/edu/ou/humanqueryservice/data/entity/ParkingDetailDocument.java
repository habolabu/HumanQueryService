package edu.ou.humanqueryservice.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Data
@Document("ParkingDetail")
public class ParkingDetailDocument implements Serializable {
    @Id
    @JsonIgnore
    private String id;
    private int parkingId;
    private int parkingTypeId;
    private int userId;
    private Date joinDate;
}
