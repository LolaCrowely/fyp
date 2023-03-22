import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.Time;
import mdlaf.components.combobox.*;
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
         JFrame.setDefaultLookAndFeelDecorated(false);
        
        //Creating the Frame
        JFrame frame = new JFrame("Midi2SheetMusic");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        JPanel panel = new JPanel();

        JLabel lbl = new JLabel("<html><h2>Welcome to Midi2SheetMusic, please enter all fields and press \"Confirm\" at the bottom!</h2><br></html>");
        JLabel lbl2 = new JLabel ("Pick your key signature");
        JLabel timeSign = new JLabel ("Time Signature:  ");
        tsfield1 = new JTextField(1);
        tsfield2 = new JTextField(1);
        JLabel tempo = new JLabel ("Tempo:  ");
        tempoField = new JTextField(3);
        JLabel midiUpload = new JLabel ("Upload Midi File: ");
        UploadBtn = new JButton("Upload Midi File");
        SheetMusic = new JButton ("<html><h2>Confirm</h2></html>");


        panel.setPreferredSize(new Dimension(944, 569));
        panel.setLayout(null);

        panel.add(lbl);
        panel.add(lbl2);
        panel.add(cb);



        panel.add(timeSign);
        panel.add(tsfield1);
        panel.add(tsfield2);



        panel.add(tempo);
        panel.add(tempoField);


        panel.add(midiUpload);
        panel.add(UploadBtn);
        UploadBtn.addActionListener(this);

        panel.add(SheetMusic);
        SheetMusic.addActionListener(this);

        lbl.setBounds (75, 15, 835, 70);
        lbl2.setBounds (145, 105, 160, 40);
        cb.setBounds (495, 105, 120, 35);
        timeSign.setBounds (145, 165, 100, 25);
        tsfield1.setBounds (495, 155, 30, 30);
        tsfield2.setBounds (530, 155, 30, 30);
        tempo.setBounds (145, 220, 100, 25);
        tempoField.setBounds (495, 210, 70, 35);
        UploadBtn.setBounds (495, 280, 130, 30);
        midiUpload.setBounds (150, 280, 265, 25);
        SheetMusic.setBounds (345, 380, 160, 70);

       // frame.pack();
       frame.add(panel);
        frame.setVisible (true);
    }

    public gui(){
        
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