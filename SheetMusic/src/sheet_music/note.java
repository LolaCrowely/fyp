package sheet_music;

import javax.sound.midi.*;

public class note implements iSheet{
    
    String step = new String();
    int octave = 0;
    int duration = 0;
    String type = new String();
    int staff = 0;
    public long tickOn = 0;
    public long tickOff = 0;
    public int keyOn = 0;
    public Sequence seq;
    public int tpb = 0;
    public int tempo = 0;

    public void noteCon(long TickOn, int KeyOn, String NOn, Sequence sec, int tempo, int staff){
        this.tickOn = TickOn;
        this.keyOn = KeyOn;
        this.step = NOn;
        this.seq = sec;
        this.tempo = tempo;
        this.staff = staff;

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
    public int getTickPerBeat(Sequence s, int tempo){
        long tickLength = s.getTickLength();
        long microLength = s.getMicrosecondLength();
        float seconds = microLength / 1000000.0f;
        float beats = tempo * seconds / 60.0f;
        float ticksperbeat = tickLength /beats;

        return (int) Math.round(ticksperbeat);
    }
    public void getDurationNType(){
        this.tpb = getTickPerBeat(seq, tempo);
        long noteTickLength = tickOff - tickOn;
        if ((noteTickLength > ((tpb*4)-(tpb/9))) && (noteTickLength < ((tpb*4)+(tpb/9)))){
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
        else if ((noteTickLength > ((tpb/8)-(tpb/9))) && (noteTickLength < ((tpb/8)+(tpb/9)))){
            this.duration = 1;
            this.type = "32nd";
        }
        
    }

    @Override
    public String toMusicXML() {

        getDurationNType();
        
        return "<note>\n<pitch>\n<step>"+step+"</step>\n<octave>"+octave+"</octave>\n</pitch>\n<duration>"+duration+"</duration>\n<type>"+type+"</type>\n<staff>"+staff+"</staff>\n</note>\n";
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