package br.com.caelum.eats.recomendacao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.lognet.springboot.grpc.GRpcService;

import br.com.caelum.eats.recomendacoes.grpc.Recomendacoes.Restaurantes;
import br.com.caelum.eats.recomendacoes.grpc.RecomendacoesDeRestaurantesGrpc.RecomendacoesDeRestaurantesImplBase;
import io.grpc.stub.StreamObserver;

@GRpcService
public class RecomendacaoService extends RecomendacoesDeRestaurantesImplBase {

	@Override
	public void recomendacoes(Restaurantes request, StreamObserver<Restaurantes> responseObserver) {
		List<Long> idsDeRestaurantes = request.getRestauranteIdList();
		List<Long> idsDeRestaurantesOrdenadosPorRecomendacoes = idsDeRestaurantes;
		if (idsDeRestaurantes.size() > 1) {
			idsDeRestaurantesOrdenadosPorRecomendacoes = new ArrayList<Long>(idsDeRestaurantes);
			Collections.shuffle(idsDeRestaurantesOrdenadosPorRecomendacoes);
		}

		Restaurantes response = Restaurantes.newBuilder()
				.addAllRestauranteId(idsDeRestaurantesOrdenadosPorRecomendacoes).build();
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}
}
