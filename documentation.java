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
 * @return Amount of income tax calculated for the tax household
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
 * @return amount of discount applicable to the gross tax of the tax household
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
 * @return amount of discount applicable to the gross tax of the tax household.
 */
public double calculDecote(double nbPtsDecl, double mImp, double seuilDecoteDeclarantSeul, double decoteMaxDeclarantSeul, double tauxDecote, double seuilDecoteDeclarantCouple, double decoteMaxDeclarantCouple)

//Revenu
/**
 * ...
 * 
 * @return household reference tax income
 */
public double getRevenuReference()

/**
 * ...
 * 
 * @return net income declared by the first tax filer in the tax household
 */
public int getRevenuNetDeclarant1()

/**
 * ... 
 * 
 * @return net income declared by the second tax filer in the tax household
 */
public int getRevenuNetDeclarant2()

/**
 * ... 
 * 
 * @param rNetDecl1 net income declared by the first person in the tax household
 * @param rNetDecl2 net income declared by the second person in the tax household
 * @param abt deduction from total household income
 * 
 * @return household reference tax income
 */
public double revenuFiscal(int rNetDecl1, int rNetDecl2, double abt)
//fait

//NombreDePart
/**
 * ...
 * 
 * @return number of household tax units
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
 * @return total number of household tax units
 */
public double nombreDePart(double nbPtsDecl, int nbEnf, int nbEnfH, boolean parIso, SituationFamiliale sitFam)

/**
 * ...
 * 
 * @param nbEnfH among children, those with disabilities
 * 
 * @return number of additional tax units attributed to disabled children in the household
 */
public double nombreDePartEnfantHandicape(int nbEnfH)

/**
 * ...
 * 
 * @param sitFam marital status (single, civil partnership, married, divorced or widowed)
 * @param nbEnf number of children in the family
 * 
 * @return 
number of additional tax units allocated to a widowed taxpayer with dependent children
 */
public double nombreDePartVeufAvecEnfant(SituationFamiliale sitFam, int nbEnf)

/**
 * ...
 * 
 * @param parIso whether or not the parent is single
 * 
 * @return number of additional tax units allocated to a single parent
 */
public double nombreDePartParentIsole(boolean parIso)

/**
 * ...
 * 
 * @param nbPtsDecl number of shares declared
 * @param nbEnf number of children in the family
 * 
 * @return number of additional tax units allocated
 */
public double nombreDePartEnfant(double nbPtsDecl, int nbEnf)

//Impot
/**
 * ...
 * 
 * @return amount of income tax before application of the discount
 */
public double getImpotAvantDecote()

/**
 * ...
 * 
 * @return net tax payable
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
 * @return amount of tax to be paid by the tax household
 */
public double impotFoyerFiscal(double rFRef, double nbPts, int[6] limites, double taux)

/**
 * ...
 * 
 * @param baisseImpot amount of applicable tax reduction
 * @param plafond ceiling above which tax cannot be further reduced
 * @param mImpDecl gross tax declared for the entire tax household
 * 
 * @return gross tax after capping but before deduction
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
 * @return amount of allowance
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
//fait