public class Stabel<T> extends Lenkeliste<T> {

    //skal redefinere metoden leggTil(T x) slik at nye elementer legges først i listen.
    @Override
    public void leggTil(T x){
        Node nyNode = new Node(x);
         if (this.start == null){
             start = nyNode;
         } else{
            Node start1 = start;
            nyNode.settNeste(start1);
            start.settForrige(nyNode);
            start = nyNode;

          }
    }
    
}