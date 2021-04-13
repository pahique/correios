package br.com.apicaller.correios.controller;

import br.com.apicaller.correios.model.GetPrazoEntregaResponse;
import br.com.apicaller.correios.service.CorreiosService;
import br.com.apicaller.correios.model.CalcPrazoResultado;
import br.com.apicaller.correios.model.Servico;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class CorreiosController {

    private final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private CorreiosService service;

    @GetMapping(value = "/api/test")
    public ResponseEntity buildPrazoEntregaCorreios() throws Exception {
        CalcPrazoResultado result = new CalcPrazoResultado();
        List<Servico> servicos = new ArrayList<>();
        Servico s1 = new Servico();
        s1.setCodigo("c1");
        s1.setDataMaxEntrega("10/05/2021");
        s1.setPrazoEntrega("1");
        servicos.add(s1);
        result.setServicos(servicos);

        JAXBContext context = JAXBContext.newInstance(CalcPrazoResultado.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter writer = new StringWriter();
        marshaller.marshal(result, writer);
        System.out.println(writer.getBuffer());
        return ResponseEntity.ok().body(writer.getBuffer());
    }


    @GetMapping(value = "/api/calcprazo")
    public ResponseEntity getPrazoEntregaCorreios(@RequestParam String codigoServico,
                                                  @RequestParam String cepOrigem,
                                                  @RequestParam String cepDestino) {
        ResponseEntity response;
        GetPrazoEntregaResponse result = new GetPrazoEntregaResponse();
        if (codigoServico == null || codigoServico.trim().isEmpty()) {
            result.setStatus("error");
            result.setMessage("Código do Serviço é obrigatório");
        } else if (cepOrigem == null || cepOrigem.trim().isEmpty()) {
            result.setStatus("error");
            result.setMessage("CEP de Origem é obrigatório");
        } else if (cepDestino == null || cepDestino.trim().isEmpty()) {
            result.setStatus("error");
            result.setMessage("CEP de Destino é obrigatório");
        }
        if ("error".equals(result.getStatus())) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Access-Control-Allow-Origin", "*");
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(result);
        } else {

            try {
                CalcPrazoResultado calcPrazo = service.calculaPrazo(codigoServico, cepOrigem, cepDestino);
                List<Servico> servicos = calcPrazo.getServicos();
                if (servicos != null && !servicos.isEmpty()) {
                    result = new GetPrazoEntregaResponse();
                    result.setStatus("success");
                    result.setMessage("Dados obtidos com sucesso");
                    result.setResultado(calcPrazo);
                    HttpHeaders headers = new HttpHeaders();
                    headers.add("Access-Control-Allow-Origin", "*");
                    response = ResponseEntity.ok().headers(headers).body(result);
                } else {
                    response = ResponseEntity.noContent().build();
                }
            } catch (Exception e) {
                log.error("Error calling Correios API", e);
                result = new GetPrazoEntregaResponse();
                result.setStatus("error");
                result.setMessage(e.getMessage());
                HttpHeaders headers = new HttpHeaders();
                headers.add("Access-Control-Allow-Origin", "*");
                response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body(result);
            }
        }
        return response;
    }
}