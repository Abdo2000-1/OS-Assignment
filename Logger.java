package hospital;

public class Logger 
{

    public static synchronized void log(String messge)
     {
        System.out.println(messge);
    }
}
