using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

namespace Lab3.Models
{
    public partial class crittersContext : DbContext
    {
        public virtual DbSet<Rosters> Rosters { get; set; }

        public crittersContext(DbContextOptions<crittersContext> options) : base(options)
        { }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
                optionsBuilder.UseMySql("DefaultConnection");
            }
        }

       

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Rosters>(entity =>
            {
                entity.HasKey(e => e.Jersey);

                entity.ToTable("rosters");

                entity.Property(e => e.Jersey)
                    .HasColumnName("jersey")
                    .HasColumnType("int(11)");

                entity.Property(e => e.Birthcity)
                    .HasColumnName("birthcity")
                    .HasColumnType("text");

                entity.Property(e => e.Birthday)
                    .HasColumnName("birthday")
                    .HasColumnType("text");

                entity.Property(e => e.Birthstate)
                    .HasColumnName("birthstate")
                    .HasColumnType("text");

                entity.Property(e => e.Fname)
                    .HasColumnName("fname")
                    .HasColumnType("text");

                entity.Property(e => e.Height)
                    .HasColumnName("height")
                    .HasColumnType("int(11)");

                entity.Property(e => e.Playerid)
                    .HasColumnName("playerid")
                    .HasColumnType("text");

                entity.Property(e => e.Position)
                    .HasColumnName("position")
                    .HasColumnType("text");

                entity.Property(e => e.Sname)
                    .HasColumnName("sname")
                    .HasColumnType("text");

                entity.Property(e => e.Weight)
                    .HasColumnName("weight")
                    .HasColumnType("int(11)");
            });
        }
    }
}
