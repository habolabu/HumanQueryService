package edu.ou.humanqueryservice.data.pojo.request.role;

import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import lombok.Data;

@Data
public class RoleRequest implements IBaseRequest {
    private int roleId;
    private String roleName;
}
