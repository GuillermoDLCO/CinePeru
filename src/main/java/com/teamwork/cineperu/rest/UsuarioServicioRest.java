package com.teamwork.cineperu.rest;

import com.teamwork.cineperu.entidad.request.RegisterUserRequest;
import com.teamwork.cineperu.entidad.request.UserAuthenticateRequest;
import com.teamwork.cineperu.entidad.request.UserTokenRequest;
import com.teamwork.cineperu.entidad.response.EntityWSBase;
import com.teamwork.cineperu.entidad.response.GetListMovieResponse;
import com.teamwork.cineperu.entidad.response.GetListTriviaResponse;
import com.teamwork.cineperu.entidad.response.UserAuthenticateResponse;
import com.teamwork.cineperu.negocio.PeliculaNegocio;
import com.teamwork.cineperu.negocio.PersonaUsuarioNegocio;
import com.teamwork.cineperu.negocio.TriviaNegocio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UsuarioServicioRest {

    @Autowired
    private PersonaUsuarioNegocio personaUsuarioNegocio;
    @Autowired
    private PeliculaNegocio peliculaNegocio;
    @Autowired
    private TriviaNegocio triviaNegocio;

    @PostMapping("/WS_RegisterUser")
    public EntityWSBase WS_RegisterUser(@RequestBody RegisterUserRequest registerUserRequest){
        return personaUsuarioNegocio.registrarPersonaUsuario(registerUserRequest);
    }

    @PostMapping("/WS_UserAuthenticate")
    public UserAuthenticateResponse WS_UserAuthenticate(@RequestBody UserAuthenticateRequest userAuthenticateRequest){
        return personaUsuarioNegocio.autenticarUsuario(userAuthenticateRequest);
    }

    @PostMapping("/WS_GetListMovie")
    public GetListMovieResponse WS_GetListMovie(@RequestBody UserTokenRequest userTokenRequest){
        return peliculaNegocio.listaPelicula(userTokenRequest);
    }

    @PostMapping("/WS_GetListTrivia")
    public GetListTriviaResponse WS_GetListTrivia(@RequestBody UserTokenRequest userTokenRequest){
        return triviaNegocio.listarTrivia(userTokenRequest);
    }
}
