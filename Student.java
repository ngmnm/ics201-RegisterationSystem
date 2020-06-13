import java.io.Serializable;

@SuppressWarnings("serial") // no need for the serial version ID here
public abstract class Student implements Comparable<Student>, Serializable {
	private String name;
	private int ID;
	private double GPA;

	public Student(String name, int ID, double GPA) {
		this.name = name;
		this.ID = ID;
		this.GPA = GPA;
	}

	public abstract String getStatus();

	public double getGPA() {
		return GPA;
	}

	public String getName() {
		return name;
	}

	public int getID() {
		return ID;
	}

	public int compareTo(Student st) {
		return this.ID - st.ID;
	}

	public String toString() {
		return name + "  " + GPA + "  " + ID + "  " + getStatus();

	}
}
