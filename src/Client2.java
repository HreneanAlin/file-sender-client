import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client2 {
     public static final int port = 5501;
     public static final String host = "127.0.0.1";

    public static void main (String [] args ) throws IOException {
        int bytesRead;
        int current = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        Socket sock = null;
        Socket socket =new Socket("127.0.0.1",5555);
        Socket socketForLength =new Socket("127.0.0.1",7777);
        Scanner scanner =new Scanner(System.in);
        try {

            System.out.println("Connecting...");
            String fileName = getFileName(socket);
            int lenght = getFileLenght(socketForLength);
            System.out.println("Conected");
            System.out.println("Enter path to download");
            System.out.println("Start downloading...");
            String path=scanner.nextLine()+"\\"+fileName;
            byte [] mybytearray  = new byte [lenght+100];
            sock = new Socket(host,port);
            InputStream is = sock.getInputStream();
            fos = new FileOutputStream(path);
            bos = new BufferedOutputStream(fos);
            bytesRead = is.read(mybytearray,0,mybytearray.length);
            current = bytesRead;

            do {
                bytesRead =
                        is.read(mybytearray, current, (mybytearray.length-current));
                if(bytesRead >= 0) current += bytesRead;
            } while(bytesRead > -1);

            bos.write(mybytearray, 0 , current);

            bos.flush();
            System.out.println("File " + fileName + " downloaded (" + current + " bytes read)");
        }
        finally {
            if (fos != null) fos.close();
            if (bos != null) bos.close();
            if (sock != null) sock.close();
        }
    }

    private static String getFileName(Socket socket) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String fileName = bufferedReader.readLine();
        socket.close();
        bufferedReader.close();
        return  fileName;
    }
    private static int getFileLenght(Socket socket) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        int lenght = Integer.parseInt(bufferedReader.readLine());
        socket.close();
        bufferedReader.close();
        return  lenght;
    }
}
