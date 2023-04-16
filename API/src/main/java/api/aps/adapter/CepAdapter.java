package api.aps.adapter;

import api.aps.domain.Cep;
import api.aps.service.CompraService;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class CepAdapter {
    public interface CepService {
        Cep getCep(String cep) throws Exception;
    }

    public static class ExternalCepService implements CepService {
        public Cep getCep(String cep) throws Exception {
            URL url = new URL("https://viacep.com.br/ws/" +cep+ "/json/");
            URLConnection connection = url.openConnection();
            InputStream is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            String cepData = "";
            StringBuilder jsonCep=new StringBuilder();

            while((cepData = br.readLine()) != null){
                jsonCep.append(cepData);
            }
            Cep cepAux = new Gson().fromJson(jsonCep.toString(), Cep.class);
            return cepAux;
        }
    }

    public class CompraCepServiceAdapter implements CepService {
        private final CompraService compraService;

        public CompraCepServiceAdapter(CompraService compraService) {
            this.compraService = compraService;
        }

        public Cep getCep(String cep) throws Exception {
            return compraService.getCep(cep);
        }
    }
}