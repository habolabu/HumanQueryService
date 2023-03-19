package edu.ou.humanqueryservice.controller.parkingDetail;

import edu.ou.coreservice.common.constant.SecurityPermission;
import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import edu.ou.coreservice.data.pojo.response.base.IBaseResponse;
import edu.ou.coreservice.service.base.IBaseService;
import edu.ou.humanqueryservice.common.constant.EndPoint;
import edu.ou.humanqueryservice.data.pojo.request.parkingDetail.ParkingDetailFindAllRequest;
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
@RequestMapping(EndPoint.ParkingDetail.BASE)
public class ParkingDetailFindAllController {
    private final IBaseService<IBaseRequest, IBaseResponse> parkingDetailFindAllService;

    /**
     * Find all parking detail
     *
     * @param parkingId     parking id
     * @param userId        user id
     * @param parkingTypeId parking type id
     * @param page          page index
     * @param bJoinDate     join date begin range
     * @param eJoinDate     join date end range
     * @return list of parking detail
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.VIEW_PARKING_DETAIL)
    @GetMapping()
    public ResponseEntity<IBaseResponse> findAllParkingDetail(
            @RequestParam(required = false) Integer parkingId,
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) Integer parkingTypeId,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            Date bJoinDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(required = false)
            Date eJoinDate
    ) {
        return new ResponseEntity<>(
                parkingDetailFindAllService.execute(
                        new ParkingDetailFindAllRequest()
                                .setParkingId(parkingId)
                                .setUserId(userId)
                                .setParkingTypeId(parkingTypeId)
                                .setPage(page)
                                .setBJoinDate(bJoinDate)
                                .setEJoinDate(eJoinDate)
                ),
                HttpStatus.OK
        );
    }
}
