package tddComponenteGamificacao.usuario;

import java.util.EnumMap;
import java.util.Map;

public class Usuario {
	private String _nome;
	private Map<TipoPonto, Integer> _pontos = new EnumMap<>(TipoPonto.class);

	public Usuario(String nome) {
		_nome = nome;
	}
	
	public String getNome() {
		return _nome;
	}
	
	public int obterQuantidadeDePontos(String tipoPonto) throws UsuarioException {
		TipoPonto tipo = TipoPonto.validarTipoPonto(tipoPonto);
		return obterQuantidadeDePontos(tipo);
	}

	public void adicionarPontos(String tipoPonto, int quantidade) throws UsuarioException {
		TipoPonto tipo = TipoPonto.validarTipoPonto(tipoPonto);
		int qtdAtual = obterQuantidadeDePontos(tipo);
		_pontos.put(tipo, qtdAtual + quantidade);
	}
	
	private int obterQuantidadeDePontos(TipoPonto tipo) {
		if(_pontos.containsKey(tipo))
			return _pontos.get(tipo);
		return 0;
	}

	public InfoPonto obterInfoSobrePontos(String tipoPonto) {
		TipoPonto tipo = TipoPonto.validarTipoPonto(tipoPonto);
		int qtdPontos = obterQuantidadeDePontos(tipo);
		if(qtdPontos > 0) {
			return new InfoPonto(getNome(), tipo, qtdPontos);
		}
		return null;
	}
}