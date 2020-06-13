import java.io.Serializable;
import java.util.ArrayList;

public class Department implements Serializable {

	private static final long serialVersionUID = 1L;
	private String code;
	private String name;
	private ArrayList<Student> list;

	public Department(String name, String code) {
		this.name = name;
		this.code = code;
		list = new ArrayList<Student>();
	}

	public void addGra(String name, int ID, double GPA) {
		Student newgra = new Graduate(name, ID, GPA);
		list.add(newgra);
	}

	public void addUnd(String name, int ID, double GPA) {
		Student newund = new Undergraduate(name, ID, GPA);
		list.add(newund);
	}

	public void deleteStu(Student s) {
		list.remove(s);
	}

	public String getName() {
		return name;

	}

	public String getCode() {
		return code;
	}

	public ArrayList<Student> getStudents() {
		return list;
	}

	public String toString() {
		return name + "  " + code + "     " + list.size();
	}

}
