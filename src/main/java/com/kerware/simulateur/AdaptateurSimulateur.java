package com.kerware.simulateur;

// le nouveau simulateur que l'on va réusiner petit à petit
import com.kerware.simulateurreusine.SimulateurReusine;

public final class AdaptateurSimulateur implements ICalculateurImpot {

    private SimulateurReusine simulateur = new SimulateurReusine();

    private int revenusNetDecl1 = 0;
    private int revenusNetDecl2 = 0;
    private SituationFamiliale situationFamiliale;
    private int nbEnfantsACharge;
    private int nbEnfantsSituationHandicap;
    private boolean parentIsole;

    /**
     * Defines the net income of the first declarant.
     *
     * @param rn the net income of first filer
     */

    @Override
    public void setRevenusNetDeclarant1(int rn) {
        this.revenusNetDecl1 = rn;
    }

    /**
     * Defines the net income of the second declarant.
     *
     * @param rn the net income of second filer
     */
    @Override
    public void setRevenusNetDeclarant2(int rn) {
        this.revenusNetDecl2 = rn;
    }

    /**
     *
     * Defines the declarant's family situation.
     *
     * @param sf the marital status (single, civil union, married, divorced or widowed)
     */
    @Override
    public void setSituationFamiliale(SituationFamiliale sf) {
        this.situationFamiliale = sf;
    }

    /**
     *Defines the number of dependent children in the tax household.
     *
     * @param nbe the number of dependent children
     */
    @Override
    public void setNbEnfantsACharge(int nbe) {
        this.nbEnfantsACharge = nbe;
    }

    /**
     * Defines the number of disabled children in the tax household.
     *
     * @param nbesh the number of disabled children
     */
    @Override
    public void setNbEnfantsSituationHandicap(int nbesh) {
        this.nbEnfantsSituationHandicap = nbesh;
    }

    /**
     * Defines whether the parent is isolated.
     *
     * @param pi true if the parent is isolated, false otherwise
     */

    @Override
    public void setParentIsole(boolean pi) {
        this.parentIsole = pi;
    }

    /**
     * Calculates net income tax using tax household information.
     */

    @Override
    public void calculImpotSurRevenuNet() {
         simulateur.calculImpot(
                 revenusNetDecl1,
                 revenusNetDecl2,
                 situationFamiliale,
                 nbEnfantsACharge,
                 nbEnfantsSituationHandicap,
                 parentIsole);
    }

    /**
     *
     Returns the net income declared by the first tax filer in the tax household.
     *
     * @return the net income declared by the first tax filer in the tax household
     */
    @Override
    public int getRevenuNetDeclatant1() {
        return revenusNetDecl1;
    }

    /**
     *
     * Returns the net income declared by the second tax filer in the tax household.
     *
     * @return the net income declared by the second tax filer in the tax household
     */
    @Override
    public int getRevenuNetDeclatant2() {
        return revenusNetDecl2;
    }

    /**
     *
     Returns the amount of the exceptional contribution.
     *
     * @return the amount of exceptional contribution
     */
    @Override
    public double getContribExceptionnelle() {
        return simulateur.getMontantContributionExceptionnelle();
    }

    /**
     *
     * Returns the household's reference tax income.
     *
     * @return the household reference tax income
     */
    @Override
    public int getRevenuFiscalReference() {
        return (int)simulateur.getRevenuReference();
    }

    /**
     * Returns the amount of the allowance.
     *
     * @return the amount of allowance
     */
    @Override
    public int getAbattement() {
        return (int)simulateur.getAbattement();
    }

    /**
     *
     * Returns the number of units in the tax household.
     *
     * @return the number of household tax units
     */
    @Override
    public double getNbPartsFoyerFiscal() {
        return simulateur.getNbParts();
    }

    /**
     *
     * Returns the tax amount before application of the discount.
     *
     * @return the amount of income tax before application of the discount
     */
    @Override
    public int getImpotAvantDecote() {
        return (int)simulateur.getImpotAvantDecote();
    }

    /**
     *
     * Returns the amount of the discount applicable to the tax household's gross tax.
     *
     * @return the amount of discount applicable to the gross tax of the tax household
     */
    @Override
    public int getDecote() {
        return (int)simulateur.getDecote();
    }

    /**
     * Returns the amount of net income tax after application of the discount.
     *
     * @return the net tax amount
     */
    @Override
    public int getImpotSurRevenuNet() {
        return (int)simulateur.getImpotNet();
    }
}
