using System;
using System.Threading;

class ThreadAbortApp
{
    /// <summary>
    /// Код рабочего потока.
    /// </summary>
    public static void ThreadProc(object args)
    {
        string name = args.ToString();
        Console.WriteLine("Рабочий поток запущен ({0}).", name);
        for (int i = 0; i < 10; i++)
        {
            // Цикл с бесполезной работой (для загрузки процессора).
            for (int j = 0; j < 1000000; j++) 
                Math.Sin(Math.Cos(j) + Math.Atan(j) * Math.Sin(Math.Cos(j) + Math.Atan(i)));
            Console.WriteLine("{0} -> {1}", name, i);
        }
        Console.WriteLine("Рабочий поток завершён ({0}).", name);
    }

    public static void Main()
    {
        // Разрешить данному процессу использовать только один процессор.
        System.Diagnostics.Process.GetCurrentProcess().ProcessorAffinity = new IntPtr(0x0001);

        // Создаём два рабочих потока.
        Console.WriteLine("Главный поток - запускаем рабочие потоки.");
        Thread t1 = new Thread(ThreadProc);
        Thread t2 = new Thread(ThreadProc);

        // Устанавливаем приоритеты потоков.
        t1.Priority = ThreadPriority.BelowNormal;
        t2.Priority = ThreadPriority.AboveNormal;

        // Запускаем потоки.
        t1.Start("первый поток");
        t2.Start("второй поток");

        // Ждём завершения обоих потоков.
        t1.Join();
        t2.Join();
        Console.WriteLine("Главный поток - выполнение обоих потоков завершено.");
    }
}