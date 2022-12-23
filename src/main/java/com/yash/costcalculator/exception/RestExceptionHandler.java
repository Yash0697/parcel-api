package com.yash.costcalculator.exception;

import static com.yash.costcalculator.util.AppConstants.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.yash.costcalculator.model.ApiResponse;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


//@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

	@ExceptionHandler(InsufficientValuesProvidedException.class)
	public ResponseEntity<ApiResponse> handleInsufficientValuesProvidedException(InsufficientValuesProvidedException ex, WebRequest request) {
		logger.error("insufficient values provided in the request");
		ResponseEntity<Object> validationResult = handleValidation(ex, ex.getResult(), request);
		@SuppressWarnings("unchecked")
		Map<String, Object> body = (HashMap<String, Object>)validationResult.getBody();
		ApiResponse apiRes = new ApiResponse(HttpStatus.BAD_REQUEST, body);
		apiRes.setError(true);
		apiRes.setResponseDate(new Date());
		apiRes.setMessage(BAD_REQUEST_MSG);
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
	
	private ResponseEntity<Object> handleValidation(InsufficientValuesProvidedException ex, BindingResult result,
			WebRequest request) {
		List<FieldError> fieldErrors = result.getFieldErrors();
		ValidationError validationError = processFieldErrors(fieldErrors);
		Map<String, Object> errorPayload = new HashMap<String, Object>();
		
		for(Entry<String, Set<String>> error: validationError.getErrors().entrySet()) {
			for (String errorValue: error.getValue()) {
				if(errorValue.startsWith(NotNull.class.getSimpleName())
						|| errorValue.startsWith(NotBlank.class.getSimpleName())
						) {
					validationError.addMandatoryField(error.getKey());
				}
			}
		}
		
		errorPayload.put("errors", validationError);
		
		return handleExceptionInternal(ex, errorPayload, new HttpHeaders(), HttpStatus.BAD_GATEWAY, request);
	}

	private ValidationError processFieldErrors(List<FieldError> fieldErrors) {
		ValidationError validationError = ValidationError.getInstance();
		for(FieldError fieldError: fieldErrors) {
			validationError.addError(fieldError.getField(), fieldError.getDefaultMessage());
		}
		return validationError;
	}
}
