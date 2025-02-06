public class Arco {

	Casilla casilla1;
	Casilla casilla2;

	Arco(Casilla c1, Casilla c2) {
		casilla1 = c1;
		casilla2 = c2;
	}

	public Casilla getCasilla1() {
		return casilla1;
	}

	public Casilla getCasilla2() {
		return casilla2;
	}

	public void setCasilla1(Casilla casilla1) {
		this.casilla1 = casilla1;
	}

	public void setCasilla2(Casilla casilla2) {
		this.casilla2 = casilla2;
	}
}
