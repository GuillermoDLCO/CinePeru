package com.teamwork.cineperu.negocio;

import com.teamwork.cineperu.entidad.UsuarioToken;
import com.teamwork.cineperu.entidad.request.UserTokenRequest;
import com.teamwork.cineperu.repositorio.UsuarioTokenRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioTokenNegocio {

    @Autowired
    private UsuarioTokenRepositorio usuarioTokenRepositorio;

    public boolean validarToken(UserTokenRequest usuUserTokenRequest){
        boolean estadoToken = false;
        try{
            UsuarioToken usuarioToken = usuarioTokenRepositorio.obtenerUsuarioPorToken(usuUserTokenRequest.getToken());
            if (usuarioToken != null){
                estadoToken = true;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return estadoToken;
    }
}
