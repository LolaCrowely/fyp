package sheet_music;

public class note implements iSheet{
    
    String step = new String();
    int octave = 0;
    int duration = 0;
    String type = new String();
    int staff = 0;
    int default_x = 0;
    String stem = new String();
    public long tickOn = 0;
    public long tickOff = 0;
    public int keyOn = 0;
    public String nOn = new String();

    public void noteCon(long TickOn, int KeyOn, String NOn){
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



    @Override
    public String toMusicXML() {

        this.octave = (keyOn/12)-1;
        
        return "<note>\n<pitch>\n<step>"+step+"</step>\n<octave>"+octave+"</octave>\n</pitch>\n<duration>"+duration+"</duration>\n<type>"+type+"</type>\n<stem>"+stem+"</stem>\n<staff>"+staff+"</staff>\n</note>\n";
    }
}
// must add in clause for <chord> element
// also the voice thing

// public class MidiNotes {
//     public long tickOn = 0;
//     public long tickOff = 0;
//     public int keyOn = 0;
//     public String nOn = new String();

//     public void midiNoteCon(long TickOn, int KeyOn, String NOn){
//         this.tickOn = TickOn;
//         this.keyOn = KeyOn;
//         this.nOn = NOn;
//     }
//     public void link(long TickOff){
//         this.tickOff = TickOff;
//     }
//     public int getKey(){
//         return keyOn;
//     }
//     public String getnOn(){
//         return nOn;
//     }
//     public long[] getTicks(){
//         long[] ticks = {tickOn, tickOff};
//         return ticks;
//     }
// }



// public void noteCon(String step, int octave, int duration, String type, int staff, String stem){
//     this.step = step;
//     this.octave = octave;
//     this.duration = duration;
//     this.type = type;
//     this.staff = staff;
//     //this.default_x = default_x;
//     this.stem = stem;
// }


//to make the identifyNote() method I need the time signature the tick difference (noteOn-noteOff)and tempo