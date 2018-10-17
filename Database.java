
package DBase;
import Reminder.*;
import Diary.*;
import java.util.*;
import java.time.*;

class ReminderComparator implements Comparator<Reminder>
{
    public int compare(Reminder r1, Reminder r2)
    {
        int r1Pri = r1.getPriority();
        int r2Pri = r2.getPriority();
        if(r1.getDate().equals(r2.getDate()))
        {
            if (r1Pri > r2Pri ) /*If Collections.sort got a positive value from comparator it will place the first element after the second one and vice versa if receives a negative value.*/
            {
                return -1;
            }
            else if (r1Pri < r2Pri )
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }
        else
        {
            return r1.getDate().compareTo(r2.getDate()); //Before -1, After 1
        }
    }
}

public class Database
{
    private ArrayList<Reminder> rems;
    
    public Database() //Constructor that intializes entries ArrayList
    {
        rems=new ArrayList<>();
    }
    
    public void createReminderwithNotes(LocalDate date, String reminderText, String noteText, int priority) //Creates an object of Reminder and adds to ArrayList
    {
        rems.add(new Reminder(date,reminderText,noteText,priority));
    }
    
    public void createReminder(LocalDate date, String reminderText, int priority)
    {
        rems.add(new Reminder(date,reminderText,priority));
    }
    
    public ArrayList<Reminder> allEvents()
    {
        ArrayList<Reminder> total=new ArrayList<>();
        for(Reminder r:rems)
        {
            total.add(r);
            Collections.sort(total, new ReminderComparator());
        }
        return total;
    }
    
    public ArrayList<Reminder> findMonth(int monthKey)
    {
        ArrayList<Reminder> monthTotal=new ArrayList<>();
        for(Reminder r: rems)
        {
            if(r.getDate().getMonthValue()==monthKey)
            {
                monthTotal.add(r);
                Collections.sort(monthTotal, new ReminderComparator());
            }
        }
        return monthTotal;
    }
    
    public ArrayList<Reminder> findDay(LocalDate date) //Reads all Entries of particular day
    {
        ArrayList<Reminder> toread=new ArrayList<>();
        for(Reminder r:rems)
        {
            if(r.getDate().equals(date))
            {
                toread.add(r);
                Collections.sort(toread, new ReminderComparator());
            }
        }
        return toread;
    }
    
    public void deleteReminder(LocalDate date) //Deletes entries of a particular day
    {
        ArrayList<Reminder> todelete=findDay(date); //Stores all entries of that day
        for(Reminder r:todelete)
        {
            rems.remove(r);
        }
    }
}


