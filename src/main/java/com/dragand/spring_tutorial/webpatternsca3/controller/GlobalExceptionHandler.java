package com.dragand.spring_tutorial.webpatternsca3.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NullPointerException.class)
    public String nullPointerHandler(Model model) {
        model.addAttribute("err", "NullPointerException");
        return "error";
    }

    //Global exception handler (for all exceptions including unexpected ones)
    @ExceptionHandler(value = Exception.class)
    public String allOtherExceptionHandler(Model model, Exception ex) {
        model.addAttribute("errType", ex.getClass());
        model.addAttribute("errMsg", ex.getMessage());
        return "error";
    }





}
