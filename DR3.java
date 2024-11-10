import java.util.*;

public class DR3 extends DeductionRules implements Observer {
    private Grille grille;
    private boolean selfModified;

    public DR3(Grille grille) {
        super(grille);
        this.grille = grille;
    }

    @Override
    public boolean applyRules() {
        boolean modified = false;

        for (int subgridIndex = 0; subgridIndex < 9; subgridIndex++) {
            modified |= applyNakedPairsLogic(subgridIndex);
        }
        update();
        return modified;
    }

    @Override
    public void update() {
        if (selfModified) {
            System.out.println("DR3 : La grille a été modifiée.");
        }
    }

    private boolean applyNakedPairsLogic(int subgridIndex) {
        boolean modified = false;
        Set<Integer> nakedPairCells = new HashSet<>();
        Set<List<Integer>> nakedPairs = new HashSet<>();

        // Recherche de cellules avec exactement deux possibilités
        for (int i : getSubgridIndices(subgridIndex)) {
            if (grille.getCell(i) == -1) {
                List<Integer> possibleValues = findPossibleValues(i);
                if (possibleValues.size() == 2) {
                    // Ajoute la paire et la cellule
                    nakedPairCells.add(i);
                    nakedPairs.add(possibleValues);
                }
            }
        }
        for (List<Integer> pair : nakedPairs) {  // Vérifie chaque naked pair
            int pairCount = 0;    
            for (int cell : nakedPairCells) {
                if (findPossibleValues(cell).equals(pair)) {  // Compte les cellules ayant exactement cette paire
                    pairCount++;
                }
            }

            // Si exactement deux cellules ont cette paire, restreindre les autres cellules
            if (pairCount == 2) {
                System.out.println("DR3: naked pairs trouvée avec les valeurs " + pair + " dans le sous-carré " + subgridIndex);

                for (int cell : getSubgridIndices(subgridIndex)) {
                    if (!nakedPairCells.contains(cell) && grille.getCell(cell) == -1) {
                        List<Integer> possibleValues = findPossibleValues(cell);
                        possibleValues.removeAll(pair);

                        // on boucle ici mais on ne devrait pas et je n'arrive pas a trouver une solution
                        // j'ai essayer d'utiliser la logique de DR2 mais fonctionne pas ou je l'applique mal
                        // le probleme c'est qu'on a aucun changement dans la grille donc cette partie sert a rien vu qu'on rien faire avec les valeurs possibles


                        if (possibleValues.size() == 1) {
                            grille.setCell(cell, possibleValues.get(0));
                            modified = true;
                            System.out.println("DR3: Valeur " + possibleValues.get(0) + " appliquée à la cellule " + cell + " en raison de naked pairs");
                        }
                        
                    }
                }
            }
        }

        selfModified = modified;
        return modified;
    }

    private List<Integer> getSubgridIndices(int subgridIndex) {
        List<Integer> indices = new ArrayList<>();
        int startRow = (subgridIndex / 3) * 3;
        int startCol = (subgridIndex % 3) * 3;

        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                indices.add(r * 9 + c);
            }
        }
        return indices;
    }

    private List<Integer> findPossibleValues(int cellIndex) {
        List<Integer> possibleValues = new ArrayList<>();
        for (int num = 1; num <= 9; num++) {
            if (isValid(cellIndex, num)) {
                possibleValues.add(num);
            }
        }
        return possibleValues;
    }

    private boolean isValid(int cellIndex, int num) {
        int row = cellIndex / 9;
        int col = cellIndex % 9;

        // Vérifie la ligne et la colonne
        for (int i = 0; i < 9; i++) {
            if (grille.getCell(row * 9 + i) == num || grille.getCell(i * 9 + col) == num) {
                return false;
            }
        }

        // Vérifie la sous-grille
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                if (grille.getCell(r * 9 + c) == num) {
                    return false;
                }
            }
        }
        return true;
    }
}
