package edu.ou.humanqueryservice.listener.parkingPayment;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ou.coreservice.listener.IBaseListener;
import edu.ou.coreservice.queue.building.external.parkingType.ParkingTypeFindPricePerDayByIdQueueE;
import edu.ou.coreservice.queue.payment.external.payment.ParkingPaymentFindAllQueueE;
import edu.ou.coreservice.repository.base.IBaseRepository;
import edu.ou.humanqueryservice.data.entity.ParkingDetailDocument;
import edu.ou.humanqueryservice.data.pojo.response.pakingPayment.ParkingPaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ParkingPaymentFindAllListener implements IBaseListener<Object, Object> {
    private final IBaseRepository<Query, List<ParkingDetailDocument>> parkingDetailFindAllRepository;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;


    /**
     * Find all parking payment detail
     *
     * @param input input of task
     * @return list of parking payment
     * @author Nguyen Trung Kien - OU
     */
    @Override
    @RabbitListener(queues = ParkingPaymentFindAllQueueE.QUEUE)
    public Object execute(Object input) {
        final List<Map> parkingPaymentDetails = new ArrayList<>();
        parkingDetailFindAllRepository
                .execute(new Query())
                .forEach(parkingDetailDocument -> {
                    final Integer pricePerDay = (Integer) rabbitTemplate.convertSendAndReceive(
                            ParkingTypeFindPricePerDayByIdQueueE.EXCHANGE,
                            ParkingTypeFindPricePerDayByIdQueueE.ROUTING_KEY,
                            parkingDetailDocument.getParkingTypeId()
                    );

                    parkingPaymentDetails.add(objectMapper.convertValue(
                            new ParkingPaymentResponse()
                                    .setParkingId(parkingDetailDocument.getParkingId())
                                    .setParkingTypeId(parkingDetailDocument.getParkingTypeId())
                                    .setJoinDate(parkingDetailDocument.getJoinDate())
                                    .setUserId(parkingDetailDocument.getUserId())
                                    .setPricePerDay(BigDecimal.valueOf(pricePerDay)),
                            Map.class
                    ));
                });
        return parkingPaymentDetails;
    }
}
