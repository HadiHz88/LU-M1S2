from .alphabet import Alphabet
from .base import Cipher


class PlayfairCipher(Cipher):
    """
    Playfair cipher with a configurable rectangular grid.

    The grid dimensions are derived automatically from the alphabet size.
    The alphabet size must not be prime (it must factor into rows × cols).

        25 chars → 5×5  (classic: use Alphabet.LETTERS_NOIJ + replace J→I)
        36 chars → 6×6  (extended: use Alphabet.ALPHANUM, supports digits)
        48 chars → 6×8  etc.

    Parameters
    ----------
    key      : Keyword used to build the substitution grid.
    alphabet : Character set. Defaults to Alphabet.LETTERS_NOIJ (25 chars, 5×5).
    filler   : Letter inserted between repeated digraph pairs (default 'X').
    replace  : Char substitutions applied before en/decryption, e.g. {'J': 'I'}.
               Automatically set to {'J': 'I'} for 25-char alphabets if omitted.

    Examples
    --------
    Classic 5×5:

    >>> pf = PlayfairCipher("MONARCHY")
    >>> pf.encrypt("ATTACK AT DAWN")
    'MZKIQSMQEKQE' (example; actual depends on grid)

    Extended 6×6 with digits:

    >>> alpha36 = Alphabet(Alphabet.ALPHANUM)
    >>> pf2 = PlayfairCipher("KEY", alphabet=alpha36, replace={})
    >>> pf2.decrypt(pf2.encrypt("MEET AT 12"))
    'MEETAT12X'   # trailing X is a padding artifact, normal for Playfair
    """

    def __init__(
        self,
        key: str,
        alphabet: Alphabet | None = None,
        filler: str = 'X',
        replace: dict[str, str] | None = None,
    ) -> None:
        self._alpha = alphabet or Alphabet(Alphabet.LETTERS_NOIJ)
        self._rows, self._cols = self._alpha.grid_dims()

        # Validate filler
        filler = filler.upper()
        if filler not in self._alpha:
            raise ValueError(f"Filler {filler!r} is not in the chosen alphabet.")
        self._filler = filler

        # Default replace: J→I only for the classic 25-char alphabet
        if replace is None:
            self._replace = {'J': 'I'} if self._alpha.size == 25 else {}
        else:
            self._replace = {k.upper(): v.upper() for k, v in replace.items()}

        self._grid, self._pos = self._build_grid(key)

    # ── Grid construction ─────────────────────────────────────────────────────

    def _build_grid(
        self, key: str
    ) -> tuple[list[list[str]], dict[str, tuple[int, int]]]:
        """Fill the grid: keyword letters first, then the remaining alphabet."""
        seen: set[str] = set()
        order: list[str] = []

        for c in self._alpha.format_text(key, replace=self._replace):
            if c not in seen:
                seen.add(c)
                order.append(c)

        for c in self._alpha.chars:
            if c not in seen:
                seen.add(c)
                order.append(c)

        grid: list[list[str]] = []
        pos:  dict[str, tuple[int, int]] = {}
        idx = 0
        for r in range(self._rows):
            row = []
            for c in range(self._cols):
                char = order[idx]; idx += 1
                row.append(char)
                pos[char] = (r, c)
            grid.append(row)

        return grid, pos

    # ── Digraph helpers ───────────────────────────────────────────────────────

    def _safe_filler(self, avoid: str) -> str:
        """Return the filler, or the first alphabet char that isn't `avoid`."""
        if self._filler != avoid:
            return self._filler
        for c in self._alpha.chars:
            if c != avoid:
                return c
        raise ValueError("Cannot find a valid filler character distinct from the input.")

    def _to_digraphs(self, text: str) -> list[tuple[str, str]]:
        """
        Split plaintext into Playfair digraphs.

        Rules:
        1. Format text (uppercase, apply replacements, strip non-alphabet chars).
        2. If both letters in a pair match, insert a filler before the second.
        3. If odd length after splitting, append a filler.
        """
        text  = self._alpha.format_text(text, replace=self._replace)
        chars = list(text)
        pairs: list[tuple[str, str]] = []
        i = 0
        while i < len(chars):
            a = chars[i]
            if i + 1 >= len(chars):
                # Odd length: pad
                pairs.append((a, self._safe_filler(a)))
                i += 1
            elif chars[i] == chars[i + 1]:
                # Repeated pair: insert filler
                pairs.append((a, self._safe_filler(a)))
                i += 1          # do NOT consume chars[i+1]; re-process it next
            else:
                pairs.append((a, chars[i + 1]))
                i += 2
        return pairs

    # ── Digraph encryption / decryption ──────────────────────────────────────

    def _encrypt_pair(self, a: str, b: str) -> tuple[str, str]:
        r1, c1 = self._pos[a]
        r2, c2 = self._pos[b]
        if r1 == r2:    # Same row → shift right
            return (self._grid[r1][(c1 + 1) % self._cols],
                    self._grid[r2][(c2 + 1) % self._cols])
        if c1 == c2:    # Same col → shift down
            return (self._grid[(r1 + 1) % self._rows][c1],
                    self._grid[(r2 + 1) % self._rows][c2])
        return self._grid[r1][c2], self._grid[r2][c1]  # Rectangle → swap cols

    def _decrypt_pair(self, a: str, b: str) -> tuple[str, str]:
        r1, c1 = self._pos[a]
        r2, c2 = self._pos[b]
        if r1 == r2:    # Same row → shift left
            return (self._grid[r1][(c1 - 1) % self._cols],
                    self._grid[r2][(c2 - 1) % self._cols])
        if c1 == c2:    # Same col → shift up
            return (self._grid[(r1 - 1) % self._rows][c1],
                    self._grid[(r2 - 1) % self._rows][c2])
        return self._grid[r1][c2], self._grid[r2][c1]  # Rectangle → same as encrypt

    # ── Public interface ──────────────────────────────────────────────────────

    def encrypt(self, plaintext: str) -> str:
        result = []
        for a, b in self._to_digraphs(plaintext):
            ea, eb = self._encrypt_pair(a, b)
            result.extend([ea, eb])
        return ''.join(result)

    def decrypt(self, ciphertext: str) -> str:
        """
        Note: Playfair decryption does not remove the filler characters inserted
        during encryption — that's left to the caller, as the cipher itself
        cannot distinguish filler from real letters.
        """
        ct = self._alpha.format_text(ciphertext)   # no replace: ciphertext is already clean
        if len(ct) % 2 != 0:
            raise ValueError("Ciphertext length must be even for Playfair decryption.")
        result = []
        for i in range(0, len(ct), 2):
            da, db = self._decrypt_pair(ct[i], ct[i + 1])
            result.extend([da, db])
        return ''.join(result)

    def show_grid(self) -> str:
        """Return a human-readable view of the substitution grid."""
        divider = '+' + (('---+') * self._cols)
        rows = [divider]
        for row in self._grid:
            rows.append('| ' + ' | '.join(row) + ' |')
            rows.append(divider)
        return '\n'.join(rows)

    def __repr__(self) -> str:
        return (
            f"PlayfairCipher("
            f"grid={self._rows}×{self._cols}, "
            f"filler={self._filler!r}, "
            f"alphabet={self._alpha!r})"
        )