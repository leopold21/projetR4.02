// Simulateur
/**
 * ...
 * 
 * @param revNetDecl1 net income declared by the first person
 * @param revNetDecl2 net income declared by the second person
 * @param sitFam marital status (single, civil partnership, married, divorced or widowed)
 * @param nbEnfants number of children in the family
 * @param nbEnfantsHandicapes among children, those with disabilities
 * @param parentIsol whether or not the parent is single
 *
 * @return 
 */
public int calculImpot(int revNetDecl1, int revNetDecl2, SituationFamiliale sitFam, int nbEnfants, int nbEnfantsHandicapes, boolean parentIsol)

/**
 * ...
 * 
 * @param revNetDecl1 net income declared by the first person
 * @param revNetDecl2 net income declared by the second person
 * @param nbEnfant number of children in the family
 * @param nbEnfantHandicapes among children, those with disabilities
 * @param parantIsol whether or not the parent is single
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
 * @param nbPtsDecl number of shares declared
 * @param mImp gross tax for entire tax household
 * @param seuilDecoteDeclarantSeul discount threshold for a single person
 * @param decoteMaxDeclarantSeul maximum discount declared for a single person
 * @param tauxDecote percentage reduction applied to gross tax to calculate the discount
 * @param seuilDecoteDeclarantCouple gross tax amount above which a couple can benefit from a discount
 * @param decoteMaxDeclarantCouple maximum amount of discount that a couple can obtain
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
 * @param rNetDecl1 net income declared by the first person in the tax household
 * @param rNetDecl2 net income declared by the second person in the tax household
 * @param abt deduction from total household income
 * 
 * @return 
 */
public double revenuFiscal(int rNetDecl1, int rNetDecl2, double abt)

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
 * @param nbPtsDecl number of shares declared
 * @param nbEnf number of children in the family
 * @param nbEnfH among children, those with disabilities
 * @param parIso whether or not the parent is single
 * @param sitFam marital status (single, civil partnership, married, divorced or widowed)
 * 
 * @return
 */
public double nombreDePart(double nbPtsDecl, int nbEnf, int nbEnfH, boolean parIso, SituationFamiliale sitFam)

/**
 * ...
 * 
 * @param nbEnfH among children, those with disabilities
 * 
 * @return 
 */
public double nombreDePartEnfantHandicape(int nbEnfH)

/**
 * ...
 * 
 * @param sitFam marital status (single, civil partnership, married, divorced or widowed)
 * @param nbEnf number of children in the family
 * 
 * @return 
 */
public double nombreDePartVeufAvecEnfant(SituationFamiliale sitFam, int nbEnf)

/**
 * ...
 * 
 * @param parIso whether or not the parent is single
 * 
 * @return 
 */
public double nombreDePartParentIsole(boolean parIso)

/**
 * ...
 * 
 * @param nbPtsDecl number of shares declared
 * @param nbEnf number of children in the family
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
 * @param decote amount of discount applied to gross tax
 * @param mImp gross tax for entire tax household
 * @param contribExceptionnelle exceptional tax on high incomes
 */
public double impotRevenuNetFinal(double decote, double nImp, double contribExceptionnelle)

/**
 * ...
 * 
 * @param rFRef taxable income
 * @param nbPts number of shares
 * @param limites taxable income bracket limits
 * @param taux tax rates by bracket
 * 
 * @return 
 */
public double impotFoyerFiscal(double rFRef, double nbPts, int[6] limites, double taux)

/**
 * ...
 * 
 * @param baisseImpot amount of applicable tax reduction
 * @param plafond ceiling above which tax cannot be further reduced
 * @param mImpDecl gross tax declared for the entire tax household
 * 
 * @return
 */
public double impotBrutApresPlafonnementAvantDecote(double baisseImpot, double plafond, double mImpDecl)

/**
 * ...
 * 
 * @param rFRef taxable income
 * @param nbPtsDecl number of shares declared
 * @param limites taxable income bracket limits
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
 * @param situationFamiliale marital status (single, civil partnership, married, divorced or widowed)
 * @param rNetDecl1 net income declared by the first person in the tax household
 * @param rNetDecl2 net income declared by the second person in the tax household
 * @param tAbt deduction rate applied to taxable income
 */
public double calculAbattement(SituationFamiliale situationFamiliale, double rNetDecl1, double rNetDecl2, double tAbt)