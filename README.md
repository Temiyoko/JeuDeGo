# Projet go - R3.04
Ce projet consiste à développer et tester un programme permettant de jouer une partie de go.

Equipe, groupe 203 : 
- Julia LEVEQUE
- Paulo MARTINS
- Marion PARMENTIER

Ce qui marche :
- Le programme prend en paramètre deux chiffres, le nombre d'IA (entre 0 et 2) et la taille initiale du plateau (entre 2 et 19, paramètre optionnel)
- La commande quit
- La commande boardsize
- La commande showboard
- La commande clear_board
- Détection de commande inconnue
- La commande play (joueur console et IA jouant automatiquement)
  - Capture d'une pièce ou d'un groupe de pièces
  - Suicide interdit
  - Le jeu se termine en cas de deux "pass" consécutifs d'un joueur 
