using System;
using System.Threading;

namespace multithreading_01
{
    class SimpleThreadApp {

        /// <summary>
        /// Код потока, который будет выполняться параллельно главному потоку.
        /// </summary>
        public static void ThreadProc()
        {
            Console.WriteLine("Второй поток работает...");
        }

        /// <summary>
        /// Главный поток приложения.
        /// </summary>
        public static void Main() {

            ThreadStart worker = new ThreadStart(ThreadProc);

            Console.WriteLine("Главный поток - создаём ещё один поток.");

            Thread t = new Thread(worker); 
            t.Start();

            Console.WriteLine ("Главный поток - второй поток запущен."); 
        } 
    }
}
