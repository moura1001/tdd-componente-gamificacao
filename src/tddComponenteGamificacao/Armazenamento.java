package tddComponenteGamificacao;

import tddComponenteGamificacao.usuario.UsuarioException;

public interface Armazenamento {
	int obterQuantidadeDePontos(String usuario, String tipoPonto) throws UsuarioException;
}
