package notes;
//this is for the object notes


public class attributes implements iSheet{
    timeSign tsign = new timeSign();
    keySign ksign = new keySign();
    public void acon(int[] ts, String key, boolean majmin){
        tsign.tscon(ts);
        ksign.kcon(key, majmin);
    }
   
    @Override
    public String toMusicXML() {
        return "<attributes>\n<divisions>2</divisions>\n"+ksign.toMusicXML()+tsign.toMusicXML()+"<staves>2</staves>\n<clef number=\"1\">\n<sign>G</sign>\n<line>2</line>\n</clef>\n<clef number \"2\">\n<sign>F</sign>\n<line>4</line>\n</clef>\n</attributes>\n";
    }
}
