package com.dragand.spring_tutorial.webpatternsca3.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;

@Controller
public class LangController {


    @GetMapping("/changeLanguage")
    public String changeLanguage(@RequestParam("lang") String lang,
                                 HttpServletRequest request)
    {

        Locale locale = new Locale(lang);
        LocaleContextHolder.setLocale(locale);
        return "redirect:" + request.getHeader("Referer");
    }


}
