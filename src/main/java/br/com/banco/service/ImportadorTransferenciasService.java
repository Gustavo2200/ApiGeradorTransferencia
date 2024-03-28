package br.com.banco.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.banco.models.Conta;
import br.com.banco.models.Transferencia;

public class ImportadorTransferenciasService {
	private List<Conta> listaConta = new ArrayList<>();
	private List<String> listaCpf = new ArrayList<>();
	private List<Transferencia> listaTransferencia = new ArrayList<>();
	
	public void lerArquivoConta() {
		try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\ebabetto\\Documents\\Projetos\\BancoEquipe1\\Contas.txt"))){
			while(reader.ready()) {
			String line = reader.readLine();
			Conta conta = new Conta();
			conta.setIdConta(Long.parseLong(line.substring(0,1)));
			conta.setIdUsuario(Long.parseLong(line.substring(1,2)));
			conta.setNumeroConta(Integer.parseInt(line.substring(2,10)));
			conta.setAgencia(Short.parseShort(line.substring(10,14)));
			conta.setSaldo(new BigDecimal(line.substring(14,22)).divide(BigDecimal.valueOf(100)));
			conta.setLimiteCredito(new BigDecimal(line.substring(22,25)));
			listaConta.add(conta);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
