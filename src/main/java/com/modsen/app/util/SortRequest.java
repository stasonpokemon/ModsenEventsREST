package com.modsen.app.util;

import com.modsen.app.exception.SortParametersNotValidException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * THIS CLASS PROTECTS THE DATABASE FROM SQL INJECTION
 */

@AllArgsConstructor
@Getter
public class SortRequest {

    private Map<String, String> correctSortRequest;

    public static <T> SortRequest by(Class<T> tClass, String[] request) {
        Map<String, String> sortRequestMap = new LinkedHashMap<>();
        if (request.length < 2) {
            throw new SortParametersNotValidException(new StringBuilder("Wrong sort request - ")
                    .append(Arrays.toString(request)
                            .replace("[", "")
                            .replace("]", ""))
                    .toString());
        }
        if (request[0].contains(",")) {
            for (String sortRequest : request) {
                String[] splitSortRequest = sortRequest.split(",");
                checkAndPutSortRequestToMap(tClass, splitSortRequest[0], splitSortRequest[1], sortRequestMap);
            }
        } else {
            checkAndPutSortRequestToMap(tClass, request[0], request[1], sortRequestMap);
        }
        return new SortRequest(sortRequestMap);
    }

    private static <T> void checkAndPutSortRequestToMap(Class<T> tClass, String sortField, String sortDirection, Map<String, String> sortRequestMap) {
        Set<String> fieldsFromClassSet = Arrays.stream(tClass.getDeclaredFields()).map(Field::getName).collect(Collectors.toSet());
        Set<String> directionsSet = Set.of("asc", "desc");
        if (!fieldsFromClassSet.contains(sortField) || !directionsSet.contains(sortDirection)) {
            throw new SortParametersNotValidException(new StringBuilder("Wrong sort request - ")
                    .append(sortField).append(",")
                    .append(sortDirection).toString());
        }
        sortRequestMap.put(sortField, sortDirection);
    }

}
