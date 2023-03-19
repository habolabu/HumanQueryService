package edu.ou.humanqueryservice.repository.roomDetail;

import edu.ou.coreservice.common.constant.Message;
import edu.ou.coreservice.common.exception.BusinessException;
import edu.ou.coreservice.common.validate.ValidValidation;
import edu.ou.coreservice.repository.base.BaseRepository;
import edu.ou.humanqueryservice.common.constant.CodeStatus;
import edu.ou.humanqueryservice.data.entity.RoomDetailDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoomDetailFindByRoomIdRepository extends BaseRepository<Integer, List<RoomDetailDocument>> {
    private final MongoTemplate mongoTemplate;
    private final ValidValidation validValidation;

    /**
     * Validate room id
     *
     * @param roomId room id
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(Integer roomId) {
        if (validValidation.isInValid(roomId)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "room identity"
            );
        }
    }

    /**
     * Find room detail list by room id
     *
     * @param roomId room id
     * @return room detail list
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected List<RoomDetailDocument> doExecute(Integer roomId) {
        return mongoTemplate.find(
                new Query(
                        Criteria.where("roomId")
                                .is(roomId)
                ),
                RoomDetailDocument.class
        );
    }

    @Override
    protected void postExecute(Integer input) {
        // do nothing
    }
}
