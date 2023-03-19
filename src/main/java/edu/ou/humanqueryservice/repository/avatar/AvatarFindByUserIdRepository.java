package edu.ou.humanqueryservice.repository.avatar;

import edu.ou.coreservice.common.constant.Message;
import edu.ou.coreservice.common.exception.BusinessException;
import edu.ou.coreservice.common.validate.ValidValidation;
import edu.ou.coreservice.repository.base.BaseRepository;
import edu.ou.humanqueryservice.common.constant.CodeStatus;
import edu.ou.humanqueryservice.data.entity.AvatarDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AvatarFindByUserIdRepository extends BaseRepository<Integer, AvatarDocument> {
    private final MongoTemplate mongoTemplate;
    private final ValidValidation validValidation;

    /**
     * Validate user id
     *
     * @param userId user id
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(Integer userId) {
        if (validValidation.isInValid(userId)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "user identity"
            );
        }
    }

    /**
     * Find user detail
     *
     * @param userId user id
     * @return user detail
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected AvatarDocument doExecute(Integer userId) {
        return mongoTemplate.findOne(
                new Query(
                        Criteria.where("userId")
                                .is(userId)
                                .andOperator(Criteria.where("isSelected").is(true))
                ),
                AvatarDocument.class
        );
    }

    @Override
    protected void postExecute(Integer input) {
        // do nothing
    }
}
