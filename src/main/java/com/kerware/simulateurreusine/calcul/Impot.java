package com.kerware.simulateurreusine.calcul;

import com.kerware.simulateurreusine.outils.ConstantesFiscales;

public class Impot {
    /**
     * Calculates gross income tax based on reference tax income and number of units.
     *
     * @param revenuFiscalReference tax income of reference
     * @param nbParts number of tax household units
     *
     * @return gross tax amount
     */
    public static double calculerImpotBrut(double revenuFiscalReference, double nbParts) {
        double revenuImposable = revenuFiscalReference / nbParts;
        double montantImpot = 0;

        int[] limites = ConstantesFiscales.TRANCHES;
        double[] taux = ConstantesFiscales.TAUX;

        int i = 0;
        do {
            if (revenuImposable >= limites[i] && revenuImposable < limites[i + 1]) {
                montantImpot += (revenuImposable - limites[i]) * taux[i];
                break;
            } else {
                montantImpot += (limites[i + 1] - limites[i]) * taux[i];
            }
            i++;
        } while (i < taux.length);

        montantImpot *= nbParts;
        return Math.round(montantImpot);
    }

    /**
     * Calculates the net tax payable based on the household's gross tax, the exceptional
     * contribution to high incomes, and the discount applied.
     *
     * @param impotBrutFoyer the household gross tax
     * @param contributionExceptionnelle the exceptional tax on high incomes
     * @param decote the amount of discount applied to gross tax
     *
     * @return the net tax amount
     */
    public static double calculerImpotNet(
            double impotBrutFoyer,
            double contributionExceptionnelle,
            double decote
    ) {
        double impotNet = impotBrutFoyer - decote + contributionExceptionnelle;
        return Math.round(Math.max(0, impotNet));
    }

}
