import java.io.*;
import java.net.*;
import java.util.List;
import java.util.ArrayList;

/**
	*This class is responsible for receiving broadcasted packets sent out by peers 
	*and check if they are online
	*@author Gurkeerat Singh
	*		Gurdev Singh
	*@version 0.4 20-10-16
	*@see http://stackoverflow.com/questions/27787776/peer-to-peer-discovery-on-lan
	*/
public class FindUsers{
	
    InetAddress inetAddress;
    List<String> ipList = new ArrayList<String>();
    List<String> nameList = new ArrayList<String>();
    String ipAddress;
    String name;
    DatagramSocket  clientSocket;
    DatagramPacket receivePacket;
	long futureTime;
    byte[] receiveData;
    Thread collect;
    boolean refresh = true;

    public FindUsers()
	{
		futureTime = System.currentTimeMillis()+1100;
        try{
            clientSocket = new DatagramSocket(4000);
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println(e.toString());
        }

        collect = new Thread(new Runnable(){
            public void run(){
                for(;;){
                    receiveData = new byte[15];
                    receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    try{
                    clientSocket.receive(receivePacket);
                    inetAddress = receivePacket.getAddress();
                    ipAddress = inetAddress.getHostAddress();
                    }
                    catch(IOException e){
                    }
                    if(!ipList.contains(ipAddress)){
                        name = new String( receivePacket.getData());
                        if(name.split(":").length>1)                //checking for code
                        {
                            if(name.split(":")[1].equals("0x22"));
                            {
                             ipList.add(ipAddress);
                             nameList.add(name.split(":")[0]);
                             receiveData = null;
                            }
                        }        
					}
                    try{
                        Thread.sleep(10);
                    }
                    catch(InterruptedException e){
                        //System.out.println("\nInterrupted!");
                        return;
                    }
                }
            }
        });


        collect.start();	//starting find peers thread
		
		while(System.currentTimeMillis()<=futureTime){
            //do nothing.
        }
        clientSocket.close();
        collect.interrupt();

        int size = nameList.size();
        if (size==0){
            System.out.println("No Buddies Online! :|");
        }
        else{

            System.out.println("\nOnline Users*******");
            for(int i = 0; i< nameList.size();i++){
                System.out.println(nameList.get(i)+ ": "+ ipList.get(i));
            }
            System.out.println("*******************");
        }
    }
    public FindUsers(int i)
    {
        futureTime = System.currentTimeMillis()+1100;
        try{
            clientSocket = new DatagramSocket(4000);
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println(e.toString());
        }

        collect = new Thread(new Runnable(){
            public void run(){
                for(;;){
                    receiveData = new byte[15];
                    receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    try{
                    clientSocket.receive(receivePacket);
                    inetAddress = receivePacket.getAddress();
                    ipAddress = inetAddress.getHostAddress();
                    }
                    catch(IOException e){
                    }
                    if(!ipList.contains(ipAddress)){
                        name = new String( receivePacket.getData());
                        if(name.split(":").length>1)                //checking for code
                        {
                            if(name.split(":")[1].equals("code"));
                            {
                             ipList.add(ipAddress);
                             nameList.add(name.split(":")[0]);
                             receiveData = null;
                            }
                        }        
                    }
                    try{
                        Thread.sleep(10);
                    }
                    catch(InterruptedException e){
                        //System.out.println("\nInterrupted!");
                        return;
                    }
                }
            }
        });


        collect.start();    //starting find peers thread
        
        while(System.currentTimeMillis()<=futureTime){
            //do nothing.
        }
        clientSocket.close();
        collect.interrupt();

        int size = nameList.size();
        if (size==0){
            System.out.println("No Buddies Online! :|");
        }
        else{
            
        }
    }
 /**
    * This method checks for existence of an IPAddress in the network and return true/false 
    */
    public boolean checkIPList(String IPAddr)
    {
        for(int i = 0; i< ipList.size();i++)
        {
                if(ipList.get(i).equals(IPAddr))
                {
                    return true;
                }

        }
        return false;
    }
/**
    * This method returns name of user from its IPAddress 
    */
    public String getNameFromIp(String IPAddr)
    {
        for(int i = 0; i< ipList.size();i++)
        {
            if(ipList.get(i).equals(IPAddr))
            {
                return nameList.get(i);
            }
        }
        return "";        
    }

  }