package com.kerware.simulateurreusine.calcul;

import com.kerware.simulateurreusine.outils.ConstantesFiscales;

public class Impot {

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

    public static double calculerImpotNet(
            double impotBrutFoyer,
            double contributionExceptionnelle,
            double decote
    ) {
        double impotNet = impotBrutFoyer - decote + contributionExceptionnelle;
        return Math.round(Math.max(0, impotNet));
    }

}
