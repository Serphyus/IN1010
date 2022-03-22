public class Stabel<T> extends Lenkeliste<T> {
    @Override
    public void leggTil(T x) {
        Node nyNode = new Node(x);

        if (this.start == null) {
            this.start = nyNode;
        }
        
        else {
            nyNode.neste = this.start;
            this.start = nyNode;
        }

        this.antall++;
    }
}