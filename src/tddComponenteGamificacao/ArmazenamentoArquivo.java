package tddComponenteGamificacao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ArmazenamentoArquivo implements Armazenamento {

	private Map<String, LinkedHashMap<String, Integer>> usuarios = new LinkedHashMap<String, LinkedHashMap<String, Integer>>();
	private String nomeArquivo;

	public ArmazenamentoArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
		carregarDadosDoArquivo();
	}

	@Override
	public int obterQuantidadeDePontos(String usuario, String tipoPonto) {
		if (this.usuarios.containsKey(usuario)) {
			LinkedHashMap<String, Integer> pontos = this.usuarios.get(usuario);
			if(pontos.containsKey(tipoPonto))
				return pontos.get(tipoPonto);
		}
		return 0;
	}

	private void carregarDadosDoArquivo() throws ArmazenamentoArquivoException {
		int lineCounter = 1;
		BufferedReader reader;

		try {
			reader = new BufferedReader(new FileReader(this.nomeArquivo));
			// first line
			String line = reader.readLine();

			while (line != null) {
				String[] registro = line.split(";");
				String usuario = registro[0];
				LinkedHashMap<String, Integer> pontos = new LinkedHashMap<String, Integer>();
				this.usuarios.put(usuario, pontos);

				for (String coluna : registro) {
					if (coluna.equals(usuario))
						continue;

					String[] infoPonto = coluna.split(":");
					pontos.put(infoPonto[0], Integer.valueOf(infoPonto[1]));
				}

				// next line
				lineCounter++;
				line = reader.readLine();
			}

			reader.close();
		} catch (IOException e) {
			throw new ArmazenamentoArquivoException(
					"Erro no processamento do arquivo " + nomeArquivo + ": " + e.getMessage());
		} catch (NumberFormatException e) {
			throw new ArmazenamentoArquivoException("Arquivo " + nomeArquivo
					+ " é inválido. Não foi possível processar a linha " + lineCounter + ": " + e.getMessage());
		}
	}

}
