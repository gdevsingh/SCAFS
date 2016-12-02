import java.net.*;
import java.io.*;
/**
	*This class is responsible for Receiving UDP chat packets from senders and displaying them on curent user's screen
	*@author Gurkeerat Singh
	*		Gurdev Singh
	*@version 0.4 20-10-16
	*@see http://stackoverflow.com/questions/9709956/java-udp-message-exchange-works-over-localhost-but-not-internet
	*/
public class MessageProcessServer extends Thread
{
    public static final int DEFAULT_PORT = 4002;
    private DatagramSocket socket;
    private DatagramPacket packet;

	/**
	*This thread is responsible for Receiving UDP chat packets from senders and displaying them on curent user's screen
	*
	*/
    public void run()
    {
        try
        {
            socket = new DatagramSocket(DEFAULT_PORT);
        }
        catch( Exception ex )
        {
            System.out.println("Problem creating socket on port: " + DEFAULT_PORT );
        }

        while (true)
        {
            try
            {
				packet = new DatagramPacket (new byte[100], 100);
                socket.receive (packet);			//receive a msg from UDPMessageServer
				byte[] data = packet.getData ();	
				String string = new String(data);

				String ipa = packet.getAddress().toString().substring(1);
				String name = new FindUsers(1).getNameFromIp(ipa);
				//System.out.println(name);
				//System.out.println(ipa);
                if((string.split(":0X")).length>1)      //to make sure no garbage data is read
                {
                    //System.out.println((string.split(":0X"))[1]);
                    if(((string.split(":0X"))[1]).trim().equals("22"))
                    {
                        System.out.println(name + " says :" + string.split(":0X")[0]);
                    }
                }
                socket.send (packet);				//sending the received packet back to client (makes no sense though) 
            }
            catch (IOException ie)
            {
                System.out.println("Error(MPS): port "+DEFAULT_PORT+" already in use!");
                System.exit(0);
            }
        }
    }
}