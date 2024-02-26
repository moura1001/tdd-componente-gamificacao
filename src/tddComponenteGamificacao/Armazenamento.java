package tddComponenteGamificacao;

import java.util.SortedSet;

import tddComponenteGamificacao.usuario.InfoPonto;
import tddComponenteGamificacao.usuario.UsuarioException;

public interface Armazenamento {
	int obterQuantidadeDePontos(String usuario, String tipoPonto) throws UsuarioException;
	
	SortedSet<InfoPonto> obterTodosOsUsuariosComTipoDePonto(String tipoPonto) throws UsuarioException;
}
