
import java.sql.Statement;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBC3_Finish {
	static int[] dataFromDataBaseArray = new int[100];

	static String dataFromDataBase;
	static String connectionURL = "jdbc:mysql://localhost:3306/heroes?autoReconnect=true&useSSL=false&serverTimezone=UTC";

	static String dataBaseTabeleName = "heroestab";
	static String login = "root";
	static String password = "";

	// save to DB params
	protected static int idSave;
	protected static int ageSave;
	protected static String nameSave;
	protected static String surnameSave;
	static Connection conn = null;
	static int choice = 0;

	/**
	 * @param args
	 */
	public static void main(String[] args) throws ClassNotFoundException {

		while (true) {
			try {
				// Ustawiamy dane dotycz¹ce pod³¹czenia
				conn = DriverManager.getConnection(connectionURL, login, password);
				// Uruchamiamy zapytanie do bazy danych
				// Tworzymy proste zapytanie do bazy danych
				String queryFrom = ("Select * FROM " + dataBaseTabeleName);
				Statement stm = conn.createStatement();
				ResultSet rsDispayFromDB = stm.executeQuery(queryFrom);

				System.out.println("Type: ");
				System.out.println("Dislpay -> 1");
				System.out.println("Insert -> 2");
				System.out.println("Delete -> 3");

				Scanner choiceInput = new Scanner(System.in);
				choice = choiceInput.nextInt();
				switch (choice) {
				case 1:
					while (rsDispayFromDB.next()) {
						displayFromDataBase(rsDispayFromDB);
						System.out.println("FINISH");
					}
					break;
				case 2:
					fillFieldsInDataBase();
					String queryTo = ("Insert into " + dataBaseTabeleName + "(id, name, surname, age) " + "values ("
							+ idSave + "," + "'" + nameSave + "'" + "," + "'" + surnameSave + "'" + "," + ageSave
							+ ");");
					stm.executeUpdate(queryTo);
					System.out.println("FINISH");
					break;
				case 3:
					System.out.println("Delete by ID:");
					Scanner choiceDelete = new Scanner(System.in);
					int choiceDelInt = choiceDelete.nextInt();
					String queryDelete = ("Delete from " + dataBaseTabeleName + " where id = " + choiceDelInt + ";");
					stm.executeUpdate(queryDelete);
					System.out.println("FINISH");
				}

				conn.close();
			} catch (SQLException wyjatek) {
				wyjatek.printStackTrace();
				System.out.println("SQLException: " + wyjatek.getMessage());
				System.out.println("SQLState: " + wyjatek.getSQLState());
				System.out.println("VendorError: " + wyjatek.getErrorCode());
			}
		}
	}

	static void displayFromDataBase(ResultSet rdisp) {
		try {
			// amount of colums in DB.
			dataFromDataBase = rdisp.getString(1);
			System.out.println("\n" + dataFromDataBase + " ");
			dataFromDataBase = rdisp.getString(2);
			System.out.println(dataFromDataBase + " ");
			dataFromDataBase = rdisp.getString(3);
			System.out.println(dataFromDataBase);
			dataFromDataBase = rdisp.getString(4);
			System.out.println(dataFromDataBase);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	static void fillFieldsInDataBase() {
		System.out.println("Fill fields to Data Base:");
		Scanner scan = new Scanner(System.in);
		System.out.println("Fill idSave:");
		idSave = scan.nextInt();
		System.out.println("Fill nameSave:");
		nameSave = scan.next();
		System.out.println("Fill surnameSave:");
		surnameSave = scan.next();
		System.out.println("Fill ageSave:");
		ageSave = scan.nextInt();
	}

}
