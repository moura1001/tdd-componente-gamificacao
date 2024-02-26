package tddComponenteGamificacao;

import java.util.SortedMap;

import tddComponenteGamificacao.usuario.UsuarioException;

public interface Armazenamento {
	int obterQuantidadeDePontos(String usuario, String tipoPonto) throws UsuarioException;
	
	SortedMap<String, Integer> obterTodosOsUsuariosComTipoDePonto(String tipoPonto) throws UsuarioException;
}
