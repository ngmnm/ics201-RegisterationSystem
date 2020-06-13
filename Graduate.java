
@SuppressWarnings("serial") // no need for the serial version ID hereF
public class Graduate extends Student {

	public Graduate(String name, int ID, double GPA) {
		super(name, ID, GPA);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getStatus() {
		if (getGPA() >= 3)
			return "Good";
		else
			return "Probation";
	}

	@Override
	public String toString() {
		return super.toString() + "  " + "Graduate";
	}

}
