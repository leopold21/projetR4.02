package com.kerware.simulateurreusine.calcul;

import com.kerware.simulateur.SituationFamiliale;

public class VerificateurDonneesFiscales {


    private static final int REVENU_MIN = 0;
    private static final int NB_ENFANTS_MIN = 0;
    private static final int NB_ENFANTS_MAX = 7;
    private static final int NB_ENF_HANDICAPES_MIN = 0;
    private static final int REVENU_AUTORISE_DECLARANT2_SI_SEUL = 0;

    public static void verifierDonnees(
            int revenuNetDeclarant1,
            int revenuNetDeclarant2,
            SituationFamiliale situationFamiliale,
            int nbEnfants,
            int nbEnfantsHandicapes,
            boolean estparentIsole
    ) {
        if (revenuNetDeclarant1 < REVENU_MIN || revenuNetDeclarant2 < REVENU_MIN) {
            throw new IllegalArgumentException("Le revenu net ne peut pas être négatif");
        }

        if (nbEnfants < NB_ENFANTS_MIN) {
            throw new IllegalArgumentException("Le nombre d'enfants ne peut pas être négatif");
        }

        if (nbEnfantsHandicapes < NB_ENF_HANDICAPES_MIN) {
            throw new IllegalArgumentException("Le nombre d'enfants handicapés ne peut pas être négatif");
        }

        if (situationFamiliale == null) {
            throw new IllegalArgumentException("La situation familiale ne peut pas être null");
        }

        if (nbEnfantsHandicapes > nbEnfants) {
            throw new IllegalArgumentException("Le nombre d'enfants handicapés ne peut pas être supérieur au nombre d'enfants");
        }

        if (nbEnfants > NB_ENFANTS_MAX) {
            throw new IllegalArgumentException("Le nombre d'enfants ne peut pas être supérieur à 7");
        }

        if (estparentIsole && (situationFamiliale == SituationFamiliale.MARIE || situationFamiliale == SituationFamiliale.PACSE)) {
            throw new IllegalArgumentException("Un parent isolé ne peut pas être marié ou pacsé");
        }

        boolean seul = situationFamiliale == SituationFamiliale.CELIBATAIRE || situationFamiliale == SituationFamiliale.DIVORCE || situationFamiliale == SituationFamiliale.VEUF;
        if (seul && revenuNetDeclarant2 > REVENU_AUTORISE_DECLARANT2_SI_SEUL) {
            throw new IllegalArgumentException("Un célibataire, un divorcé ou un veuf ne peut pas avoir de revenu pour le déclarant 2");
        }
    }

    /*
    private int revenuNetDeclarantMinimum;
    private int nbEnfantsMinimum;
    private int nbEnfantsMaximum;
    private int nbEnfantsHandicapesMinimum;

    public VerificateurDonneesFiscales(
            int paramRevenuNetDeclarantMinimum,
            int paramNbEnfantsMinimum,
            int paramNbEnfantsMaximum,
            int paramNbEnfantsHandicapesMinimum) {
        this.revenuNetDeclarantMinimum = paramRevenuNetDeclarantMinimum;
        this.nbEnfantsMinimum = paramNbEnfantsMinimum;
        this.nbEnfantsMaximum = paramNbEnfantsMaximum;
        this.nbEnfantsHandicapesMinimum = paramNbEnfantsHandicapesMinimum;
    }

    public void verifierDonnees(
            int revenuNetDeclarant1,
            int revenuNetDeclarant2,
            SituationFamiliale situationFamiliale,
            int nbEnfants,
            int nbEnfantsHandicapes,
            boolean estparentIsole
    ) {
        if (revenuNetDeclarant1 < this.revenuNetDeclarantMinimum || revenuNetDeclarant2 < this.revenuNetDeclarantMinimum) {
            throw new IllegalArgumentException("Le revenu net ne peut pas être négatif");
        }

        if (nbEnfants < this.nbEnfantsMinimum) {
            throw new IllegalArgumentException("Le nombre d'enfants ne peut pas être négatif");
        }

        if (nbEnfantsHandicapes < this.nbEnfantsHandicapesMinimum) {
            throw new IllegalArgumentException("Le nombre d'enfants handicapés ne peut pas être négatif");
        }

        if (situationFamiliale == null) {
            throw new IllegalArgumentException("La situation familiale ne peut pas être null");
        }

        if (nbEnfantsHandicapes > nbEnfants) {
            throw new IllegalArgumentException("Le nombre d'enfants handicapés ne peut pas être supérieur au nombre d'enfants");
        }

        if (nbEnfants > this.nbEnfantsMaximum) {
            throw new IllegalArgumentException("Le nombre d'enfants ne peut pas être supérieur à 7");
        }

        if (estparentIsole && (situationFamiliale == SituationFamiliale.MARIE || situationFamiliale == SituationFamiliale.PACSE)) {
            throw new IllegalArgumentException("Un parent isolé ne peut pas être marié ou pacsé");
        }

        boolean seul = situationFamiliale == SituationFamiliale.CELIBATAIRE || situationFamiliale == SituationFamiliale.DIVORCE || situationFamiliale == SituationFamiliale.VEUF;
        if (seul && revenuNetDeclarant2 > 0) {
            throw new IllegalArgumentException("Un célibataire, un divorcé ou un veuf ne peut pas avoir de revenu pour le déclarant 2");
        }
    }

    */

}
