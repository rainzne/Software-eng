import java.util.*;

public class SudokuSolver {
    private static SudokuSolver instance;
    private Grille grille;

    public SudokuSolver(Grille grille) {
        this.grille = grille;
    }
    public static SudokuSolver getInstance(Grille grille) { // pour éviter de créer plusieurs instances de SudokuSolver (singleton)
        if (instance == null) {
            instance = new SudokuSolver(grille);
        }
        return instance;
    }

    public void solve() {
        int compteur_difficulty = 1;
        int time = (int) System.currentTimeMillis() + 10000; // variable pour le temps(fin) à peu pres 10secondes
        int start = (int) System.currentTimeMillis(); // variable pour le temps (début)
        boolean madeChange;
       
        do {
            madeChange = false;
            start++;   
            DeductionRules dr1 = Factory.createRule("DR1", grille);
                boolean modifieddr1 = dr1.applyRules();
                madeChange = dr1.applyRules();
                if (modifieddr1) {    
                    madeChange = true;
                    System.out.println(" la règle DR1 a étaient appliquer...");
                    System.out.println();
                }
            if (!modifieddr1) {
                
                DeductionRules dr2 = Factory.createRule("DR2", grille);
                boolean modifieddr2 = dr2.applyRules();
                
                if (modifieddr2) {
                   
                    madeChange = true;
                    compteur_difficulty=2;
                    System.out.println("la règle DR2 a étaient appliquer...");
                    System.out.println();
                }

                if (!modifieddr2) {
                
                    DeductionRules dr3 = Factory.createRule("DR3", grille);
                    boolean modifieddr3 = dr3.applyRules();
                        if (modifieddr3) {
                            System.out.println("la règle DR3 a étaient appliquer...");
                            madeChange = true;
                            compteur_difficulty=3;
                }     
                
            }
        }  
            if (!isGrilleSolved()) {
                madeChange = true;
            }
            if (start >= time) {
                System.out.println("le temps est dépassé");
                compteur_difficulty=4;
                break;
            }
           
         } while (madeChange);
    
    
        switch (compteur_difficulty) { // affichage de la difficulté de la grille
            case 1:
                System.out.println("grille facile");
                break;
            case 2:
                System.out.println("grille moyenne");
                break;
            case 3:
                System.out.println("grille difficile");
                break;
            case 4: System.out.println ("grille Très difficile"); 
                break;
                
        }
  
        ResolutionMain();

    }

    private boolean isGrilleSolved() {
        for (int value : grille.getValues()) {
            if (value == -1) {
                return false;
            }
        }
        return true;
    }

    private void ResolutionMain() { // vérification des cellules vides (entrée a la main)
        
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 81; i++) {
            if (grille.getCell(i) == -1) {
                grille.printArray();
                List<Integer> possibleValues = findPossibleValues(i); 
                int value;
                do {
                    System.out.print("Entrez une valeur pour la cellule " + (i + 1) + " : ");
                    value = scanner.nextInt();
                    if (!possibleValues.contains(value)) {
                        System.out.println("Valeur incorrecte. Veuillez redemarrer votre grille.");
                        grille.printArray();
                        
                        
                        return;
                    }
                   
                } while (!possibleValues.contains(value)); 
                grille.setCell(i, value);
                System.out.println();
            }
        }
        
        System.out.println("La grille a été résolue avec succès !");
        getInstance(grille).printGrille();
    }
    

    public void printGrille() { // affichage de la grille
        grille.printArray();
    }
    public List<Integer> findPossibleValues(int index) { // méthode pour trouver les valeurs possibles pour chaque cellule
        int row = index / 9;   
        int col = index % 9;   
        Set<Integer> excludedValues = new HashSet<>(); // valeurs exclues pour chaque cellule (ligne, colonne et sous-grille)
    
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
        List<Integer> possibleValues = new ArrayList<>(); // valeurs possibles pour chaque cellule comparent les valeurs exclues et les valeurs possibles si non exclues -> ajout des valeurs possibles
        for (int i = 1; i <= 9; i++) {
            if (!excludedValues.contains(i)) {
                possibleValues.add(i);
            }
        }
    
        return possibleValues;
}
}
