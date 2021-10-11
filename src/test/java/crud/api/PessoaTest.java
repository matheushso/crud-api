package crud.api;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

import crud.api.domain.entity.Contato;
import crud.api.domain.entity.Pessoa;
import io.restassured.RestAssured;

@SpringBootTest(
webEnvironment = WebEnvironment.RANDOM_PORT
		)
public class PessoaTest {
	
	private static String nome = "Nome";
	private static String cpf = "533.981.950-75";
	private static String nomeContato = "Nome contato";
	private static String telefone = "(44) 3030-3030";
	private static String email = "email@hotmail.com";
	
	@LocalServerPort
	private Integer port;
	
	@Test
	public void getPessoaById() throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date dataNascimento = format.parse("02/01/1999 22:00:00");
		List<Contato> contatos = new ArrayList<>();
		
		Contato contato = mockContato(nomeContato, telefone, email);
		contatos.add(contato);
		
		Pessoa pessoa = mockPessoa(nome, cpf, dataNascimento, contatos);
		
		Pessoa postPessoa = postPessoa(pessoa);
		Pessoa getPessoaById = getPessoaById(postPessoa.getId());
		
		assertEquals(pessoa.getNome(), getPessoaById.getNome());
		assertEquals(pessoa.getCpf(), getPessoaById.getCpf());
		assertEquals(pessoa.getDataNascimento(), getPessoaById.getDataNascimento());
		
		assertEquals(pessoa.getContato().get(0).getNome(), getPessoaById.getContato().get(0).getNome());
		assertEquals(pessoa.getContato().get(0).getTelefone(), getPessoaById.getContato().get(0).getTelefone());
		assertEquals(pessoa.getContato().get(0).getEmail(), getPessoaById.getContato().get(0).getEmail());
	}
	
	@Test
	public void getPessoaByNome() throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date dataNascimento = format.parse("02/01/1999 22:00:00");
		List<Contato> contatos = new ArrayList<>();
		
		Contato contato = mockContato(nomeContato, telefone, email);
		contatos.add(contato);
		
		Pessoa pessoa = mockPessoa(nome, cpf, dataNascimento, contatos);
		Pessoa pessoa2 = mockPessoa("Pessoa", cpf, dataNascimento, contatos);
		
		postPessoa(pessoa);
		postPessoa(pessoa2);
		
		Pessoa[] pessoaByNome = getPessoaByNome("Pe");
		
		assertEquals(pessoa2.getNome(), pessoaByNome[0].getNome());
		assertEquals(pessoa2.getCpf(), pessoaByNome[0].getCpf());
		assertEquals(pessoa2.getDataNascimento(), pessoaByNome[0].getDataNascimento());
	}
	
	@Test
	public void postPessoa() throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date dataNascimento = format.parse("01/01/1999");
		List<Contato> contatos = new ArrayList<>();
		
		Contato contato = mockContato(nomeContato, telefone, email);
		contatos.add(contato);
		
		Pessoa pessoa = mockPessoa(nome, cpf, dataNascimento, contatos);
		
		postPessoa(pessoa);
	}
	
	@Test
	public void putPessoa() throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date dataNascimento = format.parse("01/01/1999");
		List<Contato> contatos = new ArrayList<>();
		
		Contato contato = mockContato(nomeContato, telefone, email);
		contatos.add(contato);
		
		Pessoa pessoa = mockPessoa(nome, cpf, dataNascimento, contatos);
		
		Pessoa postPessoa = postPessoa(pessoa);
		
		Pessoa pessoaAtualizada = mockPessoa("Nome Atualizado", cpf, dataNascimento, contatos);
		
		putPessoa(postPessoa.getId(), pessoaAtualizada);
	}
	
	@Test
	public void deletePessoa() throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date dataNascimento = format.parse("01/01/1999");
		List<Contato> contatos = new ArrayList<>();
		
		Contato contato = mockContato(nomeContato, telefone, email);
		contatos.add(contato);
		
		Pessoa pessoa = mockPessoa(nome, cpf, dataNascimento, contatos);
		
		Pessoa postPessoa = postPessoa(pessoa);
		
		deletePessoa(postPessoa.getId());
	}
	
	private Pessoa mockPessoa(String nome, String cpf, Date dataNascimento, List<Contato> contatos) {
		return Pessoa.builder()
				.nome(nome)
				.cpf(cpf)
				.dataNascimento(dataNascimento)
				.contato(contatos)
				.build();
	}
	
	private Contato mockContato(String nomeContato, String telefone, String email) {
		return Contato.builder()
				.nome(nomeContato)
				.telefone(telefone)
				.email(email)
				.build();
	}
	
	private Pessoa getPessoaById(Long id) {
		return RestAssured
			.given()
				.contentType("application/json")
			.when()
				.get("http://localhost:" + port + "/pessoas/{id}", id).as(Pessoa.class);
	}
	
	private Pessoa[] getPessoaByNome(String nome) {
		return RestAssured
			.given()
				.contentType("application/json")
			.when()
				.get("http://localhost:" + port + "/pessoas?nome={nome}", nome).as(Pessoa[].class);
	}
	
	private Pessoa postPessoa(Pessoa pessoa) {
		return RestAssured
			.given()
				.contentType("application/json")
				.body(pessoa)
			.when()
				.post("http://localhost:" + port + "/pessoas").as(Pessoa.class);
	}
	
	private void putPessoa(Long id, Pessoa pessoaAtualizada) {
		RestAssured
		.given()
			.contentType("application/json")
			.body(pessoaAtualizada)
		.when()
			.put("http://localhost:" + port + "/pessoas/{id}", id)
		.then()
			.statusCode(200);
	}
	
	private void deletePessoa(Long id) {
		RestAssured
		.given()
			.contentType("application/json")
		.when()
			.delete("http://localhost:" + port + "/pessoas/{id}", id)
		.then()
			.statusCode(200);
	}
}
