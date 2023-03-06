public class MidiNotes {
    public long tickOn = 0;
    public long tickOff = 0;
    public int keyOn = 0;
    public String nOn = new String();

    public void midiNoteCon(long TickOn, int KeyOn, String NOn){
        this.tickOn = TickOn;
        this.keyOn = KeyOn;
        this.nOn = NOn;
    }
    public void link(long TickOff){
        this.tickOff = TickOff;
    }
    public int getKey(){
        return keyOn;
    }
    public String getnOn(){
        return nOn;
    }
    public long[] getTicks(){
        long[] ticks = {tickOn, tickOff};
        return ticks;
    }
}
