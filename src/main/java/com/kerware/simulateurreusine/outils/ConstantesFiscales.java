package com.kerware.simulateurreusine.outils;

public class ConstantesFiscales {
    /*
    private int limiteTranche0 = 0 ;
    private int limiteTranche1 = 11294;
    private int limiteTranche2 = 28797;
    private int limiteTranche3 = 82341;
    private int limiteTranche4 = 177106;
    private int limiteTranche5 = Integer.MAX_VALUE;

    private double tauxTranche0 = 0.0;
    private double tauxTranche1 = 0.11;
    private double tauxTranche2 = 0.3;
    private double tauxTranche3 = 0.41;
    private double tauxTranche4 = 0.45;

    private int limiteCEHR0 = 0;
    private int limiteCEHR1 = 250000;
    private int limiteCEHR2 = 500000;
    private int limiteCEHR3 = 1000000;
    private int limiteCEHR4 = Integer.MAX_VALUE;

    private double tauxCEHRCelibataireTranche0 = 0.0;
    private double tauxCEHRCelibataireTranche1 = 0.03;
    private double tauxCEHRCelibataireTranche2 = 0.04;
    private double tauxCEHRCelibataireTranche3 = 0.04;

    private double tauxCEHRCoupleTranche0 = 0.0;
    private double tauxCEHRCoupleTranche1 = 0.0;
    private double tauxCEHRCoupleTranche2 = 0.03;
    private double tauxCEHRCoupleTranche3 = 0.04;
    */

    public static final int[] TRANCHES = { 0, 11294, 28797, 82341, 177106, Integer.MAX_VALUE };
    public static final double[] TAUX = { 0.0, 0.11, 0.3, 0.41, 0.45 };

    public static final int[] CEHR = { 0, 250000, 500000, 1000000, Integer.MAX_VALUE };
    public static final double[] TAUX_CEHR_CELIB = { 0.0, 0.03, 0.04, 0.04 };
    public static final double[] TAUX_CEHR_COUPLE = { 0.0, 0.0, 0.03, 0.04 };

    public static final int ABATTEMENT_MAX = 14171;
    public static final int ABATTEMENT_MIN = 495;
    public static final double TAUX_ABATTEMENT = 0.1;

    public static final double PLAFOND_DEMI_PART = 1759;

    public static final double SEUIL_DECOTE_SEUL = 1929;
    public static final double SEUIL_DECOTE_COUPLE = 3191;
    public static final double DECOTE_MAX_SEUL = 873;
    public static final double DECOTE_MAX_COUPLE = 1444;
    public static final double TAUX_DECOTE = 0.4525;

    public static final int REVENU_AUTORISE_DECLARANT_2_SI_SEUL = 0;
}
