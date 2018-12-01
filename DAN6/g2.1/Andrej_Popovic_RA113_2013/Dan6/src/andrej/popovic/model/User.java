package andrej.popovic.model;

public class User
{
	public User(){}
	
    public User(String ime, String type)
    {
        this.ime = ime;
        this.type = type;
    }
    
    public String ime;
    public String type;


    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User osoba = (User) o;

        return ime != null ? ime.equals(osoba.ime) : osoba.ime == null;
    }


    @Override
    public int hashCode()
    {
        return ime != null ? ime.hashCode() : 0;
    }

}
