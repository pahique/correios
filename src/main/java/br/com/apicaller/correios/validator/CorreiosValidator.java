package br.com.apicaller.correios.validator;

import br.com.apicaller.correios.exception.CepDestinoRequiredException;
import br.com.apicaller.correios.exception.CepOrigemRequiredException;
import br.com.apicaller.correios.exception.CodigoServicoRequiredException;
import org.springframework.stereotype.Component;

@Component
public class CorreiosValidator {

    public void validatePrazoEntregaParameters(String codigoServico, String cepOrigem, String cepDestino) {
        if (codigoServico == null || codigoServico.trim().isEmpty()) {
            throw new CodigoServicoRequiredException("Código do Serviço é obrigatório");
        } else if (cepOrigem == null || cepOrigem.trim().isEmpty()) {
            throw new CepOrigemRequiredException("CEP de Origem é obrigatório");
        } else if (cepDestino == null || cepDestino.trim().isEmpty()) {
            throw new CepDestinoRequiredException("CEP de Destino é obrigatório");
        }
    }
}
