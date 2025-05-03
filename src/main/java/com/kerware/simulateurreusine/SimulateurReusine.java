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

    // revenu imposable
    private double revenuImposable = 0;

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


    // Fonction de calcul de l'impôt sur le revenu net en France en 2024 sur les revenu 2023

    /**
     * ...
     *
     * @param paramRevenuNetDeclarant1 net income declared by the first person
     * @param paramRevenuNetDeclarant2 net income declared by the second person
     * @param paramSituationFamilial marital status (single, civil partnership, married, divorced or widowed)
     * @param paramNbEnfants number of children in the family
     * @param paramNbEnfantsHandicapes among children, those with disabilities
     * @param paramEstParentIsol whether or not the parent is single
     *
     * @return Amount of income tax calculated for the tax household
     */
    public int calculImpot(
            int paramRevenuNetDeclarant1,
            int paramRevenuNetDeclarant2,
            SituationFamiliale paramSituationFamilial,
            int paramNbEnfants,
            int paramNbEnfantsHandicapes,
            boolean paramEstParentIsol
    ) {

        VerificateurDonneesFiscales.verifierDonnees(
                paramRevenuNetDeclarant1,
                paramRevenuNetDeclarant2,
                paramSituationFamilial,
                paramNbEnfants,
                paramNbEnfantsHandicapes,
                paramEstParentIsol
        );

        // Initialisation des variables
        revenuNetDeclarant1 = paramRevenuNetDeclarant1;
        revenuNetDeclarant2 = paramRevenuNetDeclarant2;

        this.nbEnfants = paramNbEnfants;
        this.nbEnfantsHandicapes = paramNbEnfantsHandicapes;
        estParentIsole = paramEstParentIsol;

        int[] limites = ConstantesFiscales.TRANCHES;
        double[] taux = ConstantesFiscales.TAUX;

        int[] limitesCEHR = ConstantesFiscales.CEHR;
        double[] tauxCelib = ConstantesFiscales.TAUX_CEHR_CELIB;
        double[] tauxCouple = ConstantesFiscales.TAUX_CEHR_COUPLE;

        double tauxAbattement = ConstantesFiscales.TAUX_ABATTEMENT;
        int abattementMax = ConstantesFiscales.ABATTEMENT_MAX;
        int abattementMin = ConstantesFiscales.ABATTEMENT_MIN;

        double plafondDemiPart = ConstantesFiscales.PLAFOND_DEMI_PART;

        double seuilSeul = ConstantesFiscales.SEUIL_DECOTE_SEUL;
        double seuilCouple = ConstantesFiscales.SEUIL_DECOTE_COUPLE;
        double decoteMaxSeul = ConstantesFiscales.DECOTE_MAX_SEUL;
        double decoteMaxCouple = ConstantesFiscales.DECOTE_MAX_COUPLE;
        double tauxDecote = ConstantesFiscales.TAUX_DECOTE;

        System.out.println("--------------------------------------------------");
        System.out.println( "Revenu net declarant1 : " + revenuNetDeclarant1);
        System.out.println( "Revenu net declarant2 : " + revenuNetDeclarant2);
        System.out.println( "Situation familiale : " + paramSituationFamilial.name() );

        // Abattement
        // EXIGENCE : EXG_IMPOT_02
        this.abattement = Abattement.calculerAbattement(
                paramSituationFamilial,
                revenuNetDeclarant1,
                revenuNetDeclarant2
        );
        System.out.println( "Abattement : " + abattement);

        this.revenuFiscalReference = Revenu.calculerRevenuFiscal(
                revenuNetDeclarant1,
                revenuNetDeclarant2,
                this.abattement
        );

        System.out.println( "Revenu fiscal de référence : " + revenuFiscalReference);


        // parts déclarants
        // EXIG  : EXG_IMPOT_03
        nbPartsDeclarants = PartsFiscales.calculerPartsDeclarants(paramSituationFamilial);
        nbPartsFoyer = PartsFiscales.calculerPartsFoyer(
            paramSituationFamilial,
            nbPartsDeclarants,
            this.nbEnfants,
            this.nbEnfantsHandicapes,
            estParentIsole
        );

        // EXIGENCE : EXG_IMPOT_07:
        // Contribution exceptionnelle sur les hauts revenus
        montantContributionExceptionnelle = ContributionExceptionnelle.calculer(
                revenuFiscalReference,
                nbPartsDeclarants
        );


        // Calcul impôt des declarants
        // EXIGENCE : EXG_IMPOT_04
        montantImpotDeclarant = Impot.calculerImpotBrut(revenuFiscalReference, nbPartsDeclarants);

        // Calcul impôt foyer fiscal complet
        // EXIGENCE : EXG_IMPOT_04
        montantImpotFoyer = Impot.calculerImpotBrut(revenuFiscalReference, nbPartsFoyer);


        // Vérification de la baisse d'impôt autorisée
        // EXIGENCE : EXG_IMPOT_05
        // baisse impot
        montantImpotAvantDecote = PlafonneurAvantageFiscal.appliquerPlafond(
                montantImpotDeclarant,
                montantImpotFoyer,
                nbPartsDeclarants,
                nbPartsFoyer
        );

        // Calcul de la decote
        // EXIGENCE : EXG_IMPOT_06

        decote = Decote.calculer(montantImpotFoyer, nbPartsDeclarants);

        System.out.println( "Decote : " + decote );

        montantImpotFoyer = Impot.calculerImpotNet(
            montantImpotAvantDecote,
            montantContributionExceptionnelle,
            decote
        );

        System.out.println( "Impôt sur le revenu net final : " + montantImpotFoyer);
        return  (int) montantImpotFoyer;
    }

}
