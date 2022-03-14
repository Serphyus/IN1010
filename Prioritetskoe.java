public class Prioritetskoe<T extends Comparable<T>> extends Lenkeliste<T> {

    @Override
    public void leggTil(T x){
        Node nyNyode = new Node(x);

        //Hvis lista er tom skal den nynoden bli plassert helt på starten
        if (start == null){
            start = nyNyode;

            //Hvis det første elemenetet i lista er lik eller større en nynode
        } else if (start.data.compareTo(nyNyode.data) == 0 ||start.data.compareTo(nyNyode.data) == 1){
            Node next = start;
            start = nyNyode;
            nyNyode.neste = next;
            next.forrige = nyNyode;

            //Her begynner itereringen av lista
        } else{
            Node tmp = start;
            while(tmp.neste !=null && tmp.data.compareTo(nyNyode.data) != -1 ){
                tmp = tmp.neste;
            }
            if (tmp.neste == null){
                tmp.neste = nyNyode;
                nyNyode.forrige = tmp;
            } 
            else{
            Node next = tmp.neste;
            tmp.neste = nyNyode;
            nyNyode.forrige = tmp;
            nyNyode.neste = next;
            next.forrige = nyNyode;}
        }
}
    

    }

    

