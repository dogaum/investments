package br.com.dabage.investments.repositories;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.dabage.investments.carteira.CarteiraTO;
import br.com.dabage.investments.carteira.NegotiationTO;

public interface NegotiationRepository extends MongoRepository<NegotiationTO, BigInteger> {

	NegotiationTO findOne(BigInteger id);

	@SuppressWarnings("unchecked")
	NegotiationTO save (NegotiationTO negotiationTO);

	List<NegotiationTO> findByCarteira(CarteiraTO carteira);
}
