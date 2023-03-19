package edu.ou.humanqueryservice.controller.user;

import edu.ou.coreservice.common.constant.SecurityPermission;
import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import edu.ou.coreservice.data.pojo.response.base.IBaseResponse;
import edu.ou.coreservice.service.base.IBaseService;
import edu.ou.humanqueryservice.common.constant.EndPoint;
import edu.ou.humanqueryservice.data.pojo.request.user.UserFindDetailRequest;
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
@RequestMapping(EndPoint.User.BASE)
public class UserFindDetailController {
    private final IBaseService<IBaseRequest, IBaseResponse> userFindDetailService;

    /**
     * Find user detail
     *
     * @param userId user id
     * @return user detail
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.VIEW_USER)
    @GetMapping(EndPoint.User.DETAIL)
    public ResponseEntity<IBaseResponse> findDetailUser(
            @PathVariable int userId
    ) {
        return new ResponseEntity<>(
                userFindDetailService.execute(new UserFindDetailRequest().setUserId(userId)),
                HttpStatus.OK
        );
    }
}
