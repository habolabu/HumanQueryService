package edu.ou.humanqueryservice.service.user;

import edu.ou.coreservice.common.constant.Message;
import edu.ou.coreservice.common.exception.BusinessException;
import edu.ou.coreservice.common.validate.ValidValidation;
import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import edu.ou.coreservice.data.pojo.response.base.IBaseResponse;
import edu.ou.coreservice.data.pojo.response.impl.SuccessPojo;
import edu.ou.coreservice.data.pojo.response.impl.SuccessResponse;
import edu.ou.coreservice.queue.auth.external.role.RoleFindByUserIdQueueE;
import edu.ou.coreservice.repository.base.IBaseRepository;
import edu.ou.coreservice.service.base.BaseService;
import edu.ou.humanqueryservice.common.constant.CodeStatus;
import edu.ou.humanqueryservice.common.mapper.RoleRequestMapper;
import edu.ou.humanqueryservice.data.entity.UserDocument;
import edu.ou.humanqueryservice.data.pojo.request.user.UserFindDetailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserFindDetailService extends BaseService<IBaseRequest, IBaseResponse> {
    private final IBaseRepository<Integer, UserDocument> userFindByIdRepository;
    private final ValidValidation validValidation;
    private final RabbitTemplate rabbitTemplate;

    /**
     * validate user detail request
     *
     * @param request request
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(IBaseRequest request) {
        if (validValidation.isInValid(request, UserFindDetailRequest.class)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "user"
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
        final UserFindDetailRequest userFindDetailRequest = (UserFindDetailRequest) request;

        final UserDocument userDocument = userFindByIdRepository.execute(userFindDetailRequest.getUserId());

        if (Objects.isNull(userDocument)) {
            throw new BusinessException(
                    CodeStatus.NOT_FOUND,
                    Message.Error.NOT_FOUND,
                    "user",
                    "user identity",
                    userFindDetailRequest.getUserId()
            );
        }

        final Object roleObject = rabbitTemplate.convertSendAndReceive(
                RoleFindByUserIdQueueE.EXCHANGE,
                RoleFindByUserIdQueueE.ROUTING_KEY,
                userDocument.getOId()
        );
        final List<Map<String, Object>> roles = (List<Map<String, Object>>) roleObject;

        userDocument.setRoles(Objects.isNull(roles)
                ? List.of()
                : roles.stream()
                .map(RoleRequestMapper.INSTANCE::fromMap)
                .collect(Collectors.toList()));

        return new SuccessResponse<>(
                new SuccessPojo<>(
                        userDocument,
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
