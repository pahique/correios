package br.com.apicaller.correios.service;

import br.com.apicaller.correios.exception.CorreiosServiceException;
import br.com.apicaller.correios.model.CalcPrazoResultado;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;
import java.net.URI;

@Service
public class CorreiosService {

    private final Log log = LogFactory.getLog(this.getClass());

    public CalcPrazoResultado calculaPrazo(String codigoServico, String cepOrigem, String cepDestino) throws CorreiosServiceException, Exception {
        CalcPrazoResultado result = null;
        if (log.isDebugEnabled()) {
            log.debug(String.format("codigoServico: %s, cepOrigem: %s, cepDestino: %s", codigoServico, cepOrigem, cepDestino));
        }
        ResponseEntity<String> correiosResponse = new RestTemplate().getForEntity(
                new URI(String.format("http://ws.correios.com.br/calculador/CalcPrecoPrazo.asmx/CalcPrazo?nCdServico=%s&sCepOrigem=%s&sCepDestino=%s",
                        codigoServico, cepOrigem, cepDestino)), String.class);
        if (log.isDebugEnabled()) {
            log.debug("Response: " + correiosResponse);
        }
        if (correiosResponse != null && correiosResponse.getBody() != null && !correiosResponse.getBody().isEmpty()) {
            XMLInputFactory xif = XMLInputFactory.newFactory();
            xif.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, false);
            XMLStreamReader xsr = xif.createXMLStreamReader(new StringReader(correiosResponse.getBody()));

            JAXBContext context = JAXBContext.newInstance(CalcPrazoResultado.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            result = (CalcPrazoResultado) unmarshaller.unmarshal(xsr);
            if (result != null && result.getServicos() != null && !result.getServicos().isEmpty()
                    && result.getServicos().get(0).getMsgErro() != null
                    && !result.getServicos().get(0).getMsgErro().isEmpty()) {
                String codigoErro = result.getServicos().get(0).getErro();
                String mensagemErro = result.getServicos().get(0).getMsgErro();
                throw new CorreiosServiceException(codigoErro, mensagemErro);
            }
        }
        return result;
    }
}
