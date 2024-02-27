package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.EnumMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tddComponenteGamificacao.Armazenamento;
import tddComponenteGamificacao.Placar;
import tddComponenteGamificacao.usuario.TipoPonto;
import test.utils.ArmazenamentoMock;

public class PlacarTest {
	private Armazenamento armazenamento;
	private Placar placar;
	
	@BeforeEach
	void setup() {
		armazenamento = new ArmazenamentoMock();
		placar = new Placar(armazenamento);
	}
	
	@Test
	void deveriaRegistrarPontosParaUmDeterminadoUsuario() {
		placar.registrarPontos("usuario123", "moeda", 50);
		placar.registrarPontos("usuario123", "estrela", 50);
		
		int quantidadePontos = placar.consultarQuantidadeDePontos("usuario123", "moeda");
		assertEquals(50, quantidadePontos);
		quantidadePontos = placar.consultarQuantidadeDePontos("usuario123", "estrela");
		assertEquals(50, quantidadePontos);
	}
	
	@Test
	void deveriaObterTodosOsPontosJaRegistradosParaUmDeterminadoUsuario() {
		Map<TipoPonto, Integer> resultadoEsperado = new EnumMap<>(TipoPonto.class);
		resultadoEsperado.putAll(Map.of(
				TipoPonto.MOEDA, 20,
				TipoPonto.ESTRELA, 25
		));
		Map<TipoPonto, Integer> resultadoObtido = placar.obterTodosOsTiposDePontoRegistradosParaOUsuario("guerra");
		assertEquals(resultadoEsperado, resultadoObtido);
	}

}
