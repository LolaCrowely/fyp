import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import javax.sound.midi.*;

import sheet_music.attributes;
import sheet_music.note;



public class ConvertFile {
    public static final int noteOn = 0x90;
    public static final int noteOff = 0x80;
    public static final String[] noteNames = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};


    public static void MiditoMusicXML() throws Exception {
        Sequence sequence = MidiSystem.getSequence(new File("gav.mid"));
        FileWriter Writer1 = new FileWriter("output.musicxml");
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter what you want to call your song");
        String title = sc.next();
        //attributes f = new attributes();
        int tracknum = 0;
        for(Track track : sequence.getTracks()){
            tracknum++;
            switch (tracknum) {
                case(1) :
                    int tempocount = 0;
                    int tempo = 0;
                    int [] keySign = new int [2];
                    int [] timeSign = new int [2];
                    for(int i = 0; i < track.size(); i++){
                        MidiEvent event = track.get(i);
                        MidiMessage message = event.getMessage();
                        if (message instanceof MetaMessage){
                            MetaMessage mm = (MetaMessage) message;
                            byte[] data = mm.getData();
                            switch (mm.getType()){
                                case(0x51):
                                tempo+= getTempo(mm, data);
                                tempocount++;
                                break;
                                case(0x59):
                                keySign = getKeySign(mm, data);
                                break;
                                case(0x58):
                                timeSign = getTimeSign(mm, data);
                                break;
                                default:
                                break;
                            }
                        }
                    }
                    tempo = tempo/tempocount;
                    attributes start = new attributes();
                    start.acon(timeSign, keySign, tempo, title);
                    Writer1.write(start.toMusicXML());
                break;
                //track 2 is the right hand for the piano
                case(2):
                for (int i = 0; i < track.size(); i++){
                    MidiEvent event = track.get(i);
                    MidiMessage message = event.getMessage();
                    if(message instanceof ShortMessage){
                        ShortMessage sm = (ShortMessage) message;
                        if(sm.getCommand() == noteOn){
                            //note n = new note();
                            note n = new note();
                            int key = sm.getData1();
                            int octave = (key / 12)-1;
                            int note = key % 12;
                            String noteName = noteNames[note]+octave;
                            n.noteCon(event.getTick(), key, noteName);
                            //looking for key and nOff
                            for(int j = i+1; j <track.size(); j++){
                                MidiEvent event2 = track.get(j);
                                MidiMessage message2 = event2.getMessage();
                                if(message2 instanceof ShortMessage){
                                    ShortMessage sm2 = (ShortMessage) message2;
                                    if(sm2.getCommand() == noteOff){
                                        int key2 = sm2.getData1();
                                        int octave2 = (key2/12)-1;
                                        int note2 = key % 12;
                                        String noteName2 = noteNames[note2]+octave2;
                                        if(key2 == n.getKey() && noteName2.equals(n.getnOn())){
                                            n.link(event2.getTick());
                                            break;
                                        }
                                    }
                                }
                            }
                            //here is where I should have the note with all the information I need to turn it to musicXML
                        }
                    }
                    
                }


                break;
            }
        }
        sc.close();
        Writer1.close();
    }
    public static int getTempo(MetaMessage mm, byte[] data){

        int blah = ((data[0] & 0xff) << 16) | ((data[1] & 0xff) << 8) | (data[2] & 0xff);
        int tempo = Math.round(60000001f / blah);

        return tempo;
    }
    public static int[] getKeySign(MetaMessage mm, byte[] data){
        int [] keySignature = {data[0], data[1]};
        return keySignature;
    }
    public static int[] getTimeSign(MetaMessage mm, byte[] data){

        int[] timeSign = {data[0], ((int) Math.pow(2, data[1]))};

        return timeSign;
    }
    
}