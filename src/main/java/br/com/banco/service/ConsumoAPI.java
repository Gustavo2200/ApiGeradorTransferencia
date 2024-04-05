package br.com.banco.service;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.banco.models.Transferencia;

public class ConsumoAPI {

	public boolean transferencia() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate rest = new RestTemplate();

		try (BufferedReader reader = new BufferedReader(
				new FileReader("C:\\Users\\gparreiras\\Downloads\\Apagar\\TransferenciasGeradas.txt"))) {
			int contador = 0;
			while (reader.ready()) {
				try {
					String line = reader.readLine();

					Transferencia transf = new Transferencia();

//				transf.setCpf(line.substring(0, 11));
					transf.setAgenciaOrigem(Short.parseShort(line.substring(11, 15)));
					System.out.println(transf.getAgenciaOrigem());
					transf.setNumeroContaOrigem(Integer.parseInt(line.substring(15, 23)));
					System.out.println(transf.getNumeroContaOrigem());
					transf.setAgenciaDestino(Short.parseShort(line.substring(23, 27)));
					System.out.println(transf.getAgenciaDestino());
					transf.setNumeroContaDestino(Integer.parseInt(line.substring(27, 35)));
					System.out.println(transf.getNumeroContaDestino());
					transf.setValor(new BigDecimal(line.substring(35, 43)).divide(new BigDecimal(100)));
					System.out.println(transf.getValor());
					transf.setData(LocalDateTime.parse(line.substring(43, 62),
							DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
					System.out.println(transf.getData());
					transf.setTipo("TED");

					HttpEntity<Transferencia> resquestEntity = new HttpEntity(transf, headers);
					String url = "http://localhost:8080/Transferencia/transferir";
					ResponseEntity<String> response = rest.postForEntity(url, resquestEntity, String.class);

				}catch (HttpClientErrorException e){
					if(e.getStatusCode().equals(HttpStatus.NOT_ACCEPTABLE)) {
						contador++;
					}else {
						throw new RuntimeException(e.getMessage());
					}
				}

			}
			if(contador > 0) {
				throw new RuntimeException("Nao foi concluido " + contador + " transferência(s) pois o saldo é insuficiente");
			}

			return true;
		}

		catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

}
