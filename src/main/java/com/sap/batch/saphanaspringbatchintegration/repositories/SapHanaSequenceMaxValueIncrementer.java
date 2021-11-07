package com.sap.batch.saphanaspringbatchintegration.repositories;

import org.springframework.jdbc.support.incrementer.AbstractSequenceMaxValueIncrementer;

import javax.sql.DataSource;

//Spring batch incrementer for SAP Hana DB
public class SapHanaSequenceMaxValueIncrementer extends AbstractSequenceMaxValueIncrementer {

    public SapHanaSequenceMaxValueIncrementer() {}

    public SapHanaSequenceMaxValueIncrementer(DataSource dataSource, String incrementerName) {
        super(dataSource, incrementerName);
    }

    @Override
    protected String getSequenceQuery() {
        return "select " + getIncrementerName() + ".nextval from dummy";
    }
}
