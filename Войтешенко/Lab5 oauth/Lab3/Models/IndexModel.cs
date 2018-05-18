using System;
using System.Security.Claims;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace Lab3.Models
{
    public class IndexModel
    {
        public string GitHubAvatar { get; set; }

        public string GitHubLogin { get; set; }

        public string GitHubName { get; set; }

        public string GitHubUrl { get; set; }
       
    }
}
