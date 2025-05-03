package com.kerware.simulateurreusine.calcul;

public class Revenu {
    /**
     *
     *  Calculates the household's reference tax income based on the net income declared by the members of the tax household
     *  and the applicable tax allowance.
     *
     * @param paramRevenuNetDeclarant1 net income declared by the first person in the tax household
     * @param paramRevenuNetDeclarant2 net income declared by the second person in the tax household
     * @param paramAbattement deduction from total household income
     *
     * @return household reference tax income
     */
    public static double calculerRevenuFiscal(
            int paramRevenuNetDeclarant1,
            int paramRevenuNetDeclarant2,
            double paramAbattement){
        double revenuFiscalReference = paramRevenuNetDeclarant1 + paramRevenuNetDeclarant2;
        revenuFiscalReference -= paramAbattement;
        if ( revenuFiscalReference < 0 ) {
            revenuFiscalReference = 0;
        }
        return revenuFiscalReference;
    }

}
