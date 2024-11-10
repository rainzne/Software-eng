import java.util.ArrayList;
import java.util.List;

public class Grille implements Observable{
    private int[] grid;
      private List<Observer> observers = new ArrayList<>();

    public Grille(int[] values) {
        if (values.length != 81) {
            throw new IllegalArgumentException("La grille doit contenir 81 valeurs.");
        }
        this.grid = values;
    }

    private static String which(int n) {
        switch (n) {
            case 2, 5, 11, 14, 20, 23, 29, 32, 38, 41, 47, 50, 56, 59, 65, 68, 74, 77:
                System.out.print("  |");
                break;
            case 8, 17, 35, 44, 62, 71, 80:
                System.out.println();
                break;
            case 26, 53:
                System.out.println();
                System.out.println("-----|-----|-----");
                break;
            default:
                break;
        }
        return "";
    }

    public void printArray() {
        for (int i = 0; i < grid.length; i++) {
            System.out.print((grid[i] == -1 ? "." : grid[i]));
            which(i);
        }
    }
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(); // Notifie chaque observateur de la modification
        }
    }

    public int[] getValues() {
        return grid.clone();
    }

    public void setCell(int index, int value) {
        if (index < 0 || index >= 81) {
            throw new IndexOutOfBoundsException("Index hors limites de la grille.");
        }
        grid[index] = value;
        notifyObservers();
    }

    public int getCell(int index) {
        if (index < 0 || index >= 81) {
            throw new IndexOutOfBoundsException("Index hors limites de la grille.");
        }
        return grid[index];
    }
}
