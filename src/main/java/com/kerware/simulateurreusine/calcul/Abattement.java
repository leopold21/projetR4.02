package com.kerware.simulateurreusine.calcul;

import com.kerware.simulateur.SituationFamiliale;
import com.kerware.simulateurreusine.outils.ConstantesFiscales;

public class Abattement {

    /**
     * Calculates the tax allowance based on family situation and net income declared by members of the tax household.
     *
     * @param paramSituationFamilial the marital status (single, civil partnership, married, divorced or widowed)
     * @param paramRevenuNetDeclarant1 the net income declared by the first person in the tax household
     * @param paramRevenuNetDeclarant2 the net income declared by the second person in the tax household
     *
     * @return the sum of allowances calculated for each member of the tax household
     */
    public static double calculerAbattement(
            SituationFamiliale paramSituationFamilial,
            double paramRevenuNetDeclarant1,
            double paramRevenuNetDeclarant2) {
        long abt1 = Math.round(
                paramRevenuNetDeclarant1 * ConstantesFiscales.TAUX_ABATTEMENT
        );
        long abt2 = Math.round(
                paramRevenuNetDeclarant2 * ConstantesFiscales.TAUX_ABATTEMENT
        );

        if (abt1 > ConstantesFiscales.ABATTEMENT_MAX) {
            abt1 = ConstantesFiscales.ABATTEMENT_MAX;
        }
        if ( paramSituationFamilial == SituationFamiliale.MARIE
                || paramSituationFamilial == SituationFamiliale.PACSE ) {
            if (abt2 > ConstantesFiscales.ABATTEMENT_MAX) {
                abt2 = ConstantesFiscales.ABATTEMENT_MAX;
            }
        }

        if (abt1 < ConstantesFiscales.ABATTEMENT_MIN) {
            abt1 = ConstantesFiscales.ABATTEMENT_MIN;
        }

        if ( paramSituationFamilial == SituationFamiliale.MARIE
                || paramSituationFamilial == SituationFamiliale.PACSE ) {
            if (abt2 < ConstantesFiscales.ABATTEMENT_MIN) {
                abt2 = ConstantesFiscales.ABATTEMENT_MIN;
            }
        }

        // double abattementFinal = abt1 + abt2;
        return abt1 + abt2;
    }

}
