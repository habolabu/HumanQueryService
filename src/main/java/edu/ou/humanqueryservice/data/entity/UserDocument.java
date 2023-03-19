package edu.ou.humanqueryservice.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.ou.humanqueryservice.data.pojo.request.role.RoleRequest;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Document("User")
public class UserDocument implements Serializable {
    @Id
    @JsonIgnore
    private String id;
    @JsonProperty("id")
    private int oId;
    private String address;
    private int gender;
    private Date dateOfBirth;
    private String firstName;
    private String lastName;
    private String nationality;
    private String idCard;
    private String phoneNumber;
    private String email;
    private Date createdAt;
    private String keyCloakId;
    @Transient
    private List<RoleRequest> roles;
}
