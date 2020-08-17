import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        String userName="Alin";
        try(Socket socket =new Socket("127.0.0.1",5555)){
            BufferedReader echoes = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter stringToEcho = new PrintWriter(socket.getOutputStream(),true);
            Scanner scanner=new Scanner(System.in);
            String echoString;
            String response;
            do{
                System.out.println("Enter string to be echo: ");
                echoString=scanner.nextLine();
                stringToEcho.println(userName+": "+echoString);
                if(!echoString.equals("exit")){
                    response = echoes.readLine();
                    System.out.println(response);
                }
            }while(!echoString.equals("exit"));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
