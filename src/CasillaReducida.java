class CasillaReducida {
	
    private Casilla casilla;
    private int valorEliminado;

    CasillaReducida(Casilla c, int v) {
        casilla = c;
        valorEliminado = v;
    }

	public Casilla getCasilla() {
		return casilla;
	}

	public int getValorEliminado() {
		return valorEliminado;
	}

	public void setCasilla(Casilla casilla) {
		this.casilla = casilla;
	}

	public void setValorEliminado(int valorEliminado) {
		this.valorEliminado = valorEliminado;
	}
}