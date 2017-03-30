package rodrigo.study.hibernate.model;

import java.math.BigDecimal;
import java.util.Set;

public class MovimentacaoDTO {

	private BigDecimal valor;
	private Set<Categoria> categorias;
	
	
	public MovimentacaoDTO(BigDecimal valor, Set<Categoria> categorias) {
		super();
		this.valor = valor;
		this.categorias = categorias;
	}
	
	public MovimentacaoDTO(Object m) {
		System.out.println(m.toString());
	}
	
	
	
	
	
}
