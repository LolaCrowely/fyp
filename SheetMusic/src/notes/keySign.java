package notes;

public class keySign implements iSheet{
    String key = new String();
    boolean majmin = true;
    int sign = 0;
    String minmaj = new String();

    public void keycon(String key){
        this.key = key;
        

        switch (key){
            case "C":
                if (majmin){
                    sign = 0;
                }
                else{
                    sign = -3;
                }
            break;
            case "F":
                if (majmin){
                    sign = -1;
                }
                else{
                    sign = -4;
                }
            break;
            case "B Flat":
                if (majmin){
                    sign = -2;
                }
                else{
                    sign = -5;
                }
            break;
            case "E Flat":
                if (majmin){
                    sign = -3;
                }
                else{
                    sign = -6;
                }
            break;
            case "A Flat":
                if (majmin){
                    sign = -4;
                }
                else{
                    sign = -7;
                }
            break;
            case "D Flat":
                if (majmin){
                    sign = -5;
                }
                else{
                    sign = -7;
                }
            break;
            case "G Flat":
                if (majmin){
                    sign = -6;
                }
                else{
                    sign = -7;
                }
            break;
            case "C FLat":
                if (majmin){
                    sign = -7;
                }
                else{
                    sign = 2;
                }
            break;
            case "G":
                if (majmin){
                    sign = 1;
                }
                else{
                    sign = -2;
                }
            break;
            case "D":
                if (majmin){
                    sign = 2;
                }
                else{
                    sign = -1;
                }
            break;
            case "A":
                if (majmin){
                    sign = 3;
                }
                else{
                    sign = 0;
                }
            break;
            case "E":
                if (majmin){
                    sign = 4;
                }
                else{
                    sign = 1;
                }
            break;
            case "B":
                if (majmin){
                    sign = 5;
                }
                else{
                    sign = 2;
                }
            break;
            case "F Sharp":
                if (majmin){
                    sign = 6;
                }
                else{
                    sign = 3;
                }
            break;
            case "C Sharp":
                if (majmin){
                    sign = 7;
                }
                else{
                    sign = 4;
                }
            break;
            default:
            System.out.print("Incorrect Key Signature input");
            
        }

    }

    public void majminCon(boolean majmin){
        this.majmin = majmin;
        if (majmin)
            minmaj = "major";

        else
            minmaj = "minor";
    }

    @Override
    public String toMusicXML() {
        return "<key>\n<fifths>"+sign+"</fifths>\n<mode>"+minmaj+"</mode>\n</key>";
    }
    
}
