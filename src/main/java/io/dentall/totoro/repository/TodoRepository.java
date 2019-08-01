package io.dentall.totoro.repository;

import io.dentall.totoro.domain.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Todo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TodoRepository extends JpaRepository<Todo, Long>, JpaSpecificationExecutor<Todo> {

    @Query(value = "select distinct todo from Todo todo left join fetch todo.treatmentProcedures",
        countQuery = "select count(distinct todo) from Todo todo")
    Page<Todo> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct todo from Todo todo left join fetch todo.treatmentProcedures")
    List<Todo> findAllWithEagerRelationships();

    @Query("select todo from Todo todo left join fetch todo.treatmentProcedures where todo.id =:id")
    Optional<Todo> findOneWithEagerRelationships(@Param("id") Long id);

}
