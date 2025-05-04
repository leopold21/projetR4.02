package com.kerware.simulateurreusine;

import com.kerware.simulateur.SituationFamiliale;
import com.kerware.simulateurreusine.calcul.Abattement;
import com.kerware.simulateurreusine.calcul.Decote;
import com.kerware.simulateurreusine.calcul.Impot;
import com.kerware.simulateurreusine.calcul.PartsFiscales;
import com.kerware.simulateurreusine.calcul.PlafonneurAvantageFiscal;
import com.kerware.simulateurreusine.calcul.Revenu;
import com.kerware.simulateurreusine.calcul.VerificateurDonneesFiscales;
import com.kerware.simulateurreusine.calcul.ContributionExceptionnelle;
import com.kerware.simulateurreusine.outils.ConstantesFiscales;


// Version réusinée du Simulateur, adaptée pour une meilleure modularité et lisibilité
public final class SimulateurReusine {

    // revenu net
    private int revenuNetDeclarant1 = 0;
    private int revenuNetDeclarant2 = 0;
    // nb enfants
    private int nbEnfants = 0;
    // nb enfants handicapés
    private int nbEnfantsHandicapes = 0;

    // revenu fiscal de référence
    private double revenuFiscalReference = 0;

    // abattement
    private double abattement = 0;

    // nombre de parts des  déclarants
    private double nbPartsDeclarants = 0;
    // nombre de parts du foyer fiscal
    private double nbPartsFoyer = 0;

    // decote
    private double decote = 0;
    // impôt des déclarants
    private double montantImpotDeclarant = 0;
    // impôt du foyer fiscal
    private double montantImpotFoyer = 0;
    private double montantImpotAvantDecote = 0;
    // parent isolé
    private boolean estParentIsole = false;
    // Contribution exceptionnelle sur les hauts revenus
    private double montantContributionExceptionnelle = 0;

    // Getters pour adapter le code legacy pour les tests unitaires

    public double getRevenuReference() {
        return revenuFiscalReference;
    }

    public double getDecote() {
        return decote;
    }


    public double getAbattement() {
        return abattement;
    }

    public double getNbParts() {
        return nbPartsFoyer;
    }

    public double getImpotAvantDecote() {
        return montantImpotAvantDecote;
    }

    public double getImpotNet() {
        return montantImpotFoyer;
    }

    public int getRevenuNetDeclatant1() {
        return revenuNetDeclarant1;
    }

    public int getRevenuNetDeclatant2() {
        return revenuNetDeclarant2;
    }

    public double getMontantContributionExceptionnelle() {
        return montantContributionExceptionnelle;
    }

    private void initialiserVariables(
            int rev1, int rev2, int enfants, int enfantsHandicapes, boolean isolé
    ) {
        this.revenuNetDeclarant1 = rev1;
        this.revenuNetDeclarant2 = rev2;
        this.nbEnfants = enfants;
        this.nbEnfantsHandicapes = enfantsHandicapes;
        this.estParentIsole = isolé;
    }


    // Fonction de calcul de l'impôt sur le revenu net en France en 2024 sur les revenu 2023

    public int calculImpot(
            int paramRevenuNetDeclarant1, int paramRevenuNetDeclarant2,
            SituationFamiliale paramSituationFamilial, int paramNbEnfants,
            int paramNbEnfantsHandicapes, boolean paramEstParentIsol
    ) {
        VerificateurDonneesFiscales.verifierDonnees(paramRevenuNetDeclarant1,
                paramRevenuNetDeclarant2, paramSituationFamilial,
                paramNbEnfants, paramNbEnfantsHandicapes, paramEstParentIsol);

        // Initialisation des variables
        initialiserVariables(paramRevenuNetDeclarant1, paramRevenuNetDeclarant2,
                paramNbEnfants, paramNbEnfantsHandicapes, paramEstParentIsol);

        // Abattement EXIGENCE : EXG_IMPOT_02
        this.abattement = Abattement.calculerAbattement(
                paramSituationFamilial, revenuNetDeclarant1, revenuNetDeclarant2);
        this.revenuFiscalReference = Revenu.calculerRevenuFiscal(
                revenuNetDeclarant1, revenuNetDeclarant2, this.abattement);

        // parts déclarants EXIG  : EXG_IMPOT_03
        nbPartsDeclarants = PartsFiscales.calculerPartsDeclarants(paramSituationFamilial);
        nbPartsFoyer = PartsFiscales.calculerPartsFoyer(paramSituationFamilial, nbPartsDeclarants,
            this.nbEnfants, this.nbEnfantsHandicapes, estParentIsole);

        // Contribution exceptionnelle sur les hauts revenus EXIGENCE : EXG_IMPOT_07:
        montantContributionExceptionnelle = ContributionExceptionnelle.calculer(
                revenuFiscalReference, nbPartsDeclarants);

        // Calcul impôt des declarants : EXIGENCE : EXG_IMPOT_04
        montantImpotDeclarant = Impot.calculerImpotBrut(revenuFiscalReference, nbPartsDeclarants);

        // Calcul impôt foyer fiscal complet : EXIGENCE : EXG_IMPOT_04
        montantImpotFoyer = Impot.calculerImpotBrut(revenuFiscalReference, nbPartsFoyer);

        // Vérification de la baisse d'impôt autorisée : EXIGENCE : EXG_IMPOT_05
        // baisse impot
        montantImpotAvantDecote = PlafonneurAvantageFiscal.appliquerPlafond(
                montantImpotDeclarant, montantImpotFoyer, nbPartsDeclarants, nbPartsFoyer );

        // Calcul de la decote : EXIGENCE : EXG_IMPOT_06
        decote = Decote.calculer(montantImpotFoyer, nbPartsDeclarants);

        montantImpotFoyer = Impot.calculerImpotNet(
            montantImpotAvantDecote, montantContributionExceptionnelle, decote );

        afficherInformationsFinales(paramSituationFamilial);
        return  (int) montantImpotFoyer;
    }

    private void afficherInformationsFinales(SituationFamiliale situation) {
        System.out.println("--------------------------------------------------");
        System.out.println("Revenu net declarant1 : " + revenuNetDeclarant1);
        System.out.println("Revenu net declarant2 : " + revenuNetDeclarant2);
        System.out.println("Situation familiale : " + situation.name());
        System.out.println("Abattement : " + abattement);
        System.out.println("Revenu fiscal de référence : " + revenuFiscalReference);
        System.out.println("Parts déclarants : " + nbPartsDeclarants);
        System.out.println("Parts foyer fiscal : " + nbPartsFoyer);
        System.out.println("Impôt brut déclarants : " + montantImpotDeclarant);
        System.out.println("Impôt brut foyer : " + montantImpotFoyer);
        System.out.println("Impôt plafonné (avant décote) : " + montantImpotAvantDecote);
        System.out.println("Décote : " + decote);
        System.out.println("Contribution exceptionnelle : " + montantContributionExceptionnelle);
        System.out.println("Impôt net final : " + montantImpotFoyer);
    }

}
