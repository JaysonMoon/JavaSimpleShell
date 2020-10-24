import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleShell {

    public static void main(String[] args) throws java.io.IOException {



        String commandLine;
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

        ProcessBuilder proc = new ProcessBuilder();
        List<String> history = new ArrayList<>();

        do {

            System.out.print("jsh>");
            commandLine = console.readLine();

            String[] commands = commandLine.split(" ");
            List<String> list = new ArrayList<>();
            Collections.addAll(list, commands);

            history.addAll(list); //adds everything to the history list
            try {
                String last = list.get(list.size() - 1);
                if (last.equals("history")) {
                    for (int i = 0; i < history.size() - 1; i++)
                        System.out.println(i + " " + history.get(i)); //show index for !! function later
                    continue;
                }

                if (list.contains("cd")) {
                    if (last.equals("cd")) {
                        File file = new File(System.getProperty("user.home")); //user's home directory
                        proc.directory(file); //"...sets this process builder's working directory."
                        continue;

                    } else {
                        String directory = list.get(1);

                        File anotherFile = new File(directory);

                        if (anotherFile.exists()) {
                            System.out.println(directory); //show the new directory and set process builder
                            proc.directory(anotherFile);
                            continue;
                        } else {
                            System.out.print(": ");
                        }
                    }
                }

                if (last.equals("!!")) {
                    proc.command(history.get(history.size() - 2));

                } else if
                (last.charAt(0) == '!') { //! and then the number of the command from history
                    if (Character.getNumericValue(last.charAt(1)) <= history.size())
                        proc.command(history.get(Character.getNumericValue(last.charAt(1))));
                } else {
                    proc.command(list);
                }

                Process process = proc.start();
                InputStream is = process.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                String line;
                while ((line = br.readLine()) != null) //process the line and then close buffered reader
                    System.out.println(line);
                br.close();

                if (commandLine.equals(" ")) {
                    continue;
                }

            } catch (IOException e) {
                System.out.println("Invalid input " + e);
                continue;
            }
        } while(true);
    }
}