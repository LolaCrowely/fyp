import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.Time;
import mdlaf.components.*;
import mdlaf.components.button.MaterialButtonUI;
import mdlaf.MaterialLookAndFeel;
class gui extends JFrame implements ActionListener{
    JButton UploadBtn;
    JButton SheetMusic;
    File filePath;
    JTextField tsfield1 = new JTextField("");
    JTextField tsfield2 = new JTextField("");
    JTextField tempoField = new JTextField("");
    int[] ks = new int[2];
    String ksign = new String("");
    int[] ts = new int [2];
    int tempo = 0;
    String[] keySigns = {"C major", "C minor", "D major", "D minor", "E major", "E minor", "F major", "F minor", "G major", "G minor", "A major", "A minor", "B major", "B minor"};
    final JComboBox<String> cb = new JComboBox<String>(keySigns);


    public void main(String args[]){
        try{
        UIManager.setLookAndFeel(new MaterialLookAndFeel());
        } catch(Exception e){}
        JFrame.setDefaultLookAndFeelDecorated(true);
        createWindow();
//         //Creating the Frame
//         JFrame frame = new JFrame("Midi2SheetMusic");
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         frame.setSize(800, 800);
//         frame.setLayout(new FlowLayout());

//         JLabel lbl = new JLabel("Welcome to Midi2SheetMusic, please enter all fields and press \"Sheet Music\" at the bottom!");
//         lbl.setVisible(true);
//         frame.add(lbl);
// //key signature
//         JLabel lbl1 = new JLabel("Select one Key Signature and click Confirm");
//         lbl1.setVisible(true);
//         frame.add(lbl1);
//         cb.setVisible(true);
//         frame.add(cb);
     
// // time signature
//         JLabel lbl2 = new JLabel("Enter your Time Signature then hit Confirm");
//         lbl2.setVisible(true);
//         frame.add(lbl2);
//         tsfield1.setColumns(1);
//         tsfield2.setColumns(1);
//         frame.add(tsfield1);
//         frame.add(tsfield2);
     
// //tempo
//         JLabel lbl3 = new JLabel("Enter your Tempo here then hit Confirm");
//         lbl3.setVisible(true);
//         frame.add(lbl3);
//         tempoField.setColumns(3);
//         frame.add(tempoField);
      
        
// //upload midi file
//         UploadBtn = new JButton("Upload Midi File");
//         UploadBtn.setFocusable(false);
//         UploadBtn.addActionListener(this);
//         frame.add(UploadBtn);

//         SheetMusic = new JButton("Confirm");
//         SheetMusic.setFocusable(false);
//         SheetMusic.addActionListener(this);
//         frame.add(SheetMusic);

//         frame.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent evt) {
        // TODO Auto-generated method stub
        if(evt.getSource() == UploadBtn){
            JFileChooser midiUpload = new JFileChooser();

            //midiUpload.showOpenDialog(null);
            int res = midiUpload.showOpenDialog(null);
            if (res == JFileChooser.APPROVE_OPTION){
                this.filePath = new File(midiUpload.getSelectedFile().getAbsolutePath());

                System.out.println(filePath);
            }
        }
        else if (evt.getSource() == SheetMusic){
            this.ts[0] = Integer.parseInt(tsfield1.getText());
            this.ts[1] = Integer.parseInt(tsfield2.getText());

            this.ksign = cb.getSelectedItem().toString();
            setKeySignature(ksign);

            this.tempo = Integer.parseInt(tempoField.getText());

            ConvertFile f = new ConvertFile();
            try {
				f.MiditoMusicXML(filePath, tempo, ks, ts);
			} catch (Exception e) {
				e.printStackTrace();
                System.out.println("Didn't work");
			}

            System.out.println(filePath);
            System.out.println("Time Signature: " + ts[0]+" "+ts[1]);
            System.out.println("Tempo:" + tempo);

        }
    }
    public void createWindow(){
        JFrame frame = new JFrame("Midi2SheetMusic");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLayout(new FlowLayout());
        createPanal(frame);
        JLabel lbl = new JLabel("Welcome to Midi2SheetMusic, please enter all fields and press \"Sheet Music\" at the bottom!");
        frame.add(lbl);
        lbl.setVisible(true);
    }
    public void createPanal(JFrame frame){
        
//key signature
        JLabel lbl1 = new JLabel("Select one Key Signature and click Confirm");
        lbl1.setVisible(true);
        frame.add(lbl1);
        cb.setVisible(true);
        frame.add(cb);
     
// time signature
        JLabel lbl2 = new JLabel("Enter your Time Signature then hit Confirm");
        lbl2.setVisible(true);
        frame.add(lbl2);
        tsfield1.setColumns(1);
        tsfield2.setColumns(1);
        frame.add(tsfield1);
        frame.add(tsfield2);
     
//tempo
        JLabel lbl3 = new JLabel("Enter your Tempo here then hit Confirm");
        lbl3.setVisible(true);
        frame.add(lbl3);
        tempoField.setColumns(3);
        frame.add(tempoField);
      
        
//upload midi file
        UploadBtn = new JButton("Upload Midi File");
        UploadBtn.setFocusable(false);
        UploadBtn.addActionListener(this);
        frame.add(UploadBtn);

        SheetMusic = new JButton("Confirm");
        SheetMusic.setFocusable(false);
        SheetMusic.addActionListener(this);
        frame.add(SheetMusic);

        frame.setVisible(true);
    }







    public File getFilePath(File fPath){
        return fPath;
    }
    public void setKeySignature(String s){
        switch(s){
            case("C major"):
                this.ks[0] = 0;
                this.ks[1] = 0;
            break;
            case("C minor"):
                this.ks[0] = -3;
                this.ks[1] = 1;
            break;
            case("D major"):
                this.ks[0] = 2;
                this.ks[1] = 0;
            break;
            case("D minor"):
                this.ks[0] = -1;
                this.ks[1] = 1;
            break;
            case("E major"):
                this.ks[0] = 4;
                this.ks[1] = 0;
            break;
            case("E minor"):
                this.ks[0] = 1;
                this.ks[1] = 1;
            break;
            case("F major"):
                this.ks[0] = -1;
                this.ks[1] = 0;
            break;
            case("F minor"):
                this.ks[0] = -4;
                this.ks[1] = 1;
            break;
            case("G major"):
                this.ks[0] = 1;
                this.ks[1] = 0;
            break;
            case("G minor"):
                this.ks[0] = -2;
                this.ks[1] = 1;
            break;
            case("A major"):
                this.ks[0] = 3;
                this.ks[1] = 0;
            break;
            case("A minor"):
                this.ks[0] = 0;
                this.ks[1] = 1;
            break;
            case("B major"):
                this.ks[0] = 5;
                this.ks[1] = 0;
            break;
            case("B minor"):
                this.ks[0] = 2;
                this.ks[1] = 1;
            break;
            default:
            System.out.println(":(");
            break;
        }
        System.out.println("Key Signature: " + ks[0]+" "+ks[1]);
    }
}