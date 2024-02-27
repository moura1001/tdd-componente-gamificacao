package test.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import tddComponenteGamificacao.Armazenamento;
import tddComponenteGamificacao.ArmazenamentoArquivoException;
import tddComponenteGamificacao.usuario.InfoPonto;
import tddComponenteGamificacao.usuario.TipoPonto;
import tddComponenteGamificacao.usuario.Usuario;
import tddComponenteGamificacao.usuario.UsuarioException;

public class ArmazenamentoMock implements Armazenamento {
	
	private Map<String, Usuario> _usuarios;
	
	public ArmazenamentoMock() {
		Usuario rodrigo = new Usuario("rodrigo");
		rodrigo.adicionarPontos("estrela", 17);
		rodrigo.adicionarPontos("energia", 3);
		
		Usuario guerra = new Usuario("guerra");
		guerra.adicionarPontos("estrela", 25);
		guerra.adicionarPontos("moeda", 20);
		
		Usuario toco = new Usuario("toco");
		toco.adicionarPontos("energia", 10);
		
		Usuario fernandes = new Usuario("fernandes");
		fernandes.adicionarPontos("estrela", 19);
		
		_usuarios = new LinkedHashMap<String, Usuario>();
		_usuarios.putAll(Map.of(
				"rodrigo", rodrigo,
				"guerra", guerra,
				"toco", toco,
				"fernandes", fernandes
		));
	}

	@Override
	public int obterQuantidadeDePontos(String usuario, String tipoPonto) throws UsuarioException {
		if (_usuarios.containsKey(usuario)) {
			Usuario u = _usuarios.get(usuario);
			return u.obterQuantidadeDePontos(tipoPonto);
		}
		throw new UsuarioException("Usuário não existe");
	}

	@Override
	public SortedSet<InfoPonto> obterTodosOsUsuariosComTipoDePonto(String tipoPonto) throws UsuarioException {
		SortedSet<InfoPonto> usuarios = new TreeSet<>();
		for (Map.Entry<String, Usuario> entrada : _usuarios.entrySet()) {
			Usuario usuario = entrada.getValue();

			InfoPonto infoPonto = usuario.obterInfoSobrePontos(tipoPonto);
			if (infoPonto != null)
				usuarios.add(infoPonto);

		}
		return usuarios;
	}

	@Override
	public Map<TipoPonto, Integer> obterTodosOsTiposDePontoRegistradosParaOUsuario(String usuario)
			throws UsuarioException {
		if (_usuarios.containsKey(usuario)) {
			Usuario u = _usuarios.get(usuario);
			return u.obterPontos();
		}
		throw new UsuarioException("Usuário não existe");
	}

	@Override
	public void armazenarPontos(String usuario, String tipoPonto, int quantidadePontos)
			throws ArmazenamentoArquivoException {
		if (_usuarios.containsKey(usuario)) {
			Usuario u = _usuarios.get(usuario);
			u.adicionarPontos(tipoPonto, quantidadePontos);
		} else {
			Usuario u = new Usuario(usuario);
			u.adicionarPontos(tipoPonto, quantidadePontos);
			_usuarios.put(u.getNome(), u);
		}
	}

}
