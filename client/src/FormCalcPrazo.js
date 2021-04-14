import React, { useState } from "react";
import ResultadoCalcPrazo from './ResultadoCalcPrazo';

const FormCalcPrazo = props => {

    const [message, setMessage] = useState('');
    const [codigoServico, setCodigoServico] = useState('');
    const [cepOrigem, setCepOrigem] = useState('');
    const [cepDestino, setCepDestino] = useState('');
    const [resultado, setResultado] = useState('');

    const handleChangeCodigoServico = event => {
        setCodigoServico(event.target.value);
    }

    const handleChangeCepOrigem = event => {
        setCepOrigem(event.target.value);
    }

    const handleChangeCepDestino = event => {
        setCepDestino(event.target.value);
    }

    const handleClickCalcPrazo = () => {
        props.onCalcular({ codigoServico: (codigoServico ? codigoServico : ''),
                           cepOrigem: (cepOrigem ? cepOrigem : ''),
                           cepDestino: (cepDestino ? cepDestino : '') })
             .then(response => {
                console.log(response);
                if (response && response.status === 'success') {
                    setResultado(response.resultado);
                    setMessage('');
                } else {
                    setResultado('');
                    setMessage(response.message ? response.message : 'Erro ao calcular prazo');
                }
             })
             .catch((error) => {
                setResultado('');
                setMessage('Erro ao acessar API');
             });
    }

    return (
        <section className="form-section">
            <p className="error">{message}</p>
            <section className="field-section">
                <div>
                    <label htmlFor="codigoServicoId">Código do Serviço</label>
                    <input type="text" id="codigoServicoId" value={codigoServico} onChange={handleChangeCodigoServico}/>
                </div>
                <div>
                    <label htmlFor="cepOrigemId">CEP de Origem</label>
                    <input type="text" id="cepOrigemId" value={cepOrigem} onChange={handleChangeCepOrigem}/>
                </div>
                <div>
                    <label htmlFor="cepDestinoId">CEP de Destino</label>
                    <input type="text" id="cepDestinoId" value={cepDestino} onChange={handleChangeCepDestino}/>
                </div>
            </section>
            <section className="button-section">
                <button id="calcularButtonId" type="button" onClick={handleClickCalcPrazo}>Calcular Prazo</button>
            </section>
            <section className="result-section">
                <ResultadoCalcPrazo data={resultado}/>
            </section>
        </section>
    );
}

export default FormCalcPrazo;
