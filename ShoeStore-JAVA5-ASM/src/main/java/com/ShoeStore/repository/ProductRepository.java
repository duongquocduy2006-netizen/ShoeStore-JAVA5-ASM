package com.ShoeStore.repository;

import com.ShoeStore.model.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // Ép Hibernate lấy luôn Category, Variants và Images trong 1 câu Query
	@EntityGraph(attributePaths = {"category", "variants", "images"})
	List<Product> findAll();
	}