package edu.ou.humanqueryservice.controller.avatar;

import edu.ou.coreservice.common.constant.SecurityPermission;
import edu.ou.coreservice.common.util.SecurityUtils;
import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import edu.ou.coreservice.data.pojo.response.base.IBaseResponse;
import edu.ou.coreservice.service.base.IBaseService;
import edu.ou.humanqueryservice.common.constant.EndPoint;
import edu.ou.humanqueryservice.data.pojo.request.avatar.AvatarFindAllRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndPoint.Avatar.BASE)
public class AvatarFindAllBySelfController {
    private final IBaseService<IBaseRequest, IBaseResponse> avatarFindAllService;
    private final RabbitTemplate rabbitTemplate;

    /**
     * Find all avatar of current user
     *
     * @param page page index
     * @return list avatar of current user
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.AUTHENTICATED)
    @GetMapping()
    public ResponseEntity<IBaseResponse> findAllAvatarBySelf(
            @RequestParam(required = false, defaultValue = "1") Integer page
    ) {
        final Map<String, String> account = SecurityUtils.getCurrentAccount(rabbitTemplate);
        return new ResponseEntity<>(
                avatarFindAllService.execute(
                        new AvatarFindAllRequest()
                                .setUserId(Integer.parseInt(account.get("userId")))
                                .setPage(page)
                ),
                HttpStatus.OK
        );
    }
}
