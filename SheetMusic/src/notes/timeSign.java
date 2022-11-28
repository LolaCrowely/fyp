package notes;

public class timeSign implements iSheet{
    int[] ts = new int[2];

    public void tscon(int[] ts) {
        this.ts = ts;
    }

    @Override
    public String toMusicXML() {
        return "<time>\n<beats>"+ts[0]+"</beats>\n  <beat-type>"+ts[1]+"</beat-type>\n</time>\n";
    }
}
