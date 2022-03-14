
class IndeksertListe <T> extends Lenkeliste<T> {
        
        public void leggTil (int pos, T x) { 
                //ugyldig indeks
                if (pos < 0 || pos > stoerrelse()){
                        throw new UgyldigListeindeks(pos); 
                
                //Hvis listen er tom og indeks er 0
                } else if(start == null && pos == 0){
                        leggTil(x);
                
                // Hvis listen ikke er tom og pos er lik stoerrelse (basically festes det et nytt element bakerst i listen)
                }else if (pos == stoerrelse()){
                        leggTil(x);
                
                //Listen er ikke tom men en ny node settes helt på starten 
                } else if(pos == 0){
                        Node nyNode = new Node(x);
                        Node start1 = start;
                        nyNode.settNeste(start1);
                        start.settForrige(nyNode);
                        start = nyNode;
                }
                // Settes et sted mellom den første elementet og det siste (Listen er ikke tom)
                 else{
                        int forpos = 0;
                        Node nyNode = new Node(x);
                        Node node = start;
                        while (node != null && forpos != pos -1){
                                node = node.neste;
                                forpos ++;
                        } 
                       Node ePos = node.neste;
                        node.settNeste(nyNode);
                        nyNode.settForrige(node);
                        nyNode.settNeste(ePos);
                        ePos.settForrige(nyNode);
                }

        }

        public void sett (int pos, T x) { 
                if (pos < 0 || pos> stoerrelse()){
                        throw new UgyldigListeindeks(pos);

                }else if (pos == stoerrelse()){
                        throw new UgyldigListeindeks(pos);

                } else{
                        int forpos = 0;
                        Node node = start;
                        while (node != null && forpos != pos -1){
                                node = node.neste;
                                forpos ++;
                        } node.neste.data = x;
                }
         }

        
        public T hent (int pos) {
                if (pos < 0 || pos >= stoerrelse() || start == null){
                        throw new UgyldigListeindeks(pos);
                } else if ( pos==0){
                        return start.data;
                }
                else{
                        int forpos = 0;
                        Node node = start;
                        while (node != null && forpos != pos -1){
                                node = node.neste;
                                forpos ++;
                        } return node.neste.data;
                }

         }

     
        public T fjern (int pos) {
                if (pos < 0 || pos >= stoerrelse() || start == null){
                        throw new UgyldigListeindeks(pos);
                } else if (pos==0){
                        T f = start.data;
                        start = null; 
                        return f;
                } else if ( pos == stoerrelse() -1){
                        int forpos = 0;
                        Node node = start;
                        while (node != null && forpos != pos -1){
                                node = node.neste;
                                forpos ++; 
                        }  
                        Node fjNode = node.neste;
                        node.settNeste(null);
                        return fjNode.data;
                }
                else{
                        int forpos = 0;
                        Node node = start;
                        while (node != null && forpos != pos -1){
                                node = node.neste;
                                forpos ++;
                        } 
                        Node fjNode = node.neste; 
                        node.settNeste(node.neste.neste);
                        node.neste.settForrige(node);
                        return fjNode.data;
                }       
                
         }
        }
        
