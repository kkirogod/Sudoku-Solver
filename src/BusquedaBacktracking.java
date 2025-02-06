import java.util.*;

public class BusquedaBacktracking {

	private static Casilla[][] sudoku;
	private Stack<CasillaReducida> historial;

	BusquedaBacktracking(Casilla[][] s) {

		historial = new Stack<CasillaReducida>();
		sudoku = s;

		buscar(getCasillaMinDominio());
	}

	private int buscar(Casilla casilla) {

		int estado = AC3.estadoSudoku(sudoku);

		if (estado != 1)
			return estado;

		if (casilla == null) {
			sudoku = null;
			return -1;
		}

		int tamHistorial = historial.size();

		int valorMR = valorMenosRestrictivo(casilla);

		while (valorMR != 0) {

			boolean volverAtras = false;

			setValorCasilla(casilla, valorMR);

			if (!AllDisjoint.seCumple(casilla, sudoku) || !propagarRestricciones(casilla)) {

				volverAtras = true;

			} else { // no causa conflictos

				estado = buscar(getCasillaMinDominio());

				if (estado == -1)
					volverAtras = true;
				else if (estado == 0)
					return 0;
			}

			if (volverAtras) {

				vueltaAtras(tamHistorial);

				if (!eliminarValorCasilla(casilla, valorMR))
					return -1;

				tamHistorial = historial.size();

				valorMR = valorMenosRestrictivo(casilla);
			}
		}

		return -1;
	}

	private int valorMenosRestrictivo(Casilla casilla) {

		ArrayList<Casilla> vecinas = getVecinas(casilla);

		int valorMR = 0;
		int dominioMin = Integer.MAX_VALUE;

		for (int v : casilla.getDominio()) {

			int dominioVecinas = 0;

			for (Casilla cVecina : vecinas) {

				dominioVecinas += cVecina.getDominio().size();

				if (cVecina.getDominio().contains(v)) {
					dominioVecinas--;
				}
			}

			if (dominioVecinas < dominioMin) {
				dominioMin = dominioVecinas;
				valorMR = v;
			}
		}

		return valorMR;
	}

	private boolean propagarRestricciones(Casilla casilla) {

		ArrayList<Casilla> pendientes = new ArrayList<Casilla>();
		pendientes.add(casilla);

		while (!pendientes.isEmpty()) {

			Casilla actual = pendientes.remove(0);

			for (Casilla vecina : getVecinas(actual)) {

				if (!eliminarValorCasilla(vecina, actual.getDominio().get(0)))
					return false;
				else if (vecina.getDominio().size() == 1)
					pendientes.add(vecina);
			}
		}

		return true;
	}

	private ArrayList<Casilla> getVecinas(Casilla casilla) {

		ArrayList<Casilla> vecinas = new ArrayList<>();

		for (int i = 0; i < 9; i++) {

			int fila = casilla.getFila();
			int columna = casilla.getColumna();
			int celda = casilla.getCelda();

			if (i != columna && sudoku[fila][i].getDominio().size() > 1) // solo devolvemos las que tienen dominio mayor a 1
				vecinas.add(sudoku[fila][i]);

			if (i != fila && sudoku[i][columna].getDominio().size() > 1)
				vecinas.add(sudoku[i][columna]);

			for (int j = 0; j < 9; j++) {

				if (i != fila && j != columna && sudoku[i][j].getCelda() == celda && sudoku[i][j].getDominio().size() > 1)
					vecinas.add(sudoku[i][j]);
			}
		}

		return vecinas;
	}

	private Casilla getCasillaMinDominio() {

		for (int dominio = 2; dominio <= 9; dominio++) {

			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {

					if (sudoku[i][j].getDominio().size() == dominio)
						return sudoku[i][j];
				}
			}
		}

		return null;
	}

	private boolean eliminarValorCasilla(Casilla casilla, int valor) {

		if (casilla.getDominio().remove((Integer) valor)) {

			historial.push(new CasillaReducida(casilla, valor));

			if (casilla.getDominio().isEmpty() || (casilla.getDominio().size() == 1 && !AllDisjoint.seCumple(casilla, sudoku)))
				return false;
		}

		return true;
	}

	private void setValorCasilla(Casilla casilla, int valor) {

		ArrayList<Integer> dominio = new ArrayList<Integer>(casilla.getDominio());

		for (int v : dominio) {

			if (v != valor) {
				casilla.getDominio().remove((Integer) v);
				historial.push(new CasillaReducida(casilla, v));
			}
		}
	}

	private void vueltaAtras(int tam) {

		while (historial.size() > tam) {

			CasillaReducida casillaReducida = historial.pop();
			
			casillaReducida.getCasilla().getDominio().add(casillaReducida.getValorEliminado());
		}
	}
}
