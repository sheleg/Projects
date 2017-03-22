using System;
using System.Threading;

namespace multithreading_02
{
    class ThreadSleepApp
    {
        /// <summary>
        /// Код потока, который будет выполняться параллельно главному потоку.
        /// </summary>
        public static void ThreadProc()
        {
            Console.WriteLine("Второй поток работает.");
            int sleepTime = 5000;
            Console.WriteLine("Второй поток засыпает на {0} секунд.", sleepTime / 1000);
            Thread.Sleep(sleepTime);
            Console.WriteLine("Второй поток снова работает.");
        }

        /// <summary>
        /// Главный поток приложения.
        /// </summary>
        public static void Main()
        {
            Console.WriteLine("Главный поток - создаём ещё один поток.");
            Thread t = new Thread(ThreadProc); 
            t.Start();
            Console.WriteLine("Главный поток - второй поток запущен."); 
        } 
    }
}
