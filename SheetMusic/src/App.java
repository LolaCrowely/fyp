import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import javax.sound.midi.*;

public class App {
    
    public static final int noteOn = 0x90;
    public static final int noteOff = 0x80;
    public static final String[] noteNames = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};


    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int count = 0;
        Sequence sequence = MidiSystem.getSequence(new File("gav.mid"));
        System.out.println("Hello are you playing a piano song now or just imputting a file?\n1: Playing a Song\n2:Imputting a file");
        count = sc.nextInt();

        switch (count) {
            case 1:
            break;
            case 2:
                process(sequence);
            break;
            default:
                System.out.println("That was not an option, goodbye!");
            break;
        }


        
       sc.close();
    }
    public static void process(Sequence sequence) throws IOException{
        FileWriter MyWriter = new FileWriter("midi.txt");
        
        int trackNumber = 0;
        for (Track track :  sequence.getTracks()) {
            trackNumber++;
            MyWriter.write("Track " + trackNumber + ": size = " + track.size() +"\n");
           // System.out.println();
            for (int i=0; i < track.size(); i++) { 
                MidiEvent event = track.get(i);
                MyWriter.write("@" + event.getTick() + " \n");
                MidiMessage message = event.getMessage();
               // System.out.println("***************************" +message+"*****************************");
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    MyWriter.write("Channel: " + sm.getChannel() + " ");
                    if (sm.getCommand() == noteOn) {
                        int key = sm.getData1();
                        int octave = (key / 12)-1;
                        int note = key % 12;
                        String noteName = noteNames[note];
                        int velocity = sm.getData2();
                        MyWriter.write("Note on, " + noteName + octave + " key=" + key + " velocity: " + velocity+"\n");
                    } else if (sm.getCommand() == noteOff) {
                        int key = sm.getData1();
                        int octave = (key / 12)-1;
                        int note = key % 12;
                        String noteName = noteNames[note];
                        int velocity = sm.getData2();
                        MyWriter.write("Note off, " + noteName + octave + " key=" + key + " velocity: " + velocity+"\n");
                    } else {
                        MyWriter.write("Command:" + sm.getCommand());
                    }
                } 
                else if (message instanceof MetaMessage){
                    MetaMessage mm = (MetaMessage) message;
                    byte[] data = mm.getData();
                    int tempo = 0;
                    System.out.println("Track no " + trackNumber);
                    System.out.print("Data: "+ data);
                    for (int j = 0; j < data.length; j++){
                        System.out.print(data[j]+" ");
                    }
                    System.out.println("\nType: "+mm.getType());
                    System.out.println(mm);
                    if (mm.getType() == 81 && data.length == 3){
                        tempo = getTempo(mm, data);
                        System.out.println("Tempo: "+ tempo);
                    }
                    //check to see if this if hex or deciaml
                    //it's decimal
                    System.out.println("");
                    MyWriter.write("Meta message: " + mm.getType() + "\n");
                }
                
                else {
                    MyWriter.write("Other message: " + message.getClass() + "\n");
                }
            }
            
           // System.out.println();
        }




        MyWriter.close();

    }

    public static int getTempo(MetaMessage mm, byte[] data){

        int blah = ((data[0] & 0xff) << 16) | ((data[1] & 0xff) << 8) | (data[2] & 0xff);
        int tempo = Math.round(60000001f / blah);

        return tempo;
    }
    public static int[] getTimeSign(MetaMessage mm, byte[] data){

        int[] timeSign = {4,4};

        return timeSign;
    }
    public static String getKeySign(MetaMessage mm, byte[] data){

        

        return "";
    }
}