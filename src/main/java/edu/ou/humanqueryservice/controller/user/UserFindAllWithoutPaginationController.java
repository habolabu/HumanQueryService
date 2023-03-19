package edu.ou.humanqueryservice.controller.user;

import edu.ou.coreservice.common.constant.SecurityPermission;
import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import edu.ou.coreservice.data.pojo.response.base.IBaseResponse;
import edu.ou.coreservice.service.base.IBaseService;
import edu.ou.humanqueryservice.common.constant.EndPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndPoint.User.BASE)
public class UserFindAllWithoutPaginationController {
    private final IBaseService<IBaseRequest, IBaseResponse> userFindAllWithoutPaginationService;

    /**
     * Find all user without pagination
     *
     * @return list of user
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.VIEW_USER)
    @GetMapping(EndPoint.User.ALL)
    public ResponseEntity<IBaseResponse> findAllUserWithoutPagination() {
        return new ResponseEntity<>(
                userFindAllWithoutPaginationService.execute(null),
                HttpStatus.OK
        );
    }
}
