package edu.ou.humanqueryservice.controller.avatar;

import edu.ou.coreservice.common.constant.SecurityPermission;
import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import edu.ou.coreservice.data.pojo.response.base.IBaseResponse;
import edu.ou.coreservice.service.base.IBaseService;
import edu.ou.humanqueryservice.common.constant.EndPoint;
import edu.ou.humanqueryservice.data.pojo.request.avatar.AvatarFindAllRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndPoint.Avatar.BASE)
public class AvatarFindAllController {
    private final IBaseService<IBaseRequest, IBaseResponse> avatarFindAllService;


    /**
     * Find all avatar of user
     *
     * @param userId user id
     * @param page   page index
     * @return list avatar of user
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.VIEW_AVATAR)
    @GetMapping(EndPoint.Avatar.ALL)
    public ResponseEntity<IBaseResponse> findAllAvatar(
            @PathVariable int userId,
            @RequestParam(required = false, defaultValue = "1") int page
    ) {
        return new ResponseEntity<>(
                avatarFindAllService.execute(
                        new AvatarFindAllRequest()
                                .setUserId(userId)
                                .setPage(page)
                ),
                HttpStatus.OK
        );
    }
}
