package tddComponenteGamificacao.usuario;

import java.util.EnumMap;
import java.util.Map;

public class Usuario {
	private String _nome;
	private Map<TipoPonto, Integer> _pontos = new EnumMap<>(TipoPonto.class);
	private int _posicaoLinhaNoArquivo;

	public Usuario(String nome) {
		_nome = nome;
		_posicaoLinhaNoArquivo = 1;
	}
	
	public Usuario(String nome, int posicaoLinhaNoArquivo) {
		_nome = nome;
		_posicaoLinhaNoArquivo = posicaoLinhaNoArquivo >= 0 ? posicaoLinhaNoArquivo : -posicaoLinhaNoArquivo;
	}
	
	public String getNome() {
		return _nome;
	}
	
	public int getPosicaoLinhaNoArquivo() {
		return _posicaoLinhaNoArquivo - 1 >= 0 ? _posicaoLinhaNoArquivo - 1 : 0;
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

	public Map<TipoPonto, Integer> obterPontos() {
		Map<TipoPonto, Integer> pontos = new EnumMap<>(TipoPonto.class);
		pontos.putAll(_pontos);
		return pontos;
	}
	
	public boolean possuiPontos() {
		return !_pontos.isEmpty();
	}
	
	public String formatarRegistroParaLinhaDoArquivo() {
		if (possuiPontos()) {
			StringBuilder linhaRegistro = new StringBuilder(32);
			linhaRegistro.append(_nome + ";");
			for (Map.Entry<TipoPonto, Integer> entrada : _pontos.entrySet()) {
				TipoPonto key = entrada.getKey();
				Integer val = entrada.getValue();
				
				if (val > 0)
					linhaRegistro.append(key.name().toLowerCase() + ":" + val + ";");
			}
			// remove last semicolon
			linhaRegistro.deleteCharAt(linhaRegistro.length() - 1);
			return linhaRegistro.toString();
		}
		return null;
	}
}