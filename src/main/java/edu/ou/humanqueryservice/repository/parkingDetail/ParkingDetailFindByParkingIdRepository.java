package edu.ou.humanqueryservice.repository.parkingDetail;

import edu.ou.coreservice.common.constant.Message;
import edu.ou.coreservice.common.exception.BusinessException;
import edu.ou.coreservice.common.validate.ValidValidation;
import edu.ou.coreservice.repository.base.BaseRepository;
import edu.ou.humanqueryservice.common.constant.CodeStatus;
import edu.ou.humanqueryservice.data.entity.ParkingDetailDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ParkingDetailFindByParkingIdRepository extends BaseRepository<Integer, List<ParkingDetailDocument>> {
    private final MongoTemplate mongoTemplate;
    private final ValidValidation validValidation;

    /**
     * Validate parking id
     *
     * @param parkingId parking id
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(Integer parkingId) {
        if (validValidation.isInValid(parkingId)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "parking identity"
            );
        }
    }

    /**
     * Find parking detail list by parking id
     *
     * @param parkingId parking id
     * @return parking detail list
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected List<ParkingDetailDocument> doExecute(Integer parkingId) {
        return mongoTemplate.find(
                new Query(
                        Criteria.where("parkingId")
                                .is(parkingId)
                ),
                ParkingDetailDocument.class
        );
    }

    @Override
    protected void postExecute(Integer input) {
        // do nothing
    }
}
