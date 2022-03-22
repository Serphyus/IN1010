abstract class Lenkeliste<T> implements Liste<T> {
    protected Node start = null;
    protected int antall;

    class Node {
        public Node neste = null;
        public T data;
    
        public Node(T x) {
            this.data = x;
        }
    }

    @Override
    public int stoerrelse() {
        // returner antall noder
        return this.antall;
    }

    @Override
    public void leggTil(T x) {
        // lag nyNode av x
        Node nyNode = new Node(x);

        // hvis start er tomt sett nyNode på start
        if (this.start == null) {
            this.start = nyNode;
        }

        else {
            // iterer til node ikke har en neste
            // og så sett nyNode på node.neste
            Node node = this.start;
            while (node.neste != null) {
                node = node.neste;
            }
            node.neste = nyNode;
        }

        // inkrementer antallet
        this.antall++;
    }

    @Override
    public T hent() {
        // returner dataen til første node hvis den ikke er null
        if (this.start != null) {
            return this.start.data;
        }
        return null;
    }

    @Override
    public T fjern() {
        // hvis start ikke er null fjern start noden
        // og sett start.neste til start og fjern 1
        // verdi fra antall
        if (this.start != null) {
            T node_data = hent();
            this.start = this.start.neste;
            this.antall--;
            return node_data;
        }

        // hvis listen er tom gi en ugyldig indeks exception
        throw new UgyldigListeindeks(0);
    }

    @Override
    public String toString() {
        return String.format("Lenkeliste:\nAntall: %s\n", this.stoerrelse());
    }
}