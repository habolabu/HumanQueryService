package edu.ou.humanqueryservice.data.pojo.request.roomDetail;

import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class RoomDetailFindAllRequest implements IBaseRequest {
    private Integer roomId;
    private Integer userId;
    @NotNull
    @Min(
            value = 1,
            message = "The value must be greater than or equals to 1"
    )
    private Integer page;
    private Date bJoinDate;
    private Date eJoinDate;
}
