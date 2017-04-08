using System;
using System.Diagnostics;
using System.Threading;
using System.IO;

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
        static double f(double x)
        {
            for (int i = 0; i < 40000; i++) Math.Sin(i * Math.Cos(i));
            return Math.Pow(Math.Cos(x), 2) * Math.Sin(x);
        }

        /// <summary>
        /// Переменная для хранения результата интегрирования.
        /// </summary>
        static double res = 0.0;

        /// <summary>
        /// Вычисление определённого интеграла методом левых прямоугольников.
        /// </summary>
        /// <param name="a">Нижний предел</param>
        /// <param name="b">Верхний предел</param>
        /// <param name="eps">Точность интегрирования</param>
        static void integrate(double a, double b, double eps)
        {
            double step = b - a;
            double prev;
            res = step * f(a);
            do
            {
                step /= 2.0;
                prev = res;
                res = 0.0;
                for (double x = a; x < b; x += step) res += f(x);
                res *= step;
            } while (Math.Abs(prev - res) >= eps);
        }


        /// <summary>
        /// Параметры интегрирования.
        /// </summary>
        class IntegrateParams
        {
            public double a, b, eps;
        }

        /// <summary>
        /// Вычисление интеграла (функция запускается в отдельном потоке).
        /// </summary>
        static void integrate_thread(object p)
        {
            IntegrateParams ip = (IntegrateParams)p;    // оборачиваем параметры
            integrate(ip.a, ip.b, ip.eps);
            Console.WriteLine(Thread.CurrentThread.Name + " " + res.ToString());
        }

        static int Main(string[] args)
        {
            const double A = 0.0;
            const double B = 10.0;
            const double EPS = 0.0001;
            const int N = 10;

            Thread[] threads = new Thread[N];
            IntegrateParams[] iparams = new IntegrateParams[N];
            for (int i = 0; i < N; i++)
            {
                double step = (B - A) / N;
                iparams[i] = new IntegrateParams { a = A + step * i, b = A + step * (i + 1), eps = EPS };
                threads[i] = new Thread(new ParameterizedThreadStart(integrate_thread));
                threads[i].Priority = (i % 2 == 0 ? ThreadPriority.AboveNormal : ThreadPriority.BelowNormal);
                threads[i].Name = string.Format("Поток {0} ({1})", i, threads[i].Priority.ToString());
            }

            Thread.CurrentThread.Priority = ThreadPriority.Highest;
            for (int i = 0; i < N; i++) threads[i].Start(iparams[i]);
            Thread.CurrentThread.Priority = ThreadPriority.Lowest;

            for (int i = 0; i < N; i++) { threads[i].Join(); Thread.Sleep(0); }
            Thread.CurrentThread.Priority = ThreadPriority.Normal;

            Console.WriteLine(string.Format("Значение интеграла = {0}", res));
            Console.ReadLine();
            return 0;
        }
    }
}