package br.com.apicaller.correios.controller;

import br.com.apicaller.correios.model.CalcPrazoResultado;
import br.com.apicaller.correios.model.GetPrazoEntregaResponse;
import br.com.apicaller.correios.model.Servico;
import br.com.apicaller.correios.service.CorreiosService;
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
        GetPrazoEntregaResponse responseBody = (GetPrazoEntregaResponse) response.getBody();
        Servico responseServico = responseBody.getResultado().getServicos().get(0);
        Assert.assertEquals("09/04/2021", responseServico.getDataMaxEntrega());
    }

    @Test
    public void getPrazoEntregaCorreios_failCodigoServicoRequired() throws Exception {
        ResponseEntity response = controller.getPrazoEntregaCorreios("", "13341-632", "17514-014");
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        GetPrazoEntregaResponse responseBody = (GetPrazoEntregaResponse) response.getBody();
        Assert.assertEquals("Código do Serviço é obrigatório", responseBody.getMessage());
    }

    @Test
    public void getPrazoEntregaCorreios_failCepOrigemRequired() throws Exception {
        ResponseEntity response = controller.getPrazoEntregaCorreios("04014", "", "17514-014");
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        GetPrazoEntregaResponse responseBody = (GetPrazoEntregaResponse) response.getBody();
        Assert.assertEquals("CEP de Origem é obrigatório", responseBody.getMessage());
    }

    @Test
    public void getPrazoEntregaCorreios_failCepDestinoRequired() throws Exception {
        ResponseEntity response = controller.getPrazoEntregaCorreios("04014", "13341-632", null);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        GetPrazoEntregaResponse responseBody = (GetPrazoEntregaResponse) response.getBody();
        Assert.assertEquals("CEP de Destino é obrigatório", responseBody.getMessage());
    }
}
