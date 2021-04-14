package br.com.apicaller.correios.controller;

import br.com.apicaller.correios.exception.CepDestinoRequiredException;
import br.com.apicaller.correios.exception.CepOrigemRequiredException;
import br.com.apicaller.correios.exception.CodigoServicoRequiredException;
import br.com.apicaller.correios.exception.CorreiosValidationException;
import br.com.apicaller.correios.model.GetPrazoEntregaResponse;
import br.com.apicaller.correios.service.CorreiosService;
import br.com.apicaller.correios.model.CalcPrazoResultado;
import br.com.apicaller.correios.model.Servico;
import br.com.apicaller.correios.validator.CorreiosValidator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CorreiosController {

    private final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private CorreiosService service;
    @Autowired
    private CorreiosValidator validator;


    @GetMapping(value = "/api/calcprazo")
    public ResponseEntity getPrazoEntregaCorreios(@RequestParam String codigoServico,
                                                  @RequestParam String cepOrigem,
                                                  @RequestParam String cepDestino) {
        ResponseEntity response;
        try {
            validator.validatePrazoEntregaParameters(codigoServico, cepOrigem, cepDestino);
            CalcPrazoResultado calcPrazo = service.calculaPrazo(codigoServico, cepOrigem, cepDestino);
            List<Servico> servicos = calcPrazo.getServicos();
            if (servicos != null && !servicos.isEmpty()) {
                GetPrazoEntregaResponse result = new GetPrazoEntregaResponse();
                result.setStatus("success");
                result.setMessage("Dados obtidos com sucesso");
                result.setResultado(calcPrazo);
                HttpHeaders headers = new HttpHeaders();
                headers.add("Access-Control-Allow-Origin", "*");
                response = ResponseEntity.ok().headers(headers).body(result);
            } else {
                response = ResponseEntity.noContent().build();
            }
        } catch(CorreiosValidationException e) {
            GetPrazoEntregaResponse result = new GetPrazoEntregaResponse();
            result.setStatus("error");
            result.setMessage(e.getMessage());
            HttpHeaders headers = new HttpHeaders();
            headers.add("Access-Control-Allow-Origin", "*");
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(result);
        } catch (Exception e) {
            log.error("Error calling Correios API", e);
            GetPrazoEntregaResponse result = new GetPrazoEntregaResponse();
            result.setStatus("error");
            result.setMessage(e.getMessage());
            HttpHeaders headers = new HttpHeaders();
            headers.add("Access-Control-Allow-Origin", "*");
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body(result);
        }
        return response;
    }
}