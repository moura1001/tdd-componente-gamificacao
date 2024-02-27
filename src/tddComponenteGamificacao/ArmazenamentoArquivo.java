package tddComponenteGamificacao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import tddComponenteGamificacao.usuario.InfoPonto;
import tddComponenteGamificacao.usuario.TipoPonto;
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

			while (line != null && line.length() > 1) {
				String[] registro = line.split(";");
				Usuario usuario = new Usuario(registro[0], lineCounter);
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
			throw arquivoInvalidoException(lineCounter, e);
		}
	}

	private ArmazenamentoArquivoException arquivoInvalidoException(int lineCounter, RuntimeException e) {
		return new ArmazenamentoArquivoException("Arquivo " + _nomeArquivo
				+ " é inválido. Não foi possível processar a linha " + lineCounter + ": " + e.getMessage());
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

	public void configurarArquivoDeArmazenamento(String arquivo) {
		_nomeArquivo = arquivo;
	}

	public void relerDadosDoArquivoDeArmazenamento() throws ArmazenamentoArquivoException {
		carregarDadosDoArquivo();
	}

	public void reescreverDadosNoArquivo() throws ArmazenamentoArquivoException {
		try {
			// limpa o conteúdo do arquivo
			new FileWriter(_nomeArquivo).close();
			
			FileWriter fr = new FileWriter(new File(_nomeArquivo), false);
			BufferedWriter writer = new BufferedWriter(fr);

			for (Usuario usuario : _usuarios.values()) {
				String registro = usuario.formatarRegistroParaLinhaDoArquivo();
				if (registro != null)
					writer.write(registro + System.lineSeparator());
			}

			writer.close();
			fr.close();
		} catch (IOException e) {
			throw problemaNaEscritaException(e);
		}
	}

	private ArmazenamentoArquivoException problemaNaEscritaException(IOException e) {
		return new ArmazenamentoArquivoException("Erro na escrita do arquivo " + _nomeArquivo + ": " + e.getMessage());
	}

	@Override
	public void armazenarPontos(String usuario, String tipoPonto, int quantidadePontos)
			throws ArmazenamentoArquivoException {
		if (_usuarios.containsKey(usuario)) {
			atualizarRegistroUsuario(usuario, tipoPonto, quantidadePontos);
		} else {
			adicionarRegistroUsuario(usuario, tipoPonto, quantidadePontos);
		}
	}

	private ArmazenamentoArquivoException entradaInvalidaException(String usuario, UsuarioException e) {
		return new ArmazenamentoArquivoException(
				"Entrada inválida para o usuário " + usuario + ": " + e.getMessage());
	}

	private void atualizarRegistroUsuario(String nomeUsuario, String tipoPonto, int quantidadePontos)
			throws ArmazenamentoArquivoException {
		try {
			Usuario usuario = _usuarios.get(nomeUsuario);
			usuario.adicionarPontos(tipoPonto, quantidadePontos);
			
			reescreverDadosNoArquivo();
		} catch (UsuarioException e) {
			throw entradaInvalidaException(nomeUsuario, e);
		}
	}

	private void adicionarRegistroUsuario(String nomeUsuario, String tipoPonto, int quantidadePontos)
			throws ArmazenamentoArquivoException {
		try {
			Usuario usuario = new Usuario(nomeUsuario, _usuarios.size() + 1);
			usuario.adicionarPontos(tipoPonto, quantidadePontos);
			
			// setup writer with append mode
			FileWriter fr = new FileWriter(new File(_nomeArquivo), true);
			BufferedWriter writer = new BufferedWriter(fr);

			String registro = usuario.formatarRegistroParaLinhaDoArquivo();
			if (registro != null)
				writer.write(registro + System.lineSeparator());
			_usuarios.put(usuario.getNome(), usuario);
		} catch (IOException e) {
			throw problemaNaEscritaException(e);
		}  catch (UsuarioException e) {
			throw entradaInvalidaException(nomeUsuario, e);
		}
	}

}
