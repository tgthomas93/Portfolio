using System;
using System.Linq;

namespace MovieDatabaseApp
{
    class Program
    {
        static void Main(string[] args)
        {
            while (true)
            {
                Console.Clear();
                Console.WriteLine("Movie Database App");
                Console.WriteLine("------------------");
                DisplayMenu();

                Console.Write("\nEnter your choice: ");
                string choice = Console.ReadLine();

                switch (choice)
                {
                    case "1":
                        AddMovie();
                        break;
                    case "2":
                        ViewMovies();
                        break;
                    case "3":
                        SearchMovie();
                        break;
                    case "4":
                        Environment.Exit(0);
                        break;
                    default:
                        Console.WriteLine("Invalid choice. Press any key to continue...");
                        Console.ReadKey();
                        break;
                }
            }
        }

        static void DisplayMenu()
        {
            Console.WriteLine("\nOptions:");
            Console.WriteLine("1. Add Movie");
            Console.WriteLine("2. View Movies");
            Console.WriteLine("3. Search Movie");
            Console.WriteLine("4. Exit");
        }

        static void AddMovie()
        {
            using (var context = new MovieContext())
            {
                Console.Write("\nEnter movie title: ");
                string title = Console.ReadLine();

                Console.Write("Enter director: ");
                string director = Console.ReadLine();

                Console.Write("Enter release year: ");
                if (int.TryParse(Console.ReadLine(), out int year))
                {
                    Movie newMovie = new Movie(title, director, year);  // Provide the required arguments here
                    context.Movies.Add(newMovie);
                    context.SaveChanges();
                    Console.WriteLine("\nMovie added successfully!");
                }
                else
                {
                    Console.WriteLine("\nInvalid year. Movie not added.");
                }
            }

            Console.WriteLine("\nPress any key to continue...");
            Console.ReadKey();
        }


        static void ViewMovies()
        {
            using (var context = new MovieContext())
            {
                var movies = context.Movies.ToList();

                Console.WriteLine("\nMovie List:");
                Console.WriteLine("-----------");

                if (movies.Count == 0)
                {
                    Console.WriteLine("No movies.");
                }
                else
                {
                    foreach (var movie in movies)
                    {
                        Console.WriteLine(movie);
                    }
                }
            }

            Console.WriteLine("\nPress any key to continue...");
            Console.ReadKey();
        }

        static void SearchMovie()
        {
            using (var context = new MovieContext())
            {
                Console.Write("\nEnter movie title to search: ");
                string searchTerm = Console.ReadLine();

                var searchResults = context.Movies
                    .Where(movie => movie.Title.ToLower().Contains(searchTerm.ToLower()))
                    .ToList();

                if (searchResults.Count == 0)
                {
                    Console.WriteLine("\nNo matching movies found.");
                }
                else
                {
                    Console.WriteLine("\nSearch Results:");
                    Console.WriteLine("---------------");
                    foreach (var movie in searchResults)
                    {
                        Console.WriteLine(movie);
                    }
                }
            }

            Console.WriteLine("\nPress any key to continue...");
            Console.ReadKey();
        }
    }
}
