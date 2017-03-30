package rodrigo.study.hibernate.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Categoria {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String nome;
	
	public Categoria() {
		// TODO Auto-generated constructor stub
	}

	public Categoria(String nome) {
		super();
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "Categoria [id=" + id + ", nome=" + nome + "]";
	}
	
	
	
	

}
