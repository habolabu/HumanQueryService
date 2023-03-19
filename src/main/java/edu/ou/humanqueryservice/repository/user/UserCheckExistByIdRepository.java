package edu.ou.humanqueryservice.repository.user;

import edu.ou.coreservice.common.constant.Message;
import edu.ou.coreservice.common.exception.BusinessException;
import edu.ou.coreservice.common.validate.ValidValidation;
import edu.ou.coreservice.repository.base.BaseRepository;
import edu.ou.humanqueryservice.common.constant.CodeStatus;
import edu.ou.humanqueryservice.data.entity.UserDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class UserCheckExistByIdRepository extends BaseRepository<Integer, Boolean> {
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
     * check user exist
     *
     * @param userId user id
     * @return user exist or not
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected Boolean doExecute(Integer userId) {
        return Objects.nonNull(
                mongoTemplate.findOne(
                        new Query(
                                Criteria.where("oId")
                                        .is(userId)
                        ),
                        UserDocument.class
                )
        );
    }

    @Override
    protected void postExecute(Integer input) {
        // do nothing
    }
}
