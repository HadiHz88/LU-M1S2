from ciphers import Alphabet, VigenereCipher, PlayfairCipher

import sys
sys.stdout.reconfigure(encoding='utf-8')

# ── Vigenère: standard A-Z ────────────────────────────────────────────────────
vc = VigenereCipher("crypto")
plain = "ULISLIKEHOME"
enc = vc.encrypt(plain)
print("── Vigenère (A-Z, 26) ──")
print(f"  Key:       {vc.key}")
print(f"  Plaintext: {plain}")
print(f"  Encrypted: {enc}")
print(f"  Decrypted: {vc.decrypt(enc)}\n")

# ── Vigenère: extended A-Z + 0-9 ─────────────────────────────────────────────
alpha36 = Alphabet(Alphabet.ALPHANUM)
vc2 = VigenereCipher("KEY99", alphabet=alpha36)
plain2 = "HELLO42"
enc2 = vc2.encrypt(plain2)
print("── Vigenère (A-Z + 0-9, 36) ──")
print(f"  Key:       {vc2.key}")
print(f"  Plaintext: {plain2}")
print(f"  Encrypted: {enc2}")
print(f"  Decrypted: {vc2.decrypt(enc2)}\n")

# ── Playfair: classic 5*5 (J→I) ──────────────────────────────────────────────
pf = PlayfairCipher("MONARCHY")
plain3 = "ATTACK AT DAWN"
enc3 = pf.encrypt(plain3)
print("── Playfair (A-Z minus J, 5*5) ──")
print(pf.show_grid())
print(f"  Plaintext: {plain3}")
print(f"  Encrypted: {enc3}")
print(f"  Decrypted: {pf.decrypt(enc3)}")
print("  (trailing/inserted X chars are Playfair padding artifacts)\n")

# ── Playfair: extended 6*6 (A-Z + 0-9) ───────────────────────────────────────
alpha36_pf = Alphabet(Alphabet.ALPHANUM)
pf2 = PlayfairCipher("SECRET", alphabet=alpha36_pf, replace={})
plain4 = "MEET AT 12"
enc4 = pf2.encrypt(plain4)
print("── Playfair (A-Z + 0-9, 6*6) ──")
print(pf2.show_grid())
print(f"  Plaintext: {plain4}")
print(f"  Encrypted: {enc4}")
print(f"  Decrypted: {pf2.decrypt(enc4)}\n")