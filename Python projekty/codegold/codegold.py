import numpy as np

def lfsr(seed, poly, n):
    """Generator m-sekwencji przy użyciu rejestru przesuwnego z sprzężeniem zwrotnym (LFSR)"""
    state = seed[:]
    seq = []
    for _ in range(n):
        seq.append(state[-1])
        new_bit = sum(state[i] for i in poly) % 2  # XOR wybranych bitów
        state = [new_bit] + state[:-1]
    return seq

def gold_code(n):
    """Generuje zbiór kodów Golda dla dowolnej wartości n"""
    # Przyjmujemy, że n > 2, bo kod Golda wymaga co najmniej 3 bitów
    if n < 3:
        return None
    
    # Zakładając, że dla dowolnego n musimy dobrać odpowiednie wielomiany
    # Przykładowe wielomiany: dla ogólnych wartości n, przyjmujemy ogólny dobór
    # Wielomiany będą zależne od wartości n
    poly1 = [n-1, 1]  # Można dostosować wielomian
    poly2 = [n-1, 2, 1]  # Można dostosować wielomian

    seed = [1] + [0] * (n-1)  # Początkowy stan LFSR

    seq1 = lfsr(seed, poly1, 2**n - 1)
    seq2 = lfsr(seed, poly2, 2**n - 1)
    
    codes = [seq1]
    for shift in range(len(seq2)):  # XOR kodów w różnych przesunięciach
        codes.append(np.bitwise_xor(seq1, np.roll(seq2, shift)).tolist())

    return codes

# Funkcja do wywołania z terminala
if __name__ == "__main__":
    n = int(input("Podaj wartość n: "))
    codes = gold_code(n)
    if codes is None:
        print("Nieobsługiwana wartość n (n musi być większe niż 2).")
    else:
        print("Gold Codes:")
        for code in codes:
            print(code)
