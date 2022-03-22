public class MilResept extends HviteResept {
    public MilResept(Legemiddel middel, Lege utskrivendeLege, int pasientId) {
        super(middel, utskrivendeLege, pasientId, 3);
    }

    @Override
    public int prisAaBetale() {
        return 0;
    }
}
