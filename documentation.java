// Simulateur
/**
 * ...
 * 
 * @param revNetDecl1
 * @param revNetDecl2
 * @param sitFam
 * @param nbEnfants
 * @param nbEnfantsHandicapes
 * @param parentIsol
 *
 * @return 
 */
public int calculImpot(int revNetDecl1, int revNetDecl2, SituationFamiliale sitFam, int nbEnfants, int nbEnfantsHandicapes, int parentIsol)

/**
 * ...
 * 
 * @param revNetDecl1
 * @param revNetDecl2
 * @param nbEnfant
 * @param nbEnfantHandicapes
 * @param parantIsol 
 */
public void verifierPreconditions(int revNetDecl1, int revNetDecl2, SituationFamilial sitFam, int nbEnfants, int nbEnfantsHandicapes, boolean parentIsol)


//Decote
/**
 * ...
 * 
 * @return
 */
public double getDecote()

/**
 * ...
 * 
 * @param nbPtsDecl
 * @param mImp
 * @param seuilDecoteDeclarantSeul
 * @param decoteMaxDeclarantSeul
 * @param tauxDecote
 * @param seuilDecoteDeclarantCouple
 * @param decoteMaxDeclarantCouple
 * 
 * @return 
 */
public double calculDecote(double nbPtsDecl, double mImp, double seuilDecoteDeclarantSeul, double decoteMaxDeclarantSeul, double tauxDecote, double seuilDecoteDeclarantCouple, double decoteMaxDeclarantCouple)

//Revenu
/**
 * ...
 * 
 * @return 
 */
public double getRevenuReference()

/**
 * ...
 * 
 * @return
 */
public int getRevenuNetDeclarant1()

/**
 * ... 
 * 
 * @return 
 */
public int getRevenuNetDeclarant2()

/**
 * ... 
 * 
 * @return 
 */
public double revenuFiscal(int rNetDecl1:, int revNetDecl2, double abt)

//NombreDePart
/**
 * ...
 * 
 * @return
 */
public double getNbPart(): 

/**
 * ...
 * 
 * @param nbPtsDecl
 * @param nbEnf
 * @param nbEnfH
 * @param parIso
 * @param sitFam
 * 
 * @return
 */

public double nombreDePart(double nbPtsDecl, int nbEnf, int nbEnfH, boolean parIso, SituationFamiliale sitFam)

/**
 * ...
 * 
 * @param nbEnfH
 * 
 * @return 
 */
public double nombreDePartEnfantHandicape(int nbEnfH)

/**
 * ...
 * 
 * @param sitFam
 * @param nbEnf
 * 
 * @return 
 */
public double nombreDePartVeufAvecEnfant(SituationFamiliale sitFam, int nbEnf)

/**
 * ...
 * 
 * @param parIso
 * 
 * @return 
 */
public double nombreDePartParentIsole(boolean parIso)

/**
 * ...
 * 
 * @param nbPtsDecl
 * @param nbEnf
 * 
 * @return 
 */
public double nombreDePartEnfant(double nbPtsDecl, int nbEnf)

//Impot
/**
 * ...
 * 
 * @return
 */
public double getImpotAvantDecote()

/**
 * ...
 * 
 * @return
 */
public double getImpotNet()

/**
 * ...
 * 
 * @param decode
 * @param nImp
 * @param contribExceptionnelle
 */
public double impotRevenuNetFinal(double decote, double nImp, double contribExceptionnelle)

/**
 * ...
 * 
 * @param rFRef 
 * @param nbPart 
 * @param limites
 * @param taux
 * 
 * @return 
 */
public double impotFoyerFiscal(double rFRef, double nbPts, int[6] limites, double taux)

/**
 * ...
 * 
 * @param baisseImpot
 * @param plafond
 * @param mImpDecl
 * 
 * @return
 */
public double impotBrutApresPlafonnementAvantDecote(double baisseImpot, double plafond, double mImpDecl)

/**
 * ...
 * 
 * @param rRef
 * @param nbPtsDecl
 * @param limites
 */
public double impotDeclarant(double rFRef, double nbPtsDecl, int[6] limites)

//Abattement
/**
 * ...
 * 
 * @return 
 */
public double getAbattement()

/**
 * ...
 * 
 * @param situationFamiliale 
 * @param rNetDecl1
 * @param rNetDecl2 
 * @param tAbt 
 */
public double calculAbattement(SituationFamiliale situationFamiliale, double rNetDecl1, double rNetDecl2, double tAbt)