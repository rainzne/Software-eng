import java.util.*;
public class DR1 extends DeductionRules implements Observer {
    private boolean selfModified;
    public DR1(Grille grille) {
        super(grille);
    }

    @Override
    public boolean applyRules() { // méthode pour appliquer les règles
        
        boolean modified = false;
        for (int i = 0; i < 81; i++) {
            if (grille.getCell(i) == -1 && isSingleEmptyCellInSubgrid(i)) {
                int value = findUniqueValue(i);
                if (value != -1) {
                    grille.setCell(i, value);
                    modified = true;
                   
                    System.out.println("DR1: Cellule " + i + " prend la valeur " + value);
                    update();
                    
                    
                }
                
            }
            
        }
        
        return modified;
        
    }
    @Override
    public void update() {
        if (selfModified) {
         
         System.out.println("DR1 : La grille a été modifiée.");
        }
       
    }

    private boolean isSingleEmptyCellInSubgrid(int index) { // méthode pour vérifier si une seule cellule est vide dans la sous-grille
        int startRow = (index / 9 / 3) * 3;
        int startCol = (index % 9 / 3) * 3;
        int emptyCount = 0;

        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                if (grille.getCell(r * 9 + c) == -1) {
                    emptyCount++;
                    if (emptyCount > 1) {
                        return false;
                    }
                }
            }
        }
        return emptyCount == 1;
    }

    private int findUniqueValue(int index) { // méthode pour trouver la valeur unique pour une cellule vide
        boolean[] possibleValues = new boolean[10];
        Arrays.fill(possibleValues, true);

        for (int col = 0; col < 9; col++) {
            int rowVal = grille.getCell(index / 9 * 9 + col);
            if (rowVal != -1) {
                possibleValues[rowVal] = false;
            }
        }

        for (int row = 0; row < 9; row++) {
            int colVal = grille.getCell(row * 9 + index % 9);
            if (colVal != -1) {
                possibleValues[colVal] = false;
            }
        }

        int startRow = (index / 9 / 3) * 3;
        int startCol = (index % 9 / 3) * 3;
        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                int subVal = grille.getCell(r * 9 + c);
                if (subVal != -1) {
                    possibleValues[subVal] = false;
                }
            }
        }

        int uniqueValue = -1;
        for (int val = 1; val <= 9; val++) {
            if (possibleValues[val]) {
                if (uniqueValue == -1) {
                    uniqueValue = val;
                } else {
                    return -1;
                }
            }
        }
        return uniqueValue;
    }
}
