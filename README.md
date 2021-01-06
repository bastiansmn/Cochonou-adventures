````
 _____            _                                             _                 _                       
/  __ \          | |                                           | |               | |                      
| /  \/ ___   ___| |__   ___  _ __   ___  _   _ ______ __ _  __| |_   _____ _ __ | |_ _   _ _ __ ___  ___ 
| |    / _ \ / __| '_ \ / _ \| '_ \ / _ \| | | |______/ _` |/ _` \ \ / / _ \ '_ \| __| | | | '__/ _ \/ __|
| \__/\ (_) | (__| | | | (_) | | | | (_) | |_| |     | (_| | (_| |\ V /  __/ | | | |_| |_| | | |  __/\__ \
 \____/\___/ \___|_| |_|\___/|_| |_|\___/ \__,_|      \__,_|\__,_| \_/ \___|_| |_|\__|\__,_|_|  \___||___/
````

# Bienvenue sur Cochonou-adventures !

COCHONOU-ADVENTURES inspiré par les créateurs de Candy Crush Saga et de Farm Heroes Saga !
Un casse-tête animalier inégalé !
Associe au moins deux blocs de même
couleur pour terminer le niveau et sauver les animaux des ignobles
voleurs ! Les déplacements sont limités, planifie-les donc avec
soin. Tes capacités en résolution de casse-têtes vont être mises à
l'épreuve pendant des heures d'amusement !
<br/>
<br/>

###Qu'est-ce que Cochonou-adventures ?

Cochonou-adventures est un jeu codé en Java.
Vous pouvez jouer et sauvez les animaux en cassant les blocs ou en utilisant vos combos et bonus !
<br/>
<br/>

###Comment jouer ?

Pour jouer il vous suffit de lancer une des commandes suivantes :

- Si vous lancez le jeu depuis un IDE (on ne vous conseil pas cette version pour profiter un maximum de la version terminale) :
 Lancez la classe Jouer.java avec les arguments suivants :
  `[Votre nom] -t` (ou `--textual`) Pour la version textuelle, `[Votre nom] -g` (ou `--graphical`) pour la version graphique, ou `[Votre nom] -b --slow-mode` pour lancer le bot et voir ses actions (enlevez le `--slow-mode` pour juste permettre au bot de jouer tous les niveaux jusqu'à la fin du jeu).
  Pour activer la sérialisation, il vous suffit d'ajouter `-s` (ou `--serial`) après la commande (sauf la bot où la sérialialisation est désactivée)
  
- Si vous préferez lancer depuis une console UNIX :
nous devons déjà déplacer les images et ressources dans le bon dossier, puis compiler pour permettre à Java de comprendre où sont les images.
  Les commandes ainsi que les arguments sont par contre les mêmes. <br/> <br/>
  `cd Cochonou-adeventures` <br/>
  `mkdir java/src/ressources` <br/>
  `mv ressources/* java/src/ressources` <br/>
  `cd java/src` <br/>
  `javac Jouer.java && java Jouer` <br/>
  <br/>
  Ou bien dans sa version plus compacte : <br/>
  `cd Cochonou-adeventures && mkdir java/src/ressources && mv ressources/* java/src/ressources && cd java/src && javac Jouer.java && java Jouer` <br/>