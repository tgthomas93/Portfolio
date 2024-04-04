using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;

namespace MovieDatabaseApp
{
    public class MovieContext : DbContext
    {
        public DbSet<Movie> Movies { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseSqlServer(@"Server=DESKTOP-NK5D6E4\SQLEXPRESS2;Database=MovieDatabase;Integrated Security=True;TrustServerCertificate=True;");
        }
    }
}
