# LettersWord

-----

## Table Of Contents
* [Goal](#goal)
* [Status](#status)
* [Installation](#installation)
    * [End User](#end-user-installation)
    * [Developer](#developer-installation)
* [Usage](#usage)
* [Compatibility](#compatibility)
* [Contributing](#contributing)
* [License](#license)

## Goal

This Android application is intended to help finding words is games like
*Garden of Words*. Specifically it uses a dictionary to find words composed
of a given list of letters and matching a pattern.

Currently only a French dictionary is available. So it can only be used for
the French localized *Jardin des mots* game, or other French games.

## Status

The application is currently beta quality. It still lacks tests, including
real world ones and a decent documentation.

Starting with the 0.7 version, the application and its sources are available
from GitHub.

## Installation

### End User Installation

For releases starting at the 0.7 version, an apk is available on GitHub.

Just download it and install it on your device.

Disclaimer: The apk is signed, but the signature is not registered at Google.
You will probably have to confirm that you really want to install it. But
that also mean that I will endorse no responsibility if you install an apk
coming for another site, unless you manage to verify the signature (the public
certificate is also maintained on GitHub under `signing_cert.pem`).

### Developer Installation

You should get the full source from [GitHub](https://github.com/s-ball/lettersword.git)

## Usage

On a not French system, you will start with a big warning that the
dictionary only contains French words - said differently, `LettersWord`
cannot help in games using your current language...

Then you will have to declare the available letters to build the words.

Finally, you declare the mask for the words you are searching. Here you
will use a `_` character for any unknow letter, and the relevant character
for any known one. For example when searching for a 4 letters word having
a `t` in second position, you would use `_t__` as mask.

As a special case, the `*` mask will return all the possible words
starting at length 3. It is intended as a help for the Daily Puzzle.

NB: `LettersWord` only accept and uses lower case ASCII letters. Upper case
ones will be mapped to their lower case equivalent, and accented ones
will have to be provided without any diacritic. So `éèçï` should become
`eeci`.

## Compatibility

The application is built and tested on an Android 16.0 *Baklava* emulation
(API version 36). It is intended to support any Android version starting from
Android 7.0 *Nougat* (API version 24).

The Layout is tested on small on medium smartphones, and on a medium tablet.

## Contributing

I shall always be glad to receive issues or Pull Requests on GitHub. But as
I am the only maintainer, I cannot guarantee to react quickly to them. Please
feel free to contact me by mail if I do not answer quickly enough...

## License

`LettersWord` is distributed under the terms of the [MIT](https://spdx.org/licenses/MIT.html) license.