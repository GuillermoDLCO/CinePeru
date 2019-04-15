package com.teamwork.cineperu.negocio;

import com.teamwork.cineperu.bean.BeanTriviaPregunta;
import com.teamwork.cineperu.bean.BeanTriviaRespuesta;
import com.teamwork.cineperu.entidad.Trivia;
import com.teamwork.cineperu.entidad.TriviaDetalle;
import com.teamwork.cineperu.entidad.TriviaDetalleRespuesta;
import com.teamwork.cineperu.entidad.request.UserTokenRequest;
import com.teamwork.cineperu.entidad.response.GetListTriviaResponse;
import com.teamwork.cineperu.repositorio.TriviaDetalleRepositorio;
import com.teamwork.cineperu.repositorio.TriviaDetalleRespuestaRepositorio;
import com.teamwork.cineperu.repositorio.TriviaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TriviaNegocio {

    @Autowired
    private TriviaRepositorio triviaRepositorio;
    @Autowired
    private TriviaDetalleRepositorio triviaDetalleRepositorio;
    @Autowired
    private TriviaDetalleRespuestaRepositorio triviaDetalleRespuestaRepositorio;
    @Autowired
    private UsuarioTokenNegocio usuarioTokenNegocio;

    public GetListTriviaResponse listarTrivia(UserTokenRequest userTokenRequest){
        GetListTriviaResponse getListTriviaResponse = new GetListTriviaResponse();
        getListTriviaResponse.setErrorCode(0);
        getListTriviaResponse.setErrorMessage("");
        try{

            if (!usuarioTokenNegocio.validarToken(userTokenRequest)){
                getListTriviaResponse.setErrorCode(100);
                getListTriviaResponse.setErrorMessage("Credencial de acceso vencida o incorrecta");
                return getListTriviaResponse;
            }

            SimpleDateFormat sdp = new SimpleDateFormat("dd/MM/yyyy");
            Trivia trivia = triviaRepositorio.buscarTrivia(sdp.parse(sdp.format(new Date())));
            if (trivia != null){

                List<TriviaDetalle> listTriviaDetalle = triviaDetalleRepositorio.buscarPreguntas(trivia.getCodigoTrivia());
                List<BeanTriviaPregunta> preguntas = new ArrayList<>();

                BeanTriviaPregunta beanTriviaPregunta = null;
                for(TriviaDetalle itemPregunta : listTriviaDetalle){
                    beanTriviaPregunta = new BeanTriviaPregunta();
                    beanTriviaPregunta.setCodigoTriviaPregunta(itemPregunta.getCodigoDetalleTrivia());
                    beanTriviaPregunta.setPregunta(itemPregunta.getPregunta());

                    List<TriviaDetalleRespuesta> listTriviaDetalleRespuesta =
                            triviaDetalleRespuestaRepositorio.buscarPreguntas(trivia.getCodigoTrivia());

                    List<BeanTriviaRespuesta> respuestas = new ArrayList<>();

                    BeanTriviaRespuesta beanTriviaRespuesta = null;
                    for(TriviaDetalleRespuesta itemRespuesta : listTriviaDetalleRespuesta){
                        beanTriviaRespuesta = new BeanTriviaRespuesta();
                        beanTriviaRespuesta.setCodigoTriviaRespuesta(itemRespuesta.getCodigoDetalleRespuesta());
                        beanTriviaRespuesta.setEstadoRespuesta(itemRespuesta.isEstadoRespuesta());
                        beanTriviaRespuesta.setRespuesta(itemRespuesta.getRespuesta());
                        respuestas.add(beanTriviaRespuesta);
                    }

                    beanTriviaPregunta.setRespuestas(respuestas);
                    preguntas.add(beanTriviaPregunta);
                }

                getListTriviaResponse.setCodigoTrivia(trivia.getCodigoTrivia());
                getListTriviaResponse.setPreguntas(preguntas);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            getListTriviaResponse.setErrorCode(1);
            getListTriviaResponse.setErrorMessage("Error en procesos");
        }
        return getListTriviaResponse;
    }

}
