package edu.ou.humanqueryservice.listener.user;

import edu.ou.coreservice.listener.IBaseListener;
import edu.ou.coreservice.queue.human.external.user.UserCheckExistQueueE;
import edu.ou.coreservice.repository.base.IBaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCheckExistListener implements IBaseListener<Integer, Object> {
    private final IBaseRepository<Integer, Boolean> userCheckExistByIdRepository;


    /**
     * check user exist or not
     *
     * @param userId userId
     * @return response
     * @author Nguyen Trung Kien - OU
     */
    @Override
    @RabbitListener(queues = UserCheckExistQueueE.QUEUE)
    public Object execute(Integer userId) {
        return userCheckExistByIdRepository.execute(userId);
    }

}
