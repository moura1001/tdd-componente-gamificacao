package tddComponenteGamificacao.usuario;

enum TipoPonto {
	ENERGIA, ESTRELA, MOEDA;

	private static TipoPonto encontrarPeloNome(String nome) {
		for (TipoPonto tipoPonto : TipoPonto.values()) {
			if (tipoPonto.name().equalsIgnoreCase(nome))
				return tipoPonto;
		}
		return null;
	}
	
	static TipoPonto validarTipoPonto(String tipoPonto) throws UsuarioException {
		TipoPonto tipo = TipoPonto.encontrarPeloNome(tipoPonto);
		if(tipo == null)
			throw new UsuarioException("Tipo de ponto inválido: " + tipoPonto);
		return tipo;
	}
}
