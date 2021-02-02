package com.praxis.staffy.utilerias.filtros;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.google.gson.Gson;
import com.praxis.staffy.model.dto.RespuestaGeneral;
import com.praxis.staffy.utilerias.MensajesAplicacion;
import com.praxis.staffy.utilerias.exception.InvalidRequestTokenException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtFilter extends BasicAuthenticationFilter { // GenericFilterBean {

	public JwtFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		
		try {
			String header = request.getHeader("Authorization");
			
			if (header == null || !header.startsWith("Bearer")) {
				chain.doFilter(request, res);
				return;
			}
			
			UsernamePasswordAuthenticationToken authentication = JwtUtil.validarToken(httpServletRequest);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			chain.doFilter(request, res);
			
		} catch (InvalidRequestTokenException e) {
			log.error("Error al validar token ..." ); 
			
			HttpServletResponse response = (HttpServletResponse) res;
		    
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
