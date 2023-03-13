package sheet_music;
//this is for the object notes


public class attributes implements iSheet{
    timeSign tsign = new timeSign();
    keySign ksign = new keySign();
    String title = new String();
    int tempo = 0;
    public void acon(int[] ts, int[] key, int tempo, String title){
        tsign.tscon(ts);
        ksign.kcon(key);
        this.title = title;
        this.tempo = tempo;
    }
   
    @Override
    public String toMusicXML() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE score-partwise PUBLIC \"-//Recordare//DTD MusicXML 4.0 Partwise//EN\" \"http://www.musicxml.org/dtds/partwise.dtd\">\n<score-partwise version=\"4.0\">\n<movement-title>"+ title +"</movement-title>\n<part-list>\n<score-part id =\"P1\">\n<part-name>Right Hand</part-name>\n</score-part>\n</part-list>\n<part id=\"P1\">\n<measure number=\"1\">\n<attributes>\n<divisions>32</divisions>\n"+ksign.toMusicXML()+tsign.toMusicXML()+"<staves>2</staves>\n<clef number=\"1\">\n<sign>G</sign>\n<line>2</line>\n</clef>\n<clef number=\"2\">\n<sign>F</sign>\n<line>4</line>\n</clef>\n</attributes>\n<sound tempo=\""+ tempo+"\"/>\n";
    }
}
