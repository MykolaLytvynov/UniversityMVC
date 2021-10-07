package ua.com.foxminded.university.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(value = Exception.class)
    public ModelAndView exception(Exception exception, WebRequest request) {
        log.warn(":: [ФСЁ ПРОПАЛО] -> {}", exception.getLocalizedMessage());
        ModelAndView modelAndView = new ModelAndView("exception");
        modelAndView.addObject("errorMessage", exception.getMessage());
        modelAndView.addObject("cause", exception.getCause());
        return modelAndView;
    }

}
