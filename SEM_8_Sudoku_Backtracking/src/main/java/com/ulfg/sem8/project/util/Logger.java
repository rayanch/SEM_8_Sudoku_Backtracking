package com.ulfg.sem8.project.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Logger 
{
    private boolean muted;
    private PrintStream outputStream;
    
    public Logger()
    {
        muted = false;
        outputStream = System.out;
    }
    
    public Logger(String filename)
    {
        muted = false;
        try {
            outputStream = new PrintStream(new FileOutputStream(filename));
        } catch (FileNotFoundException ex) {
            outputStream = System.out;
            outputStream.println("Couldn't open file: " + filename);
        }
    }
    
    public void log(String tag, String data)
    {
        if(!muted)
        {
            if(!tag.isEmpty())
                outputStream.print(tag + ": ");
            outputStream.println(data);
        }
    }
    
    public void mute()
    {
        muted = true;
    }
    
    public void unmute()
    {
        muted = false;
    }
}
