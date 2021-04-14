package br.com.apicaller.correios.validator;

import br.com.apicaller.correios.exception.CepDestinoRequiredException;
import br.com.apicaller.correios.exception.CepOrigemRequiredException;
import br.com.apicaller.correios.exception.CodigoServicoRequiredException;
import org.junit.Before;
import org.junit.Test;

public class CorreiosValidatorTest {

    CorreiosValidator validator;

    @Before
    public void setUp() throws Exception {
        validator = new CorreiosValidator();
    }

    @Test(expected = CodigoServicoRequiredException.class)
    public void validatePrazoEntregaParameters_failCodigoServicoEmpty() {
        validator.validatePrazoEntregaParameters("", "13341-632", "17514-014");
    }

    @Test(expected = CodigoServicoRequiredException.class)
    public void validatePrazoEntregaParameters_failCodigoServicoNull() {
        validator.validatePrazoEntregaParameters(null, "13341-632", "17514-014");
    }

    @Test(expected = CepOrigemRequiredException.class)
    public void validatePrazoEntregaParameters_failCepOrigemEmpty() {
        validator.validatePrazoEntregaParameters("04014", "", "17514-014");
    }

    @Test(expected = CepOrigemRequiredException.class)
    public void validatePrazoEntregaParameters_failCepOrigemNull() {
        validator.validatePrazoEntregaParameters("04014", null, "17514-014");
    }

    @Test(expected = CepDestinoRequiredException.class)
    public void validatePrazoEntregaParameters_failCepDestinoEmpty() {
        validator.validatePrazoEntregaParameters("04014", "13341-632", "");
    }

    @Test(expected = CepDestinoRequiredException.class)
    public void validatePrazoEntregaParameters_failCepDestinoNull() {
        validator.validatePrazoEntregaParameters("04014", "13341-632", null);
    }
}
