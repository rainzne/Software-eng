import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Parser {
    
    public static Grille chargerGrille(String nomFichier) {
        int[] values = new int[81];
        int index = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(nomFichier))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(","); 
                for (String token : tokens) {
                    if (!token.isEmpty()) { 
                        int value = Integer.parseInt(token);
                        values[index++] = value == 0 ? -1 : value; // Remplace 0 par -1 pour indiquer les cases vides
                    }
                }
            }
            if (index != 81) {
                System.out.println("Erreur : la grille doit contenir exactement 81 valeurs.");
                return null;
            }
        } catch (IOException e) {
            System.out.println("Erreur de lecture du fichier : " + e.getMessage());
            return null;
        } 
        return new Grille(values); 
    }
}
