package com.kerware.simulateurreusine.calcul;

import com.kerware.simulateur.SituationFamiliale;

public class VerificateurDonneesFiscales {


    private static final int REVENU_MIN = 0;
    private static final int NB_ENFANTS_MIN = 0;
    private static final int NB_ENFANTS_MAX = 7;
    private static final int NB_ENF_HANDICAPES_MIN = 0;
    private static final int REVENU_AUTORISE_DECLARANT2_SI_SEUL = 0;

    /**
     *  Checks the validity of the data provided for calculating tax allowances.
     *  This method lifts an exception if one of the validity conditions is not met.
     *
     * @param revenuNetDeclarant1 net income declared by the first person
     * @param revenuNetDeclarant2 net income declared by the second person
     * @param nbEnfants number of children in the family
     * @param nbEnfantsHandicapes among children, those with disabilities
     * @param estparentIsole whether the parent is single
     */
    public static void verifierDonnees(
            int revenuNetDeclarant1,
            int revenuNetDeclarant2,
            SituationFamiliale situationFamiliale,
            int nbEnfants,
            int nbEnfantsHandicapes,
            boolean estparentIsole
    ) {
        String erreur = "";
        if (revenuNetDeclarant1 < REVENU_MIN
                || revenuNetDeclarant2 < REVENU_MIN) {
            erreur = "Le revenu net ne peut pas être négatif";
            throw new IllegalArgumentException(erreur);
        }

        if (nbEnfants < NB_ENFANTS_MIN) {
            erreur = "Le nombre d'enfants ne peut pas être négatif";
            throw new IllegalArgumentException(erreur);
        }

        if (nbEnfantsHandicapes < NB_ENF_HANDICAPES_MIN) {
            erreur = "Le nombre d'enfants handicapés ne peut pas être négatif";
            throw new IllegalArgumentException(erreur);
        }

        if (situationFamiliale == null) {
            erreur = "La situation familiale ne peut pas être null";
            throw new IllegalArgumentException(erreur);
        }

        if (nbEnfantsHandicapes > nbEnfants) {
            erreur = "Le nombre d'enfants handicapés" +
                    " ne peut pas être supérieur au nombre d'enfants";
            throw new IllegalArgumentException(erreur);
        }

        if (nbEnfants > NB_ENFANTS_MAX) {
            erreur = "Le nombre d'enfants ne peut pas être supérieur à 7";
            throw new IllegalArgumentException(erreur);
        }

        if (estparentIsole && (situationFamiliale == SituationFamiliale.MARIE
                || situationFamiliale == SituationFamiliale.PACSE)) {
            erreur = "Un parent isolé ne peut pas être marié ou pacsé";
            throw new IllegalArgumentException(erreur);
        }

        boolean seul = situationFamiliale == SituationFamiliale.CELIBATAIRE
                || situationFamiliale == SituationFamiliale.DIVORCE
                || situationFamiliale == SituationFamiliale.VEUF;
        if (seul &&
                revenuNetDeclarant2 > REVENU_AUTORISE_DECLARANT2_SI_SEUL) {
            erreur = "Un célibataire, un divorcé ou un veuf" +
                    " ne peut pas avoir de revenu pour le déclarant 2";
            throw new IllegalArgumentException(erreur);
        }
    }
}
