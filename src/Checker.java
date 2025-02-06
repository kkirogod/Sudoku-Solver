import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Checker {

    public static void main(String[] args) { // CLASE UTILIZADA SOLO PARA COMPROBAR EL FICHERO SOLUCION
    	
        String fileName = "src/solucionOpc.txt";
        boolean tieneErrores = false;

        try {
        	
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String sudokuString;
            int count = 0;
            
            while ((sudokuString = reader.readLine()) != null) {
            	
                count++;
                
                if (!isValidSudoku(sudokuString)) {
                	
                    System.out.println("Sudoku " + count + " tiene errores.");
                    tieneErrores = true;
                }	
            }
            reader.close();
            
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        
        if(!tieneErrores)
        	System.out.println("El sudoku NO tiene errores!");
    }

    public static boolean isValidSudoku(String sudokuString) {
    	
        if (sudokuString.length() != 81)
            return false;

        // Verificar filas
        for (int i = 0; i < 9; i++) {
        	
            if (!isValidSet(sudokuString.substring(i * 9, (i + 1) * 9)))
                return false;
        }

        // Verificar columnas
        for (int i = 0; i < 9; i++) {
        	
            StringBuilder column = new StringBuilder();
            
            for (int j = 0; j < 9; j++)
                column.append(sudokuString.charAt(i + j * 9));
                
            if (!isValidSet(column.toString()))
                return false;
        }

        // Verificar celdas
        for (int blockRow = 0; blockRow < 3; blockRow++) {
            for (int blockCol = 0; blockCol < 3; blockCol++) {
            	
                StringBuilder block = new StringBuilder();
                
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                    	
                        int rowIndex = blockRow * 3 + i;
                        int colIndex = blockCol * 3 + j;
                        block.append(sudokuString.charAt(rowIndex * 9 + colIndex));
                    }
                }
                
                if (!isValidSet(block.toString())) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean isValidSet(String set) {
    	
        boolean[] seen = new boolean[10];
        
        for (char c : set.toCharArray()) {
        	
            if (c != '0' && seen[c - '0'])
                return false;
            
            seen[c - '0'] = true;
        }
        
        return true;
    }
}
