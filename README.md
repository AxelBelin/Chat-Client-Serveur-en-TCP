# Java - Chat-Client-Serveur-en-TCP

## DOCUMENTATION
Approche Simple, Starter App, FullDuplex et Robot9k
Tous les programmes sont en Anglais afin de faciliter leur utilisation par
tous.
Les codes source sont tous commentés de façon détaillée.
### Partie I. Approche Simple
Il y a un soucis d’implémentation avec les deux classes (SimpleServer.java et
SimpleClient.java) concernant la déconnexion du client.
Utilisation avec un IDE (Eclipse)
Pour l’utilisation du programme procéder de la manière suivante :
1. Exécuter SimpleServer.java et SimpleClient.java dans deux consoles différentes
2. Coté serveur (dans la console de SimpleServer.java) :
• Saisir un port disponible sur la machine entre l’intervalle donnée. Par défaut le
serveur va tourner sur le port 50015.
• Les messages que vous saisirez coté client s’afficheront dans cette console
3. Coté client (dans la console de SimpleClient.java) :
• Saisir le même port qui a été saisi pour le serveur. Celui-ci figure sur la console
du serveur
• Saisir les messages de votre choix : Ils sont envoyés au serveur et apparaissent
sur la console du serveur
4. Déconnexion du client et arrêt du serveur :
• Pour déconnecter le client la commande « exit » ne fonctionne pas.
1. Dans la console du serveur : cliquer sur le bouton « Terminate » puis le
bouton « Remove Launch » :
2. Faire de même dans la console du client
3. Cliquer sur le bouton : « Remove All Terminated Launches » pour terminer
toutes les exécutions en cours (permet de tuer tous les processus utilisés
par Eclipse) afin de libérer les ports utilisés par les programmes.
Utilisation avec les lignes de commandes dans un
terminal
Pour l’utilisation du programme en lignes de commande procéder de la manière suivante :
1. Ouvrir deux terminaux :
• Dans le premier exécuter le fichier SimpleServer.java avec la commande pour
Java et sans utiliser d’arguments
• Dans le premier exécuter le fichier SimpleClient.java avec la commande pour
Java et sans utiliser d’arguments
2. Coté serveur :
• Saisir un port disponible sur la machine entre l’intervalle donnée. Par défaut le
serveur va tourner sur le port 50015.
• Les messages que vous saisirez coté client s’afficheront dans ce terminal
3. Coté client :
• Saisir le même port qui a été saisi pour le serveur. Celui-ci figure dans le
terminal du serveur
• Saisir les messages de votre choix : Ils sont envoyés au serveur et
apparaissent sur le terminal du serveur
4. Déconnexion du client et arrêt du serveur :
• Pour déconnecter le client la commande « exit » ne fonctionne pas.
1. Terminer manuellement l’exécution du programme serveur dans le terminal
du serveur
2. Faire de même dans le terminal du client
### Partie II : Starter App
Cette classe est plus pratique à utiliser et utilise les mêmes fonctionnalités que les
précédentes (grâce à des instances des classes SimpleServer.java et SimpleClient.java
respectivement pour le serveur et le client. Elle sert à relier client et serveur.
Utilisation avec un IDE (Eclipse)
Pour l’utilisation du programme procéder de la manière suivante :
1. Exécuter StarterApp.java (Tout se passe dans la même console)
2. Saisir un port serveur disponible sur la machine entre l’intervalle donnée. Par défaut
le serveur va tourner sur le port 50015.
3. Le client se connecte automatiquement : Saisir directement vos messages. Chaque
message saisi est affiché par le serveur.
4. Déconnexion du client et arrêt du serveur :
• Pour déconnecter le client la commande « exit » ne fonctionne pas.
1. Dans la console : cliquer sur le bouton « Terminate » puis le bouton
« Remove Launch » :
Utilisation avec les lignes de commandes dans un
terminal
Pour l’utilisation du programme en lignes de commande procéder de la manière suivante :
1. Exécuter le fichier SimpleServer.java avec la commande pour Java et sans utiliser
d’arguments (Tout se passe dans le même terminal)
2. Saisir un port disponible sur la machine entre l’intervalle donnée. Par défaut le serveur
va tourner sur le port 50015.
3. Saisir les messages de votre choix : comme avec l’IDE, ils sont envoyés au serveur et
apparaissent sur le terminal
4. Déconnexion du client et arrêt du serveur :
• Pour déconnecter le client la commande « exit » ne fonctionne pas.
• Terminer manuellement l’exécution du programme serveur dans le terminal
### Partie III. FullDuplex
Qu'est-ce qui se passe si vous exécutez, en même temps, un thread
pour chacune des classes que vous avez écrit? Comment pouvez-vous
corriger le comportement?
Pour corriger ce problème on peut d’une part : mettre un verrou pour protéger l’accès en
écriture à la liste des messages reçus dans les deux classes (TwoWayClient.java et
TwoWayServer.java). D’autre part, On peut créer une autre classe (FullDuplex.java)
pour relier client et serveur et ainsi limiter les problèmes de concurrence en limitant le
nombre de Threads actifs en même temps (un thread pour chacune des classes).
3 Classes sont utilisées pour cette partie : TwoWayClient.java (coté client),
TwoWayServer.java (coté serveur) et FullDuplex.java (pour relier client et serveur). Les
classes TwoWayClient.java et TwoWayServer.java ne sont pas à compiler
manuellement. Cependant, elles sont testables en enlevant le main() en commentaire
dans les codes sources.
Utilisation avec un IDE (Eclipse)
Pour l’utilisation du programme procéder de la manière suivante :
 1. Exécuter FullDuplex.java (Tout se passe dans la même console)
 2. Saisir un port serveur disponible sur la machine entre l’intervalle donnée. Par défaut
le serveur va tourner sur le port 50015.
 3. Le client se connecte automatiquement : Saisir directement vos messages. Chaque
message saisi est affiché par le serveur dans la console.
 4. Affichage de tous les messages reçus et arrêt du serveur :
• Le serveur sauvegarde tout les messages reçu sur une
mémoire partagée : une structure de données de type :
Linkedlist(). Pour la consulter :
• A tout moment saisir la commande « exit » :
◦ Déconnexion du client et du serveur puis affichage de tous
les messages reçus par le client et par le serveur et
enregistrées dans la mémoire partagée.
Utilisation avec les lignes de commandes dans un
terminal
Pour l’utilisation du programme en lignes de commande procéder de la manière suivante :
1. Exécuter le fichier FullDuplex.java avec la commande pour Java et sans utiliser
d’arguments (tout se passe dans le même terminal)
2. Saisir un port disponible sur la machine entre l’intervalle donnée. Par défaut le serveur
va tourner sur le port 50015.
3. Saisir les messages de votre choix : comme avec l’IDE, ils sont envoyés au serveur et
apparaissent sur le terminal.
4. Affichage de tous les messages reçus et arrêt du serveur :
• A tout moment saisir la commande « exit » :
◦ Détails dans la partie IDE
### Partie IV. Robot9k
Cette partie implémente le Robot9000. 3 Classes sont utilisées pour cette partie :
Robot9kTCPServer.java (coté serveur) et Robot9kTCPClient.java (coté client).
Lorsqu’un client se connecte au serveur, il reçoit un numéro qui l’identifie (à partir
de 1). Il peut envoyer autant de messages qu’il souhaite. Cependant, la seule règle c’est
que l’envoi d’un même message une deuxième fois provoque la déconnexion du client en
question. Il est alors possible de créer un autre client pour le connecter. Le numéro du
client s’incrémente pour chaque nouveau client connecté.
Utilisation avec un IDE (Eclipse)
Pour l’utilisation du programme procéder de la manière suivante :
1. Exécuter Robot9kTCPServer.java et Robot9kTCPClient.java dans deux consoles
différentes
2. Coté serveur (dans la console de Robot9kTCPServer.java) :
• Saisir un port disponible sur la machine entre l’intervalle donnée. Par défaut le
serveur va tourner sur le port 50015.
• Les messages que vous saisirez coté client s’afficheront dans les deux
consoles.
3. Coté client (dans la console de Robot9kTCPClient.java) :
• Saisir le même port qui a été saisi pour le serveur. Celui-ci figure sur la console
du serveur
• Saisir les messages de votre choix : Ils sont envoyés au serveur et apparaissent
sur la console du serveur.
4. Déconnexion du client par la saisie d’un message en double :
1. Coté Serveur, Pour chaque client déconnecté :
◦ Affichage du message de déconnexion avec le numéro du client qui a été
déconnecté
◦ Comme pour la partie III, affichage de tous les messages reçus par le
client (qui a été déconnecté) et enregistrées dans la mémoire partagée.
◦ Affichage d’un message pour savoir si l’utilisateur souhaite ou non arrêter le
serveur après la déconnexion du client.
▪ Répondre en saisissant la commande « y » pour arrêter le
serveur ce qui termine le programme du serveur.
▪ Répondre en saisissant la commande « n » pour remettre
la socket serveur en mode écoute : le serveur est prêt à
recevoir les messages d’autres clients qui se connectent.
2. Coté client, Pour chaque client déconnecté :
◦ Affichage du message de déconnexion avec le numéro du client qui a été
déconnecté
◦ Affichage d’un message pour savoir si l’utilisateur souhaite ou non connecter un
nouveau client après la déconnexion du dernier.
▪ Répondre en saisissant la commande « y » pour connecter
un nouveau client avec un nouveau numéro de client : Le
nouveau client peut à son tour envoyer des messages au
serveur. Attention, les messages déjà envoyés par tous les
anciens clients comptent comme messages double si le
nouveau client en envoie un. Cela entraînerai, pour lui aussi,
sa déconnexion.
▪ Répondre en saisissant la commande « n » pour choisir de
ne pas créer un nouveau client ce qui provoque la
terminaison du programme du client.
 5. Affichage de tous les messages reçus et déconnexion manuelle du client courant :
• A tout moment saisir la commande « exit » dans la console du
client :
◦ Déconnexion du client seulement puis affichage de tous les
messages reçus par le client et enregistrées dans la mémoire
partagée dans la console du serveur.
Utilisation avec les lignes de commandes dans un
terminal
Pour l’utilisation du programme en lignes de commande procéder de la manière suivante :
1. Ouvrir deux terminaux :
• Dans le premier exécuter le fichier Robot9kTCPServer.java avec la commande
pour Java et sans utiliser d’arguments
• Dans le premier exécuter le fichier Robot9kTCPClient.java avec la commande
pour Java et sans utiliser d’arguments
2. Coté serveur :
• Saisir un port disponible sur la machine entre l’intervalle donnée. Par défaut le
serveur va tourner sur le port 50015.
• Les messages que vous saisirez coté client s’afficheront dans ce terminal
3. Coté client :
• Saisir le même port qui a été saisi pour le serveur. Celui-ci figure dans le
terminal du serveur
• Saisir les messages de votre choix : Ils sont envoyés au
serveur et apparaissent sur les terminaux du serveur et du
client
4. Déconnexion du client par la saisie d’un message en double :
• Détails coté serveur et client dans la partie IDE
5. Affichage de tous les messages reçus et déconnexion manuelle du client courant :
• A tout moment saisir la commande « exit » dans le terminal du
client :
◦ Détails dans la partie IDE
