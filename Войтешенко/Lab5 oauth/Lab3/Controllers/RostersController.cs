using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using Lab3.Models;
using Snickler.EFCore;
using Microsoft.AspNetCore.Authorization;

// SHELEG <3
// SHELEG PRIVET <3

namespace Lab3
{
    public class RostersController : Controller
    {
        private readonly crittersContext _context;

        public RostersController(crittersContext context)
        {
            _context = context;
        }

        // GET: Rosters
        public async Task<IActionResult> Index()
        {
            _context.LoadStoredProc("selectAll").ExecuteStoredProc((handler) =>
            {
                ViewData["selectResult"] = handler.ReadToList<Rosters>();
            });
            return View(await _context.Rosters.ToListAsync());
        }

        // GET: Rosters/Details/5
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var rosters = await _context.Rosters
                .SingleOrDefaultAsync(m => m.Jersey == id);
            if (rosters == null)
            {
                return NotFound();
            }

            return View(rosters);
        }

        // GET: Rosters/Create
        public IActionResult Create()
        {

            return View();
        }

        // POST: Rosters/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("Playerid,Jersey,Fname,Sname,Position,Birthday,Weight,Height,Birthcity,Birthstate")] Rosters rosters)
        {
            if (ModelState.IsValid)
            {
                //_context.Add(rosters);
                _context.LoadStoredProc("insertProc")
                        .WithSqlParam("plid", rosters.Playerid)
                        .WithSqlParam("jer", rosters.Jersey)
                        .WithSqlParam("fn", rosters.Fname)
                        .WithSqlParam("sn", rosters.Sname)
                        .WithSqlParam("pos", rosters.Position)
                        .WithSqlParam("birth", rosters.Birthday)
                        .WithSqlParam("wght", rosters.Weight)
                        .WithSqlParam("hght", rosters.Height)
                        .WithSqlParam("birthc", rosters.Birthcity)
                        .WithSqlParam("births", rosters.Birthstate)
               .ExecuteStoredProc((handler) =>
               {
               });
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            return View(rosters);
        }

        // GET: Rosters/Edit/5
        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var rosters = await _context.Rosters.SingleOrDefaultAsync(m => m.Jersey == id);
            if (rosters == null)
            {
                return NotFound();
            }
            return View(rosters);
        }

        // POST: Rosters/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(int id, [Bind("Playerid,Jersey,Fname,Sname,Position,Birthday,Weight,Height,Birthcity,Birthstate")] Rosters rosters)
        {
            if (id != rosters.Jersey)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    //_context.Update(rosters);
                    _context.LoadStoredProc("updateProc")
                       .WithSqlParam("plid", rosters.Playerid)
                       .WithSqlParam("jer", rosters.Jersey)
                       .WithSqlParam("fn", rosters.Fname)
                       .WithSqlParam("sn", rosters.Sname)
                       .WithSqlParam("pos", rosters.Position)
                       .WithSqlParam("birth", rosters.Birthday)
                       .WithSqlParam("wght", rosters.Weight)
                       .WithSqlParam("hght", rosters.Height)
                       .WithSqlParam("birthc", rosters.Birthcity)
                       .WithSqlParam("births", rosters.Birthstate)
              .ExecuteStoredProc((handler) =>
              {
              });
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!RostersExists(rosters.Jersey))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
                }
                return RedirectToAction(nameof(Index));
            }
            return View(rosters);
        }

        // GET: Rosters/Delete/5
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var rosters = await _context.Rosters
                .SingleOrDefaultAsync(m => m.Jersey == id);
            if (rosters == null)
            {
                return NotFound();
            }

            return View(rosters);
        }

        // POST: Rosters/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            var rosters = await _context.Rosters.SingleOrDefaultAsync(m => m.Jersey == id);
            //_context.Rosters.Remove(rosters);
            _context.LoadStoredProc("deleteProc")
                        .WithSqlParam("jer", rosters.Jersey)
                    .ExecuteStoredProc((handler) => { });
            
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool RostersExists(int id)
        {
            return _context.Rosters.Any(e => e.Jersey == id);
        }
    }
}
