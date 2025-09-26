package presentation;

import entity.Abonnement;
import entity.AbonnementAvecEngagement;
import entity.AbonnementSansEngagement;
import entity.Paiement;
import metier.AbonnementService;
import metier.PaiementService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        AbonnementService abonnementService = new AbonnementService();
        PaiementService paiementService = new PaiementService();
        System.out.println("=== AbonTracker vous souhaite la bienvenue dans votre espace ===");
        int choixPrincipal;

        do {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1. Gestion des abonnements");
            System.out.println("2. Gestion des paiements");
            System.out.println("3. Operations");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");
            choixPrincipal = scanner.nextInt();
            scanner.nextLine();

            switch (choixPrincipal) {
                case 1: // Gestion abonnements
                    int choixAbo;
                    do {
                        System.out.println("\n--- MENU ABONNEMENTS ---");
                        System.out.println("1. Ajouter un abonnement");
                        System.out.println("2. Supprimer un abonnement");
                        System.out.println("3. Modifier un abonnement");
                        System.out.println("4. Afficher tous les abonnements");
                        System.out.println("5 .Resilier Abonnement");
                        System.out.println("0. Retour");
                        System.out.print("Votre choix : ");
                        choixAbo = scanner.nextInt();
                        scanner.nextLine();

                        switch (choixAbo) {
                            case 1:
                                System.out.println("Type abonnement :");
                                System.out.println("1. Avec engagement");
                                System.out.println("2. Sans engagement");
                                int type = scanner.nextInt();
                                scanner.nextLine();

                                System.out.print("Nom service : ");
                                String nomService = scanner.nextLine();

                                System.out.print("Montant mensuel : ");
                                double montant = scanner.nextDouble();

                                System.out.print("Date debut (YYYY-MM-DD) : ");
                                LocalDate dateDebut = LocalDate.parse(scanner.next());

                                Abonnement abonnement;
                                if (type == 1) {
                                    System.out.print("Duree engagement (mois) : ");
                                    int duree = scanner.nextInt();
                                    abonnement = new AbonnementAvecEngagement(
                                            nomService,
                                            montant,
                                            dateDebut,
                                            duree
                                    );
                                } else {
                                    abonnement = new AbonnementSansEngagement(
                                            nomService,
                                            montant,
                                            dateDebut
                                    );
                                }
                                abonnementService.creerAbonnement(abonnement);
                                System.out.println("Abonnement ajoute");
                                break;

                            case 2:
                                System.out.print("ID abonnement a supprimer : ");
                                String idAbonnement = scanner.next();
                                abonnementService.supprimerAbonnement(idAbonnement);
                                System.out.println("Abonnement supprime");
                                break;

                            case 3:
                                System.out.print("ID abonnement a modifier : ");
                                String idAbo = scanner.nextLine();
                                Abonnement abonne = abonnementService.findById(idAbo);

                                if (abonne == null) {
                                    System.out.println("Abonnement introuvable");
                                    break;
                                }

                                System.out.print("Nouveau nom (" + abonne.getNomService() + ") : ");
                                String newNom = scanner.nextLine();
                                if (!newNom.trim().isEmpty()) abonne.setNomService(newNom);

                                System.out.print("Nouveau montant (" + abonne.getMontantMensuel() + ") : ");
                                String montantStr = scanner.nextLine();
                                if (!montantStr.trim().isEmpty()) abonne.setMontantMensuel(Double.parseDouble(montantStr));

                                abonnementService.modifierAbonnement(abonne);
                                System.out.println("Abonnement modifie");
                                break;

                            case 4:
                                List<Abonnement> abonnements = abonnementService.listerAbonnements();
                                System.out.println("=== Liste des abonnements ===");
                                for (Abonnement a : abonnements) {
                                    System.out.println(a);
                                }
                                break;
                            case 5:
                                System.out.print("Entrez l'ID de l'abonnement à résilier : ");
                                String idResiliation = scanner.nextLine();
                                abonnementService.resilierAbonnement(idResiliation);
                                break;
                        }
                    } while (choixAbo != 0);
                    break;

                case 2: // Gestion paiements
                    int choixPaie;
                    do {
                        System.out.println("\n--- MENU PAIEMENTS ---");
                        System.out.println("1. Ajouter un paiement");
                        System.out.println("2. Supprimer un paiement");
                        System.out.println("3. Modifier un paiement");
                        System.out.println("4. Afficher tous les paiements");
                        System.out.println("5. Afficher paiements d un abonnement");
                        System.out.println("0. Retour");
                        System.out.print("Votre choix : ");
                        choixPaie = scanner.nextInt();
                        scanner.nextLine();

                        switch (choixPaie) {
                            case 1:
                                System.out.print("ID abonnement : ");
                                String abId = scanner.next();
                                scanner.nextLine();

                                System.out.print("Type paiement : ");
                                String typePaiement = scanner.nextLine();

                                System.out.print("Date echeance (YYYY-MM-DD) : ");
                                LocalDate dateEcheance = LocalDate.parse(scanner.next());

                                System.out.print("Date paiement (YYYY-MM-DD ou vide) : ");
                                String datePaiementStr = scanner.next();
                                LocalDate datePaiement = datePaiementStr.isEmpty() ? null : LocalDate.parse(datePaiementStr);

                                Paiement paiement = new Paiement(
                                        abId,
                                        dateEcheance,
                                        typePaiement
                                );
                                paiement.setDatePaiement(datePaiement);

                                paiementService.creer(paiement);
                                System.out.println("Paiement ajoute");
                                break;

                            case 2:
                                System.out.print("ID paiement a supprimer : ");
                                String idPaiement = scanner.next();
                                paiementService.supprimer(idPaiement);
                                System.out.println("Paiement supprime");
                                break;

                            case 3:
                                System.out.print("ID paiement a modifier : ");
                                String idPa = scanner.nextLine();
                                Paiement p = paiementService.findById(idPa);

                                if (p == null) {
                                    System.out.println("Paiement introuvable");
                                    break;
                                }

                                System.out.print("Nouvelle date paiement (yyyy-mm-dd) : ");
                                String dateStr = scanner.nextLine();
                                if (!dateStr.trim().isEmpty()) p.setDatePaiement(LocalDate.parse(dateStr));

                                System.out.print("Nouveau type (" + p.getTypePaiement() + ") : ");
                                String typeP = scanner.nextLine();
                                if (!typeP.trim().isEmpty()) p.setTypePaiement(typeP);

                                System.out.print("Nouveau statut (PAYE, NON_PAYE, EN_RETARD) : ");
                                String statutPaiement = scanner.nextLine();
                                if (!statutPaiement.trim().isEmpty()) {
                                    p.setStatut(Paiement.StatutPaiement.valueOf(statutPaiement));
                                }

                                paiementService.modifier(p);
                                System.out.println("Paiement modifie");
                                break;

                            case 4:
                                List<Paiement> paiements = paiementService.lister();
                                System.out.println("=== Liste des paiements ===");
                                for (Paiement pa : paiements) {
                                    System.out.println(pa);
                                }
                                break;

                            case 5:
                                afficherPaiementsAbonnement(paiementService, scanner);
                                break;
                        }
                    } while (choixPaie != 0);
                    break;

                case 3: // Operations sur les deux
                    int choixOp;
                    do {
                        System.out.println("\n--- MENU OPERATIONS ---");
                        System.out.println("1. Afficher paiements manques et total impaye");
                        System.out.println("2. Afficher somme payee d un abonnement");
                        System.out.println("3. Afficher 5 derniers paiements");
                        System.out.println("0. Retour");
                        System.out.print("Votre choix : ");
                        choixOp = scanner.nextInt();
                        scanner.nextLine();

                        switch (choixOp) {
                            case 1:
                                System.out.print("ID abonnement : ");
                                String idAbImp = scanner.nextLine();
                                List<Paiement> impayes = paiementService.getPaiementsManques(idAbImp);
                                if (impayes.isEmpty()) System.out.println("Aucun paiement manquant");
                                else {
                                    Abonnement aboImp = abonnementService.findById(idAbImp);
                                    double totalImpayes = aboImp.getMontantMensuel() * impayes.size();
                                    System.out.println("=== Paiements manques ===");
                                    for (Paiement pa : impayes) {
                                        System.out.println("ID : " + pa.getIdPaiement()
                                                + " | Date echeance : " + pa.getDateEcheance()
                                                + " | Statut : " + pa.getStatut());
                                    }
                                    System.out.println("Total impaye : " + totalImpayes);
                                }
                                break;

                            case 2:
                                System.out.print("ID abonnement : ");
                                String idAbPay = scanner.nextLine();
                                Abonnement aboPay = abonnementService.findById(idAbPay);
                                double totalPayee = paiementService.findByAbonnement(idAbPay).stream()
                                        .filter(pa -> pa.getStatut() == Paiement.StatutPaiement.PAYE)
                                        .mapToDouble(pa -> aboPay.getMontantMensuel())
                                        .sum();
                                System.out.println("Somme totale payee : " + totalPayee);
                                break;

                            case 3:
                                List<Paiement> derniers = paiementService.cinqDerniersPaiements();
                                if (derniers.isEmpty()) System.out.println("Aucun paiement trouve");
                                else {
                                    System.out.println("=== 5 derniers paiements ===");
                                    for (Paiement pa : derniers) {
                                        Abonnement a = abonnementService.findById(pa.getIdAbonnement());
                                        System.out.println("ID : " + pa.getIdPaiement()
                                                + " | Abonnement : " + pa.getIdAbonnement()
                                                + " | Date paiement : " + (pa.getDatePaiement() != null ? pa.getDatePaiement() : "Non paye")
                                                + " | Montant : " + a.getMontantMensuel()
                                                + " | Statut : " + pa.getStatut());
                                    }
                                }
                                break;
                        }
                    } while (choixOp != 0);
                    break;

                case 0:
                    System.out.println("Merci d avoir utilise l application");
                    break;

                default:
                    System.out.println("Choix invalide");
            }

        } while (choixPrincipal != 0);

        scanner.close();
    }

    public static void afficherPaiementsAbonnement(PaiementService paiementService, Scanner sc) {
        System.out.print("ID abonnement : ");
        String idAbonnement = sc.nextLine();

        List<Paiement> paiements = paiementService.findByAbonnement(idAbonnement);

        if (paiements.isEmpty()) {
            System.out.println("Aucun paiement trouve pour cet abonnement");
            return;
        }

        System.out.println("\n=== Paiements de l abonnement " + idAbonnement + " ===");
        for (Paiement p : paiements) {
            System.out.println("ID Paiement : " + p.getIdPaiement()
                    + " | Date echeance : " + p.getDateEcheance()
                    + " | Date paiement : " + (p.getDatePaiement() != null ? p.getDatePaiement() : "Non paye")
                    + " | Type : " + p.getTypePaiement()
                    + " | Statut : " + p.getStatut());
        }
    }
}
