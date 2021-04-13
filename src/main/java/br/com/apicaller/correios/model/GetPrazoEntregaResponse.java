package br.com.apicaller.correios.model;

public class GetPrazoEntregaResponse {

    private String status;
    private String message;
    private CalcPrazoResultado resultado;

    public GetPrazoEntregaResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CalcPrazoResultado getResultado() {
        return resultado;
    }

    public void setResultado(CalcPrazoResultado resultado) {
        this.resultado = resultado;
    }
}
