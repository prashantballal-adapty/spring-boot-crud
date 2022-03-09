package com.prashantballal.springbootcomplete.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

//import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import com.prashantballal.springbootcomplete.modal.Person;

@Repository("postgres")
public class PersonDataAccessService implements PersonDao {
	
	private final NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public PersonDataAccessService(NamedParameterJdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int insertPerson(UUID id, Person person) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
	    paramMap.put("id", id);
	    paramMap.put("name", person.getName());
		String sql = "" +
                "INSERT INTO person (" +
                " id, " +
                " name) " +
                "VALUES (:id, :name)";
        return jdbcTemplate.update(
                sql,
                paramMap
        );
//        return 0;
	}

//	@Override
//	public List<Person> selectAllPeaople() {
//		final String sql = "select id, name from person";
//		jdbcTemplate.query(sql, (resultSet,i) -> {
//			UUID id = UUID.fromString(resultSet.getString("id"));
//			String name = resultSet.getString("name");
//			return new Person(id, name);
//		});
		
//		return List.of(new Person(UUID.randomUUID(), "from postgres db"));
		
//		String sql = "" +
//	                "SELECT " +	               
//	                " id, " +
//	                " name " +
//	                "FROM person";
//
//	        return jdbcTemplate.query(sql, mapStudentFomDb());
//	}
	@Override
	public List<Person> selectAllPeaople() {
        String sql = "" +
                "SELECT " +
                " id, " +
                " name " +
                "FROM person";

        return jdbcTemplate.query(sql, mapPeopleFomDb());
    }
	
	private RowMapper<Person> mapPeopleFomDb() {
        return (resultSet, i) -> {
            String IdStr = resultSet.getString("id");
            UUID id = UUID.fromString(IdStr);

            String name = resultSet.getString("name");
            return new Person(
            		id,
            		name
            );
        };
    }

	@Override
	public Optional<Person> selectPersonById(UUID id) {
		return null;
//		SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
//		String sql = "" +
//                "SELECT * FROM person " +
//                "WHERE id = :id";
//        return jdbcTemplate.query(sql, namedParameters);
	}

	@Override
	public int deletePersonById(UUID id) {
		SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
		String sql = "" +
                "DELETE FROM person " +
                "WHERE id = :id";
        return jdbcTemplate.update(sql, namedParameters);
	}

	@Override
	public int updatePersonById(UUID id, Person person) {
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("name", person.getName()).addValue("id", id);
		String sql = "" +
                "UPDATE person " +
                "SET name = :name " +
                "WHERE id = :id";
        return jdbcTemplate.update(sql, namedParameters);
	}

}
