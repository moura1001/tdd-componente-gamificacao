package tddComponenteGamificacao;

import java.util.Map;
import java.util.SortedSet;

import tddComponenteGamificacao.usuario.InfoPonto;
import tddComponenteGamificacao.usuario.TipoPonto;

public class Placar {

	private Armazenamento _armazenamento;

	public Placar(Armazenamento armazenamento) {
		_armazenamento = armazenamento;
	}

	public void registrarPontos(String nomeUsuario, String tipoPonto, int quantidadePontos) {
		_armazenamento.armazenarPontos(nomeUsuario, tipoPonto, quantidadePontos);
	}

	public int consultarQuantidadeDePontos(String nomeUsuario, String tipoPonto) {
		return _armazenamento.obterQuantidadeDePontos(nomeUsuario, tipoPonto);
	}

	public Map<TipoPonto, Integer> obterTodosOsTiposDePontoRegistradosParaOUsuario(String nomeUsuario) {
		return _armazenamento.obterTodosOsTiposDePontoRegistradosParaOUsuario(nomeUsuario);
	}

	public SortedSet<InfoPonto> obterRankingDeUsuariosParaOTipoDePonto(String tipoPonto) {
		return _armazenamento.obterTodosOsUsuariosComTipoDePonto(tipoPonto);
	}

}
