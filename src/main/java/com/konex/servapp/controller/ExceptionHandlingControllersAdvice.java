package com.konex.servapp.controller;

import com.konex.servapp.Konex24Application;
import com.konex.servapp.protocol.ErrorResponse;
import com.konex.servapp.protocol.ValidationErrorResponse;
import org.omg.CORBA.portable.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kneimad on 04.10.2016.
 */
@ControllerAdvice
public class ExceptionHandlingControllersAdvice {

    private final Logger log = LoggerFactory.getLogger(Konex24Application.class);

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDbConflict(HttpServletRequest req, Exception ex) {
        log.warn("Database conflict on " + formatRequest(req), ex);

        return new ErrorResponse(req.getRequestURI(), "Entity already exists");
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleDbConflict(HttpServletRequest req, AccessDeniedException ex) {
        log.warn("Access denied conflict on " + formatRequest(req));

        return new ErrorResponse(req.getRequestURI(), ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEntityNotFound(HttpServletRequest req, EntityNotFoundException ex) {
        log.warn("Entity not found " + formatRequest(req), ex);
        return new ErrorResponse(req.getRequestURI(), "Entity not found");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleException(HttpServletRequest req, MethodArgumentNotValidException ex) {
        BindingResult bindResult = ex.getBindingResult();

        Map<String, List<String>> errors = new HashMap<String, List<String>>();

        for (FieldError e : bindResult.getFieldErrors()) {
            List<String> fieldErrors = errors.get(e.getField());
            if(fieldErrors == null) {
                fieldErrors = new ArrayList<String>(2);
                errors.put(e.getField(), fieldErrors);
            }

            fieldErrors.add(e.getDefaultMessage());
        }

        for (ObjectError e : bindResult.getGlobalErrors()) {
            List<String> objErrors = errors.get(e.getObjectName());
            if (objErrors == null) {
                objErrors = new ArrayList<String>(2);
                errors.put(e.getObjectName(), objErrors);
            }

            objErrors.add(e.getDefaultMessage());
        }

        log.info("Validation conflicts on " + formatRequest(req), ex);

        return new ValidationErrorResponse(req.getRequestURI(), errors);
    }

    @ExceptionHandler(ApplicationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ErrorResponse handleApplicationException(HttpServletRequest req, ApplicationException ex) {
        log.warn("Application exception occurred on " + formatRequest(req), ex);
        return new ErrorResponse(req.getRequestURI(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnknownException(HttpServletRequest req, Exception ex) {
        log.warn("Exception occurred on " + formatRequest(req), ex);
        return new ErrorResponse(req.getRequestURI(), ex.getMessage());
    }


    private String formatRequest(HttpServletRequest req) {
        return req.getMethod() + ' ' + req.getRequestURI();
    }
}
