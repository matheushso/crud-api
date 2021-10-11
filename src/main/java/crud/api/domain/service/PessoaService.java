package crud.api.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crud.api.domain.entity.Pessoa;
import crud.api.domain.repository.PessoaRepository;

@Service
@Transactional
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Pessoa buscarPeloId(Long id) {
		return pessoaRepository.findById(id).orElse(null);
	}
	
	public List<Pessoa> buscarPeloTermo(String nome) {
		if (nome != null && nome.trim().length() != 0) {
			return pessoaRepository.findByNomeLikeIgnoreCase("%" + nome + "%");
		}
		return pessoaRepository.findAll();
	}
	
	public Pessoa salvar(Pessoa pessoa) {
		return pessoaRepository.save(pessoa);
	}
	
	public Pessoa alterar(Long id, Pessoa pessoa) {
		Pessoa findEscola = pessoaRepository.findById(id).orElse(null);
		
		if(findEscola != null) {
			pessoa.setId(id);
			return pessoaRepository.save(pessoa);
		}
		return null;
	}
	
	public boolean excluirPeloId(Long id) {
		if (buscarPeloId(id) != null) {
			pessoaRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
    }
}
