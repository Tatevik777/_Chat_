import java.sql.*;
import java.util.Scanner;

public class Main {
    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement ps;
    // Вроде все работает и база подключается, но видимо не заполняется таблица и программа не выдает товары и цены

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connection();
        try {
            stmt = connection.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS Products (ID INTEGER, Name  TEXT, Price INTEGER);");
            stmt.execute("DELETE FROM Products");
            ps = connection.prepareStatement("INSERT INTO Products (ID,Name,Price) VALUES (?,?,?);");
            connection.setAutoCommit(false);
            for (int i = 1; i <= 10000; i++) {
                ps.setInt(1,i);
                ps.setString(2, "Product" + i);
                ps.setInt(3, i * 10);
                ps.addBatch();
            }
            ps.executeBatch();
            connection.commit();
            System.out.println("Введите /например/цена product545, чтобы узнать стоимость товара по его названию;\n" +
                    "Введите /например/сменитьцену product10 10000, чтобы изменить цену товара по по его названию;\n" +
                    "Введите /например/товарыпоцене 100 600, чтобы узнать какие товары находятся в заданном вами диапазоне;\n" +
                    "Введите выход, чтобы выйти;\n");
            Scanner sc = new Scanner(System.in);
            while (true) {
                String str = sc.nextLine();
                System.out.println("Вы ввели: "+str);
                String arr[] = str.split(" ");
                if(str.startsWith("цена")&&arr.length>=1){
                    getPriceByName(arr[1]);
                }if(str.startsWith("сменитьцену")&&arr.length>=2){
                    setPriceByName(arr[1], Integer.parseInt(arr[2]));
                }if(str.startsWith("товарыпоцене")&&arr.length>=2){
                    betweenMinAndMax(Integer.parseInt(arr[1]), Integer.parseInt(arr[2]));
                }if(str.equals("выход")){
                    break;
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();

    }



    private static void getPriceByName(String name) {
        try {
            ps = connection.prepareStatement("SELECT Cost FROM Products WHERE Name Like ? ");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println(rs.getInt("Price"));
            }
            rs.close();
            ps.close();
            if (!found) {
                System.out.println("Товары c таким именем не найден.");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void setPriceByName(String name, int price) {
        try {
            ps = connection.prepareStatement("UPDATE Products SET Price=? WHERE Name Like ? ");
            ps.setInt(1,price);
            ps.setString(2,name);
            System.out.println("Изменено: "+ps.executeUpdate()+" товаров.");


            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void betweenMinAndMax(int min, int max) {
        try {
            ps = connection.prepareStatement("SELECT * FROM Products WHERE Cost BETWEEN ? AND ?");
            ps.setInt(1, min);
            ps.setInt(2, max);
            ResultSet rs = ps.executeQuery();
            boolean found = false;
            while(rs.next()){
                found = true;
                System.out.println(rs.getInt("ID")+" "+rs.getInt("Name")+" "+rs.getInt("Price"));
            }
            rs.close();
            ps.close();
            if(!found){
                System.out.println("Товары в заданном диапазоне не найдены.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void Connection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection= DriverManager.getConnection("jdbc:sqlite:Goods.db");

    }
    private static void disconnect() throws SQLException {
        connection.close();
    }
}
