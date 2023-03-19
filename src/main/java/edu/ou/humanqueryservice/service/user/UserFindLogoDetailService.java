package edu.ou.humanqueryservice.service.user;

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
import edu.ou.humanqueryservice.data.entity.AvatarDocument;
import edu.ou.humanqueryservice.data.entity.UserDocument;
import edu.ou.humanqueryservice.data.pojo.request.user.UserFindLogoDetailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserFindLogoDetailService extends BaseService<IBaseRequest, IBaseResponse> {
    private final IBaseRepository<Integer, UserDocument> userFindByIdRepository;
    private final IBaseRepository<Integer, AvatarDocument> avatarFindByUserIdRepository;
    private final ValidValidation validValidation;

    /**
     * validate user detail request
     *
     * @param request request
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(IBaseRequest request) {
        if (validValidation.isInValid(request, UserFindLogoDetailRequest.class)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "user"
            );
        }
    }

    /**
     * Find logo detail
     *
     * @param request request
     * @return response
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected IBaseResponse doExecute(IBaseRequest request) {
        final UserFindLogoDetailRequest userFindLogoDetailRequest = (UserFindLogoDetailRequest) request;

        final UserDocument userDocument = userFindByIdRepository.execute(userFindLogoDetailRequest.getUserId());

        if (Objects.isNull(userDocument)) {
            throw new BusinessException(
                    CodeStatus.NOT_FOUND,
                    Message.Error.NOT_FOUND,
                    "user",
                    "user identity",
                    userFindLogoDetailRequest.getUserId()
            );
        }

        final AvatarDocument avatarDocument = avatarFindByUserIdRepository
                .execute(userFindLogoDetailRequest.getUserId());

        return new SuccessResponse<>(
                new SuccessPojo<>(
                        Map.of(
                                "firstName", userDocument.getFirstName(),
                                "lastName", userDocument.getLastName(),
                                "avatar", avatarDocument.getUrl()
                        ),
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
