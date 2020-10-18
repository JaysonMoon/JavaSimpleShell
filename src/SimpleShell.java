import java.io.*;
import java.util.*;

public class SimpleShell {

    public static void main(String[] args) throws java.io.IOException{

        String commandLine;
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

        ProcessBuilder pb = new ProcessBuilder();
        List<String> history = new ArrayList<String>();
        int index = 0;
        //break with ctrl+c
        while(true){
            //read user entry
            System.out.print("jsh>"); //removed "ln" written in assignment so input will be inline with jsh>
            commandLine = console.readLine();

            //user input will be split and placed into string array
            String[] commands = commandLine.split(" "); //split input at spaces
            List<String> list = new ArrayList<String>();

            //put the inputs we split into "commands" into list "list"
            for(int i = 0;i<commands.length;i++){
                list.add(commands[i]);
            }

            history.addAll(list);
            try{
                //if the user typed "history"
                if(list.get(list.size()-1).equals("history")){
                    for(String s : history) //for every element in history
                        System.out.println((index++) + " " + s); //print number in history plus value at history[index]
                    continue;
                }

                if(list.contains("cd")){
                    if(list.get(list.size()-1).equals("cd")){ //if the last element in list is cd
                        File home = new File(System.getProperty("user.home"));
                        pb.directory(home); //pb.directory returns working directory, we pass in "home"
                        continue;

                    }else{
                        String directory = list.get(1);
                        File newDirectory = new File(directory);
                        boolean exists = newDirectory.exists();

                        if(exists){
                            System.out.println("/" + directory); //added the "/" for cleaner output
                            pb.directory(newDirectory);
                            continue;
                        }
                        else{   //if directory doesn't exist
                            System.out.print("Path ");
                        }
                    }
                }

                //!! command returns the last command in history
                if(list.get(list.size()-1).equals("!!")){
                    pb.command(history.get(history.size()-2));

                }//!<integer value i> command
                else if
                (list.get(list.size()-1).charAt(0) == '!'){
                    int b = Character.getNumericValue(list.get(list.size()-1).charAt(1));
                    if(b<=history.size())//check if integer entered isn't bigger than history size
                        pb.command(history.get(b));
                }
                else{
                    pb.command(list);
                }

                Process process = pb.start();

                //obtain the input stream
                InputStream is = process.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                //read output of the process
                String line;
                while((line = br.readLine()) != null)
                    System.out.println(line);
                br.close();



                //if the user entered a return, just loop again
                if(commandLine.equals(" "))
                    continue;
            }

            //catch ioexception, output appropriate message, resume waiting for input
            catch (IOException e){
                System.out.println("Input Error, Please try again!");
            }

            /** The steps are:
             * 1. parse the input to obtain the command and any parameters
             * 2. create a ProcessBuilder object
             * 3. start the process
             * 4. obtain the output stream
             * 5. output the contents returned by the command
             */

        }



    }

}