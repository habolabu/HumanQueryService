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
public class AvatarFindByIdRepository extends BaseRepository<Integer, AvatarDocument> {
    private final MongoTemplate mongoTemplate;
    private final ValidValidation validValidation;

    /**
     * Validate avatar id
     *
     * @param avatarId avatar id
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(Integer avatarId) {
        if (validValidation.isInValid(avatarId)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "avatar identity"
            );
        }
    }

    /**
     * Find avatar detail
     *
     * @param avatarId avatar id
     * @return avatar detail
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected AvatarDocument doExecute(Integer avatarId) {
        return mongoTemplate.findOne(
                new Query(
                        Criteria.where("oId")
                                .is(avatarId)
                ),
                AvatarDocument.class
        );
    }

    @Override
    protected void postExecute(Integer input) {
        // do nothing
    }
}
