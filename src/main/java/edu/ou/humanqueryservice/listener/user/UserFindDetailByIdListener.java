package edu.ou.humanqueryservice.listener.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ou.coreservice.listener.IBaseListener;
import edu.ou.coreservice.queue.human.external.user.UserFindDetailByIdQueueE;
import edu.ou.coreservice.repository.base.IBaseRepository;
import edu.ou.humanqueryservice.data.entity.UserDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserFindDetailByIdListener implements IBaseListener<Integer, Object> {
    private final IBaseRepository<Integer, UserDocument> userFindByIdRepository;
    private final ObjectMapper objectMapper;

    /**
     * Find user detail
     *
     * @param userId userId
     * @return response
     * @author Nguyen Trung Kien - OU
     */
    @Override
    @RabbitListener(queues = UserFindDetailByIdQueueE.QUEUE)
    public Object execute(Integer userId) {
        return objectMapper.convertValue(
                userFindByIdRepository.execute(userId),
                Map.class
        );

    }
}
