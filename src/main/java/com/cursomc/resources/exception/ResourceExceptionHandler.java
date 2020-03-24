package com.cursomc.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.cursomc.service.exceptions.AuthorizationException;
import com.cursomc.service.exceptions.DataIntegrationException;
import com.cursomc.service.exceptions.FileException;
import com.cursomc.service.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
		
		StandardError erro = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	
	@ExceptionHandler(DataIntegrationException.class)
	public ResponseEntity<StandardError> dataIntegrationException(DataIntegrationException e, HttpServletRequest request){
		StandardError standardError = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request){
		ValidationError validationError = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de validação", System.currentTimeMillis());
		
		
		//(e.getBindingResult().getFieldErrors) Acessa todos os erros da exceção MethodArgumentNotValidException
		//x.getField() Pega o nome do campo
		//x.getDefaultMessage() Pega a mensagem
		for (FieldError x :  e.getBindingResult().getFieldErrors()) {
			validationError.addError(x.getField(), x.getDefaultMessage());
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
	}
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> authorization(AuthorizationException e, HttpServletRequest request){
		
		StandardError erro = new StandardError(HttpStatus.FORBIDDEN.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(erro);
	}
	
	@ExceptionHandler(FileException.class)
	public ResponseEntity<StandardError> fileException(FileException e, HttpServletRequest request){
		
		StandardError erro = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(AmazonServiceException.class)
	public ResponseEntity<StandardError> amazonServiceException(AmazonServiceException e, HttpServletRequest request){
		
		HttpStatus code = HttpStatus.valueOf(e.getErrorCode());
		StandardError erro = new StandardError(code.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(code).body(erro);
	}
	
	@ExceptionHandler(AmazonClientException.class)
	public ResponseEntity<StandardError> amazonClient(AmazonClientException e, HttpServletRequest request){
		
		StandardError erro = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(AmazonS3Exception.class)
	public ResponseEntity<StandardError> amazonS3(AmazonS3Exception e, HttpServletRequest request){
		
		StandardError erro = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	
}
