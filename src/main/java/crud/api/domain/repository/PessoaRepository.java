package crud.api.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import crud.api.domain.entity.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{
	
	List<Pessoa> findByNomeLikeIgnoreCase(String nome);
	
}
