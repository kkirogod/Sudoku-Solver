
public class AllDisjoint {

	public static boolean seCumple(Casilla casilla, Casilla[][] sudoku) { // comprueba que el valor de la casilla sea disjunto 
																		  //con el de las vecinas cuyo dominio es 1
		for (int i = 0; i < 9; i++) {

			int fila = casilla.getFila();
			int columna = casilla.getColumna();
			int celda = casilla.getCelda();

			if (i != columna && sudoku[fila][i].getDominio().size() == 1
					&& sudoku[fila][i].getDominio().get(0) == casilla.getDominio().get(0))
				return false;

			if (i != fila && sudoku[i][columna].getDominio().size() == 1
					&& sudoku[i][columna].getDominio().get(0) == casilla.getDominio().get(0))
				return false;

			for (int j = 0; j < 9; j++) {

				if (i != fila && j != columna && sudoku[i][j].getCelda() == celda
						&& sudoku[i][j].getDominio().size() == 1
						&& sudoku[i][j].getDominio().get(0) == casilla.getDominio().get(0))
					return false;
			}
		}

		return true;

	}
}
