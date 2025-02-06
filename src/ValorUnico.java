
public class ValorUnico {

	private Casilla casilla;
	private int valor;
	
	public ValorUnico(Casilla casilla, int valor) {
		
		this.casilla = casilla;
		this.valor = valor;
	}

	public boolean seCumple() {
		
		if(casilla.getDominio().size() == 1 && casilla.getDominio().get(0) == valor)
			return true;
		else
			return false;
	}

	public Casilla getCasilla() {
		return casilla;
	}

	public int getValor() {
		return valor;
	}
}
