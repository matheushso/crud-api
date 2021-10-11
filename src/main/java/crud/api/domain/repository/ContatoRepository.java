package crud.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import crud.api.domain.entity.Contato;

public interface ContatoRepository extends JpaRepository<Contato, Long>{

}
