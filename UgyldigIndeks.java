class UgyldigListeindeks extends RuntimeException {
    UgyldigListeindeks (int indeks) {
        super("Ugyldig indeks: "+indeks);
    }
}

// Program som returnerer e notat om at brukeren har satt en ugyldig indeks, det vil si et posisjons som er ikke eksisterende 
//Kanskje fordi indeksen er for stor
