package com.kerware.simulateurreusine.calcul;

import com.kerware.simulateurreusine.outils.ConstantesFiscales;

public class ContributionExceptionnelle {

    /**
     * Calculates the amount of the Contribution Exceptionnelle sur les Hauts Revenus (CEHR)
     * based on reference tax income and number of tax units.
     * @param paramRevenuFiscalReference
     * @param paramNbPartsDeclarants number of shares declared
     *
     * @return CEHR amount rounded to the nearest whole number
     */

    public static double calculer(
            double paramRevenuFiscalReference,
            double paramNbPartsDeclarants
    ) {
        int[] limites = ConstantesFiscales.CEHR;
        double[] tauxCelib = ConstantesFiscales.TAUX_CEHR_CELIB;
        double[] tauxCouple = ConstantesFiscales.TAUX_CEHR_COUPLE;

        double montant = 0;
        int i = 0;

        do {
            if (paramRevenuFiscalReference >= limites[i]
                    && paramRevenuFiscalReference < limites[i + 1]) {
                if (paramNbPartsDeclarants == 1) {
                    montant += (paramRevenuFiscalReference - limites[i]) * tauxCelib[i];
                } else {
                    montant += (paramRevenuFiscalReference - limites[i]) * tauxCouple[i];
                }
                break;
            } else {
                if (paramNbPartsDeclarants == 1) {
                    montant += (limites[i + 1] - limites[i]) * tauxCelib[i];
                } else {
                    montant += (limites[i + 1] - limites[i]) * tauxCouple[i];
                }
            }
            i++;
        } while (i < limites.length - 1);

        return Math.round(montant);
    }

}
