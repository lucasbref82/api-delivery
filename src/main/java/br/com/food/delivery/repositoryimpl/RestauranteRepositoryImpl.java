package br.com.food.delivery.repositoryimpl;

import static br.com.food.delivery.repository.spec.RestauranteSpecs.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import br.com.food.delivery.domain.model.Restaurante;
import br.com.food.delivery.repository.RestauranteRepository;
import br.com.food.delivery.repository.RestauranteRepositoryQueries;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries{

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired @Lazy
	private RestauranteRepository restauranteRepository;

	@Override
	public List<Restaurante> buscarComFiltro(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {

		var jpql = new StringBuilder();

		jpql.append("from Restaurante where 0 = 0 ");

		var parametros = new HashMap<String, Object>();

		if (StringUtils.hasLength(nome)) {
			jpql.append("and nome like :nome ");
			parametros.put("nome", "%" + nome + "%");
		}

		if (taxaFreteInicial != null) {
			jpql.append("and taxaFrete >= :taxaInicial ");
			parametros.put("taxaInicial", taxaFreteInicial);
		}

		if (taxaFreteFinal != null) {
			jpql.append("and taxaFrete <= :taxaFinal ");
			parametros.put("taxaFinal", taxaFreteFinal);
		}

		TypedQuery<Restaurante> query = manager.createQuery(jpql.toString(), Restaurante.class);

		parametros.forEach((chave, valor) -> query.setParameter(chave, valor));

		return query.getResultList();
	}

	@Override
	public List<Restaurante> buscarComCriteria(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {

		CriteriaBuilder builder = manager.getCriteriaBuilder();

		CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
		
		Root<Restaurante> root = criteria.from(Restaurante.class);
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(StringUtils.hasText(nome)) {
			Predicate nomePredicate = builder.like(root.get("nome"), "%" + nome + "%");
			predicates.add(nomePredicate);
		}
		
		if (taxaFreteInicial != null) {
			Predicate taxaFreteInicialPredicade = builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial);
			predicates.add(taxaFreteInicialPredicade);
		}
		
		if (taxaFreteFinal != null) {
			Predicate taxaFreteInicialPredicade = builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal);
			predicates.add(taxaFreteInicialPredicade);
		}
		
		criteria.where(predicates.toArray(new Predicate[0]));

		return manager.createQuery(criteria).getResultList();
	}

	@Override
	public List<Restaurante> listarComFreteGratis(String nome) {
		return restauranteRepository.findAll(comFreteGratis().and(nomeSemelhante(nome)));
	}
	

}
