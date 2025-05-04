package com.kerware.simulateurreusine.calcul;

import com.kerware.simulateur.SituationFamiliale;

public class PartsFiscales {

    private static final double PART_DECLARANT_SEUL = 1.0;
    private static final double PART_COUPLE = 2.0;
    private static final double PART_ENFANT = 0.5;
    private static final double PART_TROISIEME_ENFANT_ET_SUIVANTS = 1.0;
    private static final double PART_PARENT_ISOLE = 0.5;
    private static final double PART_VEUF_ENFANT = 1.0;
    private static final double PART_ENFANT_HANDICAPE = 0.5;

    public static double calculerPartsDeclarants(SituationFamiliale paramSituationFamilial){
        switch ( paramSituationFamilial ) {
            case MARIE:
            case PACSE:
                return PART_COUPLE;
            default:
                return PART_DECLARANT_SEUL;
        }
    }

    public static double calculerPartsFoyer(
            SituationFamiliale paramSituationFamilial,
            double paramNbPartsDeclarants,
            int paramNbEnfants,
            int paramNbEnfantsHandicapes,
            boolean paramEstParentIsole
    ){
        double parts = paramNbPartsDeclarants;

        if (paramNbEnfants <= 2) {
            parts += paramNbEnfants * PART_ENFANT;
        } else {
            parts += (2 * PART_ENFANT) + ((paramNbEnfants - 2) * PART_TROISIEME_ENFANT_ET_SUIVANTS);
        }

        if (paramEstParentIsole && paramNbEnfants > 0) {
            parts += PART_PARENT_ISOLE;
        }

        if (paramSituationFamilial == SituationFamiliale.VEUF && paramNbEnfants > 0) {
            parts += PART_VEUF_ENFANT;
        }

        parts += paramNbEnfantsHandicapes * PART_ENFANT_HANDICAPE;

        return parts;
    }

}
