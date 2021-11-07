package com.sap.batch.saphanaspringbatchintegration.repositories;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class SequenceGenerationImpl implements ISequenceGeneration{
    private final static String SEQUENCE_STMT = "select SEQ_NAME.nextval from dummy"; // from dummy syntax used by SAP Hana
    private final JdbcTemplate jdbcTemplate;

    public SequenceGenerationImpl(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long getNextSequenceForDb(String seq) {
        String stmt = SEQUENCE_STMT.replace("SEQ_NAME", seq);
        return jdbcTemplate.queryForObject(stmt, Long.class);
    }
}
