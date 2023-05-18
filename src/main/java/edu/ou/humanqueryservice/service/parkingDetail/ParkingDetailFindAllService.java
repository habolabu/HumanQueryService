package edu.ou.humanqueryservice.service.parkingDetail;

import edu.ou.coreservice.common.constant.Message;
import edu.ou.coreservice.common.exception.BusinessException;
import edu.ou.coreservice.common.util.PaginationUtils;
import edu.ou.coreservice.common.validate.ValidValidation;
import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import edu.ou.coreservice.data.pojo.response.base.IBaseResponse;
import edu.ou.coreservice.data.pojo.response.impl.SuccessPojo;
import edu.ou.coreservice.data.pojo.response.impl.SuccessResponse;
import edu.ou.coreservice.repository.base.IBaseRepository;
import edu.ou.coreservice.service.base.BaseService;
import edu.ou.humanqueryservice.common.constant.CodeStatus;
import edu.ou.humanqueryservice.data.entity.ParkingDetailDocument;
import edu.ou.humanqueryservice.data.pojo.request.parkingDetail.ParkingDetailFindAllRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ParkingDetailFindAllService extends BaseService<IBaseRequest, IBaseResponse> {
    private final IBaseRepository<Query, List<ParkingDetailDocument>> parkingDetailFindAllRepository;
    private final IBaseRepository<Query, Integer> parkingDetailGetPageAmountRepository;
    private final ValidValidation validValidation;

    /**
     * Validate request
     *
     * @param request request
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(IBaseRequest request) {
        if (validValidation.isInValid(request, ParkingDetailFindAllRequest.class)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "parking detail"
            );
        }
    }

    /**
     * Find all emergency
     *
     * @param request request
     * @return avatar list
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected IBaseResponse doExecute(IBaseRequest request) {
        final ParkingDetailFindAllRequest parkingDetailFindAllWithParamsRequest = (ParkingDetailFindAllRequest) request;

        final Query query = filter(parkingDetailFindAllWithParamsRequest);

        final List<ParkingDetailDocument> parkingDetailDocuments = parkingDetailFindAllRepository.execute(query);
        final int pageAmount = parkingDetailGetPageAmountRepository.execute(query.skip(0).limit(0));

        return new SuccessResponse<>(
                new SuccessPojo<>(
                        Map.of(
                                "data", parkingDetailDocuments,
                                "totalPage", PaginationUtils.getPageAmount(pageAmount)
                        ),
                        CodeStatus.SUCCESS,
                        Message.Success.SUCCESSFUL
                )
        );
    }

    @Override
    protected void postExecute(IBaseRequest input) {
        // do nothing
    }

    /**
     * Filter response
     *
     * @param request request params
     * @author Nguyen Trung Kien - OU
     */
    private Query filter(ParkingDetailFindAllRequest request) {
        final Query query = new Query();

        if (Objects.nonNull(request.getParkingId())) {
            query.addCriteria(
                    Criteria.where("parkingId")
                            .is(request.getParkingId())
            );
        }

        if (Objects.nonNull(request.getUserId())) {
            query.addCriteria(
                    Criteria.where("userId")
                            .is(request.getUserId())
            );
        }

        if (Objects.nonNull(request.getParkingTypeId())) {
            query.addCriteria(
                    Criteria.where("parkingTypeId")
                            .is(request.getParkingTypeId())
            );
        }

        if (Objects.nonNull(request.getBJoinDate())
                && Objects.nonNull(request.getEJoinDate())) {
            query.addCriteria(
                    Criteria.where("joinDate")
                            .gte(new Timestamp(request.getBJoinDate().getTime()))
                            .lte(new Timestamp(request.getEJoinDate().getTime()))

            );
        }

        query.skip(PaginationUtils.getSearchIndex(request.getPage()))
                .limit(PaginationUtils.getPageSize());

        return query;
    }
}
