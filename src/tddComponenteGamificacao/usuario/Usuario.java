package tddComponenteGamificacao.usuario;

import java.util.LinkedHashMap;
import java.util.Map;

public class Usuario {
	private String _nome;
	private Map<String, Integer> _pontos = new LinkedHashMap<String, Integer>();

	public Usuario(String nome) {
		_nome = nome;
	}
	
	public String getNome() {
		return _nome;
	}

	public int obterQuantidadeDePontos(String tipoPonto) {
		if(_pontos.containsKey(tipoPonto))
			return _pontos.get(tipoPonto);
		return 0;
	}

	public void adicionarPontos(String tipoPonto, int quantidade) {
		int qtdAtual = obterQuantidadeDePontos(tipoPonto);
		_pontos.put(tipoPonto, qtdAtual + quantidade);
	}
}