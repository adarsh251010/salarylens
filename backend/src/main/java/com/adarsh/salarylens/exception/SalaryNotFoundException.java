package com.adarsh.salarylens.exception;

public class SalaryNotFoundException extends RuntimeException {

    public SalaryNotFoundException(Long id) {
        super("Salary record not found with id: " + id);
    }
}