from .alphabet import Alphabet
from .base import Cipher


class VigenereCipher(Cipher):
    """
    Vigenère cipher with a configurable alphabet.

    Works on *any* alphabet — A–Z, A–Z+digits, custom sets, etc.
    The modulus is derived from the alphabet size automatically.

    Parameters
    ----------
    key      : The cipher keyword (only chars in the alphabet are kept).
    alphabet : Character set to use. Defaults to standard A–Z (26 chars).

    Examples
    --------
    >>> vc = VigenereCipher("crypto")
    >>> vc.encrypt("ULISLIKEHOME")
    'WPEBLGVBZAGC'
    >>> vc.decrypt("WPEBLGVBZAGC")
    'ULISLIKEHOME'

    With a 36-character alphabet (A–Z + 0–9):

    >>> alpha36 = Alphabet(Alphabet.ALPHANUM)
    >>> vc2 = VigenereCipher("key99", alphabet=alpha36)
    >>> vc2.decrypt(vc2.encrypt("HELLO42"))
    'HELLO42'
    """

    def __init__(self, key: str, alphabet: Alphabet | None = None) -> None:
        self._alpha = alphabet or Alphabet(Alphabet.LETTERS)
        formatted_key = self._alpha.format_text(key)
        if not formatted_key:
            raise ValueError("Key contains no characters from the chosen alphabet.")
        self._key = formatted_key

    @property
    def key(self) -> str:
        return self._key

    @property
    def alphabet(self) -> Alphabet:
        return self._alpha

    def encrypt(self, plaintext: str) -> str:
        plaintext = self._alpha.format_text(plaintext)
        stream    = self._alpha.repeat_key(self._key, len(plaintext))
        return ''.join(
            self._alpha[(self._alpha.index(p) + self._alpha.index(k)) % self._alpha.size]
            for p, k in zip(plaintext, stream)
        )

    def decrypt(self, ciphertext: str) -> str:
        ciphertext = self._alpha.format_text(ciphertext)
        stream     = self._alpha.repeat_key(self._key, len(ciphertext))
        return ''.join(
            self._alpha[(self._alpha.index(c) - self._alpha.index(k)) % self._alpha.size]
            for c, k in zip(ciphertext, stream)
        )

    def __repr__(self) -> str:
        return f"VigenereCipher(key={self._key!r}, alphabet={self._alpha!r})"