import java.util.Iterator;


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

    public class LenkelisteIterator implements Iterator<T> {
        Node node = start;

        @Override
        public boolean hasNext() {
            // hvis start er null har den ikke neste
            if (node == null) {
                return false;
            }
            
            // skjekk om den noden er null
            else {
                return (node != null);
            }
        }
    
        @Override
        public T next() {
            // hent data fra noden og forflytt noden
            // til neste og returner dens data
            T data = node.data;
            node = node.neste;
            return data;    
        }
    }

    public Iterator<T> iterator(){
        // returner en liste iterator
        return new LenkelisteIterator();
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