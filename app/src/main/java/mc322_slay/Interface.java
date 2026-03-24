package mc322_slay;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

public class Interface {
    
    private String folderPath = "MC322_Slay/assets";

    public void initialScreen() {

        Path filePath = Path.of(folderPath, "initialScreenArt.txt");
        
        try {
            Files.lines(filePath).forEach(System.out::println);
            
        } 
        catch (IOException e) {
        }
    }

    public void gameOver() {

        Path filePath = Path.of(folderPath, "gameOver.txt");
        
        try {
            Files.lines(filePath).forEach(System.out::println);
            
        } 
        catch (IOException e) {
        }
    }

    public void youWin() {
        Path filePath = Path.of(folderPath, "youwin.txt");
        
        try {
            Files.lines(filePath).forEach(System.out::println);
            
        } 
        catch (IOException e) {
        }
    }
    }
