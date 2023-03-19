package edu.ou.humanqueryservice.service.emergency;

import edu.ou.coreservice.common.constant.Message;
import edu.ou.coreservice.common.exception.BusinessException;
import edu.ou.coreservice.common.validate.ValidValidation;
import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import edu.ou.coreservice.data.pojo.response.base.IBaseResponse;
import edu.ou.coreservice.data.pojo.response.impl.SuccessPojo;
import edu.ou.coreservice.data.pojo.response.impl.SuccessResponse;
import edu.ou.coreservice.repository.base.IBaseRepository;
import edu.ou.coreservice.service.base.BaseService;
import edu.ou.humanqueryservice.common.constant.CodeStatus;
import edu.ou.humanqueryservice.data.entity.EmergencyDocument;
import edu.ou.humanqueryservice.data.pojo.request.emergency.EmergencyFindDetailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmergencyFindDetailService extends BaseService<IBaseRequest, IBaseResponse> {
    private final IBaseRepository<Integer, EmergencyDocument> emergencyFindByIdRepository;
    private final ValidValidation validValidation;

    /**
     * validate avatar detail request
     *
     * @param request request
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(IBaseRequest request) {
        if (validValidation.isInValid(request, EmergencyFindDetailRequest.class)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "emergency"
            );
        }
    }

    /**
     * Find apartment detail
     *
     * @param request request
     * @return response
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected IBaseResponse doExecute(IBaseRequest request) {
        final EmergencyFindDetailRequest emergencyFindDetailRequest = (EmergencyFindDetailRequest) request;

        final EmergencyDocument emergencyDocument = emergencyFindByIdRepository
                .execute(emergencyFindDetailRequest.getEmergencyId());

        if (Objects.isNull(emergencyDocument)) {
            throw new BusinessException(
                    CodeStatus.NOT_FOUND,
                    Message.Error.NOT_FOUND,
                    "emergency",
                    "emergency identity",
                    emergencyFindDetailRequest.getEmergencyId()
            );
        }

        return new SuccessResponse<>(
                new SuccessPojo<>(
                        emergencyDocument,
                        CodeStatus.SUCCESS,
                        Message.Success.SUCCESSFUL
                )
        );
    }

    @Override
    protected void postExecute(IBaseRequest request) {
        // do nothing
    }
}
