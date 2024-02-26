package tddComponenteGamificacao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import tddComponenteGamificacao.usuario.Usuario;
import tddComponenteGamificacao.usuario.UsuarioException;

public class ArmazenamentoArquivo implements Armazenamento {

	private Map<String, Usuario> _usuarios = new LinkedHashMap<String, Usuario>();
	private String _nomeArquivo;

	public ArmazenamentoArquivo(String nomeArquivo) {
		_nomeArquivo = nomeArquivo;
		carregarDadosDoArquivo();
	}

	@Override
	public int obterQuantidadeDePontos(String usuario, String tipoPonto) throws UsuarioException {
		if (_usuarios.containsKey(usuario)) {
			Usuario u = _usuarios.get(usuario);
			return u.obterQuantidadeDePontos(tipoPonto);
		}
		throw new UsuarioException("Usuário não existe");
	}

	private void carregarDadosDoArquivo() throws ArmazenamentoArquivoException {
		int lineCounter = 1;
		BufferedReader reader;

		try {
			reader = new BufferedReader(new FileReader(_nomeArquivo));
			// first line
			String line = reader.readLine();

			while (line != null) {
				String[] registro = line.split(";");
				Usuario usuario = new Usuario(registro[0]);
				_usuarios.put(usuario.getNome(), usuario);

				for (String coluna : registro) {
					if (coluna.equals(usuario.getNome()))
						continue;

					String[] infoPonto = coluna.split(":");
					usuario.adicionarPontos(infoPonto[0], Integer.valueOf(infoPonto[1]));
				}

				// next line
				lineCounter++;
				line = reader.readLine();
			}

			reader.close();
		} catch (IOException e) {
			throw new ArmazenamentoArquivoException(
					"Erro no processamento do arquivo " + _nomeArquivo + ": " + e.getMessage());
		} catch (NumberFormatException | UsuarioException e) {
			throw arquivoInvalido(lineCounter, e);
		}
	}

	private ArmazenamentoArquivoException arquivoInvalido(int lineCounter, RuntimeException e) {
		return new ArmazenamentoArquivoException("Arquivo " + _nomeArquivo
				+ " é inválido. Não foi possível processar a linha " + lineCounter + ": " + e.getMessage());
	}

	@Override
	public SortedMap<String, Integer> obterTodosOsUsuariosComTipoDePonto(String tipoPonto) throws UsuarioException {
		SortedMap<String, Integer> usuarios = new TreeMap<String, Integer>();
		for (Map.Entry<String, Usuario> entrada : _usuarios.entrySet()) {
			String nomeUsuario = entrada.getKey();
			Usuario usuario = entrada.getValue();
			
			int quantidadeDePontos = usuario.obterQuantidadeDePontos(tipoPonto);
			if(quantidadeDePontos > 0)
				usuarios.put(nomeUsuario, quantidadeDePontos);
				
		}
		return usuarios;
	}

}
