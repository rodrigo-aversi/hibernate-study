package rodrigo.study.hibernate.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.Test;

public class ConsultaMovimentacao {
	
	@Test
	public void test1() {
		 
		String jpql = "select distinct new rodrigo.study.hibernate.model.MovimentacaoDTO( m.valor, m.categorias ) "
				+ " from Movimentacao m join fetch m.categorias c ";
		
		EntityManager entityManager = Persistence.createEntityManagerFactory(
				"tarefas").createEntityManager();
		
		entityManager.getTransaction().begin();
		
		TypedQuery<MovimentacaoDTO> query = entityManager.createQuery(jpql, MovimentacaoDTO.class);
		List<MovimentacaoDTO> list = query.getResultList();
		
		entityManager.getTransaction().commit();
		
		System.out.println(list);
				
		
	}
	
	@Test
	public void test2() {
		
		String jpql = "select distinct m from Movimentacao m join fetch m.categorias ";
		
		EntityManager entityManager = Persistence.createEntityManagerFactory(
				"tarefas").createEntityManager();
		
		TypedQuery<Movimentacao> query = entityManager.createQuery(jpql, Movimentacao.class);
		List<Movimentacao> list = query.getResultList();
		
		System.out.println(list);
		
	}

}
