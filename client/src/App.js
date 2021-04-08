import React, { Component, useState } from "react";
import FormCalcPrazo from './FormCalcPrazo';

import './App.css';

class App extends Component {

  calcularPrazo = async (dados) => {
    const encodedCodigoServico = encodeURIComponent(dados.codigoServico);
    const encodedCepOrigem = encodeURIComponent(dados.cepOrigem);
    const encodedCepDestino = encodeURIComponent(dados.cepDestino);

    let promise = new Promise((resolve, reject) => {
        fetch('http://localhost:8080/api/calcprazo?codigoServico='+encodedCodigoServico+'&cepOrigem='+encodedCepOrigem+'&cepDestino='+encodedCepDestino)
        .then(res => res.json())
        .then((data) => {
          //console.log(data);
          resolve(data);
        })
        .catch((error) => {
          reject(error);
        });
    });
    return promise;
  }

  render() {
      return (
        <div className="App">
          <header className="App-header">
          </header>
          <main>
            <FormCalcPrazo onCalcular={this.calcularPrazo}/>
          </main>
        </div>
      );
  }
}

export default App;
