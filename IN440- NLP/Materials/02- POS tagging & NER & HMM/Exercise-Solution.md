# POS Tagging with Hidden Markov Models
```mermaid
flowchart LR
    classDef win   fill:#E1F5EEE,stroke:#1D9E75,stroke-width:2px,color:#085041
    classDef dead  fill:#F1EFE8,stroke:#B4B2A9,color:#888780
    classDef final fill:#EEEDFE,stroke:#534AB7,stroke-width:2px,color:#26215C

    subgraph t1["t = 1 · 'the'"]
        DT1["DT = 0.25"]
        NN1["NN = 0"]
        VB1["VB = 0"]
    end

    subgraph t2["t = 2 · 'cat'"]
        DT2["DT = 0"]
        NN2["NN ≈ 0.042"]
        VB2["VB = 0"]
    end

    subgraph t3["t = 3 · 'runs'"]
        DT3["DT = 0"]
        NN3["NN = 0"]
        VB3["VB ≈ 0.021"]
    end

    DT1 -- "A[DT→NN]=1 · B[NN|cat]=1/6" --> NN2
    NN2 -- "A[NN→VB]=1 · B[VB|runs]=0.5" --> VB3

    class DT1,NN2 win
    class VB3 final
    class NN1,VB1,DT2,VB2,DT3,NN3 dead
```

Given the HMM you computed in Part 1, use the Viterbi algorithm to find the most likely tag sequence for the following sentence:

$$
\text{the cat runs}
$$

## Solution

### Part 1

1. As we can see from the corpus, the possible tags are NN, VB, and DT, so:
**States (Q)**: $Q = \{NN, VB, DT\}$

2. All unique words in the corpus form the following vocabulary
**Vocabulary (V)**: $V = \{\text{the}, \text{dog}, \text{runs}, \text{lion}, \text{is}, \text{an}, \text{animal}, \text{cat}\}$

3. **Prior Probabilities ($\pi$)**:
   - $\pi(DT) = 0.5$*, since 2 out of 4 sentences start with a determiner (the).*
   - $\pi(NN) = 0.5$*, since 2 out of 4 sentences start with a noun.*
   - $\pi(VB) = 0.0$*, since no sentence starts with a verb.*

4. **Transition Matrix (A)**:

| From \ To | NN | VB | DT |
|-----------|----|----|-----|
| **DT** | 4/4 = 1 | 0 | 0 |
| **NN** | 0 | 4/4 = 1 | 0 |
| **VB** | 0 | 0 | 2/2 = 1 |

5. **Emission Matrix (B)**:

| Tag \ Word | the | dog | runs | lion | is | an | animal | cat |
|------------|-----|-----|------|------|----|----|--------|-----|
| **DT** | 2/4 = 0.5 | 0 | 0 | 0 | 0 | 2/4 = 0.5 | 0 | 0 |
| **NN** | 0 | 2/6 = 1/3 | 0 | 1/6 | 0 | 0 | 2/6 = 1/3 | 1/6 |
| **VB** | 0 | 0 | 2/4 = 0.5 | 0 | 2/4 = 0.5 | 0 | 0 | 0 |

### Part 2

We apply the **Viterbi algorithm** to find the most likely tag sequence for: **"the cat runs"**

#### Diagram

```mermaid
flowchart LR
    classDef active fill:#E8F7EE,stroke:#168A52,stroke-width:2px,color:#06402B
    classDef inactive fill:#F4F4F4,stroke:#BBBBBB,color:#777777
    classDef result fill:#EEF0FF,stroke:#4F46E5,stroke-width:2px,color:#1E1B4B

    Start((Start))

    subgraph T1["t = 1<br/>word = the"]
        DT1["DT<br/>0.25"]
        NN1["NN<br/>0"]
        VB1["VB<br/>0"]
    end

    subgraph T2["t = 2<br/>word = cat"]
        DT2["DT<br/>0"]
        NN2["NN<br/>1/24 ≈ 0.042"]
        VB2["VB<br/>0"]
    end

    subgraph T3["t = 3<br/>word = runs"]
        DT3["DT<br/>0"]
        NN3["NN<br/>0"]
        VB3["VB<br/>1/48 ≈ 0.021"]
    end

    End((Best path))

    Start --> DT1
    Start -.-> NN1
    Start -.-> VB1

    DT1 --> NN2
    NN2 --> VB3
    VB3 --> End

    DT1 -.-> DT2
    DT1 -.-> VB2
    NN1 -.-> DT2
    NN1 -.-> NN2
    NN1 -.-> VB2
    VB1 -.-> DT2
    VB1 -.-> NN2
    VB1 -.-> VB2

    DT2 -.-> DT3
    DT2 -.-> NN3
    DT2 -.-> VB3
    NN2 -.-> DT3
    NN2 -.-> NN3
    VB2 -.-> DT3
    VB2 -.-> NN3
    VB2 -.-> VB3

    class DT1,NN2 active
    class VB3 result
    class NN1,VB1,DT2,VB2,DT3,NN3 inactive
```

#### Step-by-Step Computation

**Initialization** $(t = 1,\ \text{word} = \text{"the"})$:

$$v(DT, 1) = \pi(DT) \times B[DT \mid \text{the}] = 0.5 \times 0.5 = \mathbf{0.25}$$
$$v(NN, 1) = \pi(NN) \times B[NN \mid \text{the}] = 0.5 \times 0 = 0$$
$$v(VB, 1) = \pi(VB) \times B[VB \mid \text{the}] = 0 \times 0 = 0$$

**Recursion** $(t = 2,\ \text{word} = \text{"cat"})$:

$$v(DT, 2) = 0 \quad \because B[DT \mid \text{cat}] = 0$$
$$v(NN, 2) = v(DT,1) \times A[DT \to NN] \times B[NN \mid \text{cat}] = 0.25 \times 1 \times \tfrac{1}{6} = \mathbf{\tfrac{1}{24} \approx 0.042}$$
$$v(VB, 2) = 0 \quad \because B[VB \mid \text{cat}] = 0$$

**Recursion** $(t = 3,\ \text{word} = \text{"runs"})$:

$$v(DT, 3) = 0 \quad \because B[DT \mid \text{runs}] = 0$$
$$v(NN, 3) = 0 \quad \because B[NN \mid \text{runs}] = 0$$
$$v(VB, 3) = v(NN,2) \times A[NN \to VB] \times B[VB \mid \text{runs}] = \tfrac{1}{24} \times 1 \times 0.5 = \mathbf{\tfrac{1}{48} \approx 0.021}$$

**Backtracking**:

| $t$ | Winning state | Came from |
|-----|--------------|-----------|
| 3   | VB           | NN        |
| 2   | NN           | DT        |
| 1   | DT           | —         |

#### Result

$$\boxed{\text{the}/DT \quad \text{cat}/NN \quad \text{runs}/VB}$$