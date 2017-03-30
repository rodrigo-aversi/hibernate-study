package rodrigo.study.hibernate.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.junit.Test;

public class MovimentacaoTest {
	
	
	@Test
	public void teste1() {
		
		EntityManager entityManager = Persistence.createEntityManagerFactory(
				"tarefas").createEntityManager();
		
		entityManager.getTransaction().begin();
		
		Categoria categoria = new Categoria("Alimentação");
		Set<Categoria> categorias = new HashSet<Categoria>();
		categorias.add(categoria);
		
		entityManager.persist(categoria);
		
		
		Movimentacao movimentacao = new Movimentacao(new BigDecimal("100.00"), categorias);
		
		entityManager.persist(movimentacao);

		entityManager.getTransaction().commit();
		entityManager.close();
		
	}
	
	@Test
	public void teste2() {
		
		
		EntityManager entityManager = Persistence.createEntityManagerFactory(
				"tarefas").createEntityManager();
		
		entityManager.getTransaction().begin();
		
		Categoria categoria = new Categoria("Transporte");
		entityManager.persist(categoria);
		
		Movimentacao movimentacao = entityManager.find(Movimentacao.class, 1L);
		movimentacao.adicionaCategoria(categoria);
		
		

		entityManager.getTransaction().commit();
		entityManager.close();
		
	}

}
