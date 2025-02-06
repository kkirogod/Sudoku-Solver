import java.io.IOException;
import java.util.ArrayList;

public class AC3 {
	
    private Casilla[][] sudoku;
    private ArrayList<Arco> arcos;

    public AC3(Casilla[][] casillas, ArrayList<ValorUnico> restricciones) throws IOException {

    	arcos = new ArrayList<Arco>();
    	sudoku = casillas;
    	
    	evaluarRestricciones(restricciones);

        for (int i = 0; i < 9; i++) {
            formarArcos(i);
        }

        while (!arcos.isEmpty()) {
        	
            Arco arco = arcos.remove(0);
            
            if (!tratarArco(arco)) {
            	System.out.println("ERROR: NO HAY SOLUCION");
            	sudoku = null;
                break;
            }
        }
        
        if ((sudoku != null) && (estadoSudoku(sudoku) == 1)) {
            new BusquedaBacktracking(sudoku);
        }
    }
    
    private void evaluarRestricciones(ArrayList<ValorUnico> restricciones) {
    	
    	for(ValorUnico restriccion : restricciones) {
    		
    		if(!restriccion.seCumple()) {
    			
    			ArrayList<Integer> nuevoDominio = new ArrayList<Integer>(1);
    			nuevoDominio.add(restriccion.getValor());
    			
    			restriccion.getCasilla().setDominio(nuevoDominio);
    		}
    	}
    }
    
    private void formarArcos(int n) { // como todas las filas, columnas y celdas van del 1 al 9, podemos hacerlas a la vez
    	
    	int fila = n;
    	int columna = n;
    	int celda = n;
    	
        ArrayList<Casilla> casillasCelda = new ArrayList<>();
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudoku[i][j].getCelda() == celda) {
                    casillasCelda.add(sudoku[i][j]);
                }
            }
        }
    	
    	for (int i = 0; i < 8; i++) {
            for (int j = i + 1; j < 9; j++) {
            	
                arcos.add(new Arco(sudoku[fila][i], sudoku[fila][j]));
                arcos.add(new Arco(sudoku[fila][j], sudoku[fila][i]));
                
                arcos.add(new Arco(sudoku[i][columna], sudoku[j][columna]));
                arcos.add(new Arco(sudoku[j][columna], sudoku[i][columna]));
                
                arcos.add(new Arco(casillasCelda.get(i), casillasCelda.get(j)));
                arcos.add(new Arco(casillasCelda.get(j), casillasCelda.get(i)));
            }
        }
    }

    private boolean tratarArco(Arco arco) {
    	
        Casilla casilla1 = arco.casilla1;
        Casilla casilla2 = arco.casilla2;
        
        ArrayList<Integer> dominioC1 = new ArrayList<Integer>(casilla1.getDominio());
        
        for (Integer v1 : dominioC1) {
        	
            boolean diferente = false;
            
            for (Integer v2 : casilla2.getDominio()) {
            	
                if (v1 != v2) {
                    diferente = true;
                    break;
                }
            }

            if (!diferente) {
            	
                casilla1.getDominio().remove(v1);
                
                if (casilla1.getDominio().isEmpty()) {
                    return false;
                }
                
                addArcosCasilla1NoDistinguida(casilla1, casilla2);
            }
        }
        return true;
    }

    private void addArcosCasilla1NoDistinguida(Casilla c1, Casilla c2) {
    	
        for (int i = 0; i < 9; i++) {
        	
        	int columnaC1 = c1.getColumna();
        	int filaC1 = c1.getFila();
        	int celdaC1 = c1.getCelda();
        	
        	// añadimos arco de la fila de n1
            if (i != columnaC1 && sudoku[filaC1][i] != c2) {
                arcos.add(new Arco(sudoku[filaC1][i], c1));
            }
            
            // añadimos arco de la columna de n1
            if (i != filaC1 && sudoku[i][columnaC1] != c2) {
                arcos.add(new Arco(sudoku[i][columnaC1], c1));
            }
            
            // añadimos arcos de la celda de n1
            for (int j = 0; j < 9; j++) {
                if (sudoku[i][j].getCelda() == celdaC1 && i != filaC1 && j != columnaC1 && sudoku[i][j] != c2) {
                    arcos.add(new Arco(sudoku[i][j], c1));
                }
            }
        }
    }
    
    public static int estadoSudoku(Casilla[][] sudoku) {
		
		int sumFilas [] = new int [9];
		int sumColumnas [] = new int [9];
		int sumCeldas [] = new int [9];
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				
				if (sudoku[i][j].getDominio().size() > 1) //hay algun dominio con mas de un valor
					return 1;
				
				sumFilas[sudoku[i][j].getFila()] += sudoku[i][j].getDominio().get(0);
				sumColumnas[sudoku[i][j].getColumna()] += sudoku[i][j].getDominio().get(0);
				sumCeldas[sudoku[i][j].getCelda()] += sudoku[i][j].getDominio().get(0);
			}
		}
        
        
		for (int i = 0; i < 9; i++) {
			
			if (sumFilas[i] != 45 || sumColumnas[i] != 45 || sumCeldas[i] != 45) {
				sudoku = null;
				return -1;
			}
		}
        
		return 0;
	}
}
