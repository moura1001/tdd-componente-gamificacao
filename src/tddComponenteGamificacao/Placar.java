package tddComponenteGamificacao;

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

}
