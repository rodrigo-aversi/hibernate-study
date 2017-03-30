package rodrigo.study.hibernate.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Movimentacao {

	@Id
	@GeneratedValue
	private Long id;
	private BigDecimal valor;
	
	@ManyToMany
	private Set<Categoria> categorias = new HashSet<Categoria>();
	
	public Movimentacao() {
		// TODO Auto-generated constructor stub
	}

	public Movimentacao(BigDecimal valor, Set<Categoria> categoria) {
		super();
		this.valor = valor;
		this.categorias = categoria;
	}

	public void adicionaCategoria(Categoria categoria) {
		this.categorias.add(categoria);
	}

	@Override
	public String toString() {
		return "Movimentacao [id=" + id + ", valor=" + valor + ", categorias=" + categorias + "]";
	}
	
	
	
	
	
}
