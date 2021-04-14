package br.com.apicaller.correios.model;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class CalcPrazoServico {

    @XmlElement(name = "Codigo")
    private String codigo;

    @XmlElement(name = "PrazoEntrega")
    private String prazoEntrega;

    @XmlElement(name = "EntregaDomiciliar")
    private String entregaDomiciliar;

    @XmlElement(name = "EntregaSabado")
    private String entregaSabado;

    @XmlElement(name = "Erro")
    private String erro;

    @XmlElement(name = "MsgErro")
    private String msgErro;

    @XmlElement(name = "obsFim")
    private String obsFim;

    @XmlElement(name = "DataMaxEntrega")
    private String dataMaxEntrega;

    public CalcPrazoServico() {

    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPrazoEntrega() {
        return prazoEntrega;
    }

    public void setPrazoEntrega(String prazoEntrega) {
        this.prazoEntrega = prazoEntrega;
    }

    public String getEntregaDomiciliar() {
        return entregaDomiciliar;
    }

    public void setEntregaDomiciliar(String entregaDomiciliar) {
        this.entregaDomiciliar = entregaDomiciliar;
    }

    public String getEntregaSabado() {
        return entregaSabado;
    }

    public void setEntregaSabado(String entregaSabado) {
        this.entregaSabado = entregaSabado;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    public String getMsgErro() {
        return msgErro;
    }

    public void setMsgErro(String msgErro) {
        this.msgErro = msgErro;
    }

    public String getObsFim() {
        return obsFim;
    }

    public void setObsFim(String obsFim) {
        this.obsFim = obsFim;
    }

    public String getDataMaxEntrega() {
        return dataMaxEntrega;
    }

    public void setDataMaxEntrega(String dataMaxEntrega) {
        this.dataMaxEntrega = dataMaxEntrega;
    }

}
