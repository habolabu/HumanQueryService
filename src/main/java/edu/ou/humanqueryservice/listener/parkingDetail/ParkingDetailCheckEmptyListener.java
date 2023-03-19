package edu.ou.humanqueryservice.listener.parkingDetail;

import edu.ou.coreservice.listener.IBaseListener;
import edu.ou.coreservice.queue.human.external.parkingDetail.ParkingDetailCheckEmptyQueueE;
import edu.ou.coreservice.repository.base.IBaseRepository;
import edu.ou.humanqueryservice.data.entity.ParkingDetailDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ParkingDetailCheckEmptyListener implements IBaseListener<Integer, Object> {
    private final IBaseRepository<Integer, List<ParkingDetailDocument>> parkingDetailFindByParkingIdRepository;

    /**
     * check parking is empty or not
     *
     * @param parkingId parkingId
     * @return response
     * @author Nguyen Trung Kien - OU
     */
    @Override
    @RabbitListener(queues = ParkingDetailCheckEmptyQueueE.QUEUE)
    public Object execute(Integer parkingId) {
        return parkingDetailFindByParkingIdRepository
                .execute(parkingId)
                .isEmpty();
    }
}
