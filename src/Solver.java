import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Solver {
	
	private static ArrayList<ValorUnico> restricciones = new ArrayList<ValorUnico>();

	public static void main(String[] args) throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader("src/t2.txt"));
		String linea;
		
		int i = 415003;

		while ((linea = reader.readLine()) != null) {
			
			long ini = System.currentTimeMillis();
			
			Casilla[][] sudoku = leerSudoku(linea);
			new AC3(sudoku, restricciones);
			
			BufferedWriter writer = new BufferedWriter(new FileWriter("src/solOpc.txt", true));
			
			if(sudoku != null)
				escribirSudokuFichero(sudoku, writer);
			else
				writer.write("SUDOKU " + i + " -> NO RESUELTO");
				
			writer.newLine();
			writer.close();
			
			long fin = System.currentTimeMillis();
			
			System.out.println("\nSUDOKU " + (i++) + " -> " + ((double)(fin - ini)/1000) + "s");
		}

		reader.close();
	}
	
	private static Casilla[][] leerSudoku(String linea) throws IOException {

		Casilla[][] sudoku = new Casilla[9][9];

		int posicion = 0;

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {

				sudoku[i][j] = new Casilla(i, j);
				
				char c = linea.charAt(posicion++);

				if (c != '.')
					restricciones.add(new ValorUnico(sudoku[i][j], Character.getNumericValue(c)));
			}
		}

		return sudoku;
	}
	
	public static void escribirSudokuFichero(Casilla[][] sudoku, BufferedWriter writer) throws IOException {
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				
				writer.write(Integer.toString(sudoku[i][j].getDominio().get(0)));
			}
		}
	}
}
