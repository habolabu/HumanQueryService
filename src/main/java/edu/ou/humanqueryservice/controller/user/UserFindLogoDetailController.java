package edu.ou.humanqueryservice.controller.user;

import edu.ou.coreservice.common.constant.SecurityPermission;
import edu.ou.coreservice.common.util.SecurityUtils;
import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import edu.ou.coreservice.data.pojo.response.base.IBaseResponse;
import edu.ou.coreservice.service.base.IBaseService;
import edu.ou.humanqueryservice.common.constant.EndPoint;
import edu.ou.humanqueryservice.data.pojo.request.user.UserFindLogoDetailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndPoint.User.BASE)
public class UserFindLogoDetailController {
    private final IBaseService<IBaseRequest, IBaseResponse> userFindLogoDetailService;
    private final RabbitTemplate rabbitTemplate;

    /**
     * Find logo detail
     *
     * @return logo detail
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.AUTHENTICATED)
    @GetMapping(EndPoint.User.LOGO_DETAIL)
    public ResponseEntity<IBaseResponse> findLogoDetail() {
        final Map<String, String> account = SecurityUtils.getCurrentAccount(rabbitTemplate);

        return new ResponseEntity<>(
                userFindLogoDetailService.execute(new UserFindLogoDetailRequest().setUserId(
                        Integer.parseInt(account.get("userId")))),
                HttpStatus.OK
        );
    }
}
