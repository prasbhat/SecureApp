package com.ppb.secure.repository;

import com.ppb.secure.model.Categories;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends PagingAndSortingRepository<Categories, Integer> {

    Categories findByCategoryName(String categoryName);

}
