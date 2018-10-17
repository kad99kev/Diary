package Reminder;
import DBase.*;
import Diary.*;
import java.util.*;
import java.time.*;

class Notes
{
    protected String noteText;
    
    public Notes()
    {}
    
    public Notes(String noteText)
    {
        this.noteText=noteText;
    }
    
    public String toString()
    {
        return noteText;
    }
}

public class Reminder extends Notes /*Want reminder always but not always notes*/
{
    private String reminderText;
    private LocalDate date;
    private int priority;
    
    public Reminder()
    {}
    
    public Reminder(LocalDate date,String reminderText, int priority)
    {
        this.reminderText=reminderText;
        this.date=date;
        this.priority=priority;
    }
    
    public Reminder(LocalDate date,String reminderText, String noteText, int priority)
    {
        super(noteText);
        this.reminderText=reminderText;
        this.date=date;
        this.priority=priority;
    }
    
    public LocalDate getDate()
    {
        return date;
    }
    
    public int getPriority()
    {
        return priority;
    }
    
    private String genExcl(int priority)
    {
        String excl=new String();
        for(int i=1; i<=priority; i++)
        {
            excl=excl+'!';
        }
        return excl;
    }
    
    public String toString()
    {
        String excl=genExcl(priority);
        
        if(noteText!=null)
            return date.format(Diary.dateformat)+" - "+reminderText+" |"+noteText+"|"+" "+excl;
        else
            return date.format(Diary.dateformat)+" - "+reminderText+" "+excl;
    }
}


