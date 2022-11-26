//package md;

import javax.sound.midi.*;

public class DisplayDevices {

	public static void seekDevice(){
	MidiDevice.Info[] info =MidiSystem.getMidiDeviceInfo();
	
	for (int i=0; i < info.length; i++) {
	System.out.println("("+i + ") " + info[i]);
	System.out.println("Name:        " + info[i].getName());
	System.out.println("Description: " +info[i].getDescription());
	System.out.println("Version:     " +info[i].getVersion());
	MidiDevice device = null;
	try {
		device = MidiSystem.getMidiDevice(info[i]);
	} catch (MidiUnavailableException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println("Receivers: " + device.getMaxReceivers());
	System.out.println("Transmitters: " + device.getMaxTransmitters());
	System.out.println("Device: " + device);
		}
	}
	public static void main(String[] args) {
		seekDevice();
	}
}


