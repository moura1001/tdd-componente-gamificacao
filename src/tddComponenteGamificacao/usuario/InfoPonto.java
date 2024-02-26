package tddComponenteGamificacao.usuario;

import java.util.Objects;

public class InfoPonto implements Comparable<InfoPonto> {	
	private String _nomeUsuario;
	private TipoPonto _tipoPonto;
	private int _quantidadePontos;

	public InfoPonto(String nomeUsuario, String tipoPonto, int quantidadeDePontos) {
		TipoPonto tipo = TipoPonto.validarTipoPonto(tipoPonto);
		_nomeUsuario = nomeUsuario;
		_tipoPonto = tipo;
		_quantidadePontos = quantidadeDePontos;
	}

	InfoPonto(String nomeUsuario, TipoPonto tipoPonto, int quantidadeDePontos) {
		_nomeUsuario = nomeUsuario;
		_tipoPonto = tipoPonto;
		_quantidadePontos = quantidadeDePontos;
	}
	
	public String getNomeUsuario() {
		return _nomeUsuario;
	}

	public int getQuantidadeDePontos() {
		return _quantidadePontos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_nomeUsuario, _tipoPonto);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InfoPonto other = (InfoPonto) obj;
		return Objects.equals(_nomeUsuario, other._nomeUsuario) && _tipoPonto == other._tipoPonto
				&& _quantidadePontos == other._quantidadePontos;
	}

	@Override
	public int compareTo(InfoPonto info) {
		return Integer.compare(getQuantidadeDePontos(), info.getQuantidadeDePontos());
	}
}
