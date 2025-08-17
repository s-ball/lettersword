# LettersWord

-----

## Table des matières

* [But](#but)
* [Status](#status)
* [Installation](#installation)
  * [Installation pour l'utilisateur final](#installation-pour-lutilisateur-final)
  * [Installation pour développeur](#installation-pour-développeur)
* [Utilisation](#utilisation)
* [Compatibilité](#compatibilité)
* [Contribution](#contribution)
* [License](#license)

## But

Cette application Android sert à trouver des mots pour des jeux
comme *Jardin des Mots*. Spécifiquement, elle utilise un dictionnaire
pour trouver des mots composés à partir d'une liste de lettres donnée et 
respectant un modèle.

Actuellement, seul un dictionnaire français est disponible. Donc, elle
ne peut être utilisée qu'avec la version française de *Jardin des Mots*,
ou d'autres jeux en français.

## Status

L'application a actuellement une qualité de niveau beta. Elle manque de
tests, y compris de tests dans le mode réel, et d'une documentation
décente.

À partir de la version 0.7, l'application et son code source sont disponibles
sur GitHub.

## Installation

### Installation pour l'utilisateur final

À partir de la version 0.7, un apk est disponible sur GitHub.

Il suffit de le récupérer et de l'installer sur votre appareil.

Attention : Le fichier apk est signé, mais la signature n'est pas
enregistrée chez Google. Vous aurez probablement à confirmer que vous
voulez vraiment l'installer. Mais cela signifie également que je n'endosse
aucune responsabilité si vous installez un apk provenant d'un autre site,
à moins que vous n'ayez vérifié la signature (la clé publique est aussi
enregistrée sur GitHub dans le fichier `signing_cert.pem`).

### Installation pour développeur

Vous devez récupérer l'ensemble des sources depuis [GitHub](https://github.com/s-ball/lettersword.git)

## Utilisation

Sur un système autre que français, vous commencerez avec un avertissement
comme quoi le dictionnaire ne contient que des mots français - autrement 
dit `LettersWord` ne sert à rien pour des jeux dans votre langue.

Ensuite, vous devrez déclarer les lettres disponibles pour constituer
les mots.

Enfin, vous déclarez le masque pour les mots que vous cherchez. Ici, vous
utiliserez la lettre `_` pour toute lettre inconnue, et la bonne lettre
pour toutes les autres. Par example, pour chercher un mot de 4 lettres
avec un `t` en deuxième position, vous utiliseriez un masque de `_t__`.

NB : `LettersWord` n'accepte et n'utilise que des minuscules sans accents.
Les majuscules sont remplacées par la minuscule équivalente, et les
caractères accentués doivent être fournis sans signe diacritique. Ainsi
`éèçï` deviendrait `eeci`.

## Compatibilité

L'application est compilée et testée pour une émulation d'Android 16.0
*Baklava* (API version 36). Elle est censée fonctionner sur toute version
à partir d'Android 7.0 *Nougat* (API version 24).

La mise en page est testée sur petit et moyen smartphone et sur tablette
moyenne.

## Contribution

Je serai toujours content de recevoir des signalements ou des demandes de
tirage (Pull Requests) sur GitHub. Mais comme je suis seul à m'occuper de
l'application, je ne peux promettre de réagir rapidement. N'hésitez pas à
me contacter par mel si je ne réponds pas assez vite.

## License

`LettersWord` est publié sous licence [MIT](https://spdx.org/licenses/MIT.html).