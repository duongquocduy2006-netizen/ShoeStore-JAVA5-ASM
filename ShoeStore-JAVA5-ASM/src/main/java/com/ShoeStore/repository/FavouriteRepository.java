package com.ShoeStore.repository;

import com.ShoeStore.model.Account;
import com.ShoeStore.model.Favourite;
import com.ShoeStore.model.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
    @EntityGraph(attributePaths = { "product", "product.images", "product.variants" })
    List<Favourite> findByAccount(Account account);

    Optional<Favourite> findByAccountAndProduct(Account account, Product product);

    void deleteByAccountAndProduct(Account account, Product product);

    boolean existsByAccountAndProduct(Account account, Product product);
}
