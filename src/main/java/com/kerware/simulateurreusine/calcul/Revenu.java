package com.kerware.simulateurreusine.calcul;

public class Revenu {

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
