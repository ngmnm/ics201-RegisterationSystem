import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;

import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.*;

public class RegistrationSystem extends Application {
	GridPane grid;
	// MenuItem m1;
	MenuButton stu;
	Scene win;
	static ArrayList<Department> deps;
	static FileOutputStream out;
	static FileInputStream in;
	static ObjectOutputStream write;
	static ObjectInputStream read;
	Department ics201;
	static File dataBase = new File("DataBase.dat");

	@SuppressWarnings("unchecked") // no need to check since the object will always be of type
									// "ArrayList<Department>"
	public static void main(String[] args) {
		deps = new ArrayList<Department>();
		try {
			// ------------------------------reading from file (dataBase)
			if (!dataBase.exists()) {
				System.out.println("File Not Found: Genrating a New Data Base");
				dataBase.createNewFile();
				Department engl = new Department("english", "ENGL");
				Department math = new Department("Mathamatics", "MATH");
				Department phys = new Department("Physics", "PHYS");
				deps.add(engl);
				deps.add(math);
				deps.add(phys);
			} else {
				in = new FileInputStream(dataBase);
				read = new ObjectInputStream(in);
				Object temp = read.readObject();
				deps = (ArrayList<Department>) temp;
			}
			launch(args);
		} catch (Exception e) {
			System.out.println(":(");
			e.getMessage();
			e.getStackTrace();
		}
	}
	@Override
	
	public void start(Stage primaryStage) throws Exception {// ------------------------- start method
		grid = new GridPane();

		Button conf = new Button("Confirm");
		Image confImg = new Image(new FileInputStream("confirm.png"), 20, 20, false, false);
		conf.setGraphic(new ImageView(confImg));

		Button remove = new Button("Remove student"); // --------------------remove student button (S)
		Image remImg = new Image(new FileInputStream("remove.png"), 20, 20, false, false);
		remove.setGraphic(new ImageView(remImg));

		remove.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				ChoiceBox<Department> de = new ChoiceBox<Department>();
				ChoiceBox<Student> st = new ChoiceBox<Student>();
				Label depL = new Label("Select Department: ");
				Label stuL = new Label("Select Student: ");
				de.getSelectionModel().selectedIndexProperty().addListener((v, oldValue, newValue) -> {
					int temp = (int) newValue;
					st.getItems().clear();
					for (int i = 0; i < deps.get(temp).getStudents().size(); i++) {
						st.getItems().add(deps.get(temp).getStudents().get(i));
					}
				});

				GridPane remove = new GridPane();
				remove.setPadding(new Insets(10));
				remove.setVgap(10);
				remove.setHgap(10);
				for (int i = 0; i < deps.size(); i++) {
					de.getItems().add(deps.get(i));
				}

				if (de.getSelectionModel().getSelectedItem() == deps.get(0)) {
					for (int j = 0; j < deps.get(0).getStudents().size(); j++) {
						st.getItems().add(deps.get(0).getStudents().get(j));

					}
				}
				Button sub = new Button("Submit");
				Image subImg = null;
				try {
					subImg = new Image(new FileInputStream("confirm.png"), 20, 20, false, false);
				} catch (FileNotFoundException e) {

					e.printStackTrace();
				}
				sub.setGraphic(new ImageView(subImg));
				sub.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						try {
							if (st.getSelectionModel().getSelectedItem() == null)
								throw new Exception();
							deps.get(de.getSelectionModel().getSelectedIndex()).getStudents()
									.remove(st.getSelectionModel().selectedItemProperty().getValue());
							primaryStage.setScene(win);
							conf.fire();
							primaryStage.show();

						} catch (Exception e) {
							Label ex = new Label("You Must Select a Student");
							ex.setTextFill(Color.RED);
							remove.add(ex, 2, 1);
						}
					}
				});
				Button cancel = new Button("Cancel");
				Image canImg = null;
				try {
					canImg = new Image(new FileInputStream("remove.png"), 20, 20, false, false);
				} catch (FileNotFoundException e) {

					e.printStackTrace();
				}
				cancel.setGraphic(new ImageView(canImg));
				cancel.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {

						primaryStage.setScene(win);
						primaryStage.show();
					}
				});
				remove.add(cancel, 1, 4);
				remove.add(sub, 1, 3);
				remove.add(depL, 0, 0);
				remove.add(de, 1, 0);
				remove.add(stuL, 0, 1);
				remove.add(st, 1, 1);
				Scene removing = new Scene(remove, 430, 160);
				primaryStage.setScene(removing);
				primaryStage.setTitle("Removing Student");
				primaryStage.show();
			}
		});// --------------------------------------------------remove student button (E)
		Button change = new Button("Change student");
		Image chngImg = new Image(new FileInputStream("change.png"), 20, 20, false, false);
		change.setGraphic(new ImageView(chngImg));
		change.setOnAction(new EventHandler<ActionEvent>() {// -------------------change a student (S)
			@Override
			public void handle(ActionEvent event) {
				GridPane change = new GridPane();
				change.setVgap(10);
				change.setHgap(10);

				change.setPadding(new Insets(10));
				ChoiceBox<Department> newdep = new ChoiceBox<Department>();

				for (int i = 0; i < deps.size(); i++) {
					newdep.getItems().add(deps.get(i));
				}

				ChoiceBox<Student> st = new ChoiceBox<Student>();
				ChoiceBox<Department> olddep = new ChoiceBox<Department>();
				olddep.getSelectionModel().selectedIndexProperty().addListener((v, oldValue, newValue) -> {
					int temp = (int) newValue;

					for (int i = 0; i < deps.get(temp).getStudents().size(); i++) {
						st.getItems().add(deps.get(temp).getStudents().get(i));
					}
				});

				Label oldde = new Label("Old Department");
				Label newde = new Label("New Department");
				Label stName = new Label("Students name");

				change.add(stName, 0, 1);
				change.add(oldde, 0, 0);
				change.add(newde, 0, 2);

				for (int i = 0; i < deps.size(); i++) {
					olddep.getItems().add(deps.get(i));
				}

				if (olddep.getSelectionModel().getSelectedItem() == deps.get(0)) {
					for (int j = 0; j < deps.get(0).getStudents().size(); j++) {
						st.getItems().add(deps.get(0).getStudents().get(j));
					}
				}

				Button sub = new Button("Submit");
				Image subImg = null;
				try {
					subImg = new Image(new FileInputStream("confirm.png"), 20, 20, false, false);
				} catch (FileNotFoundException e) {

					e.printStackTrace();
				}
				sub.setGraphic(new ImageView(subImg));
				sub.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						try {
							if ((newdep.getSelectionModel().getSelectedItem()) == null
									|| st.getSelectionModel().getSelectedItem() == null) {
								throw new Exception();
							}
							if ((newdep.getSelectionModel().getSelectedIndex()) == olddep.getSelectionModel()
									.getSelectedIndex()) {
								throw new Exception();
							}

							deps.get(newdep.getSelectionModel().getSelectedIndex()).getStudents()
									.add(st.getSelectionModel().selectedItemProperty().getValue());

							deps.get(olddep.getSelectionModel().getSelectedIndex()).getStudents()
									.remove(st.getSelectionModel().selectedItemProperty().getValue());
							primaryStage.setScene(win);
							conf.fire();
							primaryStage.show();
						} catch (Exception e) {
							Label ex = new Label("You Didn't Select a Student or Department");
							Label ex1 = new Label("You Can't Move a Student to the Same Department");

							if ((newdep.getSelectionModel().getSelectedIndex()) == olddep.getSelectionModel()
									.getSelectedIndex()) {
								ex.setDisable(true);
								ex1.setTextFill(Color.RED);
								change.add(ex1, 2, 4);
							} else {
								ex1.setDisable(true);
								ex.setTextFill(Color.RED);
								change.add(ex, 2, 5);
							}
						}
					}
				});
				Button cancel = new Button("Cancel");
				Image canImg = null;
				try {
					canImg = new Image(new FileInputStream("remove.png"), 20, 20, false, false);
				} catch (FileNotFoundException e) {

					e.printStackTrace();
				}
				cancel.setGraphic(new ImageView(canImg));
				cancel.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {

						primaryStage.setScene(win);
						primaryStage.show();
					}
				});

				change.add(st, 1, 1);
				change.add(olddep, 1, 0);
				change.add(newdep, 1, 2);
				change.add(sub, 1, 4);
				change.add(cancel, 1, 5);

				Scene changing = new Scene(change, 450, 200);
				primaryStage.setScene(changing);
				primaryStage.setTitle("Changing Student");
				primaryStage.show();
			}
		});// ------------------------------------------------------change a student (E)
		Button save = new Button("Save");
		Image saveImg = new Image(new FileInputStream("save.png"), 20, 20, false, false);
		save.setGraphic(new ImageView(saveImg));
		save.setOnAction(new EventHandler<ActionEvent>() {// -----------------------------------save (S)
			@Override
			public void handle(ActionEvent event) {
				try {

					// --------------reading from file (data base)
					FileOutputStream out = new FileOutputStream(dataBase);
					ObjectOutputStream write = new ObjectOutputStream(out);
					write.writeObject(deps);
					write.close();

				} catch (Exception e) {
					System.out.println(":(");
					e.getMessage();
					e.getStackTrace();

				}
			}
		});// ------------------------------------------------------------------save (E)
		Button add = new Button("Add student");
		Image addImg = new Image(new FileInputStream("add.png"), 20, 20, false, false);
		add.setGraphic(new ImageView(addImg));
		add.setOnAction(new EventHandler<ActionEvent>() {// -----------------------------------add a student (S)
			@Override
			public void handle(ActionEvent arg0) {
				GridPane addst = new GridPane();
				Label name = new Label("name");
				Label ID = new Label("ID");
				Label GPA = new Label("GPA");
				Label DEP = new Label("Department");
				Label lvl = new Label("Level");

				TextField n = new TextField();
				TextField id = new TextField();
				TextField gpa = new TextField();
				addst.setVgap(10);
				addst.setHgap(10);

				addst.setPadding(new Insets(10));
				addst.add(lvl, 0, 0);
				addst.add(name, 0, 1);
				addst.add(ID, 0, 2);
				addst.add(GPA, 0, 3);
				addst.add(n, 1, 1);
				addst.add(id, 1, 2);
				addst.add(gpa, 1, 3);
				addst.add(DEP, 0, 4);

				ChoiceBox<Department> aa = new ChoiceBox<Department>();
				for (int i = 0; i < deps.size(); i++) {
					aa.getItems().add(deps.get(i));
				}
				addst.add(aa, 1, 4);

				final ToggleGroup group = new ToggleGroup();
				RadioButton rb1 = new RadioButton("Undergraduate");
				RadioButton rb2 = new RadioButton("graduate");
				rb1.setToggleGroup(group);
				rb2.setToggleGroup(group);

				addst.add(rb1, 1, 0);
				addst.add(rb2, 2, 0);

				Button sub = new Button("Submit");
				Image subImg = null;
				try {
					subImg = new Image(new FileInputStream("confirm.png"), 20, 20, false, false);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				sub.setGraphic(new ImageView(subImg));
				addst.add(sub, 2, 4);

				Button cancel = new Button("Cancel");
				Image canImg = null;
				try {
					canImg = new Image(new FileInputStream("remove.png"), 20, 20, false, false);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				cancel.setGraphic(new ImageView(canImg));
				addst.add(cancel, 2, 5);
				cancel.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {

						primaryStage.setScene(win);
						primaryStage.show();
					}
				});
				sub.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						try {
							if (group.getSelectedToggle() == null || aa.getSelectionModel().getSelectedItem() == null) {
								throw new Exception();
							}
							if (group.getSelectedToggle() == rb1) {
								Undergraduate s1 = new Undergraduate(n.getText(), Integer.parseInt(id.getText()),
										Double.parseDouble(gpa.getText()));
								for (int i = 0; i < 3; i++) {
									if (aa.getSelectionModel().getSelectedItem() == deps.get(i))
										deps.get(i).getStudents().add(s1);
								}
							} else {
								Graduate s1 = new Graduate(n.getText(), Integer.parseInt(id.getText()),
										Double.parseDouble(gpa.getText()));
								for (int i = 0; i < deps.size(); i++) {
									if (aa.getSelectionModel().getSelectedItem() == deps.get(i))
										deps.get(i).getStudents().add(s1);
								}
							}
							primaryStage.setScene(win);
							primaryStage.show();
						} catch (Exception e) {
							Label ex = new Label("Missing Information");
							ex.setTextFill(Color.RED);
							addst.add(ex, 1, 5);
						}
					}
				});

				Scene adding = new Scene(addst);
				primaryStage.setScene(adding);
				primaryStage.setTitle("Adding Student");
				primaryStage.show();
			}
		});// -----------------------------------add a student (E)

		grid.add(add, 1, 4);
		GridPane.setHalignment(add, HPos.RIGHT);
		grid.add(remove, 2, 4);
		grid.add(change, 3, 4);
		grid.add(save, 4, 4);

		final ToggleGroup group = new ToggleGroup(); // -----------the group of choices for sort (Name,ID,GPA) (S)
		RadioButton rb1 = new RadioButton("Name");
		rb1.setToggleGroup(group);
		rb1.setSelected(true);
		RadioButton rb2 = new RadioButton("ID");
		rb2.setToggleGroup(group);
		RadioButton rb3 = new RadioButton("GPA");
		rb3.setToggleGroup(group);
		grid.add(rb1, 1, 1);
		grid.add(rb2, 2, 1);
		grid.add(rb3, 3, 1);// --------------------the group of choices for sort (Name,ID,GPA) (E)
		ChoiceBox<Department> Departments = new ChoiceBox<Department>();// the List of Departments (main scene) (S)

		for (int i = 0; i < deps.size(); i++) {
			Departments.getItems().add(deps.get(i));
		}
		Departments.getSelectionModel().clearAndSelect(0);
		Label depL = new Label("List of Departments: ");
		GridPane.setHalignment(depL, HPos.RIGHT);
		grid.add(depL, 0, 0);
		grid.add(Departments, 1, 0);// --------------- the List of Departments (main scene) (S)
		grid.add(conf, 2, 0);

		conf.setOnAction(new EventHandler<ActionEvent>() {// ---------The Confirm Button (S)
			ListView<Student> students = new ListView<Student>();
			ListView<Department> depsinf = new ListView<Department>();

			@Override
			public void handle(ActionEvent arg0) {
				for (int i = 0; i < deps.size(); i++) {
					depsinf = new ListView<Department>();
					for (int j = 0; j < deps.size(); ++j) {
						depsinf.getItems().add(deps.get(j));
					}
				}
				grid.add(depsinf, 4, 3);
				if (group.getSelectedToggle().equals(rb1)) {
					for (int i = 0; i < deps.size(); i++) {
						Collections.sort(deps.get(i).getStudents(), new Comparator<Student>() {

							@Override
							public int compare(Student o1, Student o2) {
								return o1.getName().compareTo(o2.getName());
							}

						});
					}
				} else if (group.getSelectedToggle().equals(rb2)) {

					for (int i = 0; i < deps.size(); i++) {
						Collections.sort(deps.get(i).getStudents());
					}

				} else if (group.getSelectedToggle().equals(rb3)) {
					for (int i = 0; i < deps.size(); i++) {

						Collections.sort(deps.get(i).getStudents(), new Comparator<Student>() {

							@Override
							public int compare(Student o1, Student o2) {
								if (o1.getGPA() > o2.getGPA())
									return -1;
								else if (o1.getGPA() < o2.getGPA())
									return 1;
								else
									return 0;
							}
						});
					}
				}
				for (int i = 0; i < deps.size(); i++) {
					if (Departments.getValue() == deps.get(i)) {
						students = new ListView<Student>();
						for (int j = 0; j < deps.get(i).getStudents().size(); ++j) {
							students.getItems().add(deps.get(i).getStudents().get(j));
						}
					}
				}
				grid.add(students, 0, 3);
			}
		});// -------------------------------------------------The Confirm Button (E)
		conf.fire();
		
		Label by = new Label("Sorted by:"); // ---------
		GridPane.setHalignment(by, HPos.RIGHT);																								// the main
																										// scene layout																								// (S)
		grid.add(by, 0, 1);
		Label info = new Label("Name    GPA       ID       Status     Level     ");
		Label depInfo = new Label("Name      Code           No of Students");
		grid.add(depInfo, 4, 2);
		grid.add(info, 0, 2);
		grid.setVgap(10);
		grid.setHgap(10);
		grid.setPadding(new Insets(10));
		win = new Scene(grid, 980, 400);
		primaryStage.setScene(win);
		primaryStage.setTitle("Registeration System");
		primaryStage.show();// --------------------- the main scene layout (E)
	}
}
