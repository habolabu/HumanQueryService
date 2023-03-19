package edu.ou.humanqueryservice.controller.emergency;

import edu.ou.coreservice.common.constant.SecurityPermission;
import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import edu.ou.coreservice.data.pojo.response.base.IBaseResponse;
import edu.ou.coreservice.service.base.IBaseService;
import edu.ou.humanqueryservice.common.constant.EndPoint;
import edu.ou.humanqueryservice.data.pojo.request.emergency.EmergencyFindDetailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndPoint.Emergency.BASE)
public class EmergencyFindDetailController {
    private final IBaseService<IBaseRequest, IBaseResponse> emergencyFindDetailService;

    /**
     * find detail of exist emergency
     *
     * @param emergencyId emergency id
     * @return detail of exist emergency
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.AUTHENTICATED)
    @GetMapping(EndPoint.Emergency.DETAIL)
    public ResponseEntity<IBaseResponse> findEmergencyDetail(
            @PathVariable int emergencyId
    ) {
        return new ResponseEntity<>(
                emergencyFindDetailService.execute(new EmergencyFindDetailRequest().setEmergencyId(emergencyId)),
                HttpStatus.OK
        );
    }
}
