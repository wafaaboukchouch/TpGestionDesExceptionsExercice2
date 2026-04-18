public class CompteCourant extends CompteBancaire {
    private double decouvertAutorise;
    
    public CompteCourant(String numeroCompte, String nomTitulaire, double soldeInitial, double decouvertAutorise) {
        super(numeroCompte, nomTitulaire, soldeInitial);
        this.decouvertAutorise = decouvertAutorise;
    }
    
    @Override
    public void retirer(double montant) throws FondsInsuffisantsException {
        if (montant <= 0) {
            System.out.println("Erreur : Le montant du retrait doit être positif !");
            return;
        }
        
        if (solde - montant < -decouvertAutorise) {
            double montantManquant = Math.abs(solde - montant) - decouvertAutorise;
            throw new FondsInsuffisantsException(
                "Fonds insuffisants ! Découvert autorisé de " + decouvertAutorise + " € dépassé.",
                montantManquant, solde
            );
        }
        
        solde -= montant;
        System.out.println("Retrait de " + montant + " € effectué. Nouveau solde : " + solde + " €");
    }
    
    public double getDecouvertAutorise() {
        return decouvertAutorise;
    }
}
