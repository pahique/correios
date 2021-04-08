# Cálculo de Prazo dos Correios
Teste de API REST dos correios para cálculo de prazo

## Descrição das principais classes

### CorreiosController

   Disponibiliza API REST para o client. A chamada à API dos Correios é delegada para o CorreiosService.
   
### CorreiosService

   Faz a interface com a API dos correios e converte o resultado em um objeto.
   

## Server:

  Executar SpringBoot, classe principal *br.com.deere.correios.CalcprecoprazoApplication*
  
  Ou pela linha de comando:
  
```
./mvnw spring-boot:run
```
  
## Client:

```
  cd client
  yarn start
```  

