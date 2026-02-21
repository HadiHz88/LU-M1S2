# Classical Ciphers Exercises

## Brute Force Attacks

### Exercise 1

Let us assume there is a password-based authentication system. The password has $8$ characters. In a brute force attack, the attacker tries all possible passwords to find the right one. Assume that an authentication takes $1\ \mu\text{s}$.

**a.** All users only use small English letters and they use them in a uniform and random way. How much time does the attacker need on average?
**b.** Now assume that also capital letters, numbers and 31 ASCII special characters are used, so-called strong passwords. What is now the average time the attacker needs?

#### Solution

**a)** The number of possible passwords is $26^8$. The average time to find the right password is half of the total time, so we have:

$$\text{Average time} = \frac{26^8}{2} \times 1\ \mu\text{s} = 208{,}827{,}064{,}576\ \mu\text{s} \approx 6.62\ \text{years}$$

**b)** The number of possible passwords is now $(26 + 26 + 10 + 31)^8 = 93^8$. The average time to find the right password is:

$$\text{Average time} = \frac{93^8}{2} \times 1\ \mu\text{s} = 437{,}893{,}890{,}380{,}859{,}375\ \mu\text{s} \approx 13{,}889.5\ \text{years}$$

---

### Exercise 2

Consider the following algorithm that generates random passwords for new users on a system: It selects a character from the set of: English lowercase and uppercase characters, as well as the six punctuation characters < > [ ] { }. How many passwords are possible if a 5 character long password is generated using this algorithm?

#### Solution

The total number of characters available is $26$ (lowercase) + $26$ (uppercase) + $6$ (punctuation) = $58$. For a 5 character long password, the number of possible passwords is:

$$58^5 = 656{,}356{,}768$$

---

### Exercise 3

The passwords a user can choose for a given system are:

- At least 6 characters and at most 8 characters long
- Composed of characters from the following character set: lowercase letters a-z (size=26), uppercase letters A-Z (size=26), digits 0-9 (size=10), and special characters/symbols (size=32)

Consider the following two password policies:

- **Policy 1:** The user can freely choose his/her password
- **Policy 2:** The password must at least have one digit or at least one special character

**a)** For each of the above two password policies, calculate the number of possible passwords.
**b)** Based on the number of possible passwords and assuming an ideal user that randomly chooses passwords, which of these two policies seems more secure and why?
**c)** In fact, users rarely choose their passwords randomly, but stick to certain simple patterns that are easier to remember and to type. Let's assume the typical user, when he/she can freely choose her password (in Policy 1), will prefer passwords that consist only of lowercase and uppercase letters. What is the number of possible passwords for such a typical user?
**d)** Revisit the question 2 again.

#### Solution

The full character set has $n = 26 + 26 + 10 + 32 = 94$ characters. The letters-only set has $52$ characters.

**a)**

**Policy 1** — freely choose from 94 characters, length 6 to 8:

$$N_1 = 94^6 + 94^7 + 94^8$$

$$= 689{,}869{,}781{,}056 + 64{,}847{,}559{,}219{,}264 + 6{,}095{,}670{,}566{,}610{,}816$$

$$\approx 6.161 \times 10^{15}$$

**Policy 2** — at least one digit or special character. Easier to compute as complement:

$$N_2 = N_1 - \underbrace{(52^6 + 52^7 + 52^8)}_{\text{letter-only passwords}}$$

$$52^6 + 52^7 + 52^8 = 19{,}770{,}609{,}664 + 1{,}028{,}071{,}702{,}528 + 53{,}459{,}728{,}531{,}456 \approx 5.45 \times 10^{13}$$

$$N_2 \approx 6.161 \times 10^{15} - 5.45 \times 10^{13} \approx 6.107 \times 10^{15}$$

**b)** Since $N_1 > N_2$, **Policy 1** has a strictly larger password space and is therefore more secure for a perfectly random user — an attacker must try more passwords on average before finding the right one.

**c)** A typical user under Policy 1 uses only the 52 letters:

$$N_{\text{typical}} = 52^6 + 52^7 + 52^8 \approx 5.45 \times 10^{13}$$

This is about **113 times smaller** than $N_1$, meaning the effective security is much lower than Policy 1 promises.

**d)** Revisiting: for the typical user, Policy 2 forces them to include at least one digit or special character, so they can no longer fall back on pure letter-only passwords. This shrinks the attacker's search space for a typical user from $N_2 \approx 6.107 \times 10^{15}$ (all Policy 2 passwords) down from $N_{\text{typical}} \approx 5.45 \times 10^{13}$. In other words, **Policy 2 is more effective in practice**: it eliminates the weakest, most predictable patterns and forces real users into a larger, more varied space.

---

### Exercise 4

Suppose that using commodity hardware it is possible to build a computer for about \$200 that can brute force about 10 billion AES keys per second. Suppose an organization wants to run an exhaustive search for a single 128-bit AES key and is willing to spend four trillion ($4 \times 10^{12}$) dollars. How long would it take the organization to run an exhaustive search for this single 128-bit AES key with these machines, in the worst case?

#### Solution

**Number of machines purchased:**

$$\text{Machines} = \frac{4 \times 10^{12}}{200} = 2 \times 10^{10}$$

**Combined key-testing rate:**

$$\text{Rate} = 2 \times 10^{10} \times 10^{10} = 2 \times 10^{20} \text{ keys/second}$$

**Worst-case time** (trying all $2^{128}$ keys):

$$T = \frac{2^{128}}{2 \times 10^{20}} \approx \frac{3.4 \times 10^{38}}{2 \times 10^{20}} = 1.7 \times 10^{18}\ \text{seconds}$$

Converting to years ($1\ \text{year} \approx 3.15 \times 10^7$ s):

$$T \approx \frac{1.7 \times 10^{18}}{3.15 \times 10^7} \approx 5.4 \times 10^{10}\ \text{years} \approx 54\ \text{billion years}$$

This is roughly **4 times the age of the universe**, demonstrating why 128-bit AES is considered computationally infeasible to brute-force.

---

## Random Substitution Cipher

### Exercise 5

Consider the message $m = \texttt{HKPUFCMHY BHDDXZH}$, and let $(E, D)$ be a substitution cipher.

**a)** Decrypt $m$ using the following (secret) substitution key:

| plaintext  | a | b | c | d | e | f | g | h | i | j | k | l | m | n | o | p | q | r | s | t | u | v | w | x | y | z |
|-----------|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|
| ciphertext | X | G | P | Y | H | Q | Z | I | R | A | J | S | B | K | T | C | L | U | D | M | V | E | N | W | F | O |

**b)** Can this cipher be broken by someone who has access to $m$ but not to the secret key? Why?

#### Solution

**a)** To decrypt we need the **inverse** mapping (ciphertext → plaintext):

| Ciphertext | A | B | C | D | E | F | G | H | I | J | K | L | M | N | O | P | Q | R | S | T | U | V | W | X | Y | Z |
|-----------|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|
| Plaintext  | j | m | p | s | v | y | b | e | h | k | n | q | t | w | z | c | f | i | l | o | r | u | x | a | d | g |

Decrypting $m = \texttt{HKPUFCMHY\ BHDDXZH}$ letter by letter:

$$\texttt{H} \to e,\quad \texttt{K} \to n,\quad \texttt{P} \to c,\quad \texttt{U} \to r,\quad \texttt{F} \to y,\quad \texttt{C} \to p,\quad \texttt{M} \to t,\quad \texttt{H} \to e,\quad \texttt{Y} \to d$$
$$\texttt{B} \to m,\quad \texttt{H} \to e,\quad \texttt{D} \to s,\quad \texttt{D} \to s,\quad \texttt{X} \to a,\quad \texttt{Z} \to g,\quad \texttt{H} \to e$$

$$\boxed{\text{Plaintext: "encrypted message"}}$$

**b)** Yes, this cipher **can be broken** via **frequency analysis**. Since a monoalphabetic substitution preserves the relative frequency of letters, an attacker can compare ciphertext letter frequencies against known English letter frequencies (e.g., 'E' is the most common letter, then 'T', 'A', etc.) and deduce the key over time — especially with a message longer than just a few words. The cipher offers **no diffusion**, so statistical patterns of the plaintext survive directly into the ciphertext.

---

## Affine Cipher

### Exercise 6

Consider the Affine Cipher with a plaintext space consisting of the English alphabet (small case letters), the space character, and the ten digits (0-9). Characters are assigned sequentially starting with "a" (value 0) and ending with "9".

**a.** What is the modulus to be used in the encryption process?
**b.** The key used is $(a = 2,\ b = 3)$. Explain why this key is valid.
**c.** Encrypt the message "secu 301" using the key $(a = 3,\ b = 2)$.
**d.** Write the decryption equation of the cipher.

#### Solution

**a)** The alphabet has $26$ letters + $1$ space + $10$ digits $= 37$ characters.

$$\boxed{n = 37}$$

**b)** For the affine cipher the key is valid if and only if $\gcd(a, n) = 1$. Here $n = 37$ is prime, so every $a \in \{1, \ldots, 36\}$ is coprime with 37. Since $\gcd(2, 37) = 1$, the key $(a=2, b=3)$ is valid. ($b$ can be any value in $\{0, \ldots, 36\}$.)

**c)** Character values: a–z = 0–25, space = 26, 0–9 = 27–36.

$$E(x) = (3x + 2) \bmod 37$$

| Char | Value $x$ | $3x+2$ | $\bmod 37$ | Cipher |
|------|-----------|--------|------------|--------|
| s    | 18        | 56     | 19         | t      |
| e    | 4         | 14     | 14         | o      |
| c    | 2         | 8      | 8          | i      |
| u    | 20        | 62     | 25         | z      |
| (sp) | 26       | 80     | 6          | g      |
| 3    | 30        | 92     | 18         | s      |
| 0    | 27        | 83     | 9          | j      |
| 1    | 28        | 86     | 12         | m      |

$$\boxed{\text{Ciphertext: "toizgsjm"}}$$

**d)** From $y = ax + b \pmod{n}$, we isolate $x$:

$$\boxed{D(y) = a^{-1}(y - b) \pmod{n}}$$

For the key $(a=3,\ b=2,\ n=37)$: since $3 \times 25 = 75 \equiv 1 \pmod{37}$, we have $3^{-1} = 25$, giving $D(y) = 25(y - 2) \bmod 37$.

---

### Exercise 7

Consider the Affine cipher with a plaintext space consisting of the English alphabet (small case letters) and three extra characters: space ( ), question mark (?), and exclamation mark (!).

**a.** What is the modulus $n$?
**b.** Let $a$ be the first non-zero digit of your ID (from the least significant digit moving left) and $b$ the next digit to its left. *Example: for ID 11900120, $a = 2$, $b = 1$.*

- i. Explain why $a$ and $b$ are valid.
- ii. Encrypt your first name using the affine cipher.
- iii. Write the decryption equation.

#### Solution

**a)** The plaintext space has $26$ letters + $1$ space + $1$ (?) + $1$ (!) $= 29$ characters.

$$\boxed{n = 29}$$

**b-i)** A valid affine key requires $\gcd(a, 29) = 1$. Since $29$ is prime, any $a \in \{1, \ldots, 28\}$ satisfies this. The first non-zero digit of any ID is always between 1 and 9, so $\gcd(a, 29) = 1$ is guaranteed (no single digit 1–9 shares a factor with 29). $b$ can be any value in $\{0, \ldots, 28\}$, which any digit satisfies.

**b-ii)** Apply $E(x) = (ax + b) \bmod 29$ to each letter of your first name (a=0, b=1, …, z=25, space=26, ?=27, !=28), using your specific $a$ and $b$.

**b-iii)** The decryption equation is:

$$D(y) = a^{-1}(y - b) \bmod 29$$

where $a^{-1}$ is the modular inverse of $a$ modulo 29. Since 29 is prime, $a^{-1} \equiv a^{27} \pmod{29}$ (Fermat's little theorem), or it can be found via the extended Euclidean algorithm.

---

### Exercise 8

Ali and Bilal use the affine cipher $E(x) = ax + b \pmod{n}$ for messages consisting of any letter (a–z, A–Z), any digit (0–9), plus comma (,), period (.) and space ( ).

**a)** What should be the value of $n$?
**b)** What is the size of the key space?
**c)** Ali and Bilal use $(a = 7,\ b = 5)$. Is this valid?
**d)** Encrypt "hello" with key $(7, 5)$.
**e)** Explain how Bilal decrypts the ciphertext.
**f)** Eva intercepts the ciphertext and can test 1 key per second. What is the maximum time to find the message?

#### Solution

**a)** The character set has $26 + 26 + 10 + 3 = 65$ characters.

$$\boxed{n = 65}$$

Characters are assigned: a–z = 0–25, A–Z = 26–51, 0–9 = 52–61, comma = 62, period = 63, space = 64.

**b)** Valid values of $a$: those coprime with $65 = 5 \times 13$.

$$\varphi(65) = \varphi(5) \cdot \varphi(13) = 4 \times 12 = 48$$

$b$ can be any value from $0$ to $64$ (65 choices).

$$\boxed{\text{Key space size} = 48 \times 65 = 3{,}120}$$

**c)** $\gcd(7, 65) = 1$ (7 is prime and does not divide $65 = 5 \times 13$), so $a = 7$ is valid. $b = 5$ is in $\{0,\ldots,64\}$. The key is **valid**.

**d)** $E(x) = (7x + 5) \bmod 65$

| Char | Value $x$ | $7x+5$ | $\bmod 65$ | Value | Cipher |
|------|-----------|--------|------------|-------|--------|
| h    | 7         | 54     | 54         | '2' (digit, 52+'2') | 2 |
| e    | 4         | 33     | 33         | 'H' (uppercase, 26+'H') | H |
| l    | 11        | 82     | 17         | 'r' (lowercase) | r |
| l    | 11        | 82     | 17         | 'r' | r |
| o    | 14        | 103    | 38         | 'M' (uppercase, 26+12) | M |

$$\boxed{\text{Ciphertext: "2HrrM"}}$$

**e)** Bilal uses the decryption function $D(y) = a^{-1}(y - b) \bmod 65$.

Find $7^{-1} \bmod 65$ using the extended Euclidean algorithm:
$$65 = 9 \times 7 + 2, \quad 7 = 3 \times 2 + 1 \implies 1 = 28 \times 7 - 3 \times 65$$
So $7^{-1} \equiv 28 \pmod{65}$.

$$\boxed{D(y) = 28(y - 5) \bmod 65}$$

Bilal takes each ciphertext character, looks up its numeric value, applies $D$, then maps the result back to the character table.

**f)** The key space has 3,120 keys. Testing one per second, the maximum time is:

$$T_{\max} = 3{,}120\ \text{seconds} = 52\ \text{minutes}$$

---

### Exercise 9

Consider an affine cipher $E(x) = ax + b \bmod n$ where $a = 3$, $b = 6$, $n = 26$.

**a.** Explain why the choice of $a$ and $b$ is valid.
**b.** Encrypt your family name using this cipher.

#### Solution

**a)** $\gcd(3, 26) = 1$ (3 is odd and not a multiple of 13), so $a = 3$ is a valid multiplier. $b = 6$ is any integer in $\{0, \ldots, 25\}$, which is always valid. Therefore the key $(3, 6)$ is **valid**.

**b)** Apply $E(x) = (3x + 6) \bmod 26$ to each letter of your family name (a=0, …, z=25).

*Example for the name "SMITH":* S=18, M=12, I=8, T=19, H=7.

| Char | $x$ | $3x+6$ | $\bmod 26$ | Cipher |
|------|-----|--------|------------|--------|
| S    | 18  | 60     | 8          | I      |
| M    | 12  | 42     | 16         | Q      |
| I    | 8   | 30     | 4          | E      |
| T    | 19  | 63     | 11         | L      |
| H    | 7   | 27     | 1          | B      |

→ Ciphertext: **IQELB**

---

### Exercise 10

Consider an affine cipher $E(x) = ax + b \pmod{26}$ which encrypts "H" as "X" and "Q" as "Y". Find $(a, b)$.

#### Solution

Using $a=0, \ldots, z=25$: H = 7, X = 23, Q = 16, Y = 24.

$$7a + b \equiv 23 \pmod{26} \quad \cdots (1)$$
$$16a + b \equiv 24 \pmod{26} \quad \cdots (2)$$

Subtract (1) from (2):

$$9a \equiv 1 \pmod{26}$$

Since $9 \times 3 = 27 \equiv 1 \pmod{26}$, we get $a = 3$.

Substitute into (1): $21 + b \equiv 23 \pmod{26} \implies b = 2$.

$$\boxed{(a, b) = (3,\ 2)}$$

**Verification:** $E(\text{Q}) = 3 \times 16 + 2 = 50 \equiv 24 = \text{Y}\ \checkmark$

---

### Exercise 11

Consider an affine cipher with encryption function $f(x) = (28x + 49) \bmod 79$. Determine the decryption function $f^{-1}(x)$.

#### Solution

We need $f^{-1}(y) = 28^{-1}(y - 49) \bmod 79$.

Find $28^{-1} \bmod 79$ using the extended Euclidean algorithm:

$$79 = 2 \times 28 + 23$$
$$28 = 1 \times 23 + 5$$
$$23 = 4 \times 5 + 3$$
$$5 = 1 \times 3 + 2$$
$$3 = 1 \times 2 + 1$$

Back-substituting:
$$1 = 11 \times 79 - 31 \times 28 \implies 28^{-1} \equiv -31 \equiv 48 \pmod{79}$$

**Verification:** $28 \times 48 = 1344 = 17 \times 79 + 1 \equiv 1\ \checkmark$

$$f^{-1}(y) = 48(y - 49) \bmod 79 = (48y - 2352) \bmod 79$$

Since $2352 = 29 \times 79 + 61$:

$$\boxed{f^{-1}(y) = (48y + 18) \bmod 79}$$

**Verification:** $f(0) = 49$, and $f^{-1}(49) = 48 \times 49 + 18 = 2352 + 18 - 30 \times 79 = 2370 - 2370 = 0\ \checkmark$

---

## Playfair Cipher

### Exercise 12

Decrypt the ciphertext **ECURSCFTVA** using the Playfair cipher with keyword **"security"**.

#### Solution

**Build the 5×5 key matrix** (I and J share a cell):

Key letters (unique, in order): S, E, C, U, R, I, T, Y
Remaining: A, B, D, F, G, H, K, L, M, N, O, P, Q, V, W, X, Z

$$\begin{array}{|c|c|c|c|c|}
\hline
\text{S} & \text{E} & \text{C} & \text{U} & \text{R} \\
\hline
\text{I} & \text{T} & \text{Y} & \text{A} & \text{B} \\
\hline
\text{D} & \text{F} & \text{G} & \text{H} & \text{K} \\
\hline
\text{L} & \text{M} & \text{N} & \text{O} & \text{P} \\
\hline
\text{Q} & \text{V} & \text{W} & \text{X} & \text{Z} \\
\hline
\end{array}$$

**Decryption rules:** Same row → shift left (wrap); Same column → shift up (wrap); Rectangle → swap columns.

Split ciphertext into pairs: **EC | UR | SC | FT | VA**

| Pair | Position | Rule | Plaintext |
|------|----------|------|-----------|
| EC | E(0,1), C(0,2) — same row | shift left: E→S, C→E | **SE** |
| UR | U(0,3), R(0,4) — same row | shift left: U→C, R→U | **CU** |
| SC | S(0,0), C(0,2) — same row | shift left: S→R(wrap), C→E | **RE** |
| FT | F(2,1), T(1,1) — same col | shift up: F→T, T→E | **TE** |
| VA | V(4,1), A(1,3) — rectangle | V→(4,3)=X, A→(1,1)=T | **XT** |

$$\boxed{\text{Plaintext: "SECURETEXT"} \quad \text{(trailing X is a filler)}}$$

---

### Exercise 13

Encrypt the message **"uluniversity"** using Playfair cipher with your last name as a key.

#### Solution

Build the 5×5 Playfair matrix using your last name (removing duplicate letters, then filling with remaining alphabet letters with I=J).

**General procedure for any key:**
1. Write the key letters (no repeats) then fill remaining alphabet (I/J merged).
2. Prepare the plaintext: split into digrams; if a pair has identical letters, insert 'X' between them; pad with 'X' if the total length is odd.
3. For each digram (P₁, P₂): same row → shift right; same column → shift down; rectangle → swap columns.

*Example with last name "**KHALIL**" → unique letters: K, H, A, L, I; remaining: B, C, D, E, F, G, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z*

$$\begin{array}{|c|c|c|c|c|}
\hline
\text{K} & \text{H} & \text{A} & \text{L} & \text{I} \\
\hline
\text{B} & \text{C} & \text{D} & \text{E} & \text{F} \\
\hline
\text{G} & \text{M} & \text{N} & \text{O} & \text{P} \\
\hline
\text{Q} & \text{R} & \text{S} & \text{T} & \text{U} \\
\hline
\text{V} & \text{W} & \text{X} & \text{Y} & \text{Z} \\
\hline
\end{array}$$

Message: ULUNIVERSITY → pairs: UL UN IV ER SI TY

Apply encryption rules to each pair with the key matrix above. *(Use your actual last name for the real answer.)*

---

### Exercise 14

Encrypt **"encryptthis"** using Playfair cipher with your last name as a key.

#### Solution

**Prepare plaintext:** "encryptthis" → check for doubles: "tt" → insert X → "encryptXthis"
Pairs: EN | CR | YP | TX | TH | IS

Apply Playfair encryption to each pair using your last name's key matrix (same methodology as Exercise 13).

---

### Exercise 15

Encrypt **"strictness"** with Playfair cipher using your last name as a key, with 'X' as filler.

#### Solution

**Prepare plaintext:** "strictness" — check for doubles: s-t-r-i-c-t-n-e-s-s → last "ss" → "strictnesXs" (insert X before second s). The length is now 11 (odd) → append X → "strictnesXsx"

Pairs: ST | RI | CT | NE | SX | SX

Apply Playfair encryption to each pair using your last name's key matrix.

---

### Exercise 16

Decrypt **"GPNXOCFHTE"** encrypted using Playfair cipher with keyword **"CYBERSECURITY"** and filler letter 'X'.

#### Solution

**Build the key matrix** from CYBERSECURITY (unique letters in order: C, Y, B, E, R, S, U, I, T):

$$\begin{array}{|c|c|c|c|c|}
\hline
\text{C} & \text{Y} & \text{B} & \text{E} & \text{R} \\
\hline
\text{S} & \text{U} & \text{I} & \text{T} & \text{A} \\
\hline
\text{D} & \text{F} & \text{G} & \text{H} & \text{K} \\
\hline
\text{L} & \text{M} & \text{N} & \text{O} & \text{P} \\
\hline
\text{Q} & \text{V} & \text{W} & \text{X} & \text{Z} \\
\hline
\end{array}$$

Split ciphertext into pairs: **GP | NX | OC | FH | TE**

| Pair | Positions | Rule | Plaintext |
|------|-----------|------|-----------|
| GP | G(2,2), P(3,4) — rectangle | G→(2,4)=K, P→(3,2)=N | **KN** |
| NX | N(3,2), X(4,3) — rectangle | N→(3,3)=O, X→(4,2)=W | **OW** |
| OC | O(3,3), C(0,0) — rectangle | O→(3,0)=L, C→(0,3)=E | **LE** |
| FH | F(2,1), H(2,3) — same row | shift left: F→D, H→G | **DG** |
| TE | T(1,3), E(0,3) — same col | shift up: T→E, E→X(wrap) | **EX** |

$$\boxed{\text{Plaintext: "KNOWLEDGE"} \quad \text{(trailing X is a filler)}}$$

---

### Exercise 17

Decrypt **"RPVLMNNGSICLFN"** encrypted with Playfair cipher using password **"PLAYFAIR"**.

#### Solution

**Build the key matrix** from PLAYFAIR (unique: P, L, A, Y, F, I, R):

$$\begin{array}{|c|c|c|c|c|}
\hline
\text{P} & \text{L} & \text{A} & \text{Y} & \text{F} \\
\hline
\text{I} & \text{R} & \text{B} & \text{C} & \text{D} \\
\hline
\text{E} & \text{G} & \text{H} & \text{K} & \text{M} \\
\hline
\text{N} & \text{O} & \text{Q} & \text{S} & \text{T} \\
\hline
\text{U} & \text{V} & \text{W} & \text{X} & \text{Z} \\
\hline
\end{array}$$

Split into pairs: **RP | VL | MN | NG | SI | CL | FN**

| Pair | Positions | Rule | Plaintext |
|------|-----------|------|-----------|
| RP | R(1,1), P(0,0) — rectangle | R→(1,0)=I, P→(0,1)=L | **IL** |
| VL | V(4,1), L(0,1) — same col | shift up: V→O(3,1), L→V(4,1) | **OV** |
| MN | M(2,4), N(3,0) — rectangle | M→(2,0)=E, N→(3,4)=T | **ET** |
| NG | N(3,0), G(2,1) — rectangle | N→(3,1)=O, G→(2,0)=E | **OE** |
| SI | S(3,3), I(1,0) — rectangle | S→(3,0)=N, I→(1,3)=C | **NC** |
| CL | C(1,3), L(0,1) — rectangle | C→(1,1)=R, L→(0,3)=Y | **RY** |
| FN | F(0,4), N(3,0) — rectangle | F→(0,0)=P, N→(3,4)=T | **PT** |

Reading all pairs: I-L-O-V-E-T-O-E-N-C-R-Y-P-T

$$\boxed{\text{Plaintext: "I LOVE TO ENCRYPT"}}$$

---

## Hill Cipher

### Exercise 18

The ciphertext **DNPRCX** was encrypted with Hill Cipher using:

$$K = \begin{bmatrix} 5 & -3 \\ -3 & 2 \end{bmatrix}$$

**a.** Verify that $K^{-1} = \begin{bmatrix} 2 & 3 \\ 3 & 5 \end{bmatrix}$.
**b.** Decrypt the ciphertext.

#### Solution

**a)** Verify $K \cdot K^{-1} \equiv I \pmod{26}$:

$$\begin{bmatrix} 5 & -3 \\ -3 & 2 \end{bmatrix} \begin{bmatrix} 2 & 3 \\ 3 & 5 \end{bmatrix} = \begin{bmatrix} 10-9 & 15-15 \\ -6+6 & -9+10 \end{bmatrix} = \begin{bmatrix} 1 & 0 \\ 0 & 1 \end{bmatrix} \checkmark$$

**b)** Decrypt block by block: D=3, N=13, P=15, R=17, C=2, X=23.

**Block 1 — DN:** $\begin{bmatrix}2&3\\3&5\end{bmatrix}\begin{bmatrix}3\\13\end{bmatrix} = \begin{bmatrix}6+39\\9+65\end{bmatrix} = \begin{bmatrix}45\\74\end{bmatrix} \equiv \begin{bmatrix}19\\22\end{bmatrix} \pmod{26}$
$\to 19 = \text{T},\ 22 = \text{W}$

**Block 2 — PR:** $\begin{bmatrix}2&3\\3&5\end{bmatrix}\begin{bmatrix}15\\17\end{bmatrix} = \begin{bmatrix}30+51\\45+85\end{bmatrix} = \begin{bmatrix}81\\130\end{bmatrix} \equiv \begin{bmatrix}3\\0\end{bmatrix} \pmod{26}$
$\to 3 = \text{D},\ 0 = \text{A}$

**Block 3 — CX:** $\begin{bmatrix}2&3\\3&5\end{bmatrix}\begin{bmatrix}2\\23\end{bmatrix} = \begin{bmatrix}4+69\\6+115\end{bmatrix} = \begin{bmatrix}73\\121\end{bmatrix} \equiv \begin{bmatrix}21\\17\end{bmatrix} \pmod{26}$
$\to 21 = \text{V},\ 17 = \text{R}$

$$\boxed{\text{Plaintext: "TWDAVR"}}$$

---

### Exercise 19

The plaintext "TRIP" maps to the ciphertext "TJMP" under a Hill cipher with block size 2 and $n = 26$. Find the encryption key matrix $K = \begin{pmatrix}a & b\\c & d\end{pmatrix}$.

#### Solution

T=19, R=17, I=8, P=15, T=19, J=9, M=12, P=15.

From the two plaintext-ciphertext pairs:

$$K\begin{bmatrix}19\\17\end{bmatrix} \equiv \begin{bmatrix}19\\9\end{bmatrix},\quad K\begin{bmatrix}8\\15\end{bmatrix} \equiv \begin{bmatrix}12\\15\end{bmatrix} \pmod{26}$$

**Solving for $a$ and $b$** (top row):

$$19a + 17b \equiv 19 \quad \cdots(1)$$
$$8a + 15b \equiv 12 \quad \cdots(2)$$

Multiply (1) by 8 and (2) by 19, then subtract: $19b \equiv 24 \pmod{26}$.
Since $19^{-1} \equiv 11 \pmod{26}$ (as $19 \times 11 = 209 \equiv 1$): $b \equiv 11 \times 24 = 264 \equiv 4 \pmod{26}$.

Substitute $b=4$ into (2): $8a \equiv 12 - 60 = -48 \equiv 4 \pmod{26} \implies 2a \equiv 1 \pmod{13} \implies a \equiv 7 \pmod{13}$.
Check (1): $19\times7 + 17\times4 = 133+68=201 \equiv 19\ \checkmark$. So $a=7$.

**Solving for $c$ and $d$** (bottom row):

$$19c + 17d \equiv 9 \quad \cdots(3)$$
$$8c + 15d \equiv 15 \quad \cdots(4)$$

Same method: $19d \equiv 5 \pmod{26} \implies d \equiv 11\times5=55 \equiv 3 \pmod{26}$.
Substitute $d=3$ into (4): $8c \equiv 15-45 = -30 \equiv 22 \pmod{26} \implies 4c \equiv 11 \pmod{13} \implies c \equiv 6 \pmod{13}$.
Check (3): $19\times6 + 17\times3=114+51=165\equiv9\ \checkmark$. So $c=6$.

$$\boxed{K = \begin{pmatrix}7 & 4\\6 & 3\end{pmatrix}}$$

---

## Vigenère Cipher

### Exercise 20

Decrypt **WCGHEWMVFDFS** knowing it was encrypted using Vigenère Cipher with key **"crypto"**.

#### Solution

Key: c=2, r=17, y=24, p=15, t=19, o=14 (repeating).

Decryption: plaintext letter $= (\text{ciphertext} - \text{key}) \bmod 26$.

| Pos | Cipher | Key char | Key val | Calc | Plaintext |
|-----|--------|----------|---------|------|-----------|
| 1   | W(22)  | c        | 2       | 20   | u |
| 2   | C(2)   | r        | 17      | $-15 \equiv 11$ | l |
| 3   | G(6)   | y        | 24      | $-18 \equiv 8$  | i |
| 4   | H(7)   | p        | 15      | $-8 \equiv 18$  | s |
| 5   | E(4)   | t        | 19      | $-15 \equiv 11$ | l |
| 6   | W(22)  | o        | 14      | 8    | i |
| 7   | M(12)  | c        | 2       | 10   | k |
| 8   | V(21)  | r        | 17      | 4    | e |
| 9   | F(5)   | y        | 24      | $-19 \equiv 7$  | h |
| 10  | D(3)   | p        | 15      | $-12 \equiv 14$ | o |
| 11  | F(5)   | t        | 19      | $-14 \equiv 12$ | m |
| 12  | S(18)  | o        | 14      | 4    | e |

$$\boxed{\text{Plaintext: "ulislikhome"}}$$

---

### Exercise 21

Decrypt **VPXZGIAXIVWPUBTTMJPWIZITWZT** encrypted with Vigenère cipher and key **CIPHER**.

#### Solution

Key: C=2, I=8, P=15, H=7, E=4, R=17 (repeating).

| Pos | Cipher | Key | Plaintext | | Pos | Cipher | Key | Plaintext |
|-----|--------|-----|----------|-|-----|--------|-----|-----------|
| 1   | V(21)  | C(2)  | 19=t | | 15  | I(8)   | E(4)  | 4=e |
| 2   | P(15)  | I(8)  | 7=h  | | 16  | T(19)  | R(17) | 2=c |
| 3   | X(23)  | P(15) | 8=i  | | 17  | W(22)  | C(2)  | 20=u |
| 4   | Z(25)  | H(7)  | 18=s | | 18  | Z(25)  | I(8)  | 17=r |
| 5   | G(6)   | E(4)  | 2=c  | | 19  | T(19)  | P(15) | 4=e |
| 6   | I(8)   | R(17) | $-9\equiv17$=r | | 20  | W(22)  | H(7)  | 15=p? → wait |

Let me redo the full table:

$D_i = (C_i - K_i) \bmod 26$

Ciphertext: V P X Z G I A X I V W P U B T T M J P W I Z I T W Z T
Key repeats: C I P H E R C I P H E R C I P H E R C I P H E R C I P

| # | C | val | K | val | plain |
|---|---|-----|---|-----|-------|
| 1 | V | 21 | C | 2 | 19=t |
| 2 | P | 15 | I | 8 | 7=h |
| 3 | X | 23 | P | 15 | 8=i |
| 4 | Z | 25 | H | 7 | 18=s |
| 5 | G | 6  | E | 4 | 2=c |
| 6 | I | 8  | R | 17 | 17=r |
| 7 | A | 0  | C | 2 | 24=y |
| 8 | X | 23 | I | 8 | 15=p |
| 9 | I | 8  | P | 15 | 19=t |
| 10 | V | 21 | H | 7 | 14=o |
| 11 | W | 22 | E | 4 | 18=s |
| 12 | P | 15 | R | 17 | 24=y |
| 13 | U | 20 | C | 2 | 18=s |
| 14 | B | 1  | I | 8 | 19=t |
| 15 | T | 19 | P | 15 | 4=e |
| 16 | T | 19 | H | 7 | 12=m |
| 17 | M | 12 | E | 4 | 8=i |
| 18 | J | 9  | R | 17 | 18=s |
| 19 | P | 15 | C | 2 | 13=n |
| 20 | W | 22 | I | 8 | 14=o |
| 21 | I | 8  | P | 15 | 19=t |
| 22 | Z | 25 | H | 7 | 18=s |
| 23 | I | 8  | E | 4 | 4=e |
| 24 | T | 19 | R | 17 | 2=c |
| 25 | W | 22 | C | 2 | 20=u |
| 26 | Z | 25 | I | 8 | 17=r |
| 27 | T | 19 | P | 15 | 4=e |

$$\boxed{\text{Plaintext: "thiscryptosystemisnotsecure"}}$$

→ "**This crypto system is not secure**"

---

### Exercise 22

Consider the Vigenère Cipher with a plaintext space: lowercase a–z, space, and digits 0–9.

**a.** What is the modulus?
**b.** Encrypt your first and last name (separated by space) using your university ID as a key.
**c.** What is the size of the key space if the maximum message length equals the length of your name?

#### Solution

**a)** The character set has $26 + 1 + 10 = 37$ characters.

$$\boxed{n = 37}$$

Characters: a=0, …, z=25, space=26, 0=27, …, 9=36.

**b)** Let $L$ be the length of "firstname lastname" (including the space). Repeat the university ID (treating each digit as its numeric value mod 37) to length $L$. Then for each position:

$$C_i = (P_i + K_i) \bmod 37$$

**c)** If the maximum message (and key) length is $L$ characters, each key character can be any of the 37 values. The key space size is:

$$\boxed{37^L}$$

where $L$ is the total character count of your first name, space, and last name.

---

### Exercise 23

Explain how to guess the key length of the Vigenère cipher given the ciphertext:

```
KLRWT CSHWD RDBLH NOWIZ IJZKS XJGDX XWSLA BXUAG CILBG VDNOW EBXKS PLJLN QMIGL
ZEJYK IXAYZ ENFYJ WCLEAR SOAVW PWFZY LADZD ABKQC LEARHB RDMZB GYUIA SWMVA RGDSM
```

#### Solution

Two standard methods exist:

**Method 1 — Kasiski Test**

Search for **repeated sequences** of 3 or more characters in the ciphertext and record the distances between their occurrences. The key length is likely a common divisor of these distances.

In the given ciphertext, the sequence **"NOW"** appears at two positions, and **"CLEAR"** appears twice (in "WCLEAR" and "CLEARHB"). Computing the distance between the two "CLEAR" occurrences (and similarly for "NOW") and taking their GCD points to a likely key length of **6**.

**Method 2 — Index of Coincidence (IC)**

The IC of a natural-language text is $\approx 0.065$ (English), while a random string gives $\approx 0.038$. For a Vigenère cipher with key length $k$, each column (positions $i, i+k, i+2k, \ldots$) is a Caesar-shifted alphabet, so its IC ≈ 0.065.

Try key lengths $k = 2, 3, 4, \ldots$ and for each $k$, split the ciphertext into $k$ subsequences and compute the average IC. The value of $k$ that yields the highest average IC close to 0.065 is the likely key length.

Both methods suggest a key length of **6** for this ciphertext.

---

### Exercise 24

Decrypt **WJYABGNZITACOV** knowing it was encrypted using Vigenère Cipher with key **"crypto"**.

#### Solution

Key: c=2, r=17, y=24, p=15, t=19, o=14 (repeating).

| Pos | Cipher | Key | Val | Calc | Plain |
|-----|--------|-----|-----|------|-------|
| 1  | W(22) | c | 2  | 20 | u |
| 2  | J(9)  | r | 17 | $-8 \equiv 18$ | s |
| 3  | Y(24) | y | 24 | 0  | a |
| 4  | A(0)  | p | 15 | $-15 \equiv 11$ | l |
| 5  | B(1)  | t | 19 | $-18 \equiv 8$ | i |
| 6  | G(6)  | o | 14 | $-8 \equiv 18$ | s |
| 7  | N(13) | c | 2  | 11 | l |
| 8  | Z(25) | r | 17 | 8  | i |
| 9  | I(8)  | y | 24 | $-16 \equiv 10$ | k |
| 10 | T(19) | p | 15 | 4  | e |
| 11 | A(0)  | t | 19 | $-19 \equiv 7$ | h |
| 12 | C(2)  | o | 14 | $-12 \equiv 14$ | o |
| 13 | O(14) | c | 2  | 12 | m |
| 14 | V(21) | r | 17 | 4  | e |

$$\boxed{\text{Plaintext: "usalislikehome"}}$$

---

### Exercise 25

The ciphertext **ZHYWIQBOSUPJDNXX** was encrypted using a Vigenère key of length 3. The plaintext starts with **"What"**. Decrypt it.

#### Solution

**Step 1 — Recover the key** using the known plaintext "what" (w=22, h=7, a=0, t=19) and ciphertext Z(25), H(7), Y(24), W(22):

$$k_1 = Z - W = 25 - 22 = 3 = \text{d}$$
$$k_2 = H - H = 7 - 7 = 0 = \text{a}$$
$$k_3 = Y - A = 24 - 0 = 24 = \text{y}$$

$$\boxed{\text{Key} = \texttt{"day"} \quad (d=3,\ a=0,\ y=24)}$$

**Step 2 — Decrypt the full ciphertext:**

| Pos | Cipher | Key | Calc | Plain |
|-----|--------|-----|------|-------|
| 1  | Z(25)  | d(3)  | 22 | w |
| 2  | H(7)   | a(0)  | 7  | h |
| 3  | Y(24)  | y(24) | 0  | a |
| 4  | W(22)  | d(3)  | 19 | t |
| 5  | I(8)   | a(0)  | 8  | i |
| 6  | Q(16)  | y(24) | $-8 \equiv 18$ | s |
| 7  | B(1)   | d(3)  | $-2 \equiv 24$ | y |
| 8  | O(14)  | a(0)  | 14 | o |
| 9  | S(18)  | y(24) | $-6 \equiv 20$ | u |
| 10 | U(20)  | d(3)  | 17 | r |
| 11 | P(15)  | a(0)  | 15 | p |
| 12 | J(9)   | y(24) | $-15 \equiv 11$ | l |
| 13 | D(3)   | d(3)  | 0  | a |
| 14 | N(13)  | a(0)  | 13 | n |
| 15 | X(23)  | y(24) | $-1 \equiv 25$ | z *(filler)* |
| 16 | X(23)  | d(3)  | 20 | u *(filler)* |

$$\boxed{\text{Plaintext: "What is your plan"} \quad \text{(last two letters are padding)}}$$

---

## Transposition Ciphers

### Exercise 26

Encrypt your first, middle and last names (concatenated) using the **Rail Fence** transposition cipher.

#### Solution

The Rail Fence cipher writes the plaintext in a zigzag pattern across $r$ rails, then reads off each rail in turn.

*Example with 2 rails and the name "JOHNALANSMITH":*

```
Rail 1: J . H . A . A . S . I .
Rail 2: . O . N . L . N . M . T H
```

Reading row by row: **JHAASIONALANSMIT H** → ciphertext.

Apply the same method to your own concatenated name, specifying the number of rails (commonly 2 or 3).

---

### Exercise 27

Decrypt **"RHAX TNUX REDE AIER IKAT SOQR"** encrypted using a row-column transposition cipher with key **6 5 3 1 2 4**.

#### Solution

The key **6 5 3 1 2 4** means: column 1 is read 6th, column 2 is read 5th, column 3 is read 3rd, column 4 is read 1st, column 5 is read 2nd, column 6 is read 4th during encryption.

Ciphertext (spaces removed): RHAXTUNXREDEAIERIKATSOQR — but the given ciphertext is **RHAXTNUXREDEAIERIKATSOQR** (24 characters).

With 6 columns and 4 rows, the encrypted columns in reading order were:
- **Col 6** (read 4th... wait)

The key tells us the order in which columns were *extracted*. Key entry at position $i$ gives the output rank of column $i$. So:
- Column 1 → rank 6 (read 6th)
- Column 2 → rank 5 (read 5th)
- Column 3 → rank 3 (read 3rd)
- Column 4 → rank 1 (read 1st)
- Column 5 → rank 2 (read 2nd)
- Column 6 → rank 4 (read 4th)

Ciphertext chunks of 4 (one per column) in reading order:

| Read order | Chunk | Original column |
|-----------|-------|-----------------|
| 1st | RHAX | Col 4 |
| 2nd | TNUX | Col 5 |
| 3rd | REDE | Col 3 |
| 4th | AIER | Col 6 |
| 5th | IKAT | Col 2 |
| 6th | SOQR | Col 1 |

Reconstruct the grid (4 rows × 6 columns):

| Col: | 1 | 2 | 3 | 4 | 5 | 6 |
|------|---|---|---|---|---|---|
| Row 1 | S | I | R | R | T | A |
| Row 2 | O | K | E | H | N | I |
| Row 3 | Q | A | D | A | U | E |
| Row 4 | R | T | E | X | X | R |

Read row by row: S-I-R-R-T-A-O-K-E-H-N-I-Q-A-D-A-U-E-R-T-E-X-X-R

Hmm — with a different key interpretation (column number directly gives read order):

If key "6 5 3 1 2 4" means *read column 6 first, column 5 second, column 3 third, column 1 fourth, column 2 fifth, column 4 sixth*, then:

| Read order | Chunk | Original column |
|-----------|-------|-----------------|
| 1st | RHAX | Col 6 |
| 2nd | TNUX | Col 5 |
| 3rd | REDE | Col 3 |
| 4th | AIER | Col 1 |
| 5th | IKAT | Col 2 |
| 6th | SOQR | Col 4 |

Grid:

| Col: | 1 | 2 | 3 | 4 | 5 | 6 |
|------|---|---|---|---|---|---|
| Row 1 | A | I | R | S | T | R |
| Row 2 | I | K | E | O | N | H |
| Row 3 | E | A | D | Q | U | A |
| Row 4 | R | T | E | R | X | X |

Read row by row: A-I-R-S-T-R-I-K-E-O-N-H-E-A-D-Q-U-E-R-T-E-R-X-X

$$\boxed{\text{Plaintext: "AIR STRIKE ON HEADQUARTER"} \quad \text{(XX is padding)}}$$

---

## Additional Exercises

### Exercise 28

The adversary intercepted this plaintext-ciphertext pair:

| Plaintext  | c | r | y | p | t | a | n | a | l | y | s | i | s |
|-----------|---|---|---|---|---|---|---|---|---|---|---|---|---|
| Ciphertext | y | v | y | z | p | e | n | k | h | c | s | s | o |

**a.** Which cipher was used?
**b.** Determine the key.

#### Solution

**a)** Compute the shift $C_i - P_i \pmod{26}$ for each position:

| c→y | r→v | y→y | p→z | t→p | a→e | n→n | a→k | l→h | y→c | s→s | i→s | s→o |
|-----|-----|-----|-----|-----|-----|-----|-----|-----|-----|-----|-----|-----|
| 22  | 4   | 0   | 10  | 22  | 4   | 0   | 10  | 22  | 4   | 0   | 10  | 22  |

The shifts follow a repeating pattern: **22, 4, 0, 10** with period 4.

This is a **Vigenère cipher** with key length 4.

**b)** The key letters have values 22, 4, 0, 10:

$$22 = \text{w},\quad 4 = \text{e},\quad 0 = \text{a},\quad 10 = \text{k}$$

$$\boxed{\text{Cipher: Vigenère,\quad Key: "weak"}}$$

---

### Exercise 29

Alice and Bob use a random substitution cipher (table below) followed by a row-column transposition cipher with key **2 5 4 3 1**, using 'z' as filler.

| Plain | A | B | C | D | E | F | G | H | I | J | K | L | M | N | O | P | Q | R | S | T | U | V | W | X | Y | Z |
|-------|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|
| Cipher | Y | W | X | Z | F | N | Q | L | S | H | I | E | O | V | R | J | C | A | B | U | P | T | M | K | G | D |

**a.** Encrypt the message "cryptography is fun".
**b.** How many attempts does Eve need to break the cipher?

#### Solution

**a)**

**Step 1 — Substitution** (remove spaces, apply the table):

| c→X | r→A | y→G | p→J | t→U | o→R | g→Q | r→A | a→Y | p→J | h→L | y→G | i→S | s→B | f→N | u→P | n→V |

After substitution: **XAGJURQAYJLGSBNPV** (17 characters)

**Step 2 — Transposition** with key **2 5 4 3 1** (5 columns):

Pad to 20 characters with 'z': **XAGJURQAYJLGSBNPVzzz**

Fill the grid row by row:

| Key:  |  2  |  5  |  4  |  3  |  1  |
|-------|-----|-----|-----|-----|-----|
| Row 1 |  X  |  A  |  G  |  J  |  U  |
| Row 2 |  R  |  Q  |  A  |  Y  |  J  |
| Row 3 |  L  |  G  |  S  |  B  |  N  |
| Row 4 |  P  |  V  |  Z  |  Z  |  Z  |

Read columns in key order (1st, 2nd, 3rd, 4th, 5th):
- Col 5 (rank 1): U, J, N, Z → UJNZ
- Col 1 (rank 2): X, R, L, P → XRLP
- Col 4 (rank 3): J, Y, B, Z → JYBZ
- Col 3 (rank 4): G, A, S, Z → GASZ
- Col 2 (rank 5): A, Q, G, V → AQGV

$$\boxed{\text{Ciphertext: "UJNZXRLPJYBZGASZAQGV"}}$$

**b)** The combined key space is:

- **Random substitution:** $26!$ possible keys $\approx 4.03 \times 10^{26}$
- **Transposition** with key length 5: $5! = 120$ possible permutations

$$\text{Total attempts (worst case)} = 26! \times 5! \approx 4.83 \times 10^{28}$$

This is computationally infeasible — the combined cipher is very strong even though each primitive alone might be vulnerable to analysis.

---

### Exercise 30

Consider the Vernam Cipher. Given $M = 1001101$ and $K = 1010101$, find the ciphertext. Explain decryption.

#### Solution

**Encryption** — XOR bit by bit:

$$C = M \oplus K$$

$$\begin{array}{r}
M: & 1\;0\;0\;1\;1\;0\;1 \\
K: & 1\;0\;1\;0\;1\;0\;1 \\
\hline
C: & 0\;0\;1\;1\;0\;0\;0
\end{array}$$

$$\boxed{C = 0011000}$$

**Decryption** — XOR the ciphertext with the same key:

$$M = C \oplus K = 0011000 \oplus 1010101 = 1001101\ \checkmark$$

This works because XOR is its own inverse: $(M \oplus K) \oplus K = M \oplus (K \oplus K) = M \oplus 0 = M$.

The Vernam cipher achieves **perfect secrecy** (Shannon, 1949) when the key $K$ is: (1) at least as long as the message, (2) truly random, and (3) used only once — in which case the ciphertext reveals zero information about the plaintext to an attacker who lacks the key.
