package teste.app;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Threads3 {

    // por favor professor não desconsiderar nota caso eu erre um numero aqui
    private static final String[][] capitais = {
        {"Aracaju", "-10.9167", "-37.05"}, {"Belém", "-1.4558", "-48.5039"},
        {"Belo Horizonte", "-19.9167", "-43.9333"}, {"Boa Vista", "2.81972", "-60.67333"},
        {"Brasília", "-15.7939", "-47.8828"}, {"Campo Grande", "-20.44278", "-54.64639"},
        {"Cuiabá", "-15.5989", "-56.0949"}, {"Curitiba", "-25.4297", "-49.2711"},
        {"Florianópolis", "-27.5935", "-48.55854"}, {"Fortaleza", "-3.7275", "-38.5275"},
        {"Goiânia", "-16.6667", "-49.25"}, {"João Pessoa", "-7.12", "-34.88"},
        {"Macapá", "0.033", "-51.05"}, {"Maceió", "-9.66583", "-35.73528"},
        {"Manaus", "-3.1189", "-60.0217"}, {"Natal", "-5.7833", "-35.2"},
        {"Palmas", "-10.16745", "-48.32766"}, {"Porto Alegre", "-30.0331", "-51.23"},
        {"Porto Velho", "-8.76194", "-63.90389"}, {"Recife", "-8.05", "-34.9"},
        {"Rio Branco", "-9.97472", "-67.81"}, {"Rio de Janeiro", "-22.9111", "-43.2056"},
        {"Salvador", "-12.9747", "-38.4767"}, {"São Luís", "-2.5283", "-44.3044"},
        {"São Paulo", "-23.55", "-46.6333"}, {"Vitória", "-20.2889", "-40.3083"}
    };

    // rodando a main
    public static void main(String[] args) {
        runExperimento();
    }
//run do experimento iniciando o tempo de cada repetição
    private static void runExperimento() {
        System.out.println("Rodando com 3 threads.");
        long tempototal = 0;
    //iniciar o cronometro e o loop

        for (int i = 0; i < 10; i++) {
            long startTime = System.currentTimeMillis();

            ExecutorService executor = Executors.newFixedThreadPool(3);
            List<Callable<Void>> tasks = new ArrayList<>();

            int cidadesPorThread = (int) Math.ceil((double) capitais.length / 3);

            for (int j = 0; j < 3; j++) {
                int start = j * cidadesPorThread;
                int end = Math.min(start + cidadesPorThread, capitais.length);
                tasks.add(new FetcherDaApi(capitais, start, end));
            }

            try {
                executor.invokeAll(tasks);
                executor.shutdown();
                executor.awaitTermination(1, TimeUnit.HOURS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    // finalizando cronometro e calculando tempo total

            long endTime = System.currentTimeMillis();
            tempototal += (endTime - startTime);
            System.out.println("Round " + (i + 1) + " completado em " + (endTime - startTime) + " ms.");
        }
    // média de tempo calculada em ms(milisegundos)

        System.out.println("Tempo médio com 3 threads: " + (tempototal / 10) + " ms.");
    }
}
