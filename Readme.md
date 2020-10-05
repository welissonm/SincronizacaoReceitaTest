# Serviço de sincronização de dados por meio de leitura de arquivo no formato CSV

## Cenário de Negócio
Todo dia útil por volta das 6 horas da manhã um colaborador da retaguarda do Sicredi recebe e organiza as informações de contas para enviar ao Banco Central. Todas agencias e cooperativas enviam arquivos Excel à Retaguarda. Hoje o Sicredi já possiu mais de 4 milhões de contas ativas.
Esse usuário da retaguarda exporta manualmente os dados em um arquivo CSV para ser enviada para a Receita Federal, antes as 10:00 da manhã na abertura das agências.

### Requisito:
Usar o "serviço da receita" (fake) para processamento automático do arquivo.

### Funcionalidade:
0. Criar uma aplicação SprintBoot standalone. Exemplo: java -jar SincronizacaoReceita <input-file>
1. Processa um arquivo CSV de entrada com o formato abaixo.
    
    &nbsp;
    Formato CSV:
    ```
    agencia;conta;saldo;status
    0101;12225-6;100,00;A
    0101;12226-8;3200,50;A
    3202;40011-1;-35,12;I
    3202;54001-2;0,00;P
    3202;00321-2;34500,00;B"
    ```
1. Envia a atualização para a Receita através do serviço (SIMULADO pela classe ReceitaService).
1. Retorna um arquivo com o resultado do envio da atualização da Receita. Mesmo formato adicionando o resultado em uma nova coluna.

### Logica de implementação
Um serviço de processamento em batch recebe como argumento um arquivo no formato .csv. As etapas do processo consiste em:

1. Ler o arquivo passado como valor do argumento *input-file*;
2. Processa o arquivo junto ao serviço (fake) da Receita Federal;
3. Concluído o processamento, um novo arquivo é gerado com o nome e localização passada como valor do argumento *output-file*.
4. O processamento esta agendado para ocorrer todos os dias a partir das 6 AM.

>A classe *ReceitaService* foi incorporado no projeto representando uma chamada de uma API externa.

## Tecnologias empregadas
Forma utilizados as seguintes tecnologias:
1. Springboot como *server container*;
2. Spring Batch como ferramenta de processamento de *IO* em batch;
3. Banco de dados em memória *H2*.