public class FondsInsuffisantsException extends Exception {
    private double montantManquant;
    private double soldeActuel;
    
    public FondsInsuffisantsException(String message, double montantManquant, double soldeActuel) {
        super(message);
        this.montantManquant = montantManquant;
        this.soldeActuel = soldeActuel;
    }
    
    public double getMontantManquant() {
        return montantManquant;
    }
    
    public double getSoldeActuel() {
        return soldeActuel;
    }
}
