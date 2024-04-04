using System.Runtime.Intrinsics.X86;

public class Movie
{
    public string Title { get; set; }
    public string Director { get; set; }
    public int Year { get; set; }
    public int ID { get; set; }

    public Movie(string title, string director, int year)
    {
        Title = title;
        Director = director;
        Year = year;
    }

    public override string ToString()
    {
        return $"{Title} ({Year}) - Directed by: {Director}";
    }
}
