package crud.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import crud.api.domain.entity.Pessoa;
import crud.api.domain.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
	
	@Autowired
	private PessoaService pessoaService;
	
	@GetMapping
	public List<Pessoa> getPessoa(String nome) {
		return pessoaService.buscarPeloTermo(nome);
	}
	
	@GetMapping("/{id}")
	public Pessoa getPessoaById(@PathVariable Long id) {
		return pessoaService.buscarPeloId(id);
	}
	
	@PostMapping
	public ResponseEntity<Pessoa> post(@RequestBody @Valid Pessoa pessoa) {
		pessoaService.salvar(pessoa);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoa);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Pessoa> put(@PathVariable Long id, @RequestBody Pessoa pessoa) {
		Pessoa pessoaAlterada = pessoaService.alterar(id, pessoa);
		
		if (pessoaAlterada != null) {
			return ResponseEntity.status(HttpStatus.OK).body(pessoaAlterada);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		boolean findById = pessoaService.excluirPeloId(id);
		
		if (findById) {
			return ResponseEntity.ok("");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Objeto não encontrado!");
	}
	
}
