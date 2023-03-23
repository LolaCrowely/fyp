import java.io.File;
import java.io.FileWriter;
import javax.sound.midi.*;

import sheet_music.attributes;
import sheet_music.note;



public class ConvertFile {
    public static final int noteOn = 0x90;
    public static final int noteOff = 0x80;
    public static final String[] noteNames = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};


    public File MiditoMusicXML(File filePath, int tempo, int [] keySign, int [] timeSign, String title) throws Exception {
        Sequence sequence = MidiSystem.getSequence(filePath);
        File output = new File(".\\output.musicxml");
        FileWriter Writer1 = new FileWriter(output);
        //attributes f = new attributes();
        tempo = 0;
        int tpb = 0;
        int staff = 1;
        int measureTracker = 2;
        int measureDur = 0;
        int measureValue = 1;

        switch(timeSign[1]){
            case(4):
                measureValue = 8 * timeSign[0];
            break;
            case(2):
                measureValue = 16 * timeSign[0];
            break;
            case(8):
                measureValue = 4 * timeSign[0];
            break;
            case(16):
                measureValue = 2 + timeSign[0];
            break;
            default:
                measureValue = 32;
            break;
        }
        //setting up attributes

        
        tempo = 100;
        attributes start = new attributes();
        start.acon(timeSign, keySign, tempo, title);
        Writer1.write(start.toMusicXML());
        tpb = getTickPerBeat(sequence, tempo);
        for(Track track : sequence.getTracks()){
            for (int i = 0; i < track.size(); i++){
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if(message instanceof ShortMessage){
                    ShortMessage sm = (ShortMessage) message;
                    if(sm.getCommand() == noteOn){
                        //note n = new note();
                        note n = new note();
                        int key = sm.getData1();
                        if (key < 60)
                            staff = 2;
                        else if (key >= 60)
                            staff = 1;
                        int note = key % 12;
                        String noteName = noteNames[note];
                        n.noteCon(event.getTick(), key, noteName, staff, tpb);
                        //looking for key and nOff
                        for(int j = i+1; j < track.size(); j++){
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
                        Writer1.write(n.toMusicXML());
                        if(i != track.size()-1){
                            if (staff == 2){
                                measureDur += n.getDuration();
                                if (measureDur >= measureValue ){
                                    Writer1.write(addMeasure(measureTracker));
                                    measureTracker++;
                                    measureDur = 0;
                                }
                            }
                        }
                    
                    }
                }
                
            }
            
        }
        
        Writer1.close();
        return output;
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
    public static int getTickPerBeat(Sequence s, int tempo){
        long tickLength = s.getTickLength();
        long microLength = s.getMicrosecondLength();
        float seconds = microLength / 1000000.0f;
        float beats = tempo * seconds / 60.0f;
        float ticksperbeat = tickLength /beats;

        return (int) Math.round(ticksperbeat);
    }
    public static String addMeasure(int measureNum){
        return "</measure>\n<measure number=\"" + measureNum + "\">\n";
    }
}

//see if can add doted notes


// switch (tracknum) {
//     case(1) :
//         // int tempocount = 0;
//         int [] keySign = {0,0};
//         int [] timeSign = {4,4};
//         tempo = 100;
//         // for(int i = 0; i < track.size(); i++){
//         //     MidiEvent event = track.get(i);
//         //     MidiMessage message = event.getMessage();
//         //     if (message instanceof MetaMessage){
//         //         MetaMessage mm = (MetaMessage) message;
//         //         byte[] data = mm.getData();
//         //         switch (mm.getType()){
//         //             case(0x51):
//         //             tempo+= getTempo(mm, data);
//         //             tempocount++;
//         //             break;
//         //             case(0x59):
//         //             keySign = getKeySign(mm, data);
//         //             break;
//         //             case(0x58):
//         //             timeSign = getTimeSign(mm, data);
//         //             break;
//         //             default:
//         //             break;
//         //         }
//         //     }
//         // }
//         // tempo = tempo/tempocount;
//         attributes start = new attributes();
//         start.acon(timeSign, keySign, tempo, title);
//         Writer1.write(start.toMusicXML());
//         tpb = getTickPerBeat(sequence, tempo);
//     break;
//     //track 2 is the right hand for the piano
//     case(2):
//     for (int i = 0; i < track.size(); i++){
//         MidiEvent event = track.get(i);
//         MidiMessage message = event.getMessage();
//         if(message instanceof ShortMessage){
//             ShortMessage sm = (ShortMessage) message;
//             if(sm.getCommand() == noteOn){
//                 //note n = new note();
//                 note n = new note();
//                 int key = sm.getData1();
//                 int note = key % 12;
//                 String noteName = noteNames[note];
//                 n.noteCon(event.getTick(), key, noteName, 1, tpb);
//                 //looking for key and nOff
//                 for(int j = i+1; j < track.size(); j++){
//                     MidiEvent event2 = track.get(j);
//                     MidiMessage message2 = event2.getMessage();
//                     if(message2 instanceof ShortMessage){
//                         ShortMessage sm2 = (ShortMessage) message2;
//                         if(sm2.getCommand() == noteOff){
//                             int key2 = sm2.getData1();
//                             int octave2 = (key2/12)-1;
//                             int note2 = key % 12;
//                             String noteName2 = noteNames[note2]+octave2;
//                             if(key2 == n.getKey() && noteName2.equals(n.getnOn())){
//                                 n.link(event2.getTick());
//                                 break;
//                             }
//                         }
//                     }
//                 }
//                 //here is where I should have the note with all the information I need to turn it to musicXML
//                 Writer1.write(n.toMusicXML());
//             }
//         }
        
//     }
//     System.out.println("End of track 2");
//     break;

//     case(3):
//     for (int i = 0; i < track.size(); i++){
//         MidiEvent event = track.get(i);
//         MidiMessage message = event.getMessage();
//         if(message instanceof ShortMessage){
//             ShortMessage sm = (ShortMessage) message;
//             if(sm.getCommand() == noteOn){
//                 //note n = new note();
//                 note n = new note();
//                 int key = sm.getData1();
//                 int note = key % 12;
//                 String noteName = noteNames[note];
//                 n.noteCon(event.getTick(), key, noteName, 2, tpb);
//                 //looking for key and nOff
//                 for(int j = i+1; j < track.size(); j++){
//                     MidiEvent event2 = track.get(j);
//                     MidiMessage message2 = event2.getMessage();
//                     if(message2 instanceof ShortMessage){
//                         ShortMessage sm2 = (ShortMessage) message2;
//                         if(sm2.getCommand() == noteOff){
//                             int key2 = sm2.getData1();
//                             int octave2 = (key2/12)-1;
//                             int note2 = key % 12;
//                             String noteName2 = noteNames[note2]+octave2;
//                             if(key2 == n.getKey() && noteName2.equals(n.getnOn())){
//                                 n.link(event2.getTick());
//                                 break;
//                             }
//                         }
//                     }
//                 }
//                 //here is where I should have the note with all the information I need to turn it to musicXML
//                 Writer1.write(n.toMusicXML());
//             }
//         }
        
//     }
//     System.out.println("End of track 3");
//     break;
// }