import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.text.*;
import mdlaf.MaterialLookAndFeel;
class gui extends JFrame implements ActionListener{
    JFrame frame = new JFrame("Midi2SheetMusic");
    JPanel panel = new JPanel();
    File output;
    JButton UploadBtn;
    JButton SheetMusic;
    JButton dl;
    File filePath;
    JTextField tsfield1 = new JTextField("");
    JTextField tsfield2 = new JTextField("");
    JTextField title = new JTextField("");
    JTextField tempoField = new JTextField("");
    int[] ks = {-1,-1};
    String ksign = new String("");
    int[] ts = {-1,-1};
    int tempo = 0;
    String Mtitle = new String("");
    String[] keySigns = {"C major", "C minor", "D major", "D minor", "E major", "E minor", "F major", "F minor", "G major", "G minor", "A major", "A minor", "B major", "B minor"};
    JComboBox<String> cb;
    

    public void main(String args[]){
         try{
         UIManager.setLookAndFeel(new MaterialLookAndFeel());
         } catch(Exception e){}
         JFrame.setDefaultLookAndFeelDecorated(false);
        
        //Creating the Frame
        frame = new JFrame("Midi2SheetMusic");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(944, 700);

        JLabel lbl = new JLabel("<html><h2>Welcome to Midi2SheetMusic, please enter all fields and press \"Confirm\" at the bottom!</h2><br></html>");
        JLabel lbl2 = new JLabel ("Pick your key signature");
        JLabel titlelbl = new JLabel("Select your Title");
        JLabel timeSign = new JLabel ("Time Signature:  ");
        cb  = new JComboBox<String>(keySigns);
        tsfield1 = new JTextField(1);
        tsfield2 = new JTextField(1);
        JLabel tempo = new JLabel ("Tempo:  ");
        tempoField = new JTextField(3);
        JLabel midiUpload = new JLabel ("Upload Midi File: ");
        UploadBtn = new JButton("Upload Midi File");
        SheetMusic = new JButton ("<html><h2>Confirm</h2></html>");
        dl = new JButton("<html><h2>Download Your Sheet Music!</h2></html>");


        panel.setPreferredSize(new Dimension(944, 700));
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

        panel.add(titlelbl);
        panel.add(title);

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
        UploadBtn.setBounds (495, 280, 120, 35);
        midiUpload.setBounds (145, 280, 265, 25);
        titlelbl.setBounds(145, 340, 265, 25);
        title.setBounds(495, 340, 120, 35);
        SheetMusic.setBounds (250, 550, 140, 60);

       // frame.pack();
       frame.add(panel);
        frame.setVisible (true);
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
                JLabel fileError = new JLabel("<html>*Make sure you enter a <u>MIDI</u> file (ends with <u>.mid</u>)</html>");
                fileError.setForeground(Color.red);
                fileError.setBounds (320, 450, 350, 30);
                if(filePath.toString().contains(".mid") == false){
                    
                    panel.add(fileError);
                    panel.revalidate();
                    panel.repaint();
                    System.out.println("Here");
                }
                else{
                    panel.remove(fileError);
                    panel.revalidate();
                    panel.repaint();
                }
                System.out.println(filePath);
            }
        }
        else if (evt.getSource() == SheetMusic){
            if (tsfield1.getText().isEmpty() || tsfield2.getText().isEmpty() || tempoField.getText().isEmpty() || filePath.toString().equals("") || filePath.toString().contains(".mid") == false || title.getText().isEmpty()){
                JLabel genError = new JLabel("<html>Make sure <u>ALL</u> values have been entered</html>");
                genError.setForeground(Color.red);
                genError.setBounds(340, 500, 400, 30);
                panel.add(genError);
                panel.revalidate();
                panel.repaint();

            }
            else{
                this.ts[0] = Integer.parseInt(tsfield1.getText());
                this.ts[1] = Integer.parseInt(tsfield2.getText());
                System.out.println("Time Signature 1: " + ts[0]+" "+ts[1]);
                this.ksign = cb.getSelectedItem().toString();
                setKeySignature(ksign);
                this.Mtitle = title.getText();
                this.tempo = Integer.parseInt(tempoField.getText());
                ConvertFile f = new ConvertFile();
                try {
                    output = f.MiditoMusicXML(filePath, tempo, ks, ts, Mtitle);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Didn't work");
                }
                dl.setBounds(400, 550, 300, 60);
                panel.add(dl);
                dl.addActionListener(this);
                System.out.println(filePath);
                System.out.println("Time Signature: " + ts[0]+" "+ts[1]);
                System.out.println("Tempo:" + tempo);
            }
        }
        else if(evt.getSource() == dl){
            
            JFileChooser sl = new JFileChooser();
            sl.setDialogTitle("Save file");
            File outputFile = new File(Mtitle.replace(" ", "") + ".musicxml");
            sl.setSelectedFile(outputFile);
            int res = sl.showSaveDialog(dl);
            if (res == JFileChooser.APPROVE_OPTION){
                
                outputFile = sl.getSelectedFile();
                try {
                    FileOutputStream oStream = new FileOutputStream(outputFile);
                    BufferedInputStream iStream = new BufferedInputStream(new FileInputStream(output));
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = iStream.read(buffer)) != -1){
                        oStream.write(buffer, 0, bytesRead);
                    }
    
                    iStream.close();
                    oStream.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
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