package edu.ou.humanqueryservice.data.pojo.request.user;

import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import lombok.Data;

@Data
public class UserFindLogoDetailRequest implements IBaseRequest {
    private int userId;
}
