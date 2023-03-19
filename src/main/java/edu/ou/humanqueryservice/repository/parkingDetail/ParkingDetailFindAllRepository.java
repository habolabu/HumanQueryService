package edu.ou.humanqueryservice.repository.parkingDetail;

import edu.ou.coreservice.common.constant.Message;
import edu.ou.coreservice.common.exception.BusinessException;
import edu.ou.coreservice.common.validate.ValidValidation;
import edu.ou.coreservice.repository.base.BaseRepository;
import edu.ou.humanqueryservice.common.constant.CodeStatus;
import edu.ou.humanqueryservice.data.entity.ParkingDetailDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ParkingDetailFindAllRepository extends BaseRepository<Query, List<ParkingDetailDocument>> {
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
                    "parking detail find all query"
            );
        }
    }

    /**
     * Find all parking detail
     *
     * @param query query
     * @return list of parking detail
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected List<ParkingDetailDocument> doExecute(Query query) {
        return mongoTemplate.find(
                query,
                ParkingDetailDocument.class
        );
    }

    @Override
    protected void postExecute(Query input) {
        // do nothing
    }

}
