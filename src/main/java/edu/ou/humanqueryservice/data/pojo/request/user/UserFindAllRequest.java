package edu.ou.humanqueryservice.data.pojo.request.user;

import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UserFindAllRequest implements IBaseRequest {
    @NotNull
    @Min(
            value = 1,
            message = "The value must be greater than or equals to 1"
    )
    private int page;
    private String firstName;
    private String lastName;
    private String address;
    private String idCard;
    private String phoneNumber;
    private String email;
    private String nationality;
    private Date bDateOfBirth;
    private Date eDateOfBirth;
}
