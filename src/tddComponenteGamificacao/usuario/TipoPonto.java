package tddComponenteGamificacao.usuario;

enum TipoPonto {
	ENERGIA, ESTRELA, MOEDA;

	public static TipoPonto encontrarPeloNome(String nome) {
		for (TipoPonto tipoPonto : TipoPonto.values()) {
			if (tipoPonto.name().equalsIgnoreCase(nome))
				return tipoPonto;
		}
		return null;
	}
}
