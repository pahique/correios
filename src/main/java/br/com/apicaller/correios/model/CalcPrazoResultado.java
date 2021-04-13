package br.com.apicaller.correios.model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name= "cResultado")
@XmlAccessorType(XmlAccessType.FIELD)
public class CalcPrazoResultado {

    @XmlElementWrapper(name="Servicos")
    @XmlElement(name="cServico")
    private List<Servico> servicos = new ArrayList();

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }
}
