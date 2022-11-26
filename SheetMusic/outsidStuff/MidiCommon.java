//package md;



import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;



/** Utility methods for MIDI examples.
*/
public class MidiCommon
{
/** TODO:
todo: flag long
*/
public static void listDevices(boolean bForInput,
         boolean bForOutput)
{
listDevices(bForInput, bForOutput, true);
}

public static String getInDevice(boolean bForInput,
      boolean bForOutput,
      boolean bVerbose)
{

String min="";
if (bForInput && !bForOutput)
{
out("md.getInDevice Available MIDI IN Devices:");
}
else if (!bForInput && bForOutput)
{
out("md.getInDevice Available MIDI OUT Devices:");
}
else
{
out("md.getInDevice Available MIDI Devices:");//+bForInput+bForOutput);
}

MidiDevice.Info[] aInfos = MidiSystem.getMidiDeviceInfo();

for (int i = 0; i < aInfos.length; i++)
{
try
{
MidiDevice device = MidiSystem.getMidiDevice(aInfos[i]);
int maxr = device.getMaxReceivers();
int maxt = device.getMaxTransmitters();

boolean  bAllowsInput = (device.getMaxTransmitters() != 0);
boolean  bAllowsOutput = (device.getMaxReceivers() != 0);
if ((bAllowsInput && bForInput) ||
   (bAllowsOutput && bForOutput))
{
if ((bVerbose) && (maxt==1))
{
out("" + aInfos[i].getName().trim());
min=aInfos[i].getName().trim();
}
}
}
catch (MidiUnavailableException e)
{
// device is obviously not available...
// out(e);
}

}  //end for

if (aInfos.length == 0)
{
out("[No devices available]");
}
return min;
}




public static void listDevices(boolean bForInput,
         boolean bForOutput,
         boolean bVerbose)
{
if (bForInput && !bForOutput)
{
 out("Available MIDI IN Devices:");
}
else if (!bForInput && bForOutput)
{
 out("Available MIDI OUT Devices:");
}
else
{
 out("Available MIDI Devices:");
}

MidiDevice.Info[] aInfos = MidiSystem.getMidiDeviceInfo();

for (int i = 0; i < aInfos.length; i++)
{
 try
 {
  MidiDevice device = MidiSystem.getMidiDevice(aInfos[i]);
  int maxr = device.getMaxReceivers();
  int maxt = device.getMaxTransmitters();
  //returning -1 means that you can retrieve any number of Transmitters or Receivers, limited only by memory size
  System.out.print(" (MaxR= " + maxr + " MaxT= " + maxt + ")   ");
  //long ll = device.getMicrosecondPosition();
  // System.out.print(" Timing= " + ll + " ");
  boolean  bAllowsInput = (device.getMaxTransmitters() != 0);
  boolean  bAllowsOutput = (device.getMaxReceivers() != 0);
  if ((bAllowsInput && bForInput) ||
      (bAllowsOutput && bForOutput))
  {
   if (bVerbose)
   {
   out("" + i + "  "
    + (bAllowsInput?"IN ":"   ")
    + (bAllowsOutput?"OUT ":"    ")
    + "[" + aInfos[i].getName().trim() + "]");// + ", "
    //+ aInfos[i].getVendor()) + ", "
    //+ aInfos[i].getVersion() + ", "
    //+ aInfos[i].getDescription());
   }
   else
   {
   out("" + i + "  " + aInfos[i].getName());
   }
  }
 }
 catch (MidiUnavailableException e)
 {
  // device is obviously not available...
  // out(e);
 }
}
if (aInfos.length == 0)
{
 out("[No devices available]");
}
//System.exit(0);
}


public static MidiDevice.Info getMidiDeviceInfo(String strDeviceName, boolean bForOutput)
{
	//System.out.println(bForOutput);
MidiDevice.Info[] aInfos = MidiSystem.getMidiDeviceInfo();
for (int i = 0; i < aInfos.length; i++)
{
 if (aInfos[i].getName().equals(strDeviceName))
 {
  try
  {
   MidiDevice device = MidiSystem.getMidiDevice(aInfos[i]);
   
   boolean bAllowsInput = (device.getMaxTransmitters() != 0);
   boolean bAllowsOutput = (device.getMaxReceivers() != 0);
   if ((bAllowsOutput && bForOutput) || (bAllowsInput && !bForOutput))
   {
	   System.out.println(strDeviceName+" "+bForOutput+aInfos[i]);
    return aInfos[i];
   }
  }
  catch (MidiUnavailableException e)
  {
   // TODO:
  }
 }
}
return null;
}


/** 
* Retrieve a MidiDevice.Info by index number.
* This method returns a MidiDevice.Info whose index
* is specified as parameter. This index matches the
* number printed in the listDevicesAndExit method.
* If index is too small or too big, null is returned.
*
* @param index the index of the device to be retrieved
* @return A MidiDevice.Info object of the specified index
*         or null if none could be found.
*/
public static MidiDevice.Info getMidiDeviceInfo(int index)
{
MidiDevice.Info[] aInfos = MidiSystem.getMidiDeviceInfo();
if ((index < 0) || (index >= aInfos.length)) {
 return null;
}
return aInfos[index];
}

private static void out(String strMessage)
{
System.out.println(strMessage);
}
}



/*** MidiCommon.java ***/




