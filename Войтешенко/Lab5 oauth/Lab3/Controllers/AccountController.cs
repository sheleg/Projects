using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace Lab3.Controllers
{
    [AllowAnonymous]
    public class AccountController : Controller
    {
        // GET: SignIn
        [HttpGet]
        public IActionResult SignIn(string returnUrl = "/Rosters")
        {
            return Challenge(new AuthenticationProperties { RedirectUri = returnUrl });
        }

        // GET: SignOut
        public async Task<IActionResult> SignOut()
        {
            await HttpContext.SignOutAsync("Cookies");
            return RedirectToAction("Index", "Home");
        }
    }
}
