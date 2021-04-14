package br.com.apicaller.correios.model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name= "cResultado")
@XmlAccessorType(XmlAccessType.FIELD)
public class CalcPrazoResultado {

    @XmlElementWrapper(name="Servicos")
    @XmlElement(name="cServico")
    private List<CalcPrazoServico> servicos = new ArrayList();

    public List<CalcPrazoServico> getServicos() {
        return servicos;
    }

    public void setServicos(List<CalcPrazoServico> servicos) {
        this.servicos = servicos;
    }
}
