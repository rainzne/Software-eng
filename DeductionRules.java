public abstract class DeductionRules {
    protected Grille grille;

    public DeductionRules(Grille grille) {
        this.grille = grille;
    }

    public abstract boolean applyRules();
}
