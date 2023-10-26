package org.algaworks.algafood.client;

import org.algaworks.algafood.client.api.ClientApiException;
import org.algaworks.algafood.client.api.RestauranteClient;
import org.springframework.web.client.RestTemplate;

public class ListagemRestauranteMain {

    public static void main(String[] args) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            RestauranteClient restauranteClient =
                    new RestauranteClient("http://api.algafood.local:8080", restTemplate);

            restauranteClient.listar().forEach(System.out::println);
        } catch (ClientApiException e) {
            if(e.getProblem() != null) {
                System.out.println(e.getProblem().getUserMessage());
            } else {
                System.out.println("Erro desconhecido");
                e.printStackTrace();
            }
        }
    }
}
