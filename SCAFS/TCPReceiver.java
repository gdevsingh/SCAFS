import java.net.*;
import java.io.*;
import javax.crypto.spec.SecretKeySpec ;
import javax.crypto.Cipher ;
import javax.crypto.spec.IvParameterSpec ;
import java.time.LocalDateTime;
/**
	*This class is responsible for Receiving a file from a peer and downloading it in FilesReceived Folder
	*@author Gurkeerat Singh
	*		Gurdev Singh
	*@version 0.4 20-10-16
	*@see http://stackoverflow.com/questions/4687615/how-to-achieve-transfer-file-between-client-and-server-using-java-socket
	*@see http://docs.oracle.com/javase/tutorial/networking/sockets/
	*/

public class TCPReceiver implements Runnable
{
    //private static int socketPort ;   
    private static String serverIp ;
    private static String fileName ;
    public final static int FILE_SIZE = 6022386;
	FileOutputStream fos = null;
    BufferedOutputStream bos = null;
	
	public void run()
	{
		Socket sock= null;
		try
		{
    
			ServerSocket waitSock = new ServerSocket(4001);
			while(true)
			{  
				try 
				{
					int bytesRead;
					//int current = 0;
					
					//Waiting for file sending peer
					sock = waitSock.accept();
					System.out.println("Connecting...");
			  
					//creating a blank file
					byte [] mybytearray  = new byte [FILE_SIZE];
					InputStream is = sock.getInputStream();
					fos = new FileOutputStream("FilesReceived\\"+LocalDateTime.now().getMinute()+"_"+LocalDateTime.now().getSecond()+".PNG");
					bos = new BufferedOutputStream(fos);
					if(is!=null)//System.out.println("HI");
					
					//populating the blank file created
					while ((bytesRead = is.read(mybytearray)) != -1)
					{
						//System.out.print(bytesRead+"\n"+mybytearray+"\n");
						bos.write(mybytearray, 0, bytesRead);

						//if(bytesRead < 100 )break;
					}
					System.out.println("File downloaded! ");

				}
				catch(Exception e){System.out.println(e.getMessage());}
				finally
				{
					if (fos != null) try{fos.close();}catch(Exception e){System.out.println(1+e.getMessage());}
				}
			}
		}
		catch(Exception ex) 
		{
			System.out.println("Error(TCPR):Port 4001 already in use!");
			System.exit(0);
		}
	}


}