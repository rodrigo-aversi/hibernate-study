package rodrigo.study.hibernate.model;

import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class ContaTest {

	public static void main(String[] args) {

		Conta conta = new Conta();
		conta.setAgencia("123");
		conta.setBanco("Bradesco");
		conta.setDataCriacao(Calendar.getInstance());
		conta.setNumero("123456-7");
		conta.setTitular("Rodrigo");

		EntityManager entityManager = Persistence.createEntityManagerFactory(
				"tarefas").createEntityManager();

		entityManager.getTransaction().begin();
		entityManager.persist(conta);
		entityManager.getTransaction().commit();
		entityManager.close();

	}

}
