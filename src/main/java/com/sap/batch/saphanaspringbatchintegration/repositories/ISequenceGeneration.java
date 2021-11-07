package com.sap.batch.saphanaspringbatchintegration.repositories;

// For generating DB sequences
public interface ISequenceGeneration {
    Long getNextSequenceForDb(String seq);
}
