package br.com.apicaller.correios.exception;

public class CepOrigemRequiredException extends CorreiosValidationException {

    public CepOrigemRequiredException(String message) {
        super(message);
    }
}
