using System;
using System.Threading;

class ThreadAbortApp {

    public static void ThreadProc() 
    {
        try 
        {
            Console.WriteLine("Рабочий поток запущен.");
            for (int i = 0; i < 10; i++) {
                Thread. Sleep(1000);
                Console.WriteLine("Рабочий поток -> {0}", i); 
            }
            Console.WriteLine("Рабочий поток завершён"); 
        }
        catch(ThreadAbortException e)
        {
            // ThreadAbortException здесь обработано не будет!
        }
        finally
        {
            Console.WriteLine("В рабочем потоке возникло необработанное исключение!"); 
        } 
    }

    public static void Main() 
    {
        Console.WriteLine("Главный поток - запускаем рабочий поток.");
        Thread t = new Thread(ThreadProc); 
        t.Start();

        Console.WriteLine("Главный поток - засыпаем на 5 секунд."); 
        Thread.Sleep(5000);

        Console.WriteLine("Главный поток - прерываем рабочий поток."); 
        t.Abort(); 
    } 
}