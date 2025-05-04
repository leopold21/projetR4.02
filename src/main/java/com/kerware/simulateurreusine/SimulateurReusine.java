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



// Reworked version of the Simulator, adapted for improved modularity and readability
public final class SimulateurReusine {

    // net income
    private int revenuNetDeclarant1 = 0;
    private int revenuNetDeclarant2 = 0;

    // number of children
    private int nbEnfants = 0;
    // number of disabled children
    private int nbEnfantsHandicapes = 0;

    //reference tax income
    private double revenuFiscalReference = 0;

    //allowance
    private double abattement = 0;

    // number of shares held by declarants
    private double nbPartsDeclarants = 0;
    // number of tax household units
    private double nbPartsFoyer = 0;

    //discount
    private double decote = 0;
    // impôt des déclarants
    private double montantImpotDeclarant = 0;

    //tax declarants
    private double montantImpotFoyer = 0;
    private double montantImpotAvantDecote = 0;
    // single parent
    private boolean estParentIsole = false;
    //
    //Exceptional tax on high incomes
    private double montantContributionExceptionnelle = 0;

    // Getters to adapt legacy code for unit testing
    /**
     *
     * Returns the household's reference tax income.
     *
     * @return household reference tax income
     */
    public double getRevenuReference() {
        return revenuFiscalReference;
    }

    /**
     *
     Returns the amount of the discount applicable to the tax household's gross tax.
     *
     * @return amount of discount applicable to the gross tax of the tax household
     */
    public double getDecote() {
        return decote;
    }

    /**
     *
     Returns the amount of the applicable allowance.
     *
     * @return amount of allowance
     */
    public double getAbattement() {
        return abattement;
    }

    /**
     * Returns the number of units in the tax household.
     *
     * @return the number of tax household units.
     */
    public double getNbParts() {
        return nbPartsFoyer;
    }

    /**
     * Returns the tax amount before application of the discount.
     *
     * @return number of household tax units
     */
    public double getImpotAvantDecote() {
        return montantImpotAvantDecote;
    }

    /**
     *
     Returns the amount of net tax payable
     *
     * @return net tax payable
     */
    public double getImpotNet() {
        return montantImpotFoyer;
    }

    /**
     * Returns the net income declared by the first tax filer in the tax household.
     *
     * @return net income declared by the first tax filer in the tax household
     */
    public int getRevenuNetDeclatant1() {
        return revenuNetDeclarant1;
    }

    /**
     * Returns the net income declared by the second tax filer in the tax household.
     *
     * @return net income declared by the second tax filer in the tax household
     */
    public int getRevenuNetDeclatant2() {
        return revenuNetDeclarant2;
    }

    /**
     * Returns the amount of the exceptional contribution
     *
     * @return the amount of the exceptional contribution
     */
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

    /**
     * Calculates the net income tax for a tax household in France in 2024,
     * based on income declared in 2023.
     *
     * @param paramRevenuNetDeclarant1 net income declared by the first person
     * @param paramRevenuNetDeclarant2 net income declared by the second person
     * @param paramSituationFamilial marital status
     * (single, civil partnership, married, divorced or widowed)
     * @param paramNbEnfants number of children in the family
     * @param paramNbEnfantsHandicapes among children, those with disabilities
     * @param paramEstParentIsol whether the parent is single
     *
     * @return Amount of income tax calculated for the tax household
     */
    public int calculImpot(
            int paramRevenuNetDeclarant1, int paramRevenuNetDeclarant2,
            SituationFamiliale paramSituationFamilial, int paramNbEnfants,
            int paramNbEnfantsHandicapes, boolean paramEstParentIsol
    ) {
        VerificateurDonneesFiscales.verifierDonnees(paramRevenuNetDeclarant1,
                paramRevenuNetDeclarant2, paramSituationFamilial,
                paramNbEnfants, paramNbEnfantsHandicapes, paramEstParentIsol);


        //Variable initialization
        initialiserVariables(paramRevenuNetDeclarant1, paramRevenuNetDeclarant2,
                paramNbEnfants, paramNbEnfantsHandicapes, paramEstParentIsol);

        //Abatement REQUIREMENT : EXG_IMPOT_02
        this.abattement = Abattement.calculerAbattement(
                paramSituationFamilial, revenuNetDeclarant1, revenuNetDeclarant2);
        this.revenuFiscalReference = Revenu.calculerRevenuFiscal(
                revenuNetDeclarant1, revenuNetDeclarant2, this.abattement);

        //reporting units REQUIREMENT  : EXG_IMPOT_03
        nbPartsDeclarants = PartsFiscales.calculerPartsDeclarants(paramSituationFamilial);
        nbPartsFoyer = PartsFiscales.calculerPartsFoyer(paramSituationFamilial, nbPartsDeclarants,
            this.nbEnfants, this.nbEnfantsHandicapes, estParentIsole);


        //Exceptional tax on high incomes REQUIREMENT: EXG_IMPOT_07
        montantContributionExceptionnelle = ContributionExceptionnelle.calculer(
                revenuFiscalReference, nbPartsDeclarants);

        //Tax calculation for tax filers : REQUIREMENT : EXG_IMPOT_04
        montantImpotDeclarant = Impot.calculerImpotBrut(revenuFiscalReference, nbPartsDeclarants);

        // Complete tax household calculation: REQUIREMENT: EXG_IMPOT_04
        montantImpotFoyer = Impot.calculerImpotBrut(revenuFiscalReference, nbPartsFoyer);


        // Check authorized tax reduction: REQUIREMENT: EXG_IMPOT_05
        // tax reduction
        montantImpotAvantDecote = PlafonneurAvantageFiscal.appliquerPlafond(
                montantImpotDeclarant, montantImpotFoyer, nbPartsDeclarants, nbPartsFoyer );


        // Discount calculation : REQUIREMENT : EXG_IMPOT_06
        decote = Decote.calculer(montantImpotFoyer, nbPartsDeclarants);

        montantImpotFoyer = Impot.calculerImpotNet(
            montantImpotAvantDecote, montantContributionExceptionnelle, decote );

        afficherInformationsFinales(paramSituationFamilial);
        return  (int) montantImpotFoyer;
    }

    /**
     * Displays final net income tax calculation information.
     *
     * @param situation marital status (single, civil partnership, married, divorced or widowed)
     */
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
