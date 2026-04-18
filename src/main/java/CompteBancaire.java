public abstract class CompteBancaire {
    protected String numeroCompte;
    protected double solde;
    protected String nomTitulaire;
    
    // Constructeur
    public CompteBancaire(String numeroCompte, String nomTitulaire, double soldeInitial) {
        this.numeroCompte = numeroCompte;
        this.nomTitulaire = nomTitulaire;
        this.solde = soldeInitial;
    }
    
    // Dépôt d'argent
    public void deposer(double montant) {
        if (montant > 0) {
            solde += montant;
            System.out.println("Dépôt de " + montant + " € effectué. Nouveau solde : " + solde + " €");
        } else {
            System.out.println("Erreur : Le montant du dépôt doit être positif !");
        }
    }
    
    // Retrait d'argent (abstraite car comportement différent selon type de compte)
    public abstract void retirer(double montant) throws FondsInsuffisantsException;
    
    // Affichage du solde
    public void afficherSolde() {
        System.out.println("Compte " + numeroCompte + " - Titulaire : " + nomTitulaire + " - Solde : " + solde + " €");
    }
    
    // Transfert d'argent
    public void transferer(CompteBancaire compteDestinataire, double montant) 
            throws FondsInsuffisantsException, CompteInexistantException {
        
        if (compteDestinataire == null) {
            throw new CompteInexistantException("Compte destinataire inexistant !", "Inconnu");
        }
        
        this.retirer(montant);
        compteDestinataire.deposer(montant);
        System.out.println("Transfert de " + montant + " € effectué du compte " + this.numeroCompte 
                         + " vers le compte " + compteDestinataire.numeroCompte);
    }
    
    // Getters
    public String getNumeroCompte() {
        return numeroCompte;
    }
    
    public double getSolde() {
        return solde;
    }
    
    public String getNomTitulaire() {
        return nomTitulaire;
    }
    
    @Override
    public String toString() {
        return "Compte " + numeroCompte + " - " + nomTitulaire + " - Solde : " + solde + " €";
    }
}
