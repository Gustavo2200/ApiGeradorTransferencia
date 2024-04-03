package br.com.banco.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.com.banco.models.Transferencia;

@RestController
@RequestMapping("/consulta")
public class ConsumoAPI {

	@PostMapping("/transferencia")
	public boolean returnTransf(Transferencia transferencia) {
		RestTemplate rest = new RestTemplate();
		try (BufferedReader read = new BufferedReader(new FileReader(""))) {
			while (read.ready())

				
				
				return false;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return false;
	}
}
