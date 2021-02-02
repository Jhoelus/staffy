package com.praxis.staffy.utilerias.filtros;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.google.gson.Gson;
import com.praxis.staffy.model.dto.RespuestaGeneral;
import com.praxis.staffy.utilerias.MensajesAplicacion;
import com.praxis.staffy.utilerias.exception.InvalidRequestTokenException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GeneralFilter extends BasicAuthenticationFilter{
	
	public GeneralFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}
	
	@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
		
		log.info("General Filter ..." );
        
		
		try {
			 Authentication authentication = JwtUtil.validarToken((HttpServletRequest)request);
			 SecurityContextHolder.getContext().setAuthentication(authentication);
			 return;
		} catch (InvalidRequestTokenException e) {
			log.error("Error al validar token ..." ); 
			
	    	RespuestaGeneral<String> resGeneral = new RespuestaGeneral<String>();
			resGeneral.setCode(MensajesAplicacion.MENSAJE_FALLO.getCodigo());
			resGeneral.setMessage(MensajesAplicacion.MENSAJE_FALLO.getMensaje());
	    	resGeneral.setErrors(Arrays.asList(e.getMessage()));
	    	
	    	String resGeneralJsonString = new Gson().toJson(resGeneral);

			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setStatus(400);
			response.setCharacterEncoding("UTF-8");
			out.print(resGeneralJsonString);
			return;
		}
    }
}
