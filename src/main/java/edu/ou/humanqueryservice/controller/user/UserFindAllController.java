package edu.ou.humanqueryservice.controller.user;

import edu.ou.coreservice.common.constant.SecurityPermission;
import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import edu.ou.coreservice.data.pojo.response.base.IBaseResponse;
import edu.ou.coreservice.service.base.IBaseService;
import edu.ou.humanqueryservice.common.constant.EndPoint;
import edu.ou.humanqueryservice.data.pojo.request.user.UserFindAllRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndPoint.User.BASE)
public class UserFindAllController {
    private final IBaseService<IBaseRequest, IBaseResponse> userFindAllService;

    /**
     * Find all user
     *
     * @param page         page index
     * @param firstName    first name
     * @param lastName     last name
     * @param address      address
     * @param idCard       id card
     * @param phoneNumber  phone number
     * @param email        email
     * @param nationality  nationality
     * @param bDateOfBirth date of birth begin range
     * @param eDateOfBirth date of birth end range
     * @return list of user
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.VIEW_USER)
    @GetMapping()
    public ResponseEntity<IBaseResponse> findAllUser(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String idCard,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String nationality,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            Date bDateOfBirth,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(required = false)
            Date eDateOfBirth
    ) {
        return new ResponseEntity<>(
                userFindAllService.execute(
                        new UserFindAllRequest()
                                .setPage(page)
                                .setFirstName(firstName)
                                .setLastName(lastName)
                                .setAddress(address)
                                .setIdCard(idCard)
                                .setPhoneNumber(phoneNumber)
                                .setEmail(email)
                                .setNationality(nationality)
                                .setBDateOfBirth(bDateOfBirth)
                                .setEDateOfBirth(eDateOfBirth)
                ),
                HttpStatus.OK
        );
    }
}
