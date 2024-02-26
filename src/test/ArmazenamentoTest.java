package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import tddComponenteGamificacao.Armazenamento;
import tddComponenteGamificacao.ArmazenamentoArquivo;
import tddComponenteGamificacao.usuario.UsuarioException;

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
		
		@Test
		void deveriaInformarQueOUsuarioRodrigoPossui3PontosDoTipoEnergia() {
			Armazenamento armazenamento = new ArmazenamentoArquivo("src/test/dados-teste-leitura.txt");
			int quantidadePontos = armazenamento.obterQuantidadeDePontos("rodrigo", "energia");
			assertEquals(3, quantidadePontos);
		}
		
		@Test
		void deveriaLancarExcecaoParaConsultaDeUsuarioInvalido() {
			Armazenamento armazenamento = new ArmazenamentoArquivo("src/test/dados-teste-leitura.txt");
			UsuarioException thrown = assertThrows(UsuarioException.class, () -> {
				armazenamento.obterQuantidadeDePontos("unknow", "moeda");
	        });
	        assertEquals("Usuário não existe", thrown.getMessage());
		}
		
		@Test
		void deveriaLancarExcecaoParaConsultaDeTipoDePontoInvalido() {
			Armazenamento armazenamento = new ArmazenamentoArquivo("src/test/dados-teste-leitura.txt");
			UsuarioException thrown = assertThrows(UsuarioException.class, () -> {
				armazenamento.obterQuantidadeDePontos("toco", "unknow");
	        });
	        assertEquals("Tipo de ponto inválido: unknow", thrown.getMessage());
		}
	}

}
