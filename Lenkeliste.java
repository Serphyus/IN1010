import java.util.Iterator;

abstract class Lenkeliste<T> implements Liste<T>{

    Node s = null;
      
    class Node{
        Node neste = null;
        Node forrige = null;
        T data;
        public Node (T data){
            this.data = data;
        }
        
        public void fyllNode(T x){
            data = x;
        }

        public Node hentNeste(){
            return neste;
        }

        public Node hentForrige(){
            return forrige;
        }

        public T hentData(){
            return data;
        }
        public void settNeste(Node neste){
            this.neste = neste;
        }
        
        public void settForrige(Node forrige){
            this.forrige = forrige;
        }
    }

    public class LenkelisteIterator implements Iterator<T> {
        Node node = start;

        @Override
        public boolean hasNext() {
            if (node == null) {
                return false;
            }
            
            else {
                return (node.neste != null);
            }
        }
    
        @Override
        public T next() {
            node = node.neste;
            return node.neste.data;
            
        }
        
    }

    Node start = null;

    //Metoden stoerrelse() skal returnere hvor mange elementer det er i listen.
    @Override
    public int stoerrelse(){
        int stoerrelse = 0;
        Node nyNode = start;
        if (nyNode == null){
            return stoerrelse;}

        while(nyNode != null){
            stoerrelse ++;
            nyNode = nyNode.neste;}

        return stoerrelse;
    }

    //Metoden leggTil(T x) skal legge inn et nytt element; det skal legges sist i listen
    @Override
    public void leggTil (T x){        
        Node ny = new Node(x);

        if ( start == null) {
          start = ny;

        } else {
          ny.forrige = s;
          s.neste = ny;
        }
        s = ny;        
        }
    
    /* Metoden hent() skal returnere det første elementet i listen, men det skal
    ikke fjernes fra listen */
    @Override
    public T hent(){
        if (start == null){
            return null;
        } else{
        return start.data;}
        }
    
    //Metoden fjern() skal fjerne det første elementet i listen og returnere det.
    @Override
    public T fjern(){

        if(start == null){
            throw new UgyldigListeindeks(0);

         } else if(start.neste != null) {

            T dataen = start.data;
            Node startTo = start.neste;
            start.settNeste(null);
            startTo.settForrige(null);
            start = startTo;
            return dataen;

        } else{
            
            T dataen = start.data;
            start.settNeste(null);
            start = null;
            return dataen;
            }
    }
    
    
    public String toString(){
        String listeinnhold ="";
        if (start == null){
            return "null";
        } else {
            while (start != null){
                listeinnhold += start.neste.data + " --> ";
                start = start.neste;
            }
            return listeinnhold +"null";
        }
    }

    public Iterator<T> iterator(){
        return new LenkelisteIterator();
    }
}