package br.com.banco.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.banco.models.Conta;
import br.com.banco.models.Transferencia;
import br.com.banco.models.Usuario;

public class ImportadorTransferenciasService {
	private List<Conta> listaConta = new ArrayList<>();
	private List<Usuario> listaUsuario= new ArrayList<>();
	private List<Transferencia> listaTransferencia = new ArrayList<>();
	private int indexOrigem;
	private int indexDestino;
	Random ran = new Random();
	String valorFormatado;
	
	public void lerArquivoConta() {
		try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\gparreiras\\Downloads\\Apagar\\Contas.txt"))){
			while(reader.ready()) {
			String line = reader.readLine();
			Conta conta = new Conta();
			conta.setIdConta(Long.parseLong(line.substring(0,5)));
			conta.setIdUsuario(Long.parseLong(line.substring(5,10)));
			conta.setNumeroConta(Integer.parseInt(line.substring(10,18)));
			conta.setAgencia(Short.parseShort(line.substring(18,22)));
			conta.setSaldo(new BigDecimal(line.substring(22,30)).divide(BigDecimal.valueOf(100)));
		
			listaConta.add(conta);
					}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void lerArquivoUsuario() {
		try(BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\gparreiras\\Downloads\\Apagar\\Usuarios.txt"))) {
			while(reader.ready()) {
				String line = reader.readLine();
				String[] dados = line.split(";");
				Usuario usuario = new Usuario();
				usuario.setNome(dados[1]);
				usuario.setIdUsuario(Long.parseLong(dados[0]));
				usuario.setCpf(dados[3]);
				usuario.setDataNascimento(LocalDate.parse(dados[4]));
//				usuario.setEmail(dados[4]);
				usuario.setSenha(dados[2]);
				listaUsuario.add(usuario);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void gerarTransferencia(int i1) {
		for (int i = 0; i < i1; i++) {
			Transferencia trans = new Transferencia();
			lerArquivoConta();
			lerArquivoUsuario();
			indexDestino = ran.nextInt(listaConta.size());
			indexOrigem = ran.nextInt(listaConta.size());
			while (indexDestino == indexOrigem) {
				indexOrigem = ran.nextInt(listaConta.size());
			}
			trans.setValor(new BigDecimal(ran.nextDouble(10, 100)).setScale(2, BigDecimal.ROUND_HALF_UP));
			valorFormatado = String.format("%09.2f", trans.getValor()).replace(",","");
			trans.setData(geradorData());
			salvarTransferencias(trans);
		}
	}
	public LocalDateTime geradorData() {
		LocalDateTime date = null;
		int mes = ran.nextInt(12) + 1;
		int maxdia = LocalDate.of(2023, mes, 1).lengthOfMonth();
		int dia = ran.nextInt(maxdia) + 1;
		date = LocalDateTime.of(2023, mes, dia, ran.nextInt(24), ran.nextInt(60), ran.nextInt(60),
				ran.nextInt(1000) * 1000);
		return date;
	}
	public void salvarTransferencias(Transferencia trans) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(
				"C:\\Users\\gparreiras\\Downloads\\Apagar\\TransferenciasGeradas.txt",true))) {
			writer.write(listaUsuario.get(indexOrigem).getCpf() + "" + listaConta.get(indexOrigem).getAgencia() + ""
					+ listaConta.get(indexOrigem).getNumeroConta()+ "" + listaConta.get(indexDestino).getAgencia()
					+ "" + listaConta.get(indexDestino).getNumeroConta() + "" +valorFormatado + ""
					+ trans.getData());
			writer.flush();
			writer.newLine();
			
		} catch (IOException e) {
			e.getMessage();
		}
}
}
