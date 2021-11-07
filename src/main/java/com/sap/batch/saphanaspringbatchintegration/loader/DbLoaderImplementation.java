package com.sap.batch.saphanaspringbatchintegration.loader;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import static org.springframework.jdbc.datasource.init.ScriptUtils.*;

/*
* shouldRunScript = true; only once when application ran first time
* shouldRunScript = false; always
* */
@Component
@Slf4j
public class DbLoaderImplementation implements IDbLoader {
    private final String createScripts;
    private final String dropScripts;
    private final HikariDataSource sapHanaDatasource;
    private final boolean shouldRunScript;

    @Autowired
    public DbLoaderImplementation(@Value("${saphana.create.script}") final String createScripts,
                                  @Value("${saphana.drop.script}") final String dropScripts,
                                  @Value("${saphana.run.script}") final boolean shouldRunScript,
                                  final HikariDataSource sapHanaDatasource) {
        this.createScripts = createScripts;
        this.dropScripts = dropScripts;
        this.shouldRunScript = shouldRunScript;
        this.sapHanaDatasource = sapHanaDatasource;
    }

    @Override
    public void readyDatabase() {
        if(shouldRunScript) {
            dropTables();
            createTables();
        }
    }

    private void dropTables() {
        ClassPathResource resource = new ClassPathResource(dropScripts);
        log.info("Dropping tables ... {}", createScripts);
        Connection con = DataSourceUtils.getConnection(sapHanaDatasource);
        ScriptUtils.executeSqlScript(con, new EncodedResource(resource, "UTF-8"), true, true,
                DEFAULT_COMMENT_PREFIX, DEFAULT_STATEMENT_SEPARATOR,
                DEFAULT_BLOCK_COMMENT_START_DELIMITER, DEFAULT_BLOCK_COMMENT_END_DELIMITER);
        DataSourceUtils.releaseConnection(con, sapHanaDatasource);
    }

    private void createTables() {
        ClassPathResource resource = new ClassPathResource(createScripts);
        log.info("Creating tables ... {}", createScripts);
        Connection con = DataSourceUtils.getConnection(sapHanaDatasource);
        ScriptUtils.executeSqlScript(con, new EncodedResource(resource, "UTF-8"));
        DataSourceUtils.releaseConnection(con, sapHanaDatasource);

    }
}
