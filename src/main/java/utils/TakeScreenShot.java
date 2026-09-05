package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TakeScreenShot {
    public static void main(String[] args){
        createFileName();
    }


    private static String createFileName(){
        SimpleDateFormat formater =
                new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        System.out.println(System.currentTimeMillis());
        Date date = new Date(System.currentTimeMillis());
        System.out.println(date);
        String curDate = formater.format(date);
        System.out.println(curDate);
        String fileName = "src/test/resources/screenshots"+curDate+".png";
        System.out.println(fileName);
        return fileName;
    }

    public static void takeScreenShot(TakesScreenshot screenshot){
        String fileName = createFileName();

        File screen = screenshot.getScreenshotAs(OutputType.FILE);
        try {
            Files.copy(screen.toPath(),new File(fileName).toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
