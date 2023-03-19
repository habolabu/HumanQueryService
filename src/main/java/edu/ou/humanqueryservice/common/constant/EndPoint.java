package edu.ou.humanqueryservice.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EndPoint {
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Avatar {
        public static final String BASE = "/avatar";
        public static final String ALL = "/user/{userId}";
        public static final String DETAIL = "/{avatarId}";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Emergency {
        public static final String BASE = "/emergency";
        public static final String ALL = "/user/{userId}";
        public static final String DETAIL = "/{emergencyId}";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class User {
        public static final String BASE = "/user";
        public static final String DETAIL = "/{userId}";
        public static final String DETAIL_CURRENT = "/detail";
        public static final String ALL = "/all";
        public static final String LOGO_DETAIL = "/logo";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class ParkingDetail {
        public static final String BASE = "/parking-detail";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class RoomDetail {
        public static final String BASE = "/room-detail";
    }

}
