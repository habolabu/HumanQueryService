package edu.ou.humanqueryservice.service.user;

import edu.ou.coreservice.common.constant.Message;
import edu.ou.coreservice.common.exception.BusinessException;
import edu.ou.coreservice.common.util.PaginationUtils;
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
import edu.ou.humanqueryservice.data.pojo.request.user.UserFindAllRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserFindAllService extends BaseService<IBaseRequest, IBaseResponse> {
    private final IBaseRepository<Query, List<UserDocument>> userFindAllRepository;
    private final IBaseRepository<Query, Integer> userGetPageAmountRepository;
    private final ValidValidation validValidation;
    private final RabbitTemplate rabbitTemplate;

    /**
     * Validate request
     *
     * @param request request
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(IBaseRequest request) {
        if (validValidation.isInValid(request, UserFindAllRequest.class)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "user"
            );
        }
    }

    /**
     * Find all user
     *
     * @param request request
     * @return avatar list
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected IBaseResponse doExecute(IBaseRequest request) {
        final UserFindAllRequest userFindAllWithParamsRequest = (UserFindAllRequest) request;

        final Query query = this.filter(userFindAllWithParamsRequest);

        final List<UserDocument> users = userFindAllRepository.execute(query);

        users.forEach(user -> {
            final Object roleObject = rabbitTemplate.convertSendAndReceive(
                    RoleFindByUserIdQueueE.EXCHANGE,
                    RoleFindByUserIdQueueE.ROUTING_KEY,
                    user.getOId()
            );
            final List<Map<String, Object>> roles = (List<Map<String, Object>>) roleObject;

            user.setRoles(Objects.isNull(roles)
                    ? List.of()
                    : roles.stream()
                    .map(RoleRequestMapper.INSTANCE::fromMap)
                    .collect(Collectors.toList()));
        });

        final int pageAmount = userGetPageAmountRepository.execute(query.skip(0).limit(0));

        return new SuccessResponse<>(
                new SuccessPojo<>(
                        Map.of(
                                "data", users,
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
    private Query filter(UserFindAllRequest request) {
        final Query query = new Query();

        if (Objects.nonNull(request.getFirstName())) {
            query.addCriteria(
                    Criteria.where("firstName")
                            .regex(
                                    request.getFirstName(),
                                    "i"
                            )
            );
        }

        if (Objects.nonNull(request.getLastName())) {
            query.addCriteria(
                    Criteria.where("lastName")
                            .regex(
                                    request.getLastName(),
                                    "i"
                            )
            );
        }

        if (Objects.nonNull(request.getAddress())) {
            query.addCriteria(
                    Criteria.where("address")
                            .regex(
                                    request.getAddress(),
                                    "i"
                            )
            );
        }

        if (Objects.nonNull(request.getIdCard())) {
            query.addCriteria(
                    Criteria.where("idCard")
                            .regex(
                                    request.getIdCard(),
                                    "i"
                            )
            );
        }

        if (Objects.nonNull(request.getPhoneNumber())) {
            query.addCriteria(
                    Criteria.where("phoneNumber")
                            .regex(
                                    request.getPhoneNumber(),
                                    "i"
                            )
            );
        }

        if (Objects.nonNull(request.getEmail())) {
            query.addCriteria(
                    Criteria.where("email")
                            .regex(
                                    request.getEmail(),
                                    "i"
                            )
            );
        }

        if (Objects.nonNull(request.getNationality())) {
            query.addCriteria(
                    Criteria.where("nationality")
                            .regex(
                                    request.getNationality(),
                                    "i"
                            )
            );
        }

        if (Objects.nonNull(request.getBDateOfBirth())
                && Objects.nonNull(request.getEDateOfBirth())) {
            query.addCriteria(
                    Criteria.where("dateOfBirth")
                            .gte(new Timestamp(request.getBDateOfBirth().getTime()))
                            .lte(new Timestamp(request.getEDateOfBirth().getTime()))

            );
        }

        query.skip(PaginationUtils.getSearchIndex(request.getPage()))
                .limit(PaginationUtils.getPageSize());

        return query;
    }
}
