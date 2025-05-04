# 💼 Réusinage du simulateur d’impôt

Ce projet a pour objectif de **réusiner un code existant** servant à simuler le calcul de l’impôt sur le revenu.  
Le code d’origine était lourd : une **seule méthode centralisait toute la logique métier**.

L'enjeu de ce travail est d'assurer une **modularisation claire**, une **lisibilité optimale**, tout en garantissant que les **tests initiaux restent valides** via un **adaptateur** vers notre nouvelle version.

---

## ✅ Objectifs

- Découper le code en **plusieurs classes** et **sous-méthodes cohérentes**
- Améliorer la **lisibilité**, la **maintenabilité** et la **testabilité**
- **Respecter toutes les exigences fonctionnelles** du simulateur d’origine
- **Assurer une couverture de tests supérieure à 90% (JaCoCo)**
- **Corriger toutes les erreurs Checkstyle** selon le fichier de règles de l’IUT
- Ajouter des **commentaires traçant les exigences** du simulateur
- Produire un **document lisible expliquant notre démarche**

---

## 📂 Contenu du dépôt

- `simulateur/` : code original fourni  
- `simulateurreusine/` : nouvelle version réusinée (structurée en packages)  
- `AdaptateurSimulateur.java` : adaptateur entre le code initial et notre version  
- `TestsSimulateur.java` : tests d'origine, complétés pour atteindre les 90%  
- `README.md` (ce fichier)  
- `diagramme.puml` : diagramme PlantUML du projet  
- Fichier `checkstyle.xml` (règles de l’IUT) utilisé sans aucune erreur

---

## 👥 Auteurs

- **MARIE Léo-Paul**  
- **BEUVE Léopold**

---

## 🗓️ Date de rendu

**Lundi 5 mai à minuit**

---

## 🔧 Technologies utilisées

- Java (JDK 17)  
- Maven  
- JUnit 5  
- JaCoCo (couverture des tests)  
- Checkstyle (analyse de code statique)  
- PlantUML (diagramme de classes)

---

## 🧪 Validation

- ✅ Tous les tests d'origine **passent avec succès**
- ✅ Le taux de couverture **dépasse les 90%**
- ✅ Aucune **erreur ni warning** détecté par **Checkstyle**
- ✅ L'adaptateur permet une **transition fluide** vers la nouvelle architecture
- ℹ️ **Note importante** : la classe `Simulateur.java` d’origine a été **volontairement conservée dans le dépôt** pour garantir la traçabilité des changements apportés.  
- Bien qu’elle **ne soit plus utilisée dans notre logique métier ni dans l'exécution des tests**, elle est **toujours analysée** :
 - par **JaCoCo**, dans les rapports de couverture du code (ce qui fait chuter artificiellement le pourcentage global),
 - par **Checkstyle**, dans les rapports de vérification du style.
- ✅ **Tous les tests unitaires sont désormais basés sur la classe `SimulateurReusine` via l'`AdaptateurSimulateur`**, garantissant une **couverture réelle > 90 %** et **zéro warning Checkstyle** sur notre implémentation.


---

## 🙏 Remerciements

Un grand merci à **M. CHARLES Olivier** pour son encadrement et ses conseils tout au long du projet.

---

