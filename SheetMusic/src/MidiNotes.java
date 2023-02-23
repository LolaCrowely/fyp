public class MidiNotes {
    public int tickOn = 0;
    public int tickOff = 0;
    public int keyOn = 0;
    public int keyOff = 0;
    public String nOn = new String();
    public String nOff = new String();

    public void midiNoteCon(int TickOn, int KeyOn, String NOn){
        this.tickOn = TickOn;
        this.keyOn = KeyOn;
        this.nOn = NOn;
    }
}
