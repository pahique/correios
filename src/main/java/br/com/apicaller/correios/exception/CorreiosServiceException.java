package br.com.apicaller.correios.exception;

public class CorreiosServiceException extends Exception {

    private String codigoErro;
    private String mensagemErro;

    public CorreiosServiceException(String codigoErro, String mensagemErro) {
        this.codigoErro = codigoErro;
        this.mensagemErro = mensagemErro;
    }

    public String getCodigoErro() {
        return codigoErro;
    }

    public void setCodigoErro(String codigoErro) {
        this.codigoErro = codigoErro;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }

    public void setMensagemErro(String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }
}
