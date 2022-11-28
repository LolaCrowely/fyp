package notes;

public class timeSign implements iSheet{
    int[] ts = new int[2];

    public void tscon(int[] ts) {
        this.ts[0] = ts[0];
        this.ts[1] = ts[1];
    }

    @Override
    public String toMusicXML() {
        return "<time>\n<beats>"+ts[0]+"</beats>\n<beat-type>"+ts[1]+"</beat-type>\n</time>\n";
    }
}
