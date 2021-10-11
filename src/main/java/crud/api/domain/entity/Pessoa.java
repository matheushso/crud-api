package crud.api.domain.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "pessoa")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pessoa {
	
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull @NotEmpty
	private String nome;
	
	@NotNull @NotEmpty
	private String cpf;
	
	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dataNascimento;
	
	@OneToMany(cascade = CascadeType.ALL)
	@Valid @NotNull
	private List<Contato> contato;
}