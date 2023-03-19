package edu.ou.humanqueryservice.repository.avatar;

import edu.ou.coreservice.common.constant.Message;
import edu.ou.coreservice.common.exception.BusinessException;
import edu.ou.coreservice.common.validate.ValidValidation;
import edu.ou.coreservice.repository.base.BaseRepository;
import edu.ou.humanqueryservice.common.constant.CodeStatus;
import edu.ou.humanqueryservice.data.entity.AvatarDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AvatarFindAllRepository extends BaseRepository<Query, List<AvatarDocument>> {
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
                    "avatar find all query"
            );
        }
    }

    /**
     * Find all avatar
     *
     * @param query query
     * @return list of avatar
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected List<AvatarDocument> doExecute(Query query) {
        return mongoTemplate.find(
                query,
                AvatarDocument.class
        );
    }

    @Override
    protected void postExecute(Query input) {
        // do nothing
    }

}
