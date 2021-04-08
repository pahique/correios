package br.com.deere.correios.controller;

import br.com.deere.correios.model.CalcPrazoResultado;
import br.com.deere.correios.model.Servico;
import br.com.deere.correios.service.CorreiosService;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.mockito.Mockito.*;

public class CorreiosControllerTest {

    @Mock
    CorreiosService service;
    @InjectMocks
    CorreiosController controller;
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Test
    public void getPrazoEntregaCorreios_success() throws Exception {
        CalcPrazoResultado resultado = new CalcPrazoResultado();
        Servico servico = new Servico();
        servico.setCodigo("4014");
        servico.setEntregaDomiciliar("S");
        servico.setEntregaSabado("S");
        servico.setDataMaxEntrega("09/04/2021");
        resultado.getServicos().add(servico);
        Mockito.when(service.calculaPrazo(anyString(), anyString(), anyString())).thenReturn(resultado);

        ResponseEntity response = controller.getPrazoEntregaCorreios("04014", "13341-632", "17514-014");
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> responseBody = (Map) response.getBody();
        Servico responseServico = (Servico) responseBody.get("servico");
        Assert.assertEquals("09/04/2021", responseServico.getDataMaxEntrega());
    }

    @Test
    public void getPrazoEntregaCorreios_failCodigoServicoRequired() throws Exception {
        ResponseEntity response = controller.getPrazoEntregaCorreios("", "13341-632", "17514-014");
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> responseBody = (Map) response.getBody();
        Assert.assertEquals("codigoServico obrigatório", responseBody.get("message"));
    }

    @Test
    public void getPrazoEntregaCorreios_failCepOrigemRequired() throws Exception {
        ResponseEntity response = controller.getPrazoEntregaCorreios("04014", "", "17514-014");
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> responseBody = (Map) response.getBody();
        Assert.assertEquals("cepOrigem obrigatório", responseBody.get("message"));
    }

    @Test
    public void getPrazoEntregaCorreios_failCepDestinoRequired() throws Exception {
        ResponseEntity response = controller.getPrazoEntregaCorreios("04014", "13341-632", null);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> responseBody = (Map) response.getBody();
        Assert.assertEquals("cepDestino obrigatório", responseBody.get("message"));
    }
}
