package logic;

import java.io.*;
import java.util.concurrent.TimeUnit;

public class SpeechToTextService{
    public static String convertAudioToText(String filePath) {
        try {
            String pythonExecutable="python"; 
            String scriptPath="voice_listener.py";
            File workingDir=new File("."); 

            ProcessBuilder pb=new ProcessBuilder(pythonExecutable, scriptPath);
            pb.directory(workingDir);
            pb.redirectErrorStream(true);
            Process p=pb.start();

            BufferedReader reader=new BufferedReader(new InputStreamReader(p.getInputStream()));
            
            if (p.waitFor(10,TimeUnit.SECONDS)){
                String result=reader.readLine();
                if (result!=null && !result.isEmpty() && !result.equalsIgnoreCase("Error")) {
                    return result.trim().toLowerCase();
                }
            } else {
                p.destroy();
            }
        } catch (Exception e){
            System.out.println("Bridge Error: " + e.getMessage());
        }
        return "voice not recognized";
    }
}