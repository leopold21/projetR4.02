package com.kerware.simulateurreusine.calcul;

import com.kerware.simulateurreusine.outils.ConstantesFiscales;

public class Decote {

    /**
     * Calculates the discount applicable to tax based on the amount of tax and the number of units
     * in the tax household.
     *
     * @param montantImpot gross tax amount
     * @param nbParts number of tax household units
     *
     * @return discount amount
     */
    public static double calculer(double montantImpot, double nbParts) {
        double decote = 0;

        if (nbParts == 1) {
            if (montantImpot < ConstantesFiscales.SEUIL_DECOTE_SEUL) {
                decote = ConstantesFiscales.DECOTE_MAX_SEUL;
                decote -= montantImpot * ConstantesFiscales.TAUX_DECOTE;
            }
        }

        if (nbParts == 2) {
            if (montantImpot < ConstantesFiscales.SEUIL_DECOTE_COUPLE) {
                decote = ConstantesFiscales.DECOTE_MAX_COUPLE;
                decote -= montantImpot * ConstantesFiscales.TAUX_DECOTE;
            }
        }

        decote = Math.round(decote);

        if (montantImpot <= decote) {
            decote = montantImpot;
        }

        return decote;
    }

}
