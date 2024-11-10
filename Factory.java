public class Factory {
    public static DeductionRules createRule(String ruleType, Grille grille) {
        switch (ruleType) {
            case "DR1":
                return new DR1(grille);
            case "DR2":
                return new DR2(grille);
            case "DR3":
                return new DR3(grille);
            default:
                throw new IllegalArgumentException("Type de règle inconnu : " + ruleType);
        }
    }
}
