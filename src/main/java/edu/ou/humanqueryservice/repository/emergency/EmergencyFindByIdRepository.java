package edu.ou.humanqueryservice.repository.emergency;

import edu.ou.coreservice.common.constant.Message;
import edu.ou.coreservice.common.exception.BusinessException;
import edu.ou.coreservice.common.validate.ValidValidation;
import edu.ou.coreservice.repository.base.BaseRepository;
import edu.ou.humanqueryservice.common.constant.CodeStatus;
import edu.ou.humanqueryservice.data.entity.EmergencyDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EmergencyFindByIdRepository extends BaseRepository<Integer, EmergencyDocument> {
    private final MongoTemplate mongoTemplate;
    private final ValidValidation validValidation;

    /**
     * Validate emergency id
     *
     * @param emergencyId emergency id
     */
    @Override
    protected void preExecute(Integer emergencyId) {
        if (validValidation.isInValid(emergencyId)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "emergency identity"
            );
        }
    }

    /**
     * Find emergency detail
     *
     * @param emergencyId emergency id
     * @return emergency detail
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected EmergencyDocument doExecute(Integer emergencyId) {
        return mongoTemplate.findOne(
                new Query(
                        Criteria.where("oId")
                                .is(emergencyId)
                ),
                EmergencyDocument.class
        );
    }

    @Override
    protected void postExecute(Integer input) {
        // do nothing
    }
}
