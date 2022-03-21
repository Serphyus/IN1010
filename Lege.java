public class Lege implements Comparable<Lege>{
    // en indeksert liste for legens utskrevene resepter
    private IndeksertListe<Resept> utskrevne_resepter = new IndeksertListe<>();

    private String navn;

    public Lege(String lege){
        this.navn = lege;
    }

    public String hentNavn() {
        return this.navn;
    }

    public HvitResept skrivHvitResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
        // skjekker om legemiddel er narkotisk som ikke er lov
        if (legemiddel instanceof Narkotisk) {
            throw new UlovligUtskrift(this, legemiddel);
        }
        
        HvitResept ny_resept = new HvitResept(legemiddel, this, pasient, reit);
        this.utskrevne_resepter.leggTil(ny_resept);
        return ny_resept;
    }

    public MilResept skrivMilResept(Legemiddel legemiddel, Pasient pasient) throws UlovligUtskrift {
        // skjekker om legemiddel er narkotisk som ikke er lov
        if (legemiddel instanceof Narkotisk) {
            throw new UlovligUtskrift(this, legemiddel);
        }

        MilResept ny_resept = new MilResept(legemiddel, this, pasient);
        this.utskrevne_resepter.leggTil(ny_resept);
        return ny_resept;
    }
    
    public PResept skrivPResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
        // skjekker om legemiddel er narkotisk som ikke er lov
        if (legemiddel instanceof Narkotisk) {
            throw new UlovligUtskrift(this, legemiddel);
        }
        PResept ny_resept = new PResept(legemiddel, this, pasient, reit);
        this.utskrevne_resepter.leggTil(ny_resept);
        return ny_resept;
    }

    public BlaaResept skrivBlaaResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
        // skjekker om legemiddel er narkotisk som bare er lov med spesialist leger
        if (legemiddel instanceof Narkotisk) {
            if ((this instanceof Spesialist) == false) {
                throw new UlovligUtskrift(this, legemiddel);
            }
        }

        BlaaResept ny_resept = new BlaaResept(legemiddel, this, pasient, reit);
        this.utskrevne_resepter.leggTil(ny_resept);
        return ny_resept;
    }

    public IndeksertListe<Resept> utskrevneResepter() {
        return this.utskrevne_resepter;
    }

    public int compareTo(Lege x) {
        return this.navn.compareTo(x.hentNavn()); 
    }

    public String toString(){
        // returnerer en string med legens navn og antall utskrevne resepter
        return String.format("Navn: %s\nResepter: %s", this.navn, this.utskrevne_resepter.antall);
    }
}