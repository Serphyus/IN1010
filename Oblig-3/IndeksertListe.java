public class IndeksertListe<T> extends Lenkeliste<T> {
    public void leggTil (int pos, T x) {
        // skjekker om pos er en gyldig indeks
        if (pos < 0 || pos > stoerrelse()) {
            throw new UgyldigListeindeks(pos);
        }

        // lag en nyNode av x til å settes inn
        Node nyNode = new Node(x);

        // hvis listen ikke har noder sett nyNode på start
        if (this.antall == 0) {
            this.start = nyNode;
        }
        
        else {
            // hvis pos er 0 så må start endres
            // på sammen med å sette nyNode.neste
            if (pos == 0) {
                nyNode.neste = this.start;
                this.start = nyNode;
            }

            // hvis ikke pos er 0 flytter man bare
            // noden før indeksen sin neste til den
            // nye noden nyNode som skal settes inn
            else {
                int indeks = 0;
                Node node = this.start;

                while (indeks < pos-1) {
                    node = node.neste;
                    indeks++;
                }

                nyNode.neste = node.neste;
                node.neste = nyNode;
            }
        }

        // inkrementer antallet noder
        this.antall++;
    }

    public void sett (int pos, T x) {
        // skjekker om pos er en gyldig indeks
        if (pos < 0 || pos >= stoerrelse()) {
            throw new UgyldigListeindeks(pos);
        }

        // lager en indeks og node på start
        int indeks = 0;
        Node node = this.start;

        // forlytter node til riktig indeks
        while (indeks < pos) {
            node = node.neste;
            indeks++;
        }

        // endrer data til noden
        node.data = x;
    }
    
    public T hent (int pos) {
        // skjekker om pos er en gyldig indeks
        if (pos < 0 || pos >= stoerrelse()) {
            throw new UgyldigListeindeks(pos);
        }

        // lager en indeks og node på start
        int indeks = 0;
        Node node = this.start;

        // velger neste node til man peker på riktig indeks
        while (indeks < pos) {
            node = node.neste;
            indeks++;
        }
        
        // returnerer noden sin data
        return node.data;
    }
    
    public T fjern (int pos) {
        // skjekker om pos er en gyldig indeks
        if (pos < 0 || pos >= stoerrelse()) {
            throw new UgyldigListeindeks(pos);
        }

        // lag ny node som peker til den første noden
        Node node = this.start;

        // hvis posisjon er på 0 kan vi bare forflytte
        // start pekeren sin node til start.neste
        if (pos == 0) {
            this.start = this.start.neste;
        }

        else {
            // sett indeks på 0
            int indeks = 0;

            // flytt node pekeren til indeksen
            // matcher pos argumentet
            while (indeks < pos-1) {
                node = node.neste;
                indeks++;
            }
            
            // sett en node holder mens node.neste
            // forflyttes til node.neste.neste
            Node node_holder = node;
            node = node.neste;
            
            if (node != null) {
                node_holder.neste = node.neste;
            }
        }

        // fjerner en verdi fra antall
        this.antall--;
        
        return node.data;
    }
}