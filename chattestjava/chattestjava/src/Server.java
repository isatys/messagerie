
//====Standard====//

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        Socket socket1 = null;
        Socket socket2 = null;

        InputStreamReader inputStreamReader1 = null;
        InputStreamReader inputStreamReader2 = null;
        OutputStreamWriter outputStreamWriter1 = null;
        OutputStreamWriter outputStreamWriter2 = null;

        BufferedReader bufferedReader1 = null;
        BufferedReader bufferedReader2 = null;
        BufferedWriter bufferedWriter1 = null;
        BufferedWriter bufferedWriter2 = null;

        ServerSocket serverSocket = null;

        serverSocket = new ServerSocket(1234);

        // Accept connections from two clients
        socket1 = serverSocket.accept();
        socket2 = serverSocket.accept();

        inputStreamReader1 = new InputStreamReader(socket1.getInputStream());
        inputStreamReader2 = new InputStreamReader(socket2.getInputStream());

        outputStreamWriter1 = new OutputStreamWriter(socket1.getOutputStream());
        outputStreamWriter2 = new OutputStreamWriter(socket2.getOutputStream());

        bufferedReader1= new BufferedReader(inputStreamReader1);
        bufferedReader2 = new BufferedReader(inputStreamReader2);

        bufferedWriter1 = new BufferedWriter(outputStreamWriter1);
        bufferedWriter2 = new BufferedWriter(outputStreamWriter2);

        // Keep reading and transferring data between clients until one of them sends a "Bye" message
        while (true) {
            String msgFromClient1 = bufferedReader1.readLine();
            String msgFromClient2 = bufferedReader2.readLine();

            System.out.println("Message Client 1 : " + msgFromClient1);
            System.out.println("Message Client 2 : " + msgFromClient2);


            if (msgFromClient1.equalsIgnoreCase("Bye") || msgFromClient2.equalsIgnoreCase("Bye")) {
                break;
            }

            bufferedWriter1.write(msgFromClient2);
            bufferedWriter1.newLine();
            bufferedWriter1.flush();

            bufferedWriter2.write(msgFromClient1);
            bufferedWriter2.newLine();
            bufferedWriter2.flush();
        }

        // Close the sockets and streams
        socket1.close();
        socket2.close();
        inputStreamReader1.close();
        inputStreamReader2.close();
        outputStreamWriter1.close();
        outputStreamWriter2.close();
        bufferedReader1.close();
        bufferedReader2.close();
        bufferedWriter1.close();
        bufferedWriter2.close();

    }
}

//====Asynchrone====//
//
//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//public class Server {
//    public static Map<String, BufferedWriter> clientMap = new ConcurrentHashMap<>();
//
//    public static void main(String[] args) throws IOException {
//        ServerSocket serverSocket = null;
//
//        serverSocket = new ServerSocket(1234);
//
//        while (true) {
//            Socket socket = serverSocket.accept();
//            Thread clientHandlerThread = new Thread(new ClientHandler(socket));
//            clientHandlerThread.start();
//        }
//    }
//}
//
//class ClientHandler implements Runnable {
//    private Socket socket;
//    private InputStreamReader inputStreamReader;
//    private OutputStreamWriter outputStreamWriter;
//    private BufferedReader bufferedReader;
//    private BufferedWriter bufferedWriter;
//    private String clientAddress;
//
//    public ClientHandler(Socket socket) throws IOException {
//        this.socket = socket;
//        this.inputStreamReader = new InputStreamReader(socket.getInputStream());
//        this.outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
//        this.bufferedReader = new BufferedReader(inputStreamReader);
//        this.bufferedWriter = new BufferedWriter(outputStreamWriter);
//        this.clientAddress = socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
//    }
//
//    @Override
//    public void run() {
//        try {
//            Server.clientMap.put(clientAddress, bufferedWriter);
//
//            while (true) {
//                String msgFromClient = bufferedReader.readLine();
//                String[] msgParts = msgFromClient.split(":");
//                String targetClient = msgParts[0];
//                String message = msgParts[1];
//
//                BufferedWriter targetClientWriter = Server.clientMap.get(targetClient);
//                if (targetClientWriter != null) {
//                    targetClientWriter.write(clientAddress + ": " + message);
//                    targetClientWriter.newLine();
//                    targetClientWriter.flush();
//                }
//
//                if (message.equalsIgnoreCase("Bye")) {
//                    break;
//                }
//                bufferedWriter.write(msgFromClient);
//                bufferedWriter.newLine();
//                bufferedWriter.flush();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (socket != null)
//                    socket.close();
//                if (inputStreamReader != null)
//                    inputStreamReader.close();
//                if (outputStreamWriter != null)
//                    outputStreamWriter.close();
//                if (bufferedReader != null)
//                    bufferedReader.close();
//                if (bufferedWriter != null)
//                    bufferedWriter.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}