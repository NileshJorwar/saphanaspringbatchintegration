package com.sap.batch.saphanaspringbatchintegration.repositories;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.batch.item.database.support.DataFieldMaxValueIncrementerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.jdbc.support.incrementer.HanaSequenceMaxValueIncrementer;

import javax.sql.DataSource;

public class DataFieldMaxValueIncrementerFactoryImpl implements DataFieldMaxValueIncrementerFactory {

    HikariDataSource sapHanaDatasource;
    private String columnNameForIncrement = "ID";
    private String[] supportedDbTypes = {"HDB"};

    public DataFieldMaxValueIncrementerFactoryImpl(final HikariDataSource sapHanaDatasource) {
        this.sapHanaDatasource = sapHanaDatasource;
    }

    @Override
    public DataFieldMaxValueIncrementer getIncrementer(String databaseType, String incName) {
       if(databaseType == "HDB") {
            return new SapHanaSequenceMaxValueIncrementer(sapHanaDatasource, incName);
        }
        throw new IllegalArgumentException("Database type not provided");
    }

    public void setIncrementerColumnName(String columnNameForIncrement) {
        this.columnNameForIncrement = columnNameForIncrement;
    }
    @Override
    public boolean isSupportedIncrementerType(String databaseType) {
        if((databaseType == "HDB")) {
            return true;
        }
        return false;
    }

    @Override
    public String[] getSupportedIncrementerTypes() {
        return supportedDbTypes;
    }
}
