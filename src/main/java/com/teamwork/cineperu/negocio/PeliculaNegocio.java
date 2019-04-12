package com.teamwork.cineperu.negocio;

import com.teamwork.cineperu.entidad.Pelicula;
import com.teamwork.cineperu.entidad.UsuarioToken;
import com.teamwork.cineperu.entidad.request.UserTokenRequest;
import com.teamwork.cineperu.entidad.response.GetListMovieResponse;
import com.teamwork.cineperu.repositorio.PeliculaRepositorio;
import com.teamwork.cineperu.repositorio.UsuarioTokenRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeliculaNegocio {

    @Autowired
    private PeliculaRepositorio peliculaRepositorio;
    @Autowired
    private UsuarioTokenRepositorio usuarioTokenRepositorio;

    public GetListMovieResponse listaPelicula(UserTokenRequest usuUserTokenRequest){
        GetListMovieResponse getListMovieResponse = new GetListMovieResponse();
        getListMovieResponse.setErrorCode(0);
        getListMovieResponse.setErrorMessage("");
        try{
            System.out.println("token:"+usuUserTokenRequest.getToken());
            UsuarioToken usuarioToken = usuarioTokenRepositorio.obtenerUsuarioPorToken(usuUserTokenRequest.getToken());
            if (usuarioToken != null){
                List<Pelicula> listaPelicula = (List<Pelicula>) peliculaRepositorio.findAll();
                System.out.println("listaPelicula:"+listaPelicula);
                getListMovieResponse.setLista(listaPelicula);
            }
        }catch (Exception ex){
            getListMovieResponse.setErrorCode(5);
            getListMovieResponse.setErrorMessage("Error al obtener listado de peliculas");
            ex.printStackTrace();
        }
        return getListMovieResponse;
    }
}
