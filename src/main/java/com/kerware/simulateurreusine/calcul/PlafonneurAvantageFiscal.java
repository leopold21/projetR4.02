package com.kerware.simulateurreusine.calcul;

import com.kerware.simulateurreusine.outils.ConstantesFiscales;

public class PlafonneurAvantageFiscal {

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
