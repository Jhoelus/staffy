package com.praxis.staffy.utilerias;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.praxis.staffy.model.dto.RespuestaGeneral;
import com.praxis.staffy.model.dto.UsuarioDto;
import com.praxis.staffy.utilerias.exception.InvalidRequestTokenException;
import com.praxis.staffy.utilerias.filtros.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginFilter extends AbstractAuthenticationProcessingFilter {

	public LoginFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		
		List<String> errores = new ArrayList<>();
		
		// obtenemos el body de la peticion que viene en formato JSON
        InputStream body = request.getInputStream();
        
        // Realizamos un mapeo a nuestra clase User para tener ahi los datos
        UsuarioDto user = new ObjectMapper().readValue(body, UsuarioDto.class);
        
        // Validar User DTO
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        
        Set<ConstraintViolation<UsuarioDto>> violations = validator.validate(user);

        if(!violations.isEmpty()) {
        	for (ConstraintViolation<UsuarioDto> violation : violations) {
        	    log.error(violation.getMessage()); 
        	    errores.add(violation.getMessage());
        	}
        	
    		throw new InvalidRequestTokenException(errores.get(0));
        }
        
        // contra el que definimos en la clase SecurityConfig
        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user.getUsuario(), user.getContrasena(), Collections.emptyList()));
	}
	
	 @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse response, FilterChain chain,
            Authentication auth) throws IOException, ServletException {

        // Si la autenticacion fue exitosa, agregamos el token a la respuesta
		String token =  "Bearer " + JwtUtil.generarToken(auth);
		response.addHeader("Authorization", "Bearer " + token); 
		
		RespuestaGeneral<String> resGeneral = new RespuestaGeneral<String>();
		resGeneral.setCode(MensajesAplicacion.MENSAJE_EXITO.getCodigo());
		resGeneral.setMessage(MensajesAplicacion.MENSAJE_EXITO.getMensaje());
		resGeneral.setData(token);
		
		String resGeneralJsonString = new Gson().toJson(resGeneral);

		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(resGeneralJsonString);
//		out.flush();
		
	 }

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		
		log.error("Error al authenticar..." + failed.getMessage()); 
		
    	RespuestaGeneral<String> resGeneral = new RespuestaGeneral<String>();
		resGeneral.setCode(MensajesAplicacion.MENSAJE_FALLO.getCodigo());
		resGeneral.setMessage(MensajesAplicacion.MENSAJE_FALLO.getMensaje());
    	resGeneral.setErrors(Arrays.asList(failed.getMessage()));
    	
    	String resGeneralJsonString = new Gson().toJson(resGeneral);

		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setStatus(400);
		response.setCharacterEncoding("UTF-8");
		out.print(resGeneralJsonString);
	}
	 
	 
}
