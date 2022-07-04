package ru.gbf.sugar.sugar.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.gbf.sugar.sugar.dto.SugarFilter;
import ru.gbf.sugar.sugar.entity.Sugar;
import ru.gbf.sugar.sugar.repository.SugarRepository;
import ru.gbf.sugar.sugar.repository.SugarSpecification;

import java.util.List;


@Service
@AllArgsConstructor
public class SugarService {
    private final SugarRepository sugarRepository;

    public Sugar save(Sugar sugar) {
        return sugarRepository.save(sugar);
    }

    public List<Sugar> getAll() {
        return (List<Sugar>) sugarRepository.findAll();
    }

    public List<Sugar> getAllWithFilter(SugarFilter sugarFilter) {
        return sugarRepository.findAll(
                new SugarSpecification(sugarFilter),
                PageRequest.of(
                        sugarFilter.getPagination().getPage(),
                        sugarFilter.getPagination().getSize(),
                        Sort.by(Sort.Order.asc("name"))
                )
        ).getContent();
    }
}
