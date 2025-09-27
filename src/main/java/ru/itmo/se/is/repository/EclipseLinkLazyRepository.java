package ru.itmo.se.is.repository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.eclipse.persistence.expressions.Expression;
import org.eclipse.persistence.expressions.ExpressionBuilder;
import org.eclipse.persistence.queries.ReadAllQuery;
import org.eclipse.persistence.queries.ReportQuery;
import org.eclipse.persistence.sessions.DatabaseSession;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
public abstract class EclipseLinkLazyRepository<T> implements LazyRepository<T> {

    private Class<T> entityClass;
    private DatabaseSession session;

    @Override
    public long count(Map<String, String> filterBy) {
        ExpressionBuilder builder = new ExpressionBuilder();
        Expression expr = buildFilters(builder, filterBy);

        ReportQuery query = new ReportQuery(entityClass, builder);
        query.addCount();
        query.setShouldReturnSingleValue(true);
        if (expr != null) {
            query.setSelectionCriteria(expr);
        }

        Number count = (Number) session.executeQuery(query);
        return count.longValue();
    }

    @Override
    public List<T> load(int first, int pageSize, String sortField, int sortOrder, Map<String, String> filterBy) {
        ExpressionBuilder builder = new ExpressionBuilder();
        Expression expr = buildFilters(builder, filterBy);

        ReadAllQuery query = new ReadAllQuery(entityClass);
        if (expr != null) {
            query.setSelectionCriteria(expr);
        }

        if (sortField != null && !sortField.isEmpty()) {
            Expression sortExpr = builder.get(sortField);
            query.addOrdering(sortOrder == -1 ? sortExpr.descending() : sortExpr.ascending());
        }

        query.setFirstResult(first);
        query.setMaxRows(pageSize);

        return (List<T>) session.executeQuery(query);
    }

    private Expression buildFilters(ExpressionBuilder builder, Map<String, String> filterBy) {
        Expression expr = null;
        boolean hasInvalidEnums = false;
        for (Map.Entry<String, String> entry : filterBy.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            Class<?> fieldType = session.getDescriptor(entityClass)
                    .getMappingForAttributeName(key)
                    .getAttributeClassification();

            if (fieldType.isEnum()) {
                try {
                    Object enumValue = Enum.valueOf((Class<? extends Enum>) fieldType, value.toUpperCase());
                    Expression condition = builder.get(key).equal(enumValue);
                    expr = (expr == null) ? condition : expr.and(condition);
                } catch (IllegalArgumentException ignored) {
                    hasInvalidEnums = true;
                    break;
                }
            } else {
                Expression condition = builder.get(key).equal(value);
                expr = (expr == null) ? condition : expr.and(condition);
            }
        }
        if (hasInvalidEnums) {
            expr = builder.value(1).equal(0);
        }
        return expr;
    }
}

