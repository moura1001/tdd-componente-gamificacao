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
		TipoPonto tipo = validarTipoPonto(tipoPonto);
		return obterQuantidadeDePontos(tipo);
	}

	public void adicionarPontos(String tipoPonto, int quantidade) throws UsuarioException {
		TipoPonto tipo = validarTipoPonto(tipoPonto);
		int qtdAtual = obterQuantidadeDePontos(tipo);
		_pontos.put(tipo, qtdAtual + quantidade);
	}
	
	private TipoPonto validarTipoPonto(String tipoPonto) {
		TipoPonto tipo = TipoPonto.encontrarPeloNome(tipoPonto);
		if(tipo == null)
			throw new UsuarioException("Tipo de ponto inválido: " + tipoPonto);
		return tipo;
	}
	
	private int obterQuantidadeDePontos(TipoPonto tipo) {
		if(_pontos.containsKey(tipo))
			return _pontos.get(tipo);
		return 0;
	}
}