import java.util.*;


public class DR2 extends DeductionRules implements Observer { 
    private boolean selfModified;
    
    public DR2(Grille grille) {
        super(grille);
        
        selfModified = false;
    }

    @Override
    public boolean applyRules() { // méthode pour appliquer les règles
        boolean modified = false;
        for (int i = 0; i < 9; i++) {
            modified |= eliminateDuplicatesInRow(i);
            modified |= eliminateDuplicatesInColumn(i);
        }
        update();
        return modified;
    }
    @Override
    public void update() {
        if (selfModified) {
         
         System.out.println("DR2 : La grille a été modifiée.");
        }
       
    }

    private boolean eliminateDuplicatesInRow(int rowIndex) { // méthode pour éliminer les doublons dans la ligne
        Set<Integer> seenValues = new HashSet<>(); // ensemble pour les valeurs vues
        boolean modified = false;

        for (int colIndex = 0; colIndex < 9; colIndex++) {
            int index = rowIndex * 9 + colIndex;
            int value = grille.getCell(index);
            if (value != -1) {
                seenValues.add(value);
            }
        }

        for (int colIndex = 0; colIndex < 9; colIndex++) {
            int index = rowIndex * 9 + colIndex;
            if (grille.getCell(index) == -1) {
                List<Integer> possibleValues = findPossibleValues(index);
                possibleValues.removeAll(seenValues);

                if (possibleValues.size() == 1) {
                    grille.setCell(index, possibleValues.get(0));
                    modified = true;
                    System.out.println("DR2: Cellule " + index + " prend la valeur " + possibleValues.get(0));
                }
            }
        }
        return modified;
    }

    private boolean eliminateDuplicatesInColumn(int colIndex) { // méthode pour éliminer les doublons dans la colonne
        Set<Integer> seenValues = new HashSet<>();
        boolean modified = false;

        for (int rowIndex = 0; rowIndex < 9; rowIndex++) {
            int index = rowIndex * 9 + colIndex;
            int value = grille.getCell(index);
            if (value != -1) {
                seenValues.add(value);
            }
        }

        for (int rowIndex = 0; rowIndex < 9; rowIndex++) {
            int index = rowIndex * 9 + colIndex;
            if (grille.getCell(index) == -1) {
                List<Integer> possibleValues = findPossibleValues(index);
                possibleValues.removeAll(seenValues);

                if (possibleValues.size() == 1) {
                    grille.setCell(index, possibleValues.get(0));
                    modified = true;
                    System.out.println("DR2: Cellule " + index + " prend la valeur " + possibleValues.get(0));
                }
            }
        }
        return modified;
    }

    public List<Integer> findPossibleValues(int index) { // méthode pour trouver les valeurs possibles pour chaque cellule
        int row = index / 9;
        int col = index % 9;
        Set<Integer> excludedValues = new HashSet<>();

        for (int c = 0; c < 9; c++) {
            int value = grille.getCell(row * 9 + c);
            if (value != -1) {
                excludedValues.add(value);
            }
        }

        for (int r = 0; r < 9; r++) {
            int value = grille.getCell(r * 9 + col);
            if (value != -1) {
                excludedValues.add(value);
            }
        }

        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                int value = grille.getCell(r * 9 + c);
                if (value != -1) {
                    excludedValues.add(value);
                }
            }
        }

        List<Integer> possibleValues = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            if (!excludedValues.contains(i)) {
                possibleValues.add(i);
            }
        }

        return possibleValues;
    }
}
