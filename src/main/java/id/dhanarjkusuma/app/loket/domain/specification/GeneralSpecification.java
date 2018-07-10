package id.dhanarjkusuma.app.loket.domain.specification;


import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GeneralSpecification<T> implements Specification<T> {

    private SearchCriteria criteria;

    public GeneralSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        String ops = criteria.getOperation().toLowerCase();
        switch (ops) {
            case ">":
                return toPredicateGreaterThan(root, builder);
            case "<":
                return toPredicateLessThan(root, builder);
            case ":":
                return toPredicateSemiColon(root, builder);
            case "like":
                return toPredicateLike(root, builder);
            case ">=":
                return toPredicateGreaterThanOrEqual(root, builder);
            case "<=":
                return toPredicateLessThanOrEqual(root, builder);
            case "between":
                return toPredicateBetween(root, builder);
            default:
                return null;
        }
    }

    @SuppressWarnings("unchecked")
    private Expression getExpression(Root<T> root) {
        if (criteria.getKey().contains(".")) {
            String[] classes = criteria.getKey().split("\\.");
            Path path = root.get(classes[0]);
            for (int i = 1; i < classes.length; i++) {
                path = path.get(classes[i]);
            }
            return path;
        } else {
            return root.get(criteria.getKey());
        }
    }

    // Predicate : ;
    private Predicate toPredicateSemiColon(Root<T> root, CriteriaBuilder builder) {
        Expression expression = getExpression(root);
        if (expression.getJavaType().equals(java.util.Date.class)) {
            Boolean isDateString = criteria.getValue() instanceof String;
            return builder.equal(
                    getExpression(root),
                    (isDateString) ? toDate(criteria.getValue().toString()) : (Date) criteria.getValue()
            );
        } else if (expression.getJavaType() instanceof Class && ((Class<?>) expression.getJavaType()).isEnum()) {
            return builder.equal(
                    getExpression(root),
                    criteria.getValue()
            );
        } else if (expression.getJavaType().equals(boolean.class) || expression.getJavaType().equals(java.lang.Boolean.class)) {
            return builder.equal(
                    getExpression(root),
                    criteria.getValue()
            );
        }
        return builder.equal(
                getExpression(root),
                String.valueOf(criteria.getValue())
        );
    }

    // Predicate LIKE ;
    @SuppressWarnings("unchecked")
    private Predicate toPredicateLike(Root<T> root, CriteriaBuilder builder) {
        Expression expression = getExpression(root);
        if (expression.getJavaType().equals(java.util.Date.class)) {
            Boolean isDateString = criteria.getValue() instanceof String;
            return builder.like(
                    getExpression(root),
                    "%" + String.valueOf(criteria.getValue()) + "%"
            );
        } else if (expression.getJavaType() instanceof Class && ((Class<?>) expression.getJavaType()).isEnum()) {
            return builder.like(
                    getExpression(root),
                    "%" + String.valueOf(criteria.getValue()) + "%"
            );
        } else if (expression.getJavaType().equals(boolean.class) || expression.getJavaType().equals(java.lang.Boolean.class)) {
            return builder.like(
                    getExpression(root),
                    "%" + String.valueOf(criteria.getValue()) + "%"
            );
        }
        return builder.like(
                getExpression(root),
                "%" + criteria.getValue() + "%"
        );
    }

    // Predicate < value ;
    @SuppressWarnings("unchecked")
    private Predicate toPredicateLessThan(Root<T> root, CriteriaBuilder builder) {
        Expression expression = getExpression(root);
        if (expression.getJavaType().equals(java.util.Date.class)) {
            if (criteria.getValue() instanceof String) {
                return builder.equal(
                        getExpression(root),
                        toDate(criteria.getValue().toString())
                );
            }
            return builder.lessThan(
                    expression,
                    (Date) criteria.getValue()
            );
        }
        return builder.lessThan(
                expression,
                criteria.getValue().toString()
        );
    }

    // Predicate > value ;
    @SuppressWarnings("unchecked")
    private Predicate toPredicateGreaterThan(Root<T> root, CriteriaBuilder builder) {
        Expression expression = getExpression(root);
        if (expression.getJavaType().equals(java.util.Date.class)) {
            if (criteria.getValue() instanceof String) {
                return builder.equal(
                        getExpression(root),
                        toDate(criteria.getValue().toString())
                );
            }
            return builder.greaterThan(
                    expression,
                    (Date) criteria.getValue()
            );
        }
        return builder.greaterThan(
                expression,
                criteria.getValue().toString()
        );
    }

    // Predicate >= value ;
    @SuppressWarnings("unchecked")
    private Predicate toPredicateGreaterThanOrEqual(Root<T> root, CriteriaBuilder builder) {
        Expression expression = getExpression(root);
        if (expression.getJavaType().equals(java.util.Date.class)) {
            if (criteria.getValue() instanceof String) {
                return builder.greaterThanOrEqualTo(
                        expression,
                        toDate(criteria.getValue().toString())
                );
            }
            return builder.greaterThanOrEqualTo(
                    expression,
                    (Date) criteria.getValue()
            );
        }
        return builder.greaterThanOrEqualTo(
                expression,
                criteria.getValue().toString()
        );
    }

    // Predicate <= value ;
    @SuppressWarnings("unchecked")
    private Predicate toPredicateLessThanOrEqual(Root<T> root, CriteriaBuilder builder) {
        Expression expression = getExpression(root);
        if (expression.getJavaType().equals(java.util.Date.class)) {
            if (criteria.getValue() instanceof String) {
                return builder.lessThanOrEqualTo(
                        expression,
                        toDate(criteria.getValue().toString())
                );
            }
            return builder.lessThanOrEqualTo(
                    expression,
                    (Date) criteria.getValue()
            );
        }
        return builder.greaterThanOrEqualTo(
                expression,
                criteria.getValue().toString()
        );
    }

    // Predicate Between
    private String[] getBetweenValues() {
        String[] values = criteria.getValue().toString().trim().split(":");
        if (values.length != 2) {
            throw new InvalidCriteriaOperator(" Operator between need 2 parameter value. ");
        }
        return values;
    }

    @SuppressWarnings("unchecked")
    private Predicate toPredicateBetween(Root<T> root, CriteriaBuilder builder) {
        Expression expression = getExpression(root);
        String[] values = getBetweenValues();
        if (expression.getJavaType().equals(java.util.Date.class)) {
            return builder.between(
                    expression,
                    toDate(values[0]),
                    toDate(values[1])
            );
        }
        return builder.between(
                expression,
                values[0],
                values[1]
        );
    }

    @SuppressWarnings("unchecked")
    private Date toDate(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    static class InvalidCriteriaOperator extends RuntimeException {
        public InvalidCriteriaOperator(String reason) {
            super("Invalid Criteria Operator : " + reason);
        }
    }

}
