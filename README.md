# RecuTrabalhoThreads
Trabalho de recuperação da aula de programação concorrente e distribuida

Experimento
Quatro versões do experimento devem ser criadas e executadas, tomando como base:
● Uma versão de referência, sem uso de threads (ou, mais precisamente, apenas com a thread principal
da main);
● Uma versão com 3 threads, sendo cada uma responsável pela requisição de 9 capitais;
● Uma versão com 9 threads, sendo cada uma responsável pela requisição de 3 capitais;
● Uma versão com 27 threads, sendo cada uma responsável pela requisição de 1 capital.
Cada versão do experimento deve consistir na execução de 10 repetições/rodadas de um algoritmo composto
por:
1. 27 requisições HTTP (uma requisição por capital);
2. Processamento do dado recebido, calculando as temperaturas média, mínima e máxima por capital
por dia;
3. Armazenamento dos dados gerados em memória.
4. Exibição dos dados gerados no console.
O objetivo é computar o tempo de execução de cada rodada e, ao fim das 10 rodadas, calcular o tempo
médio para de execução (soma dos tempos de cada rodada dividido por 10).
Nas versões com threads, cada execução deve ter o tempo final computado após o fim de todas as threads
secundárias existentes (exemplo de como contabilizar o tempo aqui). Atente-se para isso: apenas após o
fim das threads criadas explicitamente no código deve ser gerado o tempo de finalização do
experimento.
