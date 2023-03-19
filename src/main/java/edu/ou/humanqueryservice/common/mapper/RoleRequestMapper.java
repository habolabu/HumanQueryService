package edu.ou.humanqueryservice.common.mapper;

import edu.ou.humanqueryservice.data.pojo.request.role.RoleRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Map;

@Mapper
public interface RoleRequestMapper {
    RoleRequestMapper INSTANCE = Mappers.getMapper(RoleRequestMapper.class);

    /**
     * Map HashMap<String, String> object to RoleDocument object
     *
     * @param map represents for RoleDocument object
     * @return RoleRequest object
     * @author Nguyen Trung Kien - OU
     */
    @Mapping(target = "roleId", source = "id", qualifiedByName = "objectToInt")
    @Mapping(target = "roleName", source = "display", qualifiedByName = "objectToString")
    RoleRequest fromMap(Map<String, Object> map);

    /**
     * Convert object to String
     *
     * @param object object will be converted
     * @return String object
     * @author Nguyen Trung Kien - OU
     */
    @Named("objectToString")
    static String objectToString(Object object) {
        return (String) object;
    }

    /**
     * Convert object to int
     *
     * @param object object will be converted
     * @return int value
     * @author Nguyen Trung Kien - OU
     */
    @Named("objectToInt")
    static int objectToInt(Object object) {
        return (int) object;
    }
}
