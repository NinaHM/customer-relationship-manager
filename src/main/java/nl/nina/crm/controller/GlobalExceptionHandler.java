package nl.nina.crm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(EmptyResultDataAccessException.class)
	public String handleEmptyResultException(EmptyResultDataAccessException e) {
		log.error("Error caught: " + e.getClass() + " - " + e.getMessage());
		return "error/404";
	}

	@ExceptionHandler(Exception.class)
	public String handleException(Exception e) {
		log.error("Error caught: " + e.getClass() + " - " + e.getMessage());
		return "error/error";
	}
}