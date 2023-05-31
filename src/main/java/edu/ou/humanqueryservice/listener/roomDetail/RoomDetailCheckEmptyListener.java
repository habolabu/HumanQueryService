package edu.ou.humanqueryservice.listener.roomDetail;

import edu.ou.coreservice.listener.IBaseListener;
import edu.ou.coreservice.queue.human.external.roomDetail.RoomDetailCheckEmptyQueueE;
import edu.ou.coreservice.repository.base.IBaseRepository;
import edu.ou.humanqueryservice.data.entity.RoomDetailDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoomDetailCheckEmptyListener implements IBaseListener<Integer, Object> {
    private final IBaseRepository<Integer, List<RoomDetailDocument>> roomDetailFindByRoomIdRepository;

    /**
     * check room is empty or not
     *
     * @param roomId roomId
     * @return response
     * @author Nguyen Trung Kien - OU
     */
    @Override
    @RabbitListener(queues = RoomDetailCheckEmptyQueueE.QUEUE)
    public Object execute(Integer roomId) {
        return roomDetailFindByRoomIdRepository
                .execute(roomId)
                .isEmpty();
    }
}
