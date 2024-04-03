package br.com.banco.service;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.banco.models.Transferencia;

public class ConsumoAPI {

	public boolean transferencia() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate rest = new RestTemplate();

		try (BufferedReader reader = new BufferedReader(
				new FileReader("C:\\Users\\hnantes\\Documents\\BancoDigitalEquipe1\\TransferenciasGeradas.txt"))) {
			while (reader.ready()) {
				String line = reader.readLine();
				
				Transferencia transf = new Transferencia();

//				transf.setCpf(line.substring(0, 11));
				transf.setAgenciaOrigem(Short.parseShort(line.substring(11, 15)));
				transf.setNumeroContaOrigem(Integer.parseInt(line.substring(15, 23)));

				transf.setAgenciaDestino(Short.parseShort(line.substring(23, 27)));
				transf.setNumeroContaDestino(Integer.parseInt(line.substring(27, 35)));

				transf.setValor(new BigDecimal(line.substring(35, 43)).divide(new BigDecimal(100)));

				transf.setData(LocalDateTime.parse(line.substring(43, 62),
						DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
				transf.setTipo("TED");

				HttpEntity<Transferencia> resquestEntity = new HttpEntity(transf, headers);
				String url = "http://localhost:8080/Transferencia/transferir";
				ResponseEntity<String> response = rest.postForEntity(url, resquestEntity, String.class);

			}

			return true;
		}

		catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

}
