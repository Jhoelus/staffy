package com.praxis.staffy.configuracion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.praxis.staffy.model.dto.RespuestaGeneral;
import com.praxis.staffy.utilerias.MensajesAplicacion;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalException {

	 @ExceptionHandler(MethodArgumentNotValidException.class)
	 @ResponseBody
     public ResponseEntity<RespuestaGeneral<String>> methodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
		 	log.error("Error bad request...");
			
			RespuestaGeneral<String> respuesta =  new RespuestaGeneral<String>();
			respuesta.setCode(MensajesAplicacion.MENSAJE_FALLO.getCodigo());
			respuesta.setMessage(MensajesAplicacion.MENSAJE_FALLO.getMensaje());
			
			List<String> errores = new ArrayList<String>();
			
			for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
				errores.add(fieldError.getField() + " : " + fieldError.getDefaultMessage());
			}
			
			respuesta.setErrors(errores);
			
		 return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
	 }
	 
	 @ExceptionHandler(MissingServletRequestParameterException.class)
	 @ResponseBody
     public ResponseEntity<RespuestaGeneral<String>> handleMethodParameterException(HttpServletRequest request, MethodArgumentNotValidException e) {
		 	log.error("Error bad request...");
			
			RespuestaGeneral<String> respuesta =  new RespuestaGeneral<String>();
			respuesta.setCode(MensajesAplicacion.MENSAJE_FALLO.getCodigo());
			respuesta.setMessage(MensajesAplicacion.MENSAJE_FALLO.getMensaje());
			
//			List<String> errores = new ArrayList<String>();
//			
//			for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
//				errores.add(fieldError.getField() + " : " + fieldError.getDefaultMessage());
//			}
//			
			respuesta.setErrors(Arrays.asList("test"));
			
		 return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
	 }
	 
	@ExceptionHandler(Exception.class)
	@ResponseBody
	protected ResponseEntity<Object> handleMethod(Exception ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		log.error("Error internal ...");

		RespuestaGeneral<String> respuesta =  new RespuestaGeneral<String>();
		respuesta.setCode(MensajesAplicacion.MENSAJE_ERROR.getCodigo());
		respuesta.setMessage(MensajesAplicacion.MENSAJE_ERROR.getMensaje());
		
		log.error(ex.getMessage());
		
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
