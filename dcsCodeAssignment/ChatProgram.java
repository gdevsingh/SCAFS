/**
	*This class is responsible for generating CLI for the Swinburne Chat and File Server
	*@author Gurkeerat Singh
	*		Gurdev Singh
	*@version 0.4 20-10-16
	*/
import java.io.File;
import java.io.*;
public class ChatProgram
{
	/**
	*This method is responsible for generating CLI for the Swinburne Chat and File Server
	*@throws IOException
	*/
	public static void main(String args[]) throws IOException
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
		System.out.println("**d3v3l0p3r5*******");
		System.out.println("**Gurkeerat Singh**");
		System.out.println("**Gurdev Singh*****");
		System.out.println("*******************");
		
		ChatProgram chat = new ChatProgram();
		chat.createDirectory();
		
		//starting Broadcastonline class to broadcast its presence
		
		String name = "";
        while(name.length()==0)
		{
			System.out.println("Please enter your name");
        	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		 	name = br.readLine();
		}
		//if(name.trim()!=null)
		{
			
			FindUsers f = new FindUsers();
			BroadcastOnline bo = new BroadcastOnline(name.trim());
			bo.start();
		}
			
		//starting TCPReceiver to receive files 
		Thread cli = new Thread(new TCPReceiver());
        cli.start();
		//try{Thread.sleep(2000);}catch(Exception e){System.out.println(":|");}
		
		//starting message receiver class
		Thread msp = new Thread(new MessageProcessServer());
		msp.start();
		
		//starting messagesender class
		Thread udpms = new Thread(new UDPMessageServer());
		udpms.start();
	}
	

/**
	*This method is responsible for creation of directory Files Received to store received files 
	*@see https://www.mkyong.com/java/how-to-create-directory-in-java/
	*/ 
    public void createDirectory() {

        File file = new File("FilesReceived");
        if (!file.exists()) {
            if (file.mkdir()) {
                //System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }

        

    }


}