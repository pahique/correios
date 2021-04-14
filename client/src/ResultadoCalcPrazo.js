import React from "react";

function renderRow(key, propertyName, propertyValue) {
  return <tr key={key} className="data-row"><td>{propertyName}</td><td>{propertyValue}</td></tr>
};

function renderResultado(resultado) {
  let rows = [];
  if (resultado && resultado.servicos) {
    let servico = resultado.servicos[0];
    if (servico.erro) {
        rows.push(renderRow('erro', 'Código de Erro', servico.erro));
        rows.push(renderRow('msgErro', 'Mensagem de Erro', servico.msgErro));
    } else {
        rows.push(renderRow('codigoServico', 'Código do Serviço', servico.codigo));
        rows.push(renderRow('prazoEntrega', 'Prazo Entrega', servico.prazoEntrega));
        rows.push(renderRow('entregaDomiciliar', 'Entrega Domiciliar', servico.entregaDomiciliar));
        rows.push(renderRow('entregaSabado', 'Entrega Sábado', servico.entregaSabado));
        rows.push(renderRow('dataMaxEntrega', 'Data Máxima de Entrega', servico.dataMaxEntrega));
    }
  }
  return rows;
};

const ResultadoCalcPrazo = props => {

    const resultado = renderResultado(props.data);

    return (props.data ?
        (<table className="data-table">
          <thead>
          </thead>
          <tbody>
            {resultado}
          </tbody>
        </table>) : ""
      );
  }

export default ResultadoCalcPrazo;