package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

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
	
	@Nested
	@DisplayName("casos de teste para info gerais sobre os pontos recebidos")
	class InfoPontoGeral {
		@Test
		void deveriaObterTodosOsUsuariosQueReceberamUmTipoDePontoEspecifico() {
			Armazenamento armazenamento = new ArmazenamentoArquivo("src/test/dados-teste-leitura.txt");
			SortedMap<String, Integer> resultadoEsperado = new TreeMap<String, Integer>();
			resultadoEsperado.putAll(Map.of("rodrigo", 17, "guerra", 25, "fernandes", 19));
			SortedMap<String, Integer> resultadoObtido = armazenamento.obterTodosOsUsuariosComTipoDePonto("estrela");
			assertEquals(resultadoEsperado, resultadoObtido);
		}
	}

}
