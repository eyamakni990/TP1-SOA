# TP1 : Modélisation XML et interrogation XPath avec Maven en Java
 
  Matière : Architecture SOA et services web

  Filière : LSI3 

**Technologies :** XML, XSD, Java, Maven et XPath

## 1. Introduction

L’objectif de ce TP est de modéliser un domaine réel à l’aide du langage XML, de définir un schéma XSD pour vérifier la structure et les données du document XML, puis d’implémenter une application Java permettant de valider le document et d’interroger ses données avec XPath.

Le domaine choisi est celui d’un réseau social appelé **SocialNet**. Cette application permet de représenter des utilisateurs, leurs publications, les réactions et les commentaires associés.

## 2. Modélisation du domaine

### 2.1. Choix du domaine

Le réseau social a été choisi parce qu’il représente un domaine réel contenant plusieurs entités liées entre elles. Il permet d’illustrer l’organisation des données XML et l’utilisation des requêtes XPath pour rechercher des informations.

### 2.2. Structure du document XML

Le fichier `reseau.xml` représente les données du réseau social. Il contient deux grandes parties :

- **Utilisateurs :** informations personnelles, adresse e-mail, date d’inscription, nombre d’abonnés, nombre d’abonnements, identifiant, rôle et état du compte.
- **Publications :** contenu, date de publication, identifiant de l’auteur, visibilité, réactions et commentaires.

Chaque utilisateur possède un identifiant unique, par exemple `U1`, `U2` ou `U3`. Chaque publication possède également un identifiant, comme `P1` ou `P2`.

Les attributs permettent de représenter certaines informations directement dans les balises. Par exemple, `actif` indique si un utilisateur est actif et `visibilite` précise si une publication est publique ou réservée aux amis.

### 2.3. Exemple de structure XML

  <img width="590" height="290" alt="image" src="https://github.com/user-attachments/assets/03d6107f-36e8-435c-95b3-e27d969b528f" />


  <img width="655" height="482" alt="image" src="https://github.com/user-attachments/assets/99a4e20f-5397-45b7-98d6-7b50ac029a9a" />

   <img width="379" height="40" alt="image" src="https://github.com/user-attachments/assets/cbbfbfd0-b02c-4f32-833d-1652fcd876e7" />


Cet exemple présente un utilisateur et une publication contenant une réaction et un commentaire. Le fichier complet contient plusieurs utilisateurs et publications.

## 3. Création du schéma XSD

Le fichier `reseau.xsd` définit la structure que le document XML doit respecter.

Il précise notamment :

- Les éléments obligatoires, comme `nom`, `email` et `datePublication`.
- Les types de données, comme `xs:string`, `xs:date`, `xs:boolean` et `xs:nonNegativeInteger`.
- Les attributs obligatoires, comme `id`, `actif`, `role`, `auteur` et `visibilite`.
- La possibilité d’avoir plusieurs utilisateurs, publications, réactions et commentaires grâce à `maxOccurs="unbounded"` ou à `minOccurs="0"`.

Les types `xs:ID` et `xs:IDREF` permettent de déclarer des identifiants et des références vers des identifiants existants.

L’utilisation d’un schéma XSD améliore la fiabilité des données en permettant de détecter les erreurs de structure et de type avant l’exploitation du document.

## 4. Création du projet Maven

Le projet a été créé dans IntelliJ IDEA sous le nom `SocialNet`.

L’organisation des fichiers est la suivante :

   <img width="415" height="601" alt="image" src="https://github.com/user-attachments/assets/4f1f2edd-ba19-4e13-bb15-934db85c66e7" />


Le fichier `pom.xml` contient les informations du projet et la configuration de compilation Java.

Aucune dépendance externe n’est nécessaire pour les fonctionnalités utilisées, car les API XML, XSD et XPath sont disponibles dans Java grâce à JAXP.

## 5. Validation du document XML avec XSD

La validation est implémentée dans la classe `Main.java` à l’aide des classes `SchemaFactory`, `Schema` et `Validator`.

Le programme charge le fichier XSD, crée un schéma, puis vérifie le document XML à l’aide de ce schéma.

Si le document respecte les règles définies dans le XSD, le programme affiche :

  <img width="425" height="64" alt="image" src="https://github.com/user-attachments/assets/5f01dc5b-679a-4b72-bbdc-4a4450564672" />


Dans le cas contraire, une exception signale l’erreur détectée.

Cette étape permet de vérifier que le document XML respecte la structure attendue avant d’exécuter les requêtes XPath.

## 6. Interrogation du document XML avec XPath

XPath permet de sélectionner des éléments ou des attributs à partir de leur position et de leurs propriétés dans un document XML.

Dans ce projet, `XPathFactory` permet de créer un objet XPath. Les expressions sont ensuite compilées avec `XPathExpression` et exécutées sur le document XML.

### 6.1. Afficher tous les noms des utilisateurs

**Expression XPath :**

  <img width="184" height="48" alt="image" src="https://github.com/user-attachments/assets/d25dd66d-059a-4360-abea-20be8bd3323c" />


**Explication :** sélectionne l’élément `nom` de chaque utilisateur.

**Résultat attendu :**

  <img width="323" height="119" alt="image" src="https://github.com/user-attachments/assets/138709a2-2f3e-4f1e-9ac0-b69f9c6990a3" />


### 6.2. Extraire les utilisateurs actifs

**Expression XPath :**

  <img width="345" height="43" alt="image" src="https://github.com/user-attachments/assets/d9b4564a-1085-4fde-b6e4-f62ea11662a8" />

**Explication :** sélectionne les noms des utilisateurs dont l’attribut `actif` vaut `true`.

**Résultat attendu :**
   
   <img width="293" height="92" alt="image" src="https://github.com/user-attachments/assets/6cda2c41-8b91-45f7-8ed7-9363a08e5a4c" />


### 6.3. Lister les publications publiques

**Expression XPath :**

   <img width="444" height="41" alt="image" src="https://github.com/user-attachments/assets/5b77c64e-c701-4137-965e-bef053d6d2ca" />


**Explication :** sélectionne le contenu des publications dont la visibilité est publique.

**Résultat attendu :**

  <img width="334" height="63" alt="image" src="https://github.com/user-attachments/assets/fe379414-e4b2-47bc-bca9-854aaac73137" />


### 6.4. Rechercher les publications ayant une réaction « like »

**Expression XPath :**

  <img width="515" height="37" alt="image" src="https://github.com/user-attachments/assets/5a545b4b-055e-4547-bcb1-6f5beee54163" />


**Explication :** sélectionne l’identifiant des publications contenant au moins une réaction dont le type est `like`.

**Résultat attendu :**

  <img width="361" height="85" alt="image" src="https://github.com/user-attachments/assets/0ea868d8-2e18-4b84-ada1-46dd9b27c241" />


### 6.5. Afficher tous les commentaires

**Expression XPath :**

  <img width="217" height="41" alt="image" src="https://github.com/user-attachments/assets/fdd2d575-249f-4826-803b-536d6349b05a" />


**Explication :** sélectionne le texte de chaque commentaire présent dans le document.

**Résultat attendu :**

  <img width="313" height="49" alt="image" src="https://github.com/user-attachments/assets/91278717-c281-4766-8e27-175a9bf8e4f9" />


### 6.6. Afficher les types de réactions

**Expression XPath :**

  <img width="186" height="40" alt="image" src="https://github.com/user-attachments/assets/10c74deb-5f2c-4a59-97ad-ba0898255cd5" />


**Explication :** récupère l’attribut `type` de chaque réaction.

**Résultat attendu :**

  <img width="288" height="110" alt="image" src="https://github.com/user-attachments/assets/13e8518f-4ee0-4042-bb7e-07a939cbdfb0" />


### 6.7. Rechercher les publications avec des commentaires

**Expression XPath :**

   <img width="460" height="33" alt="image" src="https://github.com/user-attachments/assets/29293b86-a605-4961-9099-50120fc5702b" />


**Explication :** sélectionne l’identifiant des publications qui contiennent au moins un commentaire.

**Résultat attendu :**

  <img width="405" height="61" alt="image" src="https://github.com/user-attachments/assets/58b5a4ff-ebec-428f-956f-b44eaf2af0de" />


### 6.8. Afficher les identifiants des utilisateurs

**Expression XPath :**

  <img width="199" height="43" alt="image" src="https://github.com/user-attachments/assets/5546ac27-25f4-4e15-a3dd-4ff66b612449" />


**Explication :** sélectionne l’attribut `id` de chaque utilisateur.

**Résultat attendu :**

  <img width="398" height="114" alt="image" src="https://github.com/user-attachments/assets/cdce4e7e-2103-4371-b90d-d415a34e640b" />


## 7. Tests et vérification

Après l’exécution de la classe principale `Main.java`, deux fonctionnalités sont vérifiées :

1. **Validation XML/XSD :** le programme confirme que le document XML respecte le schéma XSD.
2. **Interrogation XPath :** les requêtes permettent d’extraire les utilisateurs actifs, les publications publiques, les réactions, les commentaires et les identifiants.

Un test supplémentaire consiste à modifier temporairement une valeur ou un type de donnée dans le fichier XML afin de provoquer une erreur de validation, puis à restaurer la valeur correcte.

Par exemple, remplacer une date valide par une valeur incorrecte permet de vérifier que le schéma XSD détecte l’erreur de format de date.

  <img width="474" height="36" alt="image" src="https://github.com/user-attachments/assets/2e70930b-298c-4395-a692-032fe1456841" />
  <img width="1377" height="80" alt="image" src="https://github.com/user-attachments/assets/aec36ed8-6ace-43e4-96ca-ce6b08faae0f" />


## 8. Conclusion

Ce TP a permis de mettre en pratique les principales étapes de la manipulation de données XML en Java.

Le document XML représente les données du réseau social SocialNet, tandis que le schéma XSD définit les règles de validation. L’application Java vérifie la conformité du document et utilise XPath pour extraire les informations nécessaires.

Ce travail permet de comprendre l’intérêt de XML pour la représentation structurée des données, de XSD pour leur validation et de XPath pour leur interrogation.
