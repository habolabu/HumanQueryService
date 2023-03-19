package edu.ou.humanqueryservice.repository.roomDetail;

import edu.ou.coreservice.common.constant.Message;
import edu.ou.coreservice.common.exception.BusinessException;
import edu.ou.coreservice.common.validate.ValidValidation;
import edu.ou.coreservice.repository.base.BaseRepository;
import edu.ou.humanqueryservice.common.constant.CodeStatus;
import edu.ou.humanqueryservice.data.entity.RoomDetailDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoomDetailFindAllRepository extends BaseRepository<Query, List<RoomDetailDocument>> {
    private final MongoTemplate mongoTemplate;
    private final ValidValidation validValidation;

    /**
     * Validate input
     *
     * @param query input of task
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(Query query) {
        if (validValidation.isInValid(query)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "room detail find all query"
            );
        }
    }

    /**
     * Find all room detail
     *
     * @param query query
     * @return list of room detail
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected List<RoomDetailDocument> doExecute(Query query) {
        return mongoTemplate.find(
                query,
                RoomDetailDocument.class
        );
    }

    @Override
    protected void postExecute(Query input) {
        // do nothing
    }
}
