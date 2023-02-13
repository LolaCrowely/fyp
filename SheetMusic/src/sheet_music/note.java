package sheet_music;

public class note implements iSheet{
    
    String step = new String();
    int octave = 0;
    int duration = 0;
    String type = new String();
    int staff = 0;
    int default_x = 0;
    String stem = new String();

    public void noteCon(String step, int octave, int duration, String type, int staff, String stem){
        this.step = step;
        this.octave = octave;
        this.duration = duration;
        this.type = type;
        this.staff = staff;
        //this.default_x = default_x;
        this.stem = stem;
    }



    @Override
    public String toMusicXML() {
        return "<note>\n<pitch>\n<step>"+step+"</step>\n<octave>"+octave+"</octave>\n</pitch>\n<duration>"+duration+"</duration>\n<type>"+type+"</type>\n<stem>"+stem+"</stem>\n<staff>"+staff+"</staff>\n</note>\n";
    }
}
// must add in clause for <chord> element
// also the voice thing