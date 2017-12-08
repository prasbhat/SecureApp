package com.ppb.secure.repository;

import com.ppb.secure.model.PersonalInfo;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonalInfoRepository extends PagingAndSortingRepository<PersonalInfo, Integer> {
    List<PersonalInfo> findByShortNameContaining(String shortName);
    List<PersonalInfo> findByApplicationNameContaining(String applicationName);
    List<PersonalInfo> findByCategories_systemCategoriesId(int systemCategoriesId);
}
