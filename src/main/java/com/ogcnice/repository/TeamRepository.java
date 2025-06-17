package com.ogcnice.repository;

import com.ogcnice.entity.Team;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long>, JpaSpecificationExecutor<Team> {
    @Query("SELECT t FROM Team t LEFT JOIN FETCH t.players WHERE t.id = :id")
    Optional<Team> findByIdWithPlayers(@Param("id") Long id);
    boolean existsByName(String name);
    boolean existsByAcronym(String acronym);
}