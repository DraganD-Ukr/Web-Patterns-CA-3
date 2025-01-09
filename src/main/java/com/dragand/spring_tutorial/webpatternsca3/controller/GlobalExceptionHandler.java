package com.dragand.spring_tutorial.webpatternsca3.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Handles NullPointerException
     * @param model Model
     * @param ex NullPointerException
     * @return error page
     */
    @ExceptionHandler(value = NullPointerException.class)
    public String nullPointerHandler(Model model, NullPointerException ex) {
        log.error("NullPointerException occurred: ", ex);
        model.addAttribute("errType", "NullPointerException");
        model.addAttribute("errMsg", ex.getMessage());
        return "error";
    }

    /**
     * Handles any exception
     * @param model Model
     * @param ex Exception
     * @return error page
     */
    //Global exception handler (for all exceptions including unexpected ones)
    @ExceptionHandler(value = Exception.class)
    public String allOtherExceptionHandler(Model model, Exception ex) {
        log.error("An exception occurred: ", ex);
        model.addAttribute("errType", ex.getClass());
        model.addAttribute("errMsg", ex.getMessage());
        return "error";
    }





}
