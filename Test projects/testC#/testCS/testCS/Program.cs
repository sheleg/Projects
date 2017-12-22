using System;
using System.Collections.Generic;
using System.IO;

namespace testCS
{
    internal static class Program
    {
        private static void Main(string[] args)
        {
//            var firstFile = File.ReadAllText("in.txt")
//                .Split(new char[] {' ', '\r', '\n'}, StringSplitOptions.RemoveEmptyEntries);
//            var secondFile = File.ReadAllText("polynom.txt")
//                .Split(new char[] {' ', '\r', '\n'}, StringSplitOptions.RemoveEmptyEntries);
//            var pol = new int[firstFile[0].Length];
//            for (var i = 0; i < pol.Length; i++)
//            {
//                pol[i] = firstFile[0].ToCharArray()[i] - '0';
//            }
//
//            var x = new int[secondFile[0].Length];
//            var y = new int[secondFile[2].Length];
//            var oper = new char[secondFile[1].Length];
//
//            for (var i = 0; i < x.Length; i++)
//            {
//                x[i] = secondFile[0].ToCharArray()[i] - '0';
//            }
//            for (var i = 0; i < y.Length; i++)
//            {
//                y[i] = secondFile[2].ToCharArray()[i] - '0';
//            }

            int[] test = {1, 1, 1};
            int[] test1 = {0, 0, 1};
            test = Mod(test, test1);
            foreach (var t in test)
            {
                Console.WriteLine(t);
            }
            Console.ReadKey();
        }

        private static int[] plus(int[] x, int[] y)
        {
            var res = x.Length > y.Length ? x : y;
            for (var i = 0; i < Math.Min(x.Length, y.Length); i++)
            {
                res[i] = (y[i] + x[i]) % 2;
            }
            return res;
        }

        private static int[] Mod(int[] x, int[] m)
        {
            var kx = x.Length - 1;
            while (x[kx] == 0 && kx > 0) kx--;
            var km = m.Length - 1;
            while (m[km] == 0 && km > 0) km--;
            var del = kx - km;
            if (del < 0) return x;

            for (var i = 0; i < km + 1; i++)
            {
                x[i + del] = (m[i] + x[i + del]) % 2;
            }
            var ost = Mod(x, m);
            return ost;
        }

        private static int[] Mult(int[] x, int[] y, int[] m)
        {
            var res = new int[x.Length + y.Length];
            for (var i = 0; i < x.Length; i++)
            {
                for (var j = 0; j < y.Length; j++)
                {
                    res[i + j] += x[i] * y[j];
                    res[i + j] %= 2;
                }
            }
            return (Mod(res, m));
        }

        public static int[] Dev(int[] x, int[] y, int[] m)
        {
            var res = new int[x.Length];

            var kx = x.Length - 1;
            while (x[kx] == 0 && kx > 0) kx--;
            var ky = y.Length - 1;
            while (y[ky] == 0 && ky > 0) ky--;
            var del = kx - ky;
            if (del < 0) return new int[] {0};

            for (var i = 0; i < ky + 1; i++)
            {
                x[i + del] = (y[i] + x[i + del]) % 2;
            }
            res[del] = 1;
            res = plus(res, Dev(x, y, m));
            return Mod(res, m);
        }

        private static int[] Exp(int[] x, int n, int[] m)
        {
            var res = x;
            for (var i = 1; i < n; i++)
            {
                res = Mult(res, x, m);
            }

            return res;
        }

        public static int[] Obr(int[] x, int[] m)
        {
            var res = x;
            var resP = res;
            while (!Check(res))
            {
                resP = res;
                res = Mult(res, x, m);
            }
            return resP;
        }

        private static bool Check(IReadOnlyList<int> res)
        {
            if (res[0] == 0) return false;
            for (var i = 1; i < res.Count; i++)
            {
                if (res[i] == 1) return false;
            }
            return true;
        }
    }
}