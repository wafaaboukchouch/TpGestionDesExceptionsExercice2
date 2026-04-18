import java.util.ArrayList;
import java.util.Scanner;

public class MainExercice2 {
    private static ArrayList<CompteBancaire> comptes = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        int choix;
        
        do {
            afficherMenu();
            choix = scanner.nextInt();
            scanner.nextLine(); // Consommer le retour à la ligne
            
            switch (choix) {
                case 1:
                    ajouterCompte();
                    break;
                case 2:
                    supprimerCompte();
                    break;
                case 3:
                    effectuerDepot();
                    break;
                case 4:
                    effectuerRetrait();
                    break;
                case 5:
                    effectuerTransfert();
                    break;
                case 6:
                    appliquerInteretsEpargne();
                    break;
                case 7:
                    afficherTousComptes();
                    break;
                case 0:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide !");
            }
        } while (choix != 0);
        
        scanner.close();
    }
    
    private static void afficherMenu() {
        System.out.println("\n=== GESTION DES COMPTES BANCAIRES ===");
        System.out.println("1. Ajouter un compte");
        System.out.println("2. Supprimer un compte");
        System.out.println("3. Effectuer un dépôt");
        System.out.println("4. Effectuer un retrait");
        System.out.println("5. Effectuer un transfert");
        System.out.println("6. Appliquer les intérêts (comptes épargne)");
        System.out.println("7. Afficher tous les comptes");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");
    }
    
    private static void ajouterCompte() {
        System.out.println("\n--- Ajout d'un compte ---");
        System.out.print("Type de compte (1: Courant, 2: Épargne) : ");
        int type = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Numéro de compte : ");
        String numero = scanner.nextLine();
        
        System.out.print("Nom du titulaire : ");
        String nom = scanner.nextLine();
        
        System.out.print("Solde initial : ");
        double solde = scanner.nextDouble();
        
        if (type == 1) {
            System.out.print("Découvert autorisé : ");
            double decouvert = scanner.nextDouble();
            comptes.add(new CompteCourant(numero, nom, solde, decouvert));
            System.out.println("Compte courant ajouté avec succès !");
        } else if (type == 2) {
            System.out.print("Taux d'intérêt (%): ");
            double taux = scanner.nextDouble();
            comptes.add(new CompteEpargne(numero, nom, solde, taux));
            System.out.println("Compte épargne ajouté avec succès !");
        } else {
            System.out.println("Type de compte invalide !");
        }
    }
    
    private static void supprimerCompte() {
        System.out.print("\nNuméro du compte à supprimer : ");
        String numero = scanner.nextLine();
        
        CompteBancaire compte = trouverCompte(numero);
        if (compte != null) {
            comptes.remove(compte);
            System.out.println("Compte " + numero + " supprimé avec succès !");
        } else {
            System.out.println("Compte non trouvé !");
        }
    }
    
    private static void effectuerDepot() {
        System.out.print("\nNuméro du compte : ");
        String numero = scanner.nextLine();
        
        CompteBancaire compte = trouverCompte(numero);
        if (compte != null) {
            System.out.print("Montant à déposer : ");
            double montant = scanner.nextDouble();
            compte.deposer(montant);
        } else {
            System.out.println("Compte non trouvé !");
        }
    }
    
    private static void effectuerRetrait() {
        System.out.print("\nNuméro du compte : ");
        String numero = scanner.nextLine();
        
        CompteBancaire compte = trouverCompte(numero);
        if (compte != null) {
            System.out.print("Montant à retirer : ");
            double montant = scanner.nextDouble();
            try {
                compte.retirer(montant);
            } catch (FondsInsuffisantsException e) {
                System.out.println("ERREUR : " + e.getMessage());
                System.out.println("Montant manquant : " + e.getMontantManquant() + " €");
                System.out.println("Solde actuel : " + e.getSoldeActuel() + " €");
            }
        } else {
            System.out.println("Compte non trouvé !");
        }
    }
    
    private static void effectuerTransfert() {
        System.out.print("\nNuméro du compte source : ");
        String numeroSource = scanner.nextLine();
        System.out.print("Numéro du compte destinataire : ");
        String numeroDest = scanner.nextLine();
        
        CompteBancaire source = trouverCompte(numeroSource);
        CompteBancaire destinataire = trouverCompte(numeroDest);
        
        if (source == null) {
            System.out.println("Compte source non trouvé !");
            return;
        }
        
        if (destinataire == null) {
            System.out.println("Compte destinataire non trouvé !");
            return;
        }
        
        System.out.print("Montant à transférer : ");
        double montant = scanner.nextDouble();
        
        try {
            source.transferer(destinataire, montant);
        } catch (FondsInsuffisantsException e) {
            System.out.println("ERREUR DE TRANSFERT : " + e.getMessage());
            System.out.println("Montant manquant : " + e.getMontantManquant() + " €");
            System.out.println("Solde actuel : " + e.getSoldeActuel() + " €");
        } catch (CompteInexistantException e) {
            System.out.println("ERREUR : " + e.getMessage());
        }
    }
    
    private static void appliquerInteretsEpargne() {
        System.out.println("\n--- Application des intérêts pour tous les comptes épargne ---");
        boolean trouve = false;
        for (CompteBancaire compte : comptes) {
            if (compte instanceof CompteEpargne) {
                ((CompteEpargne) compte).appliquerInterets();
                trouve = true;
            }
        }
        if (!trouve) {
            System.out.println("Aucun compte épargne trouvé !");
        }
    }
    
    private static void afficherTousComptes() {
        System.out.println("\n=== LISTE DES COMPTES ===");
        if (comptes.isEmpty()) {
            System.out.println("Aucun compte enregistré.");
        } else {
            for (CompteBancaire compte : comptes) {
                System.out.println(compte);
                if (compte instanceof CompteCourant) {
                    System.out.println("  Type: Courant, Découvert autorisé: " + ((CompteCourant) compte).getDecouvertAutorise() + " €");
                } else if (compte instanceof CompteEpargne) {
                    System.out.println("  Type: Épargne, Taux: " + ((CompteEpargne) compte).getTauxInteret() + "%");
                }
            }
        }
    }
    
    private static CompteBancaire trouverCompte(String numero) {
        for (CompteBancaire compte : comptes) {
            if (compte.getNumeroCompte().equals(numero)) {
                return compte;
            }
        }
        return null;
    }
}
