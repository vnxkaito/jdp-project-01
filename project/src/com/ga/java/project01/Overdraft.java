package com.ga.java.project01;

import java.time.LocalDateTime;
import java.util.Optional;

public abstract class Overdraft {

    String accountId;
    double feeAmount;
    LocalDateTime timestamp;

    public Overdraft(String accountId, double feeAmount, LocalDateTime timestamp){
        this.accountId = accountId;
        this.feeAmount = feeAmount;
        this.timestamp = timestamp;
    }

    public Optional<String> getAccountId(){
        return Optional.ofNullable(accountId);
    }

    public Optional<Double> getFeeAmount(){
        return Optional.of(feeAmount);
    }

    public Optional<LocalDateTime> getTimestamp(){
        return Optional.ofNullable(timestamp);
    }

    protected abstract boolean create();
    protected abstract boolean delete();
    protected abstract boolean update();
}
