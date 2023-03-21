package sheet_music;

//import javax.sound.midi.*;

public class note implements iSheet{
    
    public String step = new String();
    public int octave = 0;
    public int duration = 0;
    public String type = new String();
    public int staff = 0;
    public long tickOn = 0;
    public long tickOff = 0;
    public int keyOn = 0;
    public int tpb = 0;
    public int alter = 0;

    public void noteCon(long TickOn, int KeyOn, String NOn, int staff, int tpb){
        this.tickOn = TickOn;
        this.keyOn = KeyOn;
        this.step = NOn;
        this.staff = staff;
        this.tpb = tpb;

        this.octave = (keyOn/12)-1;
    }
    public void link(long TickOff){
        this.tickOff = TickOff;
    }
    public int getKey(){
        return keyOn;
    }
    public String getnOn(){
        return step+octave;
    }
    public long[] getTicks(){
        long[] ticks = {tickOn, tickOff};
        return ticks;
    }
    public int getDuration(){
        return duration;
    }
    
    
    public void conDurationNType(){
        long noteTickLength = tickOff - tickOn;
        if ((noteTickLength > ((tpb*4)-(tpb/9)))){
            this.duration = 32;
            this.type = "whole";
        }
        else if ((noteTickLength > ((tpb*2)-(tpb/9))) && (noteTickLength < ((tpb*2)+(tpb/9)))){
            this.duration = 16;
            this.type = "half";
        }
        else if ((noteTickLength > (tpb-(tpb/9))) && (noteTickLength < (tpb+(tpb/9)))){
            this.duration = 8;
            this.type = "quarter";
        }
        else if ((noteTickLength > ((tpb/2)-(tpb/9))) && (noteTickLength < ((tpb/2)+(tpb/9)))){
            this.duration = 4;
            this.type = "eighth";
        }
        else if ((noteTickLength > ((tpb/4)-(tpb/9))) && (noteTickLength < ((tpb/4)+(tpb/9)))){
            this.duration = 2;
            this.type = "16th";
        }
        else if ((noteTickLength < ((tpb/8)+(tpb/9)))){
            this.duration = 1;
            this.type = "32nd";
        }
    }

    @Override
    public String toMusicXML() {

        conDurationNType();
        if (step.length() > 1){
            alter = 1;
            step = step.substring(0, 1);
        }
        return "<note>\n<pitch>\n<step>"+step+"</step>\n<alter>"+ alter +"</alter>\n<octave>"+octave+"</octave>\n</pitch>\n<duration>"+duration+"</duration>\n<type>"+type+"</type>\n<staff>"+staff+"</staff>\n</note>\n";
    }
}
// must add in clause for <chord> element
// also the voice thing




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