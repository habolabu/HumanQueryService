package edu.ou.humanqueryservice.data.pojo.request.avatar;

import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class AvatarFindDetailRequest implements IBaseRequest {
    @Min(
       value = 1,
       message = "Value must be greater than or equals to 1"
    )
    private int avatarId;
}
