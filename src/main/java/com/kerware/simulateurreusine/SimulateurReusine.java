package com.kerware.simulateurreusine;

import com.kerware.simulateur.SituationFamiliale;
import com.kerware.simulateurreusine.calcul.Abattement;
import com.kerware.simulateurreusine.calcul.Revenu;
import com.kerware.simulateurreusine.calcul.VerificateurDonneesFiscales;
import com.kerware.simulateurreusine.outils.ConstantesFiscales;


// Version réusinée du Simulateur, adaptée pour une meilleure modularité et lisibilité
public class SimulateurReusine {

    // revenu net
    private int revenuNetDeclarant1 = 0;
    private int revenuNetDeclarant2 = 0;
    // nb enfants
    private int nbEnfants = 0;
    // nb enfants handicapés
    private int nbEnfantsHandicapes = 0;

    // revenu fiscal de référence
    private double revenuFiscalReference = 0;

    // revenu imposable
    private double revenuImposable = 0;

    // abattement
    private double abattement = 0;

    // nombre de parts des  déclarants
    private double nbPartsDeclarants = 0;
    // nombre de parts du foyer fiscal
    private double nbPartsFoyer = 0;

    // decote
    private double decote = 0;
    // impôt des déclarants
    private double montantImpotDeclarant = 0;
    // impôt du foyer fiscal
    private double montantImpotFoyer = 0;
    private double montantImpotAvantDecote = 0;
    // parent isolé
    private boolean estParentIsole = false;
    // Contribution exceptionnelle sur les hauts revenus
    private double montantContributionExceptionnelle = 0;

    // Getters pour adapter le code legacy pour les tests unitaires

    public double getRevenuReference() {
        return revenuFiscalReference;
    }

    public double getDecote() {
        return decote;
    }


    public double getAbattement() {
        return abattement;
    }

    public double getNbParts() {
        return nbPartsFoyer;
    }

    public double getImpotAvantDecote() {
        return montantImpotAvantDecote;
    }

    public double getImpotNet() {
        return montantImpotFoyer;
    }

    public int getRevenuNetDeclatant1() {
        return revenuNetDeclarant1;
    }

    public int getRevenuNetDeclatant2() {
        return revenuNetDeclarant2;
    }

    public double getMontantContributionExceptionnelle() {
        return montantContributionExceptionnelle;
    }


    // Fonction de calcul de l'impôt sur le revenu net en France en 2024 sur les revenu 2023

    /**
     * ...
     *
     * @param paramRevenuNetDeclarant1 net income declared by the first person
     * @param paramRevenuNetDeclarant2 net income declared by the second person
     * @param paramSituationFamilial marital status (single, civil partnership, married, divorced or widowed)
     * @param paramNbEnfants number of children in the family
     * @param paramNbEnfantsHandicapes among children, those with disabilities
     * @param paramEstParentIsol whether or not the parent is single
     *
     * @return Amount of income tax calculated for the tax household
     */
    public int calculImpot(
            int paramRevenuNetDeclarant1,
            int paramRevenuNetDeclarant2,
            SituationFamiliale paramSituationFamilial,
            int paramNbEnfants,
            int paramNbEnfantsHandicapes,
            boolean paramEstParentIsol
    ) {

//        VerificateurDonneesFiscales verificateurDonnees = new VerificateurDonneesFiscales(0, 0, 0, 0);
//        verificateurDonnees.verifierDonnees(revNetDecl1, revNetDecl2, paramSituationFamilial, nbEnfants, nbEnfantsHandicapes, parentIsol);

        VerificateurDonneesFiscales.verifierDonnees(
                paramRevenuNetDeclarant1,
                paramRevenuNetDeclarant2,
                paramSituationFamilial,
                paramNbEnfants,
                paramNbEnfantsHandicapes,
                paramEstParentIsol
        );

        // Initialisation des variables
        revenuNetDeclarant1 = paramRevenuNetDeclarant1;
        revenuNetDeclarant2 = paramRevenuNetDeclarant2;

        this.nbEnfants = paramNbEnfants;
        this.nbEnfantsHandicapes = paramNbEnfantsHandicapes;
        estParentIsole = paramEstParentIsol;

        int[] limites = ConstantesFiscales.TRANCHES;
        double[] taux = ConstantesFiscales.TAUX;

        int[] limitesCEHR = ConstantesFiscales.CEHR;
        double[] tauxCelib = ConstantesFiscales.TAUX_CEHR_CELIB;
        double[] tauxCouple = ConstantesFiscales.TAUX_CEHR_COUPLE;

        double tauxAbattement = ConstantesFiscales.TAUX_ABATTEMENT;
        int abattementMax = ConstantesFiscales.ABATTEMENT_MAX;
        int abattementMin = ConstantesFiscales.ABATTEMENT_MIN;

        double plafondDemiPart = ConstantesFiscales.PLAFOND_DEMI_PART;

        double seuilSeul = ConstantesFiscales.SEUIL_DECOTE_SEUL;
        double seuilCouple = ConstantesFiscales.SEUIL_DECOTE_COUPLE;
        double decoteMaxSeul = ConstantesFiscales.DECOTE_MAX_SEUL;
        double decoteMaxCouple = ConstantesFiscales.DECOTE_MAX_COUPLE;
        double tauxDecote = ConstantesFiscales.TAUX_DECOTE;

        System.out.println("--------------------------------------------------");
        System.out.println( "Revenu net declarant1 : " + revenuNetDeclarant1);
        System.out.println( "Revenu net declarant2 : " + revenuNetDeclarant2);
        System.out.println( "Situation familiale : " + paramSituationFamilial.name() );

        // Abattement
        // EXIGENCE : EXG_IMPOT_02
        this.abattement = Abattement.calculerAbattement(paramSituationFamilial, revenuNetDeclarant1, revenuNetDeclarant2);
        System.out.println( "Abattement : " + abattement);

        this.revenuFiscalReference = Revenu.calculerRevenuFiscal(revenuNetDeclarant1, revenuNetDeclarant2, this.abattement);

        System.out.println( "Revenu fiscal de référence : " + revenuFiscalReference);


        // parts déclarants
        // EXIG  : EXG_IMPOT_03
        switch ( paramSituationFamilial ) {
            case CELIBATAIRE:
                nbPartsDeclarants = 1;
                break;
            case MARIE:
                nbPartsDeclarants = 2;
                break;
            case DIVORCE:
                nbPartsDeclarants = 1;
                break;
            case VEUF:
                nbPartsDeclarants = 1;
                break;
            case PACSE:
                nbPartsDeclarants = 2;
                break;
        }

        System.out.println( "Nombre d'enfants  : " + this.nbEnfants);
        System.out.println( "Nombre d'enfants handicapés : " + this.nbEnfantsHandicapes);

        // parts enfants à charge
        if ( this.nbEnfants <= 2 ) {
            nbPartsFoyer = nbPartsDeclarants + this.nbEnfants * 0.5;
        } else if ( this.nbEnfants > 2 ) {
            nbPartsFoyer = nbPartsDeclarants +  1.0 + ( this.nbEnfants - 2 );
        }

        // parent isolé

        System.out.println( "Parent isolé : " + estParentIsole);

        if (estParentIsole) {
            if ( this.nbEnfants > 0 ){
                nbPartsFoyer = nbPartsFoyer + 0.5;
            }
        }

        // Veuf avec enfant
        if ( paramSituationFamilial == SituationFamiliale.VEUF && this.nbEnfants > 0 ) {
            nbPartsFoyer = nbPartsFoyer + 1;
        }

        // enfant handicapé
        nbPartsFoyer = nbPartsFoyer + this.nbEnfantsHandicapes * 0.5;

        System.out.println( "Nombre de parts : " + nbPartsFoyer);

        // EXIGENCE : EXG_IMPOT_07:
        // Contribution exceptionnelle sur les hauts revenus
        montantContributionExceptionnelle = 0;
        int i = 0;
        do {
            if ( revenuFiscalReference >= limitesCEHR[i] && revenuFiscalReference < limitesCEHR[i+1] ) {
                if ( nbPartsDeclarants == 1 ) {
                    montantContributionExceptionnelle += ( revenuFiscalReference - limitesCEHR[i] ) * ConstantesFiscales.TAUX_CEHR_CELIB[i];
                } else {
                    montantContributionExceptionnelle += ( revenuFiscalReference - limitesCEHR[i] ) * ConstantesFiscales.TAUX_CEHR_COUPLE[i];
                }
                break;
            } else {
                if ( nbPartsDeclarants == 1 ) {
                    montantContributionExceptionnelle += ( limitesCEHR[i+1] - limitesCEHR[i] ) * ConstantesFiscales.TAUX_CEHR_CELIB[i];
                } else {
                    montantContributionExceptionnelle += ( limitesCEHR[i+1] - limitesCEHR[i] ) * ConstantesFiscales.TAUX_CEHR_COUPLE[i];
                }
            }
            i++;
        } while( i < 5);

        montantContributionExceptionnelle = Math.round(montantContributionExceptionnelle);
        System.out.println( "Contribution exceptionnelle sur les hauts revenus : " + montantContributionExceptionnelle);

        // Calcul impôt des declarants
        // EXIGENCE : EXG_IMPOT_04
        revenuImposable = revenuFiscalReference / nbPartsDeclarants;

        montantImpotDeclarant = 0;

        i = 0;
        do {
            if ( revenuImposable >= limites[i] && revenuImposable < limites[i+1] ) {
                montantImpotDeclarant += ( revenuImposable - limites[i] ) * taux[i];
                break;
            } else {
                montantImpotDeclarant += ( limites[i+1] - limites[i] ) * taux[i];
            }
            i++;
        } while( i < 5);

        montantImpotDeclarant = montantImpotDeclarant * nbPartsDeclarants;
        montantImpotDeclarant = Math.round(montantImpotDeclarant);

        System.out.println( "Impôt brut des déclarants : " + montantImpotDeclarant);

        // Calcul impôt foyer fiscal complet
        // EXIGENCE : EXG_IMPOT_04
        revenuImposable =  revenuFiscalReference / nbPartsFoyer;
        montantImpotFoyer = 0;
        i = 0;

        do {
            if ( revenuImposable >= limites[i] && revenuImposable < limites[i+1] ) {
                montantImpotFoyer += ( revenuImposable - limites[i] ) * taux[i];
                break;
            } else {
                montantImpotFoyer += ( limites[i+1] - limites[i] ) * taux[i];
            }
            i++;
        } while( i < 5);

        montantImpotFoyer = montantImpotFoyer * nbPartsFoyer;
        montantImpotFoyer = Math.round(montantImpotFoyer);

        System.out.println( "Impôt brut du foyer fiscal complet : " + montantImpotFoyer);

        // Vérification de la baisse d'impôt autorisée
        // EXIGENCE : EXG_IMPOT_05
        // baisse impot

        double baisseImpot = montantImpotDeclarant - montantImpotFoyer;

        System.out.println( "Baisse d'impôt : " + baisseImpot );

        // dépassement plafond
        double ecartPts = nbPartsFoyer - nbPartsDeclarants;

        double plafond = (ecartPts / 0.5) * ConstantesFiscales.PLAFOND_DEMI_PART;

        System.out.println( "Plafond de baisse autorisée " + plafond );

        if ( baisseImpot >= plafond ) {
            montantImpotFoyer = montantImpotDeclarant - plafond;
        }

        System.out.println( "Impôt brut après plafonnement avant decote : " + montantImpotFoyer);
        montantImpotAvantDecote = montantImpotFoyer;

        // Calcul de la decote
        // EXIGENCE : EXG_IMPOT_06

        decote = 0;
        // decote
        if ( nbPartsDeclarants == 1 ) {
            if ( montantImpotFoyer < ConstantesFiscales.SEUIL_DECOTE_SEUL ) {
                decote = ConstantesFiscales.DECOTE_MAX_SEUL - ( montantImpotFoyer * tauxDecote );
            }
        }
        if (  nbPartsDeclarants == 2 ) {
            if ( montantImpotFoyer < ConstantesFiscales.SEUIL_DECOTE_COUPLE ) {
                decote =  ConstantesFiscales.DECOTE_MAX_COUPLE - ( montantImpotFoyer * tauxDecote  );
            }
        }
        decote = Math.round( decote );

        if ( montantImpotFoyer <= decote ) {
            decote = montantImpotFoyer;
        }

        System.out.println( "Decote : " + decote );

        montantImpotFoyer = montantImpotFoyer - decote;

        montantImpotFoyer += montantContributionExceptionnelle;

        montantImpotFoyer = Math.round(montantImpotFoyer);

        System.out.println( "Impôt sur le revenu net final : " + montantImpotFoyer);
        return  (int) montantImpotFoyer;
    }

}
