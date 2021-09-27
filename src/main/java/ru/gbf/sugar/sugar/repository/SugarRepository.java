package ru.gbf.sugar.sugar.repository;

import org.springframework.data.repository.CrudRepository;
import ru.gbf.sugar.sugar.entity.Sugar;

import java.util.ArrayList;

public interface SugarRepository extends CrudRepository<ru.gbf.sugar.sugar.entity.Sugar, String> {
     Sugar save(Sugar sugar);
     ArrayList<Sugar> getSugarBySynchronizationflag(Boolean s);
    Sugar getSugarByName(String name);
    Sugar findSugarByColorAndFormAndName(String color,String form,String name);
    void deleteSugarByColorAndFormAndName(String color,String form,String name);
}
