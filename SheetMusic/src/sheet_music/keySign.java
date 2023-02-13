package sheet_music;

public class keySign implements iSheet{
    int[] key = new int[2];
    boolean majmin = true;
    int sign = 0;
    String minmaj = new String();

    public void kcon(int[] key, boolean majmin){

        this.majmin = majmin;
        if (key[1] == 0){
            minmaj = "major";
        }
        else{
            minmaj = "minor";
        }

        this.key = key;
        


    }

    @Override
    public String toMusicXML() {
        return "<key>\n<fifths>"+key[0]+"</fifths>\n<mode>"+minmaj+"</mode>\n</key>\n";
    }
    
}
