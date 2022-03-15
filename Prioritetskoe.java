public class Prioritetskoe<T extends Comparable<T>> extends Lenkeliste<T> {
    public void leggTil(T x) {
        // lager nyNode av x og setter node til start
        Node nyNode = new Node(x);
        Node node = this.start;

        // hvis listens antall er null sett nyNode til start
        if (this.antall == 0) {
            this.start = nyNode;
        }

        else {
            // hvis x til nyNode er før den neste noden i
            // alfabetisk rekkefølge og vi aldri forflytter
            // oss fra start sett start og nyNode.neste
            if (node.data.compareTo(x) > 0) {
                nyNode.neste = this.start;
                this.start = nyNode;
            }

            // ellers iterer helt til slutten ved at neste
            // node er null eller ved å finne en node som
            // er etter nyNode i alfabetisk rekkefølge
            else {
                while (node.neste != null && node.neste.data.compareTo(x) < 0) {
                    node = node.neste;
                }

                // lagre den nye noden
                nyNode.neste = node.neste;
                node.neste = nyNode;
            }
        }

        // inkrementer antall
        this.antall++;
    }
}