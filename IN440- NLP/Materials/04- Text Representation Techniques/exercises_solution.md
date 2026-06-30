# Chapter 4: Text Representation Techniques- Exercises

## Questions

### Exercise 1: Frequency-Based Vectorization Techniques in NLP

In this exercise, you will apply different frequency-based vectorization techniques commonly used in Natural Language Processing (NLP). You are given a small corpus consisting of four documents. 

#### Given Corpus 

- D1: cats like milk 
- D2: dogs like bones 
- D3: cats and dogs play 
- D4: milk and bones are food 

#### Tasks 

Using the given corpus, build the following document-term matrices manually. 

1. Bag of Words (BoW) 

Construct the Document-Term Matrix using the Bag of Words approach. 
- Identify the vocabulary of the corpus. 
- Create the document-term matrix. 
- Use raw term frequencies. 

2. Bag of N-grams (n = 2) 

Construct the Document-Term Matrix using bigrams (2-grams). 
- Extract all unique bigrams from the corpus. 
- Build the document-bigram matrix. 
- Use raw frequencies. 

3. TF-IDF Vectorization 

Construct the TF-IDF matrix for the corpus. 
- Compute the Term Frequency (TF) for each term. 
- Compute the Inverse Document Frequency (IDF). 
- Compute the TF-IDF weights. 
- Present the final TF-IDF matrix. 

### Exercise 2: Preparing Training Data for Word2Vec 

- Using the same corpus from exercise 1, prepare the training samples required for 
training Word2Vec models using both Skip-gram and CBOW architectures. 
- Assume a context window size of 1. 
- For the Skip-gram model, generate all training pairs. 
- For the CBOW model, generate all training pairs.

---

## Solution

### Exercise 1

#### 1. Bag of Words (BoW)

**Vocabulary of the corpus:**

$$
V = \{cats, like, milk, dogs, bones, and, play, are, food\}
$$

**document-term matrix (raw term frequencies):**

| Document | cats | like | milk | dogs | bones | and | play | are | food |
|----------|------|------|------|------|-------|-----|------|-----|-----|
| D1       | 1    | 1    | 1    | 0    | 0     | 0   | 0    | 0   | 0   |
| D2       | 0    | 1    | 0    | 1    | 1     | 0   | 0    | 0   | 0   |
| D3       | 1    | 0    | 0    | 1    | 0     | 1   | 1    | 0   | 0   |
| D4       | 0    | 0    | 1    | 0    | 1     | 1   | 0    | 1   | 1

#### 2. Bag of N-grams (n = 2)

**Vocabulary of the corpus:**

$$
V = \{\text{cats like}, \text{like milk}, \text{dogs like}, \text{like bones}, \text{cats and}, \text{and dogs}, \text{dogs play}, \text{milk and}, \text{and bones}, \text{bones are}, \text{are food}\}
$$

**document-bigram matrix (raw frequencies):**

| - | cats like | like milk | dogs like | like bones | cats and | and dogs | dogs play | milk and | and bones | bones are | are food |
|----------|------------|------------|------------|------------|-----------|-----------|-----------|-----------|------------|------------|----------|
| D1       | 1    | 1    | 0    | 0    | 0     | 0   | 0    | 0   | 0   | 0   | 0   |
| D2       | 0    | 0    | 1    | 1    | 0     | 0   | 0    | 0   | 0   | 0   | 0   |
| D3       | 0    | 0    | 0    | 0    | 1     | 1   | 1    | 0   | 0   | 0   | 0   |
| D4       | 0    | 0    | 0    | 0    | 0     | 0   | 0    | 1   | 1   | 1   | 1   |

#### 3. TF-IDF Vectorization

**Step 1: Compute Term Frequency (TF)**

$$
TF(t,d) = \frac{\text{count of term } t \text{ in document } d}{\text{total number of terms in document } d}
$$

| Document | cats | like | milk | dogs | bones | and | play | are | food |
|----------|------|------|------|------|-------|-----|------|-----|-----|
| D1       | 0.33 | 0.33 | 0.33 | 0    | 0     | 0   | 0    | 0   | 0   |
| D2       | 0    | 0.33 | 0    | 0.33 | 0.33  | 0   | 0    | 0   | 0   |
| D3       | 0.25 | 0    | 0    | 0.25 | 0     | 0.25| 0.25 | 0   | 0   |
| D4       | 0    | 0    | 0.2  | 0    | 0.2   | 0.2 | 0    | 0.2 | 0.2 |

**Step 2: Compute Inverse Document Frequency (IDF)**

$$
IDF(t) = \log_{10} \left( \frac{N}{\text{number of documents containing term } t} \right)
\\
\text{where } N \text{ is the total number of documents}
$$

| Term  | df |   IDF |
| ----- | -: | ----: |
| cats  |  2 | 0.301 |
| like  |  2 | 0.301 |
| milk  |  2 | 0.301 |
| dogs  |  2 | 0.301 |
| bones |  2 | 0.301 |
| and   |  2 | 0.301 |
| play  |  1 | 0.602 |
| are   |  1 | 0.602 |
| food  |  1 | 0.602 |


**Step 3: Compute TF-IDF weights**

$$
TF\text{-}IDF(t,d) = TF(t,d) \times IDF(t)
$$

| Document |  cats |  like |  milk |  dogs | bones |   and |  play |   are |  food |
| -------- | ----: | ----: | ----: | ----: | ----: | ----: | ----: | ----: | ----: |
| D1       | 0.100 | 0.100 | 0.100 |     0 |     0 |     0 |     0 |     0 |     0 |
| D2       |     0 | 0.100 |     0 | 0.100 | 0.100 |     0 |     0 |     0 |     0 |
| D3       | 0.075 |     0 |     0 | 0.075 |     0 | 0.075 | 0.151 |     0 |     0 |
| D4       |     0 |     0 | 0.060 |     0 | 0.060 | 0.060 |     0 | 0.120 | 0.120 |

---

### Exercise 1

Given corpus:

- D1: cats like milk
- D2: dogs like bones
- D3: cats and dogs play
- D4: milk and bones are food

Context window size = 1.


#### 1. Skip-Gram Training Pairs

In Skip-Gram, the center word is used to predict its context words.

**D1: cats like milk**

| Input | Output |
|---|---|
| cats | like |
| like | cats |
| like | milk |
| milk | like |

**D2: dogs like bones**

| Input | Output |
|---|---|
| dogs | like |
| like | dogs |
| like | bones |
| bones | like |

**D3: cats and dogs play**

| Input | Output |
|---|---|
| cats | and |
| and | cats |
| and | dogs |
| dogs | and |
| dogs | play |
| play | dogs |

**D4: milk and bones are food**

| Input | Output |
|---|---|
| milk | and |
| and | milk |
| and | bones |
| bones | and |
| bones | are |
| are | bones |
| are | food |
| food | are |

---

#### 2. CBOW Training Samples

In CBOW, the context words are used to predict the center word.

**D1: cats like milk**

| Context Input | Output |
|---|---|
| [like] | cats |
| [cats, milk] | like |
| [like] | milk |

**D2: dogs like bones**

| Context Input | Output |
|---|---|
| [like] | dogs |
| [dogs, bones] | like |
| [like] | bones |

**D3: cats and dogs play**

| Context Input | Output |
|---|---|
| [and] | cats |
| [cats, dogs] | and |
| [and, play] | dogs |
| [dogs] | play |

**D4: milk and bones are food**

| Context Input | Output |
|---|---|
| [and] | milk |
| [milk, bones] | and |
| [and, are] | bones |
| [bones, food] | are |
| [are] | food |

--- 

## Note

| Aspect                 | Skip-Gram                               | CBOW                               |
| ---------------------- | --------------------------------------- | ---------------------------------- |
| **Goal**               | Predict the surrounding (context) words | Predict the center word            |
| **Input**              | One center word                         | All surrounding context words      |
| **Output**             | One context word at a time              | One center word                    |
| **Training Samples**   | More (one pair per context word)        | Fewer (one sample per center word) |
| **Training Speed**     | Slower                                  | Faster                             |
| **Rare Words**         | Better at learning rare words           | Less effective for rare words      |
| **Common Words**       | Good                                    | Very good                          |
| **Accuracy**           | Usually higher                          | Usually slightly lower             |
| **Computational Cost** | Higher                                  | Lower                              |
