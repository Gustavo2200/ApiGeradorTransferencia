package br.com.banco.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.banco.service.ConsumoAPI;
import br.com.banco.service.ImportadorTransferenciasService;

@Controller
@RequestMapping("/transferencia")
public class ImportadorTransferenciasController {
	private ImportadorTransferenciasService its = new ImportadorTransferenciasService();
	private ConsumoAPI consu = new ConsumoAPI();
	@PostMapping("/salvar/{i}")
	public ResponseEntity<String> salvar (@PathVariable int i){
		its.gerarTransferencia(i);
		return new ResponseEntity<>("transferencias geradas",HttpStatus.OK);
	}
	
	@PostMapping("/importar")
	public ResponseEntity<String> importar (){
		if (consu.transferencia()) {
		 return new ResponseEntity<>("Importado com sucesso", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Importacao nao sucedida", HttpStatus.BAD_REQUEST);
		}
	}
}
