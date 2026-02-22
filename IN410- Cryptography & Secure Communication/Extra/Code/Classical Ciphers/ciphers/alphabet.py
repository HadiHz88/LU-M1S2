import math
import string as _string


class Alphabet:
    """
    Defines the working character set for any cipher.

    Presets
    -------
    Alphabet.LETTERS       : A–Z            (26) — standard
    Alphabet.LETTERS_NOIJ  : A–Z minus J    (25) — classic 5×5 Playfair
    Alphabet.ALPHANUM      : A–Z + 0–9      (36) — 6×6 Playfair grid

    Parameters
    ----------
    chars : Ordered string of characters. Uppercased and deduplicated automatically.
    omit  : Characters to exclude, e.g. 'J' for classic Playfair.

    Examples
    --------
    >>> alpha = Alphabet(Alphabet.ALPHANUM)
    >>> alpha.size
    36
    >>> 'Z' in alpha, '9' in alpha, '!' in alpha
    (True, True, False)
    """

    LETTERS      = _string.ascii_uppercase                           # 26
    LETTERS_NOIJ = _string.ascii_uppercase.replace('J', '')          # 25 → 5×5
    ALPHANUM     = _string.ascii_uppercase + _string.digits          # 36 → 6×6

    def __init__(self, chars: str = LETTERS, omit: str = ""):
        omit_set = set(omit.upper())
        seen: set[str] = set()
        result: list[str] = []
        for c in chars.upper():
            if c not in omit_set and c not in seen:
                seen.add(c)
                result.append(c)
        if not result:
            raise ValueError("Alphabet must contain at least one character after filtering.")
        self._chars = result
        self._index: dict[str, int] = {c: i for i, c in enumerate(result)}

    # ── Core ──────────────────────────────────────────────────────────────────

    @property
    def size(self) -> int:
        return len(self._chars)

    @property
    def chars(self) -> list[str]:
        return list(self._chars)

    def __len__(self) -> int:
        return self.size

    def __contains__(self, c: str) -> bool:
        return c.upper() in self._index

    def __getitem__(self, i: int) -> str:
        return self._chars[i]

    def index(self, c: str) -> int:
        """Return the position of `c`. Raises ValueError if not found."""
        try:
            return self._index[c.upper()]
        except KeyError:
            raise ValueError(f"{c!r} is not in this alphabet.")

    # ── Text helpers ──────────────────────────────────────────────────────────

    def format_text(self, text: str, replace: dict[str, str] | None = None) -> str:
        """
        Uppercase `text`, optionally replace characters, then keep only
        characters that belong to this alphabet.

        Parameters
        ----------
        replace : Substitutions applied *before* filtering, e.g. {'J': 'I'}.
        """
        text = text.upper()
        if replace:
            for old, new in replace.items():
                text = text.replace(old.upper(), new.upper())
        return ''.join(c for c in text if c in self)

    def repeat_key(self, key: str, length: int) -> str:
        """Repeat and truncate a formatted key string to exactly `length` characters."""
        key = self.format_text(key)
        if not key:
            raise ValueError("Key contains no characters from this alphabet.")
        return (key * (length // len(key) + 1))[:length]

    # ── Playfair grid helpers ─────────────────────────────────────────────────

    def grid_dims(self) -> tuple[int, int]:
        """
        Find the most-square (rows, cols) factoring for a Playfair grid.
        rows ≤ cols is always guaranteed.

        Raises
        ------
        ValueError
            If the alphabet size is prime (can't form a rectangle).
            Suggested sizes: 25 (5×5), 36 (6×6), 48 (6×8), 64 (8×8).
        """
        n = self.size
        r = math.isqrt(n)
        while r > 1 and n % r != 0:
            r -= 1
        if r == 1:
            raise ValueError(
                f"Alphabet size {n} is prime and cannot form a rectangular grid. "
                f"Consider sizes like 25 (5×5), 36 (6×6), 48 (6×8), or 64 (8×8)."
            )
        return r, n // r  # (rows, cols)

    def __repr__(self) -> str:
        preview = ''.join(self._chars[:12]) + ('...' if self.size > 12 else '')
        return f"Alphabet(size={self.size}, chars={preview!r})"