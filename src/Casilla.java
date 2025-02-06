import java.util.ArrayList;

class Casilla {
	
	private int fila, columna, celda;
	private ArrayList<Integer> dominio;

	Casilla(int f, int c) {

		fila = f;
		columna = c;
		
		celda = celdaDe(f, c);

		dominio = new ArrayList<>();
		for (int i = 1; i <= 9; i++) {
			dominio.add(i);
		}
	}

	private int celdaDe(int fila, int columna) {

		int celda = 0;

		if (fila < 3) {
			
			if (columna < 3)
				celda = 0;
			else if (columna < 6)
				celda = 1;
			else
				celda = 2;
			
		} else if (fila < 6) {
			
			if (columna < 3)
				celda = 3;
			else if (columna < 6)
				celda = 4;
			else
				celda = 5;
			
		} else {
			
			if (columna < 3)
				celda = 6;
			else if (columna < 6)
				celda = 7;
			else
				celda = 8;
			
		}
		
		return celda;
	}
	
	@Override
    public boolean equals(Object c) {
		
        if (this == c)
            return true;
            
        if (c == null)
            return false;
            
        if (getClass() != c.getClass())
            return false;
        
        Casilla aux = (Casilla) c;
        
        if (aux.fila == this.fila && aux.columna == this.columna && aux.celda == this.celda)
            return true;
        else
            return false;
    }

	public int getFila() {
		return fila;
	}

	public int getColumna() {
		return columna;
	}

	public int getCelda() {
		return celda;
	}

	public ArrayList<Integer> getDominio() {
		return dominio;
	}

	public void setFila(int fila) {
		this.fila = fila;
	}

	public void setColumna(int columna) {
		this.columna = columna;
	}

	public void setCelda(int celda) {
		this.celda = celda;
	}

	public void setDominio(ArrayList<Integer> dominio) {
		this.dominio = dominio;
	}
}