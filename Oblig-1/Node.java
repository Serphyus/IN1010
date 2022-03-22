public class Node {
    // deklarer instans variablene prossessorer
    // og minne for senere assignment
    private int prosessorer;
    private int minne;
    
    // lag en public konstruktor som skjekker om
    // prossessorer og minne er en gyldig verdi
    // for deretter å lagre dem. hvis argumentene
    // ikke er en gyldig verdi kaster den en
    // exception for å bli behandlet i Hovedprogram
    public Node(int prosessorer, int minne) {
        if (prosessorer > 16) {
            throw new IllegalArgumentException(String.format("%s utgaar maks porsessorer verdi 16", prosessorer));
        }
        
        if (minne < 0 || minne > 4096) {
            throw new IllegalArgumentException(String.format("%s utgaar maks  minne verdi 4096", minne));
        }

        this.minne = minne;
        this.prosessorer = prosessorer;
    }

    // returner instans variablen prossessorer
    public int antProsessorer() {
        return prosessorer;
    }

    // returner true eller false basert på om noden
    // sitt minne er mer eller lik argumentet paakrevdMinne
    public boolean nokMinne(int paakrevdMinne) {
        return (minne >= paakrevdMinne);
    }
}
