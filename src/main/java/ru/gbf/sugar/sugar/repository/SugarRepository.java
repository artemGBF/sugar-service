package ru.gbf.sugar.sugar.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.gbf.sugar.sugar.entity.Sugar;

import java.util.List;

public interface SugarRepository extends PagingAndSortingRepository<Sugar, Long> {
    List<Sugar> findAllByName(String name, Pageable pageable);

    List<Sugar> findAllByColor(String color, Pageable pageable);

    List<Sugar> findAllByNameAndColor(String name, String color, Pageable pageable);
}
