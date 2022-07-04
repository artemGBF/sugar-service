package ru.gbf.sugar.sugar.repository;

import org.springframework.data.jpa.domain.Specification;
import ru.gbf.sugar.sugar.dto.SugarFilter;
import ru.gbf.sugar.sugar.entity.Sugar;
import ru.gbf.sugar.sugar.entity.SugarForm;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Locale;

public class SugarSpecification implements Specification<Sugar> {
    private final SugarFilter filter;

    public SugarSpecification(SugarFilter filter) {
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<Sugar> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        String color = filter.getColor();
        String name = filter.getName();
        SugarForm form = filter.getForm();

        Predicate predicate = builder.equal(root.get("active"), true);
        if (name != null) {
            predicate = builder.and(
                    predicate,
                    builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase(Locale.ROOT) + "%")
            );
        }
        if (color != null) {
            predicate = builder.and(predicate, builder.equal(root.get("color"), color));
        }
        if (form != null) {
            predicate = builder.and(predicate, builder.equal(root.get("form"), form));
        }

        return predicate;
    }
}
