public class main {
    public static void main(String[] args) {
        if (args.length != 1) {  // Vérifie qu'il y a exactement un argument (le nom du fichier)
                System.out.println("Utilisation : java Main <nom_du_fichier>");
                return;
        }
    
        String nomFichier = args[0];
        Grille grille = Parser.chargerGrille(nomFichier);
    
         if (grille == null) {
            System.out.println("Erreur lors du chargement de la grille.");
            return;
        }

        SudokuSolver solver = new SudokuSolver(grille);
        System.out.println("Grille initiale :");
        
        solver.printGrille();
        System.out.println();
        solver.solve();
        
    }
}
