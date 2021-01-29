Ce README est destinée à la compréhension de la partie  web du projet "Suis-ton-sportif"

# SOMMAIRE : 
    1/ Description
    2/ Installation et lancement du projet


## 1/ Description 
La partie web est réalisée entièrement en JavaScript.\
Pour le coté client nous utilisons React.\
Pour le coté serveur nous utilison node.js avec Express.\
Pour la base de donnée nous avons décidé d'utiliser MariaDB.


## 2/ Installation
### Installation de la partie web : 
1. A la racine du dossier web : npm install
2. Dans le dossier client : npm install
3. Dans le dossier serveur : npm install
4. Pour la connexion à la base de donnée créer un fichier .env à la racine du projet et ajouter dedans 4 variables BDD_HOST,BDD_USER,BDD_PASSWORD,BDD_DATABASE avec vos informations personnelles de connexion du genre : 
\BDD_HOST = localhost\
BDD_USER = username\
BDD_PASSWORD = password\
BDD_DATABASE = database\


### Lancement du projet : 
Se mettre à la racine du dossier web et : npm run dev.

Pour lancer uniquement le coté serveur : 
Se mettre dans le dossier serveur et : npm start.

Pour lancer uniquement le coté client : 
Se mettre dans le dossier client et : npm start.



