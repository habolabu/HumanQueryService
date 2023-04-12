package edu.ou.humanqueryservice.controller.emergency;

import edu.ou.coreservice.common.constant.SecurityPermission;
import edu.ou.coreservice.common.util.SecurityUtils;
import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import edu.ou.coreservice.data.pojo.response.base.IBaseResponse;
import edu.ou.coreservice.service.base.IBaseService;
import edu.ou.humanqueryservice.common.constant.EndPoint;
import edu.ou.humanqueryservice.data.pojo.request.emergency.EmergencyFindAllRequest;
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
@RequestMapping(EndPoint.Emergency.BASE)
public class EmergencyFindAllBySelfController {
    private final IBaseService<IBaseRequest, IBaseResponse> emergencyFindAllService;
    private final RabbitTemplate rabbitTemplate;

    /**
     * Find all emergency of current user
     *
     * @param page page index
     * @return list emergency of current user
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.AUTHENTICATED)
    @GetMapping()
    public ResponseEntity<IBaseResponse> findAllEmergencyBySelf(
            @RequestParam(required = false, defaultValue = "1") Integer page
    ) {
        return new ResponseEntity<>(
                emergencyFindAllService.execute(
                        new EmergencyFindAllRequest()
                                .setUserId(SecurityUtils.getCurrentAccount(rabbitTemplate).getUserId())
                                .setPage(page)
                ),
                HttpStatus.OK
        );
    }
}
