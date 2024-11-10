# Software-eng
Ce programme Java est conçu pour résoudre des grilles de Sudoku en utilisant un ensemble de règles de déduction personnalisées et des motifs de conception. Il lit la grille initiale à partir d'un fichier texte (test.txt), applique trois règles de déduction successives et demande à l'utilisateur de remplir les cases vides restantes si nécessaire.

Fichiers et Structure
Le projet contient les fichiers suivants :

Grille.java : Gère la grille de Sudoku en stockant, récupérant et affichant les valeurs des cellules.

DeductionRules.java : Classe de base abstraite pour les règles de déduction, contenant une méthode applyRules() à surcharger dans les règles spécifiques.

DR1.java : Première règle de déduction, qui remplit une cellule si elle est la seule vide dans son sous-carré 3x3 et qu'une seule valeur possible peut être assignée.

DR2.java : Deuxième règle de déduction, qui vérifie que chaque ligne et colonne ne contiennent pas de valeurs dupliquées.

DR3.java : Troisième règle de déduction, qui applique la stratégie des "Paires Nues" pour éliminer les valeurs possibles dans les cellules d'une ligne, colonne, ou sous-carré 3x3.

Parser.java : Lit la grille de Sudoku initiale à partir de test.txt rentrée depuis l'entrée standard.

Main.java : Point d'entrée principal du programme, initialise la grille, applique les règles et gère la saisie utilisateur si des cases vides restent après les déductions.

Observable et Observer: notre design pattern observer

Factory : notre factory

Installation et Préparation
Fichier d’entrée : Assurez-vous qu’un fichier test.txt est présent dans le répertoire racine du projet. Ce fichier doit contenir 81 nombres séparés par des virgules (les cellules vides sont représentées par des 0, que le programme remplacera par -1 pour faciliter le traitement).

Compilez les fichiers Java avec la commande javac *.java.
Exécutez le programme avec java main nomfichierATester.txt (ex java main facile.txt).

Si la grille ne se resout pas en 10 secondes alors on passe a la prise en charge manuel
