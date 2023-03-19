package edu.ou.humanqueryservice.data.pojo.request.user;

import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class UserFindDetailRequest implements IBaseRequest {
    @Min(
            value = 1,
            message = "Value must be greater than or equals to 1"
    )
    private int userId;
}
