package com.kerware.simulateurreusine;

import com.kerware.simulateur.SituationFamiliale;

// Version réusinée du Simulateur, adaptée pour une meilleure modularité et lisibilité
public class SimulateurReusine {


    // Les limites des tranches de revenus imposables
    private int limiteTranche0 = 0 ;
    private int limiteTranche1 = 11294;
    private int limiteTranche2 = 28797;
    private int limiteTranche3 = 82341;
    private int limiteTranche4 = 177106;
    private int limiteTranche5 = Integer.MAX_VALUE;

    private int[] limites = new int[6];

    // Les taux d'imposition par tranche
    private double tauxTranche0 = 0.0;
    private double tauxTranche1 = 0.11;
    private double tauxTranche2 = 0.3;
    private double tauxTranche3 = 0.41;
    private double tauxTranche4 = 0.45;

    private double[] taux = new double[5];

    // Les limites des tranches pour la contribution exceptionnelle sur les hauts revenus
    private int limiteCEHR0 = 0;
    private int limiteCEHR1 = 250000;
    private int limiteCEHR2 = 500000;
    private int limiteCEHR3 = 1000000;
    private int limiteCEHR4 = Integer.MAX_VALUE;

    private int[] limitesCEHR = new int[5];

    // Les taux de la contribution exceptionnelle sur les hauts revenus (CEHR) pour les celibataires
    private double tauxCEHRCelibataireTranche0 = 0.0;
    private double tauxCEHRCelibataireTranche1 = 0.03;
    private double tauxCEHRCelibataireTranche2 = 0.04;
    private double tauxCEHRCelibataireTranche3 = 0.04;

    private double[] tauxCEHRCelibataire = new double[4];

    // Les taux de la contribution exceptionnelle sur les hauts revenus pour les couples
    private double tauxCEHRCoupleTranche0 = 0.0;
    private double tauxCEHRCoupleTranche1 = 0.0;
    private double tauxCEHRCoupleTranche2 = 0.03;
    private double tauxCEHRCoupleTranche3 = 0.04;

    private double[] tauxCEHRCouple = new double[4];

    // Abattement
    private  int limiteAbattementMaxParDeclarant = 14171;
    private  int limiteAbattementMinParDeclarant = 495;
    private double tauxAbattement = 0.1;

    // Plafond de baisse maximal par demi part
    private double plafDemiPart = 1759;

    private double seuilDecoteDeclarantSeul = 1929;
    private double seuilDecoteDeclarantCouple    = 3191;

    private double decoteMaxDeclarantSeul = 873;
    private double decoteMaxDeclarantCouple = 1444;
    private double tauxDecote = 0.4525;

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

    public int calculImpot(int revNetDecl1, int revNetDecl2, SituationFamiliale sitFam, int nbEnfants, int nbEnfantsHandicapes, boolean parentIsol) {

        // Préconditions
        if ( revNetDecl1  < 0 || revNetDecl2 < 0 ) {
            throw new IllegalArgumentException("Le revenu net ne peut pas être négatif");
        }

        if ( nbEnfants < 0 ) {
            throw new IllegalArgumentException("Le nombre d'enfants ne peut pas être négatif");
        }

        if ( nbEnfantsHandicapes < 0 ) {
            throw new IllegalArgumentException("Le nombre d'enfants handicapés ne peut pas être négatif");
        }

        if ( sitFam == null ) {
            throw new IllegalArgumentException("La situation familiale ne peut pas être null");
        }

        if ( nbEnfantsHandicapes > nbEnfants ) {
            throw new IllegalArgumentException("Le nombre d'enfants handicapés ne peut pas être supérieur au nombre d'enfants");
        }

        if ( nbEnfants > 7 ) {
            throw new IllegalArgumentException("Le nombre d'enfants ne peut pas être supérieur à 7");
        }

        if ( parentIsol && ( sitFam == SituationFamiliale.MARIE || sitFam == SituationFamiliale.PACSE ) ) {
            throw new IllegalArgumentException("Un parent isolé ne peut pas être marié ou pacsé");
        }

        boolean seul = sitFam == SituationFamiliale.CELIBATAIRE || sitFam == SituationFamiliale.DIVORCE || sitFam == SituationFamiliale.VEUF;
        if (  seul && revNetDecl2 > 0 ) {
            throw new IllegalArgumentException("Un célibataire, un divorcé ou un veuf ne peut pas avoir de revenu pour le déclarant 2");
        }

        // Initialisation des variables

        revenuNetDeclarant1 = revNetDecl1;
        revenuNetDeclarant2 = revNetDecl2;

        this.nbEnfants = nbEnfants;
        this.nbEnfantsHandicapes = nbEnfantsHandicapes;
        estParentIsole = parentIsol;

        limites[0] = limiteTranche0;
        limites[1] = limiteTranche1;
        limites[2] = limiteTranche2;
        limites[3] = limiteTranche3;
        limites[4] = limiteTranche4;
        limites[5] = limiteTranche5;

        taux[0] = tauxTranche0;
        taux[1] = tauxTranche1;
        taux[2] = tauxTranche2;
        taux[3] = tauxTranche3;
        taux[4] = tauxTranche4;

        limitesCEHR[0] = limiteCEHR0;
        limitesCEHR[1] = limiteCEHR1;
        limitesCEHR[2] = limiteCEHR2;
        limitesCEHR[3] = limiteCEHR3;
        limitesCEHR[4] = limiteCEHR4;

        tauxCEHRCelibataire[0] = tauxCEHRCelibataireTranche0;
        tauxCEHRCelibataire[1] = tauxCEHRCelibataireTranche1;
        tauxCEHRCelibataire[2] = tauxCEHRCelibataireTranche2;
        tauxCEHRCelibataire[3] = tauxCEHRCelibataireTranche3;

        tauxCEHRCouple[0] = tauxCEHRCoupleTranche0;
        tauxCEHRCouple[1] = tauxCEHRCoupleTranche1;
        tauxCEHRCouple[2] = tauxCEHRCoupleTranche2;
        tauxCEHRCouple[3] = tauxCEHRCoupleTranche3;

        System.out.println("--------------------------------------------------");
        System.out.println( "Revenu net declarant1 : " + revenuNetDeclarant1);
        System.out.println( "Revenu net declarant2 : " + revenuNetDeclarant2);
        System.out.println( "Situation familiale : " + sitFam.name() );

        // Abattement
        // EXIGENCE : EXG_IMPOT_02
        long abt1 = Math.round(revenuNetDeclarant1 * tauxAbattement);
        long abt2 = Math.round(revenuNetDeclarant2 * tauxAbattement);

        if (abt1 > limiteAbattementMaxParDeclarant) {
            abt1 = limiteAbattementMaxParDeclarant;
        }
        if ( sitFam == SituationFamiliale.MARIE || sitFam == SituationFamiliale.PACSE ) {
            if (abt2 > limiteAbattementMaxParDeclarant) {
                abt2 = limiteAbattementMaxParDeclarant;
            }
        }

        if (abt1 < limiteAbattementMinParDeclarant) {
            abt1 = limiteAbattementMinParDeclarant;
        }

        if ( sitFam == SituationFamiliale.MARIE || sitFam == SituationFamiliale.PACSE ) {
            if (abt2 < limiteAbattementMinParDeclarant) {
                abt2 = limiteAbattementMinParDeclarant;
            }
        }

        abattement = abt1 + abt2;
        System.out.println( "Abattement : " + abattement);

        revenuFiscalReference = revenuNetDeclarant1 + revNetDecl2 - abattement;
        if ( revenuFiscalReference < 0 ) {
            revenuFiscalReference = 0;
        }

        System.out.println( "Revenu fiscal de référence : " + revenuFiscalReference);


        // parts déclarants
        // EXIG  : EXG_IMPOT_03
        switch ( sitFam ) {
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
        if ( sitFam == SituationFamiliale.VEUF && this.nbEnfants > 0 ) {
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
                    montantContributionExceptionnelle += ( revenuFiscalReference - limitesCEHR[i] ) * tauxCEHRCelibataire[i];
                } else {
                    montantContributionExceptionnelle += ( revenuFiscalReference - limitesCEHR[i] ) * tauxCEHRCouple[i];
                }
                break;
            } else {
                if ( nbPartsDeclarants == 1 ) {
                    montantContributionExceptionnelle += ( limitesCEHR[i+1] - limitesCEHR[i] ) * tauxCEHRCelibataire[i];
                } else {
                    montantContributionExceptionnelle += ( limitesCEHR[i+1] - limitesCEHR[i] ) * tauxCEHRCouple[i];
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

        double plafond = (ecartPts / 0.5) * plafDemiPart;

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
            if ( montantImpotFoyer < seuilDecoteDeclarantSeul ) {
                decote = decoteMaxDeclarantSeul - ( montantImpotFoyer * tauxDecote );
            }
        }
        if (  nbPartsDeclarants == 2 ) {
            if ( montantImpotFoyer < seuilDecoteDeclarantCouple ) {
                decote =  decoteMaxDeclarantCouple - ( montantImpotFoyer * tauxDecote  );
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
