package edu.ou.humanqueryservice.data.pojo.request.emergency;

import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class EmergencyFindDetailRequest implements IBaseRequest {
    @Min(
            value = 1,
            message = "Value must be greater than or equals to 1"
    )
    private int emergencyId;
}
