package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import tddComponenteGamificacao.Armazenamento;
import tddComponenteGamificacao.ArmazenamentoArquivo;

class ArmazenamentoTest {

	@Nested
	@DisplayName("casos de teste para info sobre pontos de tipos específicos")
	class InfoPontoEspecifico {
		@Test
		void deveriaInformarQueOUsuarioGuerraPossui25PontosDoTipoEstrela() {
			Armazenamento armazenamento = new ArmazenamentoArquivo("src/test/dados-teste-leitura.txt");
			int quantidadePontos = armazenamento.obterQuantidadeDePontos("guerra", "estrela");
			assertEquals(25, quantidadePontos);
		}
	}

}
