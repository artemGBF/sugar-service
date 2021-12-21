package ru.gbf.sugar.sugar.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.gbf.sugar.sugar.dto.SugarFilter;
import ru.gbf.sugar.sugar.entity.Sugar;
import ru.gbf.sugar.sugar.repository.SugarRepository;

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
        PageRequest request = PageRequest.of(sugarFilter.getPage(), sugarFilter.getSize());
        if (sugarFilter.getName() != null) {
            if (sugarFilter.getColor() != null) {
                return sugarRepository.findAllByNameAndColor(
                        sugarFilter.getName(),
                        sugarFilter.getColor(),
                        request,
                        Sort.by(Sort.Order.asc("name"))
                );
            }
            return sugarRepository.findAllByName(
                    sugarFilter.getName(),
                    request,
                    Sort.by(Sort.Order.asc("name"))
            );
        }
        if (sugarFilter.getColor() != null) {
            return sugarRepository.findAllByColor(
                    sugarFilter.getColor(),
                    request,
                    Sort.by(Sort.Order.asc("name"))
            );
        }
        return null;
    }
}
