
public class CompteEpargne extends CompteBancaire {
    private double tauxInteret;
    
    public CompteEpargne(String numeroCompte, String nomTitulaire, double soldeInitial, double tauxInteret) {
        super(numeroCompte, nomTitulaire, soldeInitial);
        this.tauxInteret = tauxInteret;
    }
    
    @Override
    public void retirer(double montant) throws FondsInsuffisantsException {
        if (montant <= 0) {
            System.out.println("Erreur : Le montant du retrait doit être positif !");
            return;
        }
        
        if (solde - montant < 0) {
            throw new FondsInsuffisantsException(
                "Fonds insuffisants ! Solde actuel : " + solde + " €, retrait demandé : " + montant + " €",
                montant - solde, solde
            );
        }
        
        solde -= montant;
        System.out.println("Retrait de " + montant + " € effectué. Nouveau solde : " + solde + " €");
    }
    
    // Application des intérêts
    public void appliquerInterets() {
        double interets = solde * tauxInteret / 100;
        solde += interets;
        System.out.println("Intérêts de " + interets + " € appliqués. Nouveau solde : " + solde + " €");
    }
    
    public double getTauxInteret() {
        return tauxInteret;
    }
}
