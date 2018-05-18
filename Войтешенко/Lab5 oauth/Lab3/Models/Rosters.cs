using System;
using System.Collections.Generic;

namespace Lab3.Models
{
    public partial class Rosters
    {
        public string Playerid { get; set; }
        public int Jersey { get; set; }
        public string Fname { get; set; }
        public string Sname { get; set; }
        public string Position { get; set; }
        public string Birthday { get; set; }
        public int? Weight { get; set; }
        public int? Height { get; set; }
        public string Birthcity { get; set; }
        public string Birthstate { get; set; }
    }
}
