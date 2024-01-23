package edu.tanks.repository;

import edu.tanks.model.Result;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class ResultsRepository {
    private final JdbcTemplate jdbcTemplate;

    public ResultsRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void save(Result result) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("results")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> params = new HashMap<>();
        params.put("p1_shots", result.getP1Shots());
        params.put("p1_hits", result.getP1Hits());
        params.put("p1_misses", result.getP1Misses());
        params.put("p2_shots", result.getP2Shots());
        params.put("p2_hits", result.getP2Hits());
        params.put("p2_misses", result.getP2Misses());
        params.put("date_time", result.getDateTime());
        Number generatedId = simpleJdbcInsert.executeAndReturnKey(params);
        result.setId(generatedId.longValue());
    }
}
