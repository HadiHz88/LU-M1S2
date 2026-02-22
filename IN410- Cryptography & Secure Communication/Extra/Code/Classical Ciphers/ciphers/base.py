from abc import ABC, abstractmethod


class Cipher(ABC):
    """
    Abstract base class that every cipher must implement.

    Subclasses must define `encrypt` and `decrypt`.
    The `__call__` shorthand lets you use a cipher instance directly:

        cipher("hello", mode="encrypt")
        cipher("XMCKL", mode="decrypt")
    """

    @abstractmethod
    def encrypt(self, plaintext: str) -> str:
        """Return the encrypted form of `plaintext`."""
        ...

    @abstractmethod
    def decrypt(self, ciphertext: str) -> str:
        """Return the decrypted form of `ciphertext`."""
        ...

    def __call__(self, text: str, *, mode: str = "encrypt") -> str:
        if mode == "encrypt":
            return self.encrypt(text)
        elif mode == "decrypt":
            return self.decrypt(text)
        raise ValueError(f"mode must be 'encrypt' or 'decrypt', got {mode!r}")

    def __repr__(self) -> str:
        return f"{self.__class__.__name__}()"