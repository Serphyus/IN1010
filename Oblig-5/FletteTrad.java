public class FletteTrad implements Runnable {
    private Monitor2 monitor;

    public FletteTrad(Monitor2 monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        while (this.monitor.antallHashMaps() > 1) {
            this.monitor.mergeHashMaps();
        }
    }
}