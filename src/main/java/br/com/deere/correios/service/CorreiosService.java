package br.com.deere.correios.service;

import br.com.deere.correios.model.CalcPrazoResultado;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.net.URI;

@Service
public class CorreiosService {

    public CalcPrazoResultado calculaPrazo(String codigoServico, String cepOrigem, String cepDestino) throws Exception {
        CalcPrazoResultado result = null;
        ResponseEntity<String> correiosResponse = new RestTemplate().getForEntity(
                new URI(String.format("http://ws.correios.com.br/calculador/CalcPrecoPrazo.asmx/CalcPrazo?nCdServico=%s&sCepOrigem=%s&sCepDestino=%s",
                        codigoServico, cepOrigem, cepDestino)), String.class);
        System.out.println("Response: " + correiosResponse);
        if (correiosResponse != null && correiosResponse.getBody() != null && !correiosResponse.getBody().isEmpty()) {
            XMLInputFactory xif = XMLInputFactory.newFactory();
            xif.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, false);
            XMLStreamReader xsr = xif.createXMLStreamReader(new StringReader(correiosResponse.getBody()));

            JAXBContext context = JAXBContext.newInstance(CalcPrazoResultado.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            result = (CalcPrazoResultado) unmarshaller.unmarshal(xsr);
        }
        return result;
    }
}