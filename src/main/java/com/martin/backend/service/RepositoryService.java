package com.martin.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.Getter;

@Service
public class RepositoryService {

	@Autowired
	JdbcTemplate jdbcTemplate;

	private @Getter String query;

	/**
	 * Constructor Looks for the values of ZIPNAME and TABLENAME and inserts them
	 * into query string accordingly.
	 * 
	 * @param columnName
	 * @param tableName
	 */
	public RepositoryService(@Value("${ZIPNAME}") String columnName, @Value("${TABLENAME}") String tableName) {
		/*
		 * TODO I would create default values for these in case these are not supplied
		 * otherwise
		 */
		query = "SELECT " + columnName + " FROM " + tableName;
	}

	/**
	 * Queries the database using the constructed query string
	 * 
	 * @return
	 */
	public String getZip() {
		return jdbcTemplate.queryForObject(query, String.class);
	}
}
