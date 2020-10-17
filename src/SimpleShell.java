import java.io.*;
import java.util.*;

public class SimpleShell{
    public static void main(String[] args) throws IOException{

        String commandLine;
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

        String[] cmd = new String[] {"test.sh"};
        ProcessBuilder pb = new ProcessBuilder();

        //we break out with ctrl+d
        while (true){
            //read user input
            System.out.print("jsh>");
            commandLine = console.readLine();

            //if user entered "return", loop again
            if(commandLine.equals(""))
                continue;
        }

    }
}