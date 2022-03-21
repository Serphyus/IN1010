public class MilResept extends HvitResept {
    public MilResept(Legemiddel middel, Lege utskrivendeLege, Pasient pasient) {
        super(middel, utskrivendeLege, pasient, 3);
    }

    @Override
    public int prisAaBetale() {
        return 0;
    }
}
