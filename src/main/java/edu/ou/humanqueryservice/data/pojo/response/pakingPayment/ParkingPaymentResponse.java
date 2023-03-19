package edu.ou.humanqueryservice.data.pojo.response.pakingPayment;


import edu.ou.coreservice.data.pojo.response.base.IBaseResponse;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain = true)
public class ParkingPaymentResponse implements IBaseResponse {
    private int parkingId;
    private int parkingTypeId;
    private int userId;
    private Date joinDate;
    private BigDecimal pricePerDay;
}

