package com.praxis.staffy.service.notificaciones;

import com.praxis.staffy.model.dto.RespuestaGeneral;

public interface NotificacionService {
	RespuestaGeneral<?> findAllNotificaciones();
}
