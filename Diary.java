package Diary;
import DBase.*;
import Reminder.*;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Diary
{
    private Database dbase;
    public static DateTimeFormatter dateformat =DateTimeFormatter.ofPattern("d/M/y");
    Scanner input=new Scanner(System.in);
    Diary() //Creates database
    {
        dbase=new Database();
    }
    
    private LocalDate readDate()
    {
        System.out.println("Enter date as day/month/year [eg:1/1/2018]");
        LocalDate date;
        try
        {
            date = LocalDate.parse(input.nextLine(), dateformat);
        }
        catch (Exception e)
        {
            System.out.println("Error. Please try again.");
            return readDate();
        }
        return date;
    }
    
    public int getPriority()
    {
        System.out.println("Enter priority");
        System.out.println("3->High priority");
        System.out.println("2->Medium priority");
        System.out.println("1->Low priority");
        System.out.println("0->No priority");
        try
        {
            int priority=input.nextInt();
            if(priority>=0 && priority<4)
            {
                return priority;
            }
            else
            {
                System.out.println("Please enter a valid priority");
                input.nextLine();
                input.nextLine();
                return getPriority();
            }
        }
        catch(Exception e)
        {
            System.out.println("Please enter a valid priority");
            input.nextLine();
            input.nextLine();
            return getPriority();
        }
    }
    
    public void addReminder()
    {
        LocalDate date=readDate();
        System.out.println("Enter event");
        String reminderText=input.nextLine();
        int priority=getPriority();
        addNote(date,reminderText,priority);
    }
    
    public void addNote(LocalDate date, String reminderText, int priority) /*Created this method incase there's an error while entering 1 or 0*/
    {
        input.nextLine();
        String choice="0";
        System.out.println("Would you like to add a note?");
        System.out.println("Enter: 1-->Yes or 0-->No");
        choice=input.nextLine();
        if (choice.equals("1"))
        {
            System.out.println("Enter note");
            String noteText=input.nextLine();
            dbase.createReminderwithNotes(date,reminderText,noteText,priority);
        }
        else if (choice.equals("0"))
        {
            dbase.createReminder(date,reminderText,priority);
        }
        else
        {
            System.out.println("Sorry, I didn't catch that. Please enter 1 or 0");
            addNote(date,reminderText,priority);
        }
    }
    
    
    public void printReminder(LocalDate day)
    {
        ArrayList<Reminder> rems = dbase.findDay(day);
        for (Reminder r: rems) {
            System.out.println(r);
        }
    }
    
    public void searchReminder()
    {
        ArrayList<Reminder> rems=new ArrayList<>();
        int monthKey;
        System.out.println("Enter:");
        System.out.println("1. To show events for a particular day");
        System.out.println("2. To show all events for a particular month");
        System.out.println("3. To show all events for the year");
        String choice=input.nextLine();
        switch(choice)
        {
            case "1":
            {
                LocalDate date=readDate();
                rems=dbase.findDay(date);
                searchReminderPrint(rems);
                break;
            }
                
            case "2":
            {
                System.out.println("Enter the month you'd like to read");
                System.out.println("Enter 1 for January, 2 for February, etc.");
                try
                {
                    monthKey=input.nextInt();
                    rems=dbase.findMonth(monthKey);
                    input.nextLine();
                    searchReminderPrint(rems);
                }
                catch (Exception e)
                {
                    System.out.println("Error. Please try again.");
                    input.nextLine();
                    searchReminder();
                }
                break;
            }
                
            case "3":
            {
                rems=dbase.allEvents();
                searchReminderPrint(rems);
                break;
            }
                
            default:
            {
                System.out.println("Please enter a valid number\nPress enter to continue");
                input.nextLine();
                searchReminder();
            }
        }
    }
    
    public void searchReminderPrint(ArrayList<Reminder> rems)
    {
        if (rems.size() > 0)
        {
            System.out.println("Entries found: ");
            for(Reminder r: rems)
                System.out.println(r);
        }
        else
        {
            // Nothing found
            System.out.println("No entries were found.");
        }
        input.nextLine(); //Used these so that user can read before homescreen pops again
    }
    
    public void deleteReminder() //Method to delete
    {
        System.out.println("Enter date to be cleared");
        LocalDate date=readDate();
        dbase.deleteReminder(date);
    }
    
    public void displayHomeScreen() //Homescreen method
    {
        System.out.println("\n");
        System.out.println("Welcome to your diary\n");
        System.out.println("Today is: "+LocalDate.now().format(dateformat));
        System.out.println();
        // printing the home screen
        System.out.println("Yesterday:\n------");
        printReminder(LocalDate.now().minusDays(1));
        System.out.println();
        System.out.println("Today:\n------");
        printReminder(LocalDate.now());
        System.out.println();
        System.out.println("Tomorrow:\n---------");
        printReminder(LocalDate.now().plusDays(1));
        System.out.println();
    }
    
    public static void main(String[]args)
    {
        Scanner input=new Scanner(System.in);
        Diary diary=new Diary();
        boolean check=true;
        String choice;
        while(check)
        {
            diary.displayHomeScreen();
            System.out.println();
            System.out.println("---------");
            System.out.println("Enter:");
            System.out.println("---------");
            System.out.println("1. To add an event\n2. To read events\n3. To delete events\n4. To exit");
            System.out.println("---------");
            choice=input.nextLine();
            switch(choice)
            {
                case "1":
                {
                    diary.addReminder();
                    break;
                }
                case "2":
                {
                    diary.searchReminder();
                    break;
                }
                case "3":
                {
                    diary.deleteReminder();
                    break;
                }
                case "4":
                {
                    check=false;
                    System.out.println("Closing your virtual diary");
                    System.out.println("Have a nice day! :)");
                    break;
                }
                default:
                {
                    System.out.println("Please enter a valid number");
                    input.nextLine();
                }
            }
        }
    }
}

