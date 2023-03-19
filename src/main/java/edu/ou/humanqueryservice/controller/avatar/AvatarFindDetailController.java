package edu.ou.humanqueryservice.controller.avatar;

import edu.ou.coreservice.common.constant.SecurityPermission;
import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import edu.ou.coreservice.data.pojo.response.base.IBaseResponse;
import edu.ou.coreservice.service.base.IBaseService;
import edu.ou.humanqueryservice.common.constant.EndPoint;
import edu.ou.humanqueryservice.data.pojo.request.avatar.AvatarFindDetailRequest;
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
@RequestMapping(EndPoint.Avatar.BASE)
public class AvatarFindDetailController {
    private final IBaseService<IBaseRequest, IBaseResponse> avatarFindDetailService;

    /**
     * find detail of exist avatar
     *
     * @param avatarId avatar id
     * @return detail of exist avatar
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.AUTHENTICATED)
    @GetMapping(EndPoint.Avatar.DETAIL)
    public ResponseEntity<IBaseResponse> findAvatarDetail(
            @PathVariable int avatarId
    ) {
        return new ResponseEntity<>(
                avatarFindDetailService.execute(new AvatarFindDetailRequest().setAvatarId(avatarId)),
                HttpStatus.OK
        );
    }
}
