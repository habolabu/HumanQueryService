package edu.ou.humanqueryservice.controller.emergency;

import edu.ou.coreservice.common.constant.SecurityPermission;
import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import edu.ou.coreservice.data.pojo.response.base.IBaseResponse;
import edu.ou.coreservice.service.base.IBaseService;
import edu.ou.humanqueryservice.common.constant.EndPoint;
import edu.ou.humanqueryservice.data.pojo.request.emergency.EmergencyFindAllRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndPoint.Emergency.BASE)
public class EmergencyFindAllController {
    private final IBaseService<IBaseRequest, IBaseResponse> emergencyFindAllService;


    /**
     * Find all emergency of user
     *
     * @param userId user id
     * @param page   page index
     * @return list emergency of user
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.VIEW_EMERGENCY)
    @GetMapping(EndPoint.Emergency.ALL)
    public ResponseEntity<IBaseResponse> findAllEmergency(
            @PathVariable int userId,
            @RequestParam(required = false, defaultValue = "1") int page
    ) {
        return new ResponseEntity<>(
                emergencyFindAllService.execute(
                        new EmergencyFindAllRequest()
                                .setUserId(userId)
                                .setPage(page)
                ),
                HttpStatus.OK
        );
    }
}
