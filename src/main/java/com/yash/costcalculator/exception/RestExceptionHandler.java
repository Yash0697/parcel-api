package com.yash.costcalculator.exception;

import static com.yash.costcalculator.util.AppConstants.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.yash.costcalculator.model.ApiResponse;


@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

	@ExceptionHandler(InsufficientValuesProvidedException.class)
	public ResponseEntity<ApiResponse> handleInsufficientValuesProvidedException(InsufficientValuesProvidedException ex, WebRequest request) {
		logger.error("insufficient values provided in the request");
		ApiResponse apiRes = new ApiResponse();
		apiRes.setError(true);
		apiRes.setMessage(BAD_REQUEST_MSG);
		apiRes.setStatusCode(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(apiRes, HttpStatus.BAD_REQUEST);
	}
		
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse> handleCommonException(Exception ex, WebRequest request) {
		logger.error("error occurred. sending error response");
		ApiResponse apiRes = new ApiResponse();
		apiRes.setError(true);
		apiRes.setMessage(DEFAULT_EXCEPTION_MSG);
		apiRes.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<>(apiRes, HttpStatus.BAD_REQUEST);
	}
}
