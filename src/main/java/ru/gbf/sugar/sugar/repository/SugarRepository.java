package ru.gbf.sugar.sugar.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.gbf.sugar.sugar.entity.Sugar;

public interface SugarRepository extends PagingAndSortingRepository<Sugar, Long>, JpaSpecificationExecutor<Sugar> {
}
