
@SuppressWarnings("serial") // no need for the serial version ID here
public class Undergraduate extends Student {

	public Undergraduate(String name, int ID, double GPA) {
		super(name, ID, GPA);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getStatus() {
		if (getGPA() >= 3)
			return "Honor";
		else if (getGPA() >= 2 && getGPA() < 3)
			return "Good";
		else
			return "probation";
	}

	@Override
	public String toString() {
		return super.toString() + "  " + "Undergraduate";
	}

}
