package ru.itmo.se.is.repository;

import java.util.List;
import java.util.Map;

public interface LazyRepository<T> {
    long count(Map<String, Object> filterBy);

    List<T> load(int first, int pageSize, String sortField, int sortOrder, Map<String, Object> filterBy);
}
