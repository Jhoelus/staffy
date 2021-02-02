package com.praxis.staffy.service.usuarios.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.praxis.staffy.model.dto.ClienteDto;
import com.praxis.staffy.model.dto.RespuestaGeneral;
import com.praxis.staffy.model.dto.UsuarioDto;
import com.praxis.staffy.model.entidad.UsuarioEntity;
import com.praxis.staffy.repository.UsuarioRepository;
import com.praxis.staffy.service.usuarios.UsuarioService;
import com.praxis.staffy.utilerias.MensajesAplicacion;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class UsuarioServiceImpl implements UserDetailsService, UsuarioService {
	
	UsuarioRepository usuarioRepository;
	
	PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("Iniciando la authentication", username);
        
		List<UsuarioEntity> usuarios = usuarioRepository.findByUsuario(username);
		UsuarioEntity usuarioBD = usuarios.stream().findFirst().orElse(null);
		
        if (usuarioBD == null) {
        	log.info("Username {} no encontrado", username);
            throw new UsernameNotFoundException("Username no enconstrado");
        } else {
            Collection<? extends GrantedAuthority> authorities = getGrantedAuthorities(usuarioBD);
            return new org.springframework.security.core.userdetails.User(usuarioBD.getUsuario(),usuarioBD.getContrasena(), authorities);
        }
	}
	
	private Collection<? extends GrantedAuthority> getGrantedAuthorities(UsuarioEntity usuarioBD) {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        
        list.add(new SimpleGrantedAuthority(usuarioBD.getRol()));
        
        return list;
    }

	@Override
	public RespuestaGeneral<?> cambiarPassword(UsuarioDto user) {
		
	    log.info("Servicio usuarios: cambiar password de {} ", user.getUsuario());
		
	    RespuestaGeneral<List<ClienteDto>> response = new RespuestaGeneral<List<ClienteDto>>();
	    
	    List<UsuarioEntity> usuarios = usuarioRepository.findByUsuario(user.getUsuario());
		UsuarioEntity usuarioBD = usuarios.stream().findFirst().orElse(null);
		
		if (usuarioBD == null) {
			response.setCode(MensajesAplicacion.MENSAJE_FALLO.getCodigo());
			response.setMessage(MensajesAplicacion.MENSAJE_FALLO.getMensaje());
			response.setErrors(Arrays.asList("Usuario no encontrado"));
		} else {
			
			usuarioBD.setContrasena(passwordEncoder.encode(user.getContrasena()));
			usuarioRepository.save(usuarioBD);
			
			response.setCode(MensajesAplicacion.MENSAJE_EXITO.getCodigo());
			response.setMessage(MensajesAplicacion.MENSAJE_EXITO.getMensaje());
			
		}
		
		return response;
	}
	
}
