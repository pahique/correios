package br.com.deere.correios.controller;

import br.com.deere.correios.model.CalcPrazoResultado;
import br.com.deere.correios.model.Servico;
import br.com.deere.correios.service.CorreiosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CorreiosController {

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
        ResponseEntity result = null;
        Map<String, Object> responseMap = new HashMap();
        if (codigoServico == null || codigoServico.trim().isEmpty()) {
            responseMap.put("status", "fail");
            responseMap.put("message", "codigoServico obrigatório");
        } else if (cepOrigem == null || cepOrigem.trim().isEmpty()) {
            responseMap.put("status", "fail");
            responseMap.put("message", "cepOrigem obrigatório");
        } else if (cepDestino == null || cepDestino.trim().isEmpty()) {
            responseMap.put("status", "fail");
            responseMap.put("message", "cepDestino obrigatório");
        }
        if (!responseMap.isEmpty()) {
            result = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
        } else {

            try {
                CalcPrazoResultado calcPrazo = service.calculaPrazo(codigoServico, cepOrigem, cepDestino);
                List<Servico> servicos = calcPrazo.getServicos();
                if (servicos != null && !servicos.isEmpty()) {
                    responseMap = new HashMap();
                    responseMap.put("status", "success");
                    responseMap.put("dataMaxEntrega", servicos.get(0).getDataMaxEntrega());
                    responseMap.put("message", servicos.get(0).getMsgErro());
                    result = ResponseEntity.ok().body(responseMap);
                } else {
                    result = ResponseEntity.noContent().build();
                }
            } catch (Exception e) {
                e.printStackTrace();
                responseMap = new HashMap();
                responseMap.put("status", "error");
                responseMap.put("message", e.getMessage());
                result = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMap);
            }
        }
        return result;
    }
}