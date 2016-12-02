import java.net.*;
import java.io.*;
import java.util.*;
/**
	*This class is responsible for Sending UDP messages to the server to broadcast to all the online users
	*@author Gurkeerat Singh
	*		Gurdev Singh
	*@version 0.4 20-10-16
	*@see http://stackoverflow.com/questions/9709956/java-udp-message-exchange-works-over-localhost-but-not-internet
	*/
public class UDPMessageServer extends Thread
{
    private String hostname= "255.255.255.255";
    private int port=4002;
    private InetAddress host;		//storing broadcast address 
    private DatagramSocket socket;	//udpsocket to send msgs to
    DatagramPacket packet;			//packet to be sent to the socket
	String byemsg = "";
	public static final String CODE= "0X22"; //code for authentication
	
	/**
	*This method is responsible for accepting user input and deciding activity (broadcasting chat, sending file to a peer, displaying online users, exit)
	*
	*/
    public void run()
    {
        while(true)
        {
        try
        {
			
            System.out.print(">>");

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String msg = br.readLine();
			String [] readFirst = msg.trim().split(" ");
			
			if(readFirst[0].equalsIgnoreCase("file"))			//sending file to a peer
			{
				//System.out.println("works");
				if(readFirst.length == 3)
				{	
					FindUsers fu = new FindUsers(1);
					if(!fu.checkIPList(readFirst[1]))		//checking if IP exists in nw
					{
						System.out.println("Error: No such user online!");
						continue;
					}	
					File myFile = new File (readFirst[2]);
					if(!myFile.exists())					//checking if file exists in system
					{
						System.out.println("Error: No such file exists");
					}
					else
					{		
						TCPSender sendFile = new TCPSender();
						System.out.println();
						sendFile.sendAFile(readFirst[1],readFirst[2]);
					}
				}
				else
				{
					System.out.println("file command usage: <file IPAddress FileToSend>");
				}
				continue;
			}
			else if(readFirst[0].equalsIgnoreCase("showall"))	//show online peers
			{
				FindUsers f = new FindUsers();
				continue;
			}
			else if(readFirst[0].equalsIgnoreCase("bye"))		//exiting
			{
				byemsg = "I left";	
				host = InetAddress.getByName(hostname);
				socket = new DatagramSocket (null);
				packet = new DatagramPacket (byemsg.getBytes(), 0,host, port);
				packet.setData(byemsg.getBytes());
				socket.send (packet);
				socket.receive (packet);
				socket.close ();
				byte[] data = packet.getData ();
				String string = new String(data); 					//convert byte array data into string
				System.exit(0);
			}
			else if(readFirst[0].equalsIgnoreCase("help"))		//System documentation
			{
				System.out.println(" ____   ___   __   ____  ____ ");
				System.out.println("/ ___) / __) / _\\ (  __)/ ___)");
				System.out.println("\\___ \\( (__ /    \\ ) _) \\___ \\");
				System.out.println("(____/ \\___)\\_/\\_/(__)  (____/");
				System.out.println("Swinburne Chat And File Server\n");
				System.out.println("This piece of network application lets you chat, send a file and discover other online buddies available for chatting and file sharing over your local network.");
				System.out.println("\nCommand \t Use");
				System.out.println("\nfile \t\t <file IPAddress FileToSend> : used to send a file to an online peer ");
				System.out.println("showall \t <showall> : used to display available peers");
				System.out.println("help \t\t <help> : display this documentation");
				System.out.println("bye \t\t <bye> : exit the system\n");
				continue;
			}
			else
			{
				host = InetAddress.getByName(hostname);			//converting broadcastaddress string to ipaddress
				socket = new DatagramSocket (null);				//creating socket at a random port
				packet = new DatagramPacket (msg.getBytes(), 0,host, port);
				packet.setData((msg+":"+CODE).getBytes());					//setting data read from user to the datapacket to be sent with CODE
				socket.send (packet);							//sending out packet
				socket.receive (packet);
				socket.close ();
				byte[] data = packet.getData ();
				String string = new String(data); 				 // convert byte array data into string
           }
        }
        catch(Exception e)
        {
        	e.printStackTrace();
            System.out.println("Error(UDPM): port "+port+" already in use!");
            System.exit(0);
        }
		}
    }
}

