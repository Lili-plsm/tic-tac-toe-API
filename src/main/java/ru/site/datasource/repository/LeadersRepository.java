package ru.site.datasource.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.site.datasource.model.Leader;

public interface LeadersRepository extends CrudRepository<Leader, Long> {

    @Query(value = """
                SELECT *
        FROM (
            SELECT
                ROW_NUMBER() OVER (ORDER BY p1.id ASC) AS id,
                CASE
                    WHEN (SELECT COUNT(*) FROM players p2 
                          WHERE p1.id = p2.id AND (p2.status = 'DRAW' OR p2.status = 'LOSE')) = 0
                    THEN NULL
                    ELSE ROUND(
                        1.0 * (SELECT COUNT(*) FROM players p2 WHERE p1.id = p2.id AND p2.status = 'WINNING')
                        /
                        NULLIF((SELECT COUNT(*) FROM players p2 WHERE p1.id = p2.id AND (p2.status = 'DRAW' OR p2.status = 'LOSE')), 0)
                    , 1)
                END AS win_ratio,
                p1.id AS user_id
            FROM players p1
        ) AS sub
        WHERE win_ratio IS NOT NULL
        ORDER BY win_ratio DESC
        LIMIT :limit
        """, nativeQuery = true)
    List<Leader> findTopPlayers(@Param("limit") int limit);
}
