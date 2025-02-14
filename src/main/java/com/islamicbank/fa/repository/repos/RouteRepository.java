package com.islamicbank.fa.repository.repos;

import com.islamicbank.fa.repository.entity.Route;
import com.islamicbank.fa.repository.entity.RoutePK;
import com.islamicbank.fa.repository.entity.vo.RouteView;
import lombok.NonNull;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
@CacheConfig(cacheNames = "travels")
public interface RouteRepository extends CrudRepository<Route, RoutePK> {
    
    @Query(value = """
        SELECT new com.islamicbank.fa.repository.entity.vo.RouteView
            (routePK.source, routePK.destination, price)
        FROM Route
        ORDER BY routePK.source ASC
        """)
    Set<RouteView> getAll();
    
    @Query(value = """
        SELECT sum(price)
        FROM Route
        WHERE routePK IN (:routePKs)
        """)
    BigDecimal getTripCost(Iterable<RoutePK> routePKs);
    
    List<Route> findAllByRoutePKIn(Collection<RoutePK> routePK);
    
    @CacheEvict(key = "#p0.routePK.source + #p0.routePK.destination")
    @Override
    <S extends Route> @NonNull S save(@NonNull S s);
    
    @CacheEvict(allEntries = true)
    @Override
    <S extends Route> @NonNull Iterable<S> saveAll(@NonNull Iterable<S> iterable);
}
