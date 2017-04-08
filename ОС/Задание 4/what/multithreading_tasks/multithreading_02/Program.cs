using System;
using System.Diagnostics;
using System.Threading;
using System.IO;
using System.Collections.Generic;

namespace IntegrateThread
{
    /// <summary>
    /// Пример интегрирования функции.
    /// </summary>
    class Program
    {
        /// <summary>
        /// Подынтегральная функция.
        /// </summary>
        static double Func(double x)
        {
            for (int i = 0; i < 1000; i++) Math.Sin(i * Math.Cos(i));
            return Math.Pow(x, 2) * Math.Sin(x);
        }

        /// <summary>
        /// Вычисление определённого интеграла методом левых прямоугольников.
        /// </summary>
        /// <param name="a">Нижний предел</param>
        /// <param name="b">Верхний предел</param>
        /// <param name="eps">Точность интегрирования</param>
        /// <returns>Численное значение интеграла</returns>
        static double IntegrateF(double a, double b, double eps)
        {
            double step = b - a;
            double prev, curr = step * Func(a);
            do
            {
                step /= 2.0;
                prev = curr;
                curr = 0.0;
                for (double x = a; x < b; x += step) curr += Func(x);
                curr *= step;
            } while (Math.Abs(prev - curr) >= eps);
            return curr;
        }

        /// <summary>
        /// Класс для передачи параметров.
        /// </summary>
        internal class IntParams
        {
            public double A, B, EPS, _R;
        }

        /// <summary>
        /// Функция для вычисления интеграла - запускается в отдельном потоке.
        /// </summary>
        static void IntegrThreadProc(object p)
        {
            IntParams ip = (IntParams)p;
            ip._R = IntegrateF(ip.A, ip.B, ip.EPS);
            Console.WriteLine(Thread.CurrentThread.Name + " " + ip._R.ToString());
            Thread.Sleep(0);
        }

        const double POINT_A = 0.0;
        const double POINT_B = 10.0;
        const double EPSILON = 0.0001;
        const int NUM_OF_THREADS = 10;

        static void Main(string[] args)
        {
            IntParams[] iparams = new IntParams[NUM_OF_THREADS];
            List<Thread> threads = new List<Thread>(NUM_OF_THREADS);

            Thread.CurrentThread.Priority = ThreadPriority.Highest;
            for (int i = 0; i < NUM_OF_THREADS; i++)
            {
                threads.Add(new Thread(new ParameterizedThreadStart(IntegrThreadProc)));
                threads[i].Priority = (i % 2 != 0 ? ThreadPriority.AboveNormal : ThreadPriority.BelowNormal);
                double step = (POINT_B - POINT_A) / NUM_OF_THREADS;
                iparams[i] = new IntParams { A = POINT_A + step * i, B = POINT_A + step * (i + 1), EPS = EPSILON, _R = 0 };
                threads[i].Name = string.Format("Поток №{0} (приоритет = {1})", i, threads[i].Priority.ToString());
                threads[i].Start(iparams[i]);
            }
            Console.WriteLine("Потоки запущены");
            Thread.Sleep(0);
            Thread.CurrentThread.Priority = ThreadPriority.Lowest;

            while(threads[NUM_OF_THREADS-1].ThreadState != System.Threading.ThreadState.Running)
                Thread.Sleep(30);

            for (int i = 0; i < NUM_OF_THREADS; i++)
            {
                if (threads[i].IsAlive)
                {
                    threads[i].Priority = ThreadPriority.Highest;
                    threads[i].Suspend();
                }
            }
            Thread.CurrentThread.Priority = ThreadPriority.Normal;

            double sum = 0.0;
            for (int i = 0; i < NUM_OF_THREADS; sum += iparams[i++]._R) ;
            Console.WriteLine(string.Format("Значение интеграла = {0}", sum));
        }
    }
}