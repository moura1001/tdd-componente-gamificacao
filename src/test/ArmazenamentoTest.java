package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import tddComponenteGamificacao.Armazenamento;
import tddComponenteGamificacao.ArmazenamentoArquivo;
import tddComponenteGamificacao.usuario.InfoPonto;
import tddComponenteGamificacao.usuario.TipoPonto;
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
			SortedSet<InfoPonto> resultadoEsperado = new TreeSet<>();
			resultadoEsperado.addAll(List.of(
					new InfoPonto("rodrigo", "estrela", 17),
					new InfoPonto("guerra", "estrela", 25),
					new InfoPonto("fernandes", "estrela", 19)
			));
			SortedSet<InfoPonto> resultadoObtido = armazenamento.obterTodosOsUsuariosComTipoDePonto("estrela");
			assertEquals(resultadoEsperado, resultadoObtido);
		}
		
		@Test
		void deveriaObterTodosOsTiposDePontosJaRegistradosParaOUsuarioGuerra() {
			Armazenamento armazenamento = new ArmazenamentoArquivo("src/test/dados-teste-leitura.txt");
			Map<TipoPonto, Integer> resultadoEsperado = new EnumMap<>(TipoPonto.class);
			resultadoEsperado.putAll(Map.of(
					TipoPonto.MOEDA, 20,
					TipoPonto.ESTRELA, 25
			));
			Map<TipoPonto, Integer> resultadoObtido = armazenamento.obterTodosOsTiposDePontoRegistradosParaOUsuario("guerra");
			assertEquals(resultadoEsperado, resultadoObtido);
		}
	}
	
	@Nested
	@DisplayName("casos de teste para armazenamento dos pontos recebidos")
	class ArmazenarPonto {
		@Test
		void deveriaArmazenarAAtualizaçãoDePontosRecebidaPeloUsuarioGuerraENaoAfetarOsOutrosRegistros() {
			ArmazenamentoArquivo armazenamento = new ArmazenamentoArquivo("src/test/dados-teste-leitura.txt");
			armazenamento.configurarArquivoDeArmazenamento("src/test/arquivo-teste-escrita.txt");
			armazenamento.reescreverDadosNoArquivo();
			armazenamento.armazenarPontos("guerra", "estrela", 15);
			
			int quantidadePontos = armazenamento.obterQuantidadeDePontos("guerra", "estrela");
			assertEquals(40, quantidadePontos);
			
			quantidadePontos = armazenamento.obterQuantidadeDePontos("rodrigo", "estrela");
			assertEquals(17, quantidadePontos);
			
			quantidadePontos = armazenamento.obterQuantidadeDePontos("toco", "energia");
			assertEquals(10, quantidadePontos);
		}
	}

}
