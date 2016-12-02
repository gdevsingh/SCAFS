import java.io.*;
import java.net.*;
import java.util.List;
import java.util.ArrayList;



/**
	*This class is responsible for broadcasting a packet out in the network
	*continuously to persist their presence in network
	*@author Gurkeerat Singh
	*		Gurdev Singh
	*@version 0.4 20-10-16
	*/
public class BroadcastOnline extends Thread{
    public  String name;
	
	//setting name of the user received from ChatProgram
	public BroadcastOnline(String name)
	{
        
        this.name = name+":0x22";
    }
	
	public void run(){
        try
        {
            byte[] sendData = new byte[1];
			//String hello="hello";
            DatagramSocket serverSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName("255.255.255.255");
           
            sendData = name.getBytes();
            for(;;)
            {	//sending packet out for persisting presence in n/w continuously
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 4000);
                serverSocket.send(sendPacket);
                Thread.sleep(1000);
            }
        }
         catch (Exception e){}
    }
}  