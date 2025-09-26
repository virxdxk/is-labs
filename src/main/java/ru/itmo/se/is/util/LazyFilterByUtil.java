package ru.itmo.se.is.util;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.UriInfo;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class LazyFilterByUtil {

    private final Set<String> excludedParams = Set.of("first", "pageSize", "sortField", "sortOrder");

    public Map<String, String> extractFilterByFromUriInfo(UriInfo uriInfo) {
        return uriInfo.getQueryParameters()
                .keySet().stream()
                .filter(k -> !excludedParams.contains(k))
                .collect(Collectors.toMap(
                        k -> k,
                        k -> uriInfo.getQueryParameters().getFirst(k)
                ));
    }
}
