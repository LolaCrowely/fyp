//package md;


	import javax.sound.midi.*;

//import synth.MidiComposer;

import java.util.ArrayList;
	import java.util.List;
	import java.io.*;
	
	public class testMidi {
		
		private boolean	m_bPrintTimeStampAsTicks;

	public void testMidi()
	{
	    MidiDevice device;
	    //what does this do? = get all connected(port) and internal midi devices synths/sequencers
	    MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
	    for (int i = 0; i < infos.length; i++) {
	        try {
	        	//connect to the device? get/access? that one particular device!
	        device = MidiSystem.getMidiDevice(infos[i]);
	        //does the device have any transmitters?
	        //if it does, add it to the device list
	        System.out.println("The Device: "+infos[i]);

	        //get all transmitters
	        List<Transmitter> transmitters = device.getTransmitters();
	        //and for each transmitter
	        
	        
//	        List<Receiver> receivers = device.getReceivers();
//	        
//	        System.out.println("The Device: "+transmitters.size()+receivers.size());

	        //The Gervill sequencer has no transmitters or receivers
	        for(int j = 0; j<transmitters.size();j++) {
	        	//System.out.println("j= "+j);
	            //create a new receiver
	            transmitters.get(j).setReceiver(
	                    //using my own MidiInputReceiver
	                    new MidiInputReceiver(device.getDeviceInfo().toString()));
	        }
	       

	        Transmitter trans = device.getTransmitter();
	        trans.setReceiver(new MidiInputReceiver(device.getDeviceInfo().toString()));

	        //open each device
	        device.open();
	        //if code gets this far without throwing an exception
	        //print a success message
	        System.out.println(device.getDeviceInfo()+"("+device.getDeviceInfo().getDescription()+")"+" Was Opened");
	    } catch (MidiUnavailableException e) {}
	}


	}
	//tried to write my own class. I thought the send method handles an MidiEvents sent to it
	public class MidiInputReceiver implements Receiver {
	public String name;
	public MidiInputReceiver(String name) {
	    this.name = name;
	}
	public void send(MidiMessage msg, long timeStamp) {


	    byte[] aMsg = msg.getMessage();
	    // take the MidiMessage msg and store it in a byte array

	    // msg.getLength() returns the length of the message in bytes
	    for(int i=0;i<msg.getLength();i++){
	        System.out.println(aMsg[i]);
	        // aMsg[0] is something, velocity maybe? Not 100% sure.
	        // aMsg[1] is the note value as an int. This is the important one.
	        // aMsg[2] is pressed or not (0/100), it sends 100 when they key goes down,  
	        // and 0 when the key is back up again. With a better keyboard it could maybe
	        // send continuous values between then for how quickly it's pressed? 
	        // I'm only using VMPK for testing on the go, so it's either 
	        // clicked or not.
 	        messageInfo(msg, timeStamp);

	    }
	   // System.out.println();
	}
	public void close() {}
	}
	
	//TL - this method is the send method of MidiInReceive.java in synth package
	public void messageInfo(MidiMessage message, long lTimeStamp)
	{
		//System.out.println("  Receiver send: " + ((ShortMessage) message).getCommand());
		String	strMessage = null;
		if (message instanceof ShortMessage)
		{
			System.out.print(" decode a short message: " + ((ShortMessage) message).getCommand() + " " + ((ShortMessage) message).getData1()+ " " + ((ShortMessage) message).getData2());
			strMessage = decodeMessage((ShortMessage) message);
		}
		
		else  
		{
			System.out.println("unknown " + ((SysexMessage) message).getStatus());
			strMessage = "unknown message type";
		}
//		String	strTimeStamp = null;
//		if (m_bPrintTimeStampAsTicks)
//		{
//			strTimeStamp = "tick " + lTimeStamp + ": ";
//		}
//		else
//		{
//			if (lTimeStamp == -1L)
//			{
//				strTimeStamp = "timestamp [unknown]: ";
//			}
//			else
//			{
//				mytime=lTimeStamp;
//				strTimeStamp = "timestamp " + lTimeStamp + " us: ";
//			}
//		}
//		m_printStream.println(strTimeStamp + strMessage);
	}
	
	
	public String decodeMessage(ShortMessage message)
	{
		System.out.print(" \n Decoding Message: ");
		String	strMessage = null;
		switch (message.getCommand())
		{
		case 0xb0:
			strMessage = "control change " + message.getData1() + " value: " + message.getData2();
			//control(mytime,message.getData1(),message.getData2());
			break;
		case 0x90:
			strMessage = "note on " + message.getData1() + " value: " + message.getData2();
			//control(mytime,message.getData1(),message.getData1());
			break;
		case 0x80:
			strMessage = "note off " + message.getData1() + " value: " + message.getData2();
			break;
		case 0xe0:
			//strMessage = "pitch wheel change " + get14bitValue(message.getData1(), message.getData2());
			break;
		default:
			//strMessage = "unknown message: " + intToHex(message.getCommand()) + " " + " status = " + message.getStatus() + ", byte1 = " + message.getData1() + ", byte2 = " + message.getData2();
			
			//MidiComposer.createShortEvent(192,12,message.getData2(),message.getData1(),0);
			break;
		}
		//playSequence();
		//Oscillators os = new Oscillators(true);
	
		if (message.getCommand() != 0xF0)
		{
			int	nChannel = message.getChannel() + 1;
			String	strChannel = "midichannel " + nChannel + ": ";
			strMessage = strChannel + strMessage;
		}
		//sysex messages have no channel number
		else{System.out.println("Sysex Message");}
//		smCount++;
//		smByteCount+=message.getLength();
		return null;//"["+getHexString(message)+"] "+strMessage;
	}
	
	public static void main(String[] args) {
		testMidi main = new testMidi();
		main.testMidi();
	}
	}
