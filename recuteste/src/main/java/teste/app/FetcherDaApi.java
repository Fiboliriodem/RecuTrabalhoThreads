package teste.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FetcherDaApi implements Callable<Void> {

    private final String[][] capitais;
    private final int start;
    private final int end;
    // preparando a api + colocando as variaveis na url pra funcionar
    public static final String API_URL = "https://archive-api.open-meteo.com/v1/archive?latitude=%s&longitude=%s&start_date=2024-01-01&end_date=2024-01-31&hourly=temperature_2m";

    public FetcherDaApi(String[][] capitais, int start, int end) {
        this.capitais = capitais;
        this.start = start;
        this.end = end;
    }

    @Override
    public Void call() {
        // loop definindo a latitude, longitude e tentando o GET + conexao pro site da api
        for (int i = start; i < end; i++) {
            String cidade = capitais[i][0];
            String latitude = capitais[i][1];
            String longitude = capitais[i][2];
            try {
                String urlStr = String.format(API_URL, latitude, longitude);
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                int responseCode = conn.getResponseCode();
                if (responseCode == 200) {
                    StringBuilder content;
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                        String inputLine;
                        content = new StringBuilder();
                        while ((inputLine = in.readLine()) != null) {
                            content.append(inputLine);
                        }
                    }
                    conn.disconnect();

                    // Processar os dados recebidos usando dependencia do maven
                    JSONObject json = new JSONObject(content.toString());
                    processadorDados(cidade, json);
                } else {
                    System.out.println("Erro na requisição para " + cidade + ": " + responseCode);
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //processador para os dados da api
    private void processadorDados(String cidade, JSONObject json) {
        JSONArray temperatures = json.getJSONObject("hourly").getJSONArray("temperature_2m");

        // descobrindo a temp diária
        double[][] dailyTemperatures = new double[31][24];
        for (int i = 0; i < temperatures.length(); i++) {
            int day = i / 24;
            int hour = i % 24;
            dailyTemperatures[day][hour] = temperatures.getDouble(i);
        }


        // loop pra pegar as 3 medidas de cada dia
        for (int day = 0; day < 31; day++) {
            double minTemp = Double.MAX_VALUE;
            double maxTemp = Double.MIN_VALUE;
            double sumTemp = 0;
            int cont = 0;

            for (int hour = 0; hour < 24; hour++) {
                double temp = dailyTemperatures[day][hour];
                sumTemp += temp;
                minTemp = Math.min(minTemp, temp);
                maxTemp = Math.max(maxTemp, temp);
                cont++;
            }

            double avgTemp = sumTemp / cont;
            System.out.println(String.format("Cidade: %s, Dia: %d, Min: %.2f, Max: %.2f, Média: %.2f", cidade, day + 1, minTemp, maxTemp, avgTemp));
        }
    }
}
