package ru.itmo.se.is.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.persistence.metamodel.SingularAttribute;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
public abstract class JpaLazyRepository<T> implements LazyRepository<T> {

    private Class<T> entityClass;
    private EntityManager em;

    @Override
    public long count(Map<String, String> filterBy) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<T> root = cq.from(this.entityClass);
        cq = cq.select(cb.count(root));
        this.applyFilters(cb, cq, root, filterBy);
        TypedQuery<Long> query = em.createQuery(cq);
        return query.getSingleResult();
    }

    private void applyFilters(CriteriaBuilder cb, CriteriaQuery<?> cq, Root<T> root, Map<String, String> filterBy) {
        List<Predicate> predicates = new ArrayList<>();
        boolean hasInvalidEnums = false;
        for (Map.Entry<String, String> entry : filterBy.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            SingularAttribute<? super T, ?> attribute =
                    em.getMetamodel().entity(entityClass).getSingularAttribute(key);
            Class<?> attributeType = attribute.getJavaType();
            if (attributeType.isEnum()) {
                try {
                    Object enumValue = Enum.valueOf((Class<? extends Enum>) attributeType, value.toUpperCase());
                    predicates.add(cb.equal(root.get(key), enumValue));
                } catch (IllegalArgumentException ignored) {
                    hasInvalidEnums = true;
                    break;
                }
            } else {
                predicates.add(cb.equal(root.get(key), value));
            }
        }
        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[0])));
        }
        if (hasInvalidEnums) {
            cq.where(cb.equal(cb.literal(1), cb.literal(0)));
        }
    }

    @Override
    public List<T> load(int first, int pageSize, String sortField, int sortOrder, Map<String, String> filterBy) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(this.entityClass);
        Root<T> root = cq.from(this.entityClass);
        cq = cq.select(root);
        this.applyFilters(cb, cq, root, filterBy);
        this.applySort(cb, cq, root, sortField, sortOrder);
        TypedQuery<T> query = em.createQuery(cq);
        query.setFirstResult(first);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }

    private void applySort(CriteriaBuilder cb, CriteriaQuery<T> cq, Root<T> root, String sortField, int sortOrder) {
        if (sortField != null && !sortField.isEmpty()) {
            Path<?> sortPath = root.get(sortField);
            if (sortOrder == -1) {
                cq.orderBy(cb.desc(sortPath));
            } else {
                cq.orderBy(cb.asc(sortPath));
            }
        }
    }
}
