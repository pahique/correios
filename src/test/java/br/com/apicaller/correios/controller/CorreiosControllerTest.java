package br.com.apicaller.correios.controller;

import br.com.apicaller.correios.exception.CepDestinoRequiredException;
import br.com.apicaller.correios.exception.CepOrigemRequiredException;
import br.com.apicaller.correios.exception.CodigoServicoRequiredException;
import br.com.apicaller.correios.exception.CorreiosServiceException;
import br.com.apicaller.correios.model.CalcPrazoResultado;
import br.com.apicaller.correios.model.GetPrazoEntregaResponse;
import br.com.apicaller.correios.model.CalcPrazoServico;
import br.com.apicaller.correios.service.CorreiosService;
import br.com.apicaller.correios.validator.CorreiosValidator;
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

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;

public class CorreiosControllerTest {

    @Mock
    CorreiosService service;
    @Mock
    CorreiosValidator validator;
    @InjectMocks
    CorreiosController controller;
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Test
    public void getPrazoEntregaCorreios_success() throws Exception {
        CalcPrazoResultado resultado = new CalcPrazoResultado();
        CalcPrazoServico servico = new CalcPrazoServico();
        servico.setCodigo("4014");
        servico.setEntregaDomiciliar("S");
        servico.setEntregaSabado("S");
        servico.setDataMaxEntrega("09/04/2021");
        resultado.getServicos().add(servico);
        Mockito.doNothing().when(validator).validatePrazoEntregaParameters(anyString(), anyString(), anyString());
        Mockito.when(service.calculaPrazo(anyString(), anyString(), anyString())).thenReturn(resultado);

        ResponseEntity response = controller.getPrazoEntregaCorreios("04014", "13341-632", "17514-014");
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        GetPrazoEntregaResponse responseBody = (GetPrazoEntregaResponse) response.getBody();
        CalcPrazoServico responseServico = responseBody.getResultado().getServicos().get(0);
        Assert.assertEquals("09/04/2021", responseServico.getDataMaxEntrega());
    }

    @Test
    public void getPrazoEntregaCorreios_failCodigoServicoRequired() throws Exception {
        Mockito.doThrow(new CodigoServicoRequiredException("Código do Serviço é obrigatório")).when(validator).validatePrazoEntregaParameters(any(), any(), any());
        ResponseEntity response = controller.getPrazoEntregaCorreios("", "13341-632", "17514-014");
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        GetPrazoEntregaResponse responseBody = (GetPrazoEntregaResponse) response.getBody();
        Assert.assertEquals("Código do Serviço é obrigatório", responseBody.getMessage());
    }

    @Test
    public void getPrazoEntregaCorreios_failCepOrigemRequired() throws Exception {
        Mockito.doThrow(new CepOrigemRequiredException("CEP de Origem é obrigatório")).when(validator).validatePrazoEntregaParameters(any(), any(), any());
        ResponseEntity response = controller.getPrazoEntregaCorreios("04014", "", "17514-014");
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        GetPrazoEntregaResponse responseBody = (GetPrazoEntregaResponse) response.getBody();
        Assert.assertEquals("CEP de Origem é obrigatório", responseBody.getMessage());
    }

    @Test
    public void getPrazoEntregaCorreios_failCepDestinoRequired() throws Exception {
        Mockito.doThrow(new CepDestinoRequiredException("CEP de Destino é obrigatório")).when(validator).validatePrazoEntregaParameters(any(), any(), any());
        ResponseEntity response = controller.getPrazoEntregaCorreios("04014", "13341-632", null);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        GetPrazoEntregaResponse responseBody = (GetPrazoEntregaResponse) response.getBody();
        Assert.assertEquals("CEP de Destino é obrigatório", responseBody.getMessage());
    }

    @Test
    public void getPrazoEntregaCorreios_failCodigoServicoInvalido() throws Exception {
        Mockito.doNothing().when(validator).validatePrazoEntregaParameters(anyString(), anyString(), anyString());
        Mockito.doThrow(new CorreiosServiceException("001", "Código de serviço inválido")).when(service).calculaPrazo("XXXXX", "13341-632", "17514-014");
        ResponseEntity response = controller.getPrazoEntregaCorreios("XXXXX", "13341-632", "17514-014");
        Assert.assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        GetPrazoEntregaResponse responseBody = (GetPrazoEntregaResponse) response.getBody();
        Assert.assertEquals("Código de serviço inválido", responseBody.getMessage());
    }
}
