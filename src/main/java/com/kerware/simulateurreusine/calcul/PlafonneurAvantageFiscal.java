package com.kerware.simulateurreusine.calcul;

import com.kerware.simulateurreusine.outils.ConstantesFiscales;

public class PlafonneurAvantageFiscal {

    /**
     * Applies a ceiling to the tax reduction based on the number of units declared and the number of units
     * in the household.
     *
     * @param montantImpotDeclarant the tax amount calculated for the tax filer
     * @param montantImpotFoyer the tax amount calculated for the household
     * @param nbPartsDeclarants the number of reporting units
     * @param nbPartsFoyer the total number of tax household units
     *
     * @return the household tax after application of the ceiling
     */
    public static double appliquerPlafond(
            double montantImpotDeclarant,
            double montantImpotFoyer,
            double nbPartsDeclarants,
            double nbPartsFoyer
    ) {
        double baisseImpot = montantImpotDeclarant - montantImpotFoyer;

        double ecartParts = nbPartsFoyer - nbPartsDeclarants;
        double plafond = ecartParts / ConstantesFiscales.VALEUR_DEMI_PART;
        plafond *= ConstantesFiscales.PLAFOND_DEMI_PART;
        System.out.println("Baisse d'impôt : " + baisseImpot);
        System.out.println("Plafond de baisse autorisée : " + plafond);

        if (baisseImpot >= plafond) {
            montantImpotFoyer = montantImpotDeclarant - plafond;
        }

        return montantImpotFoyer;
    }
}
