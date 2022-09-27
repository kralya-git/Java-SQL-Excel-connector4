

package com.test.idea;

//импортируем библиотеки для работы с excel
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
//для работы с потоками (будем использовать в блоке с excel)
import  java.io.FileOutputStream;
//для работы с массивами данных
import java.util.Arrays;
//для считывания данных с клавиатуры
import java.util.Scanner;
//для работы с sql
import com.mysql.cj.jdbc.Driver;
//в особенности потом понадобятся Connection, ResultSet и Statement
import java.sql.*;


//главный класс
public class substring_database {

    //точка входа в программу + вывод информации об ошибках с бд
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        //классу scanner присваиваем в качестве аргумента system.in
        Scanner scan = new Scanner(System.in);

        //начальное значение для switch case
        int x = 0;
        String s = "";

        //ввод названия таблицы с клавиатуры
        System.out.println("Введите название таблицы: ");
        String tablename = scan.nextLine();

        //цикл работает до тех пор, пока пользователь не введет 5
        while (!"5".equals(s)) {
            System.out.println();
            System.out.println("1. Вывести все таблицы из текущей БД.");
            System.out.println("2. Создать таблицу в БД.");
            System.out.println("3. Добавить данные в таблицу.");
            System.out.println("4. Сохранить данные в Excel.");
            System.out.println("5. Выйти из программы.");
            s = scan.next();

            //пробуем перевести пользовательский ввод в int
            try {
                x = Integer.parseInt(s);
            }
            //выдаем сообщение об ошибке ввода, и так до тех пор, пока пользователь не введет число
            catch (NumberFormatException e) {
                System.out.println("Неверный формат ввода.");
            }

            //оператор switch для множества развилок
            switch (x) {

                //если пользователь ввел цифру 1, то...
                case 1 -> {

                    //регистрируем драйвер для дальнейшей работы (управление jdbc)
                    DriverManager.registerDriver(new Driver());

                    //имя драйвера
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    //пытаемся установить соединение с заданным url бд
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "кщще");
                    System.out.println("Успешно законнектились к БД!");

                    //statement для выполнения sql запросов
                    //соответственно, createStatement создает этот объект для работы с бд
                    Statement stmt = con.createStatement();

                    //ResultSet - объект java, содержащий результаты выполнения sql запросов
                    ResultSet rs = stmt.executeQuery("Show tables");
                    System.out.println("Таблицы из текущей БД: ");

                    //rs.next() - построчный вывод названий таблиц в цикле
                    while (rs.next()) {
                        System.out.print(rs.getString(1));
                        System.out.println();
                    }
                }

                //если пользователь ввел цифру 2, то...
                case 2 -> {

                    //регистрируем драйвер для дальнейшей работы (управление jdbc)
                    DriverManager.registerDriver(new Driver());

                    //имя драйвера
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    //пытаемся установить соединение с заданным url бд
                    Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "кщще");
                    System.out.println("Успешно законнектились к БД!");

                    //statement для выполнения sql запросов
                    //соответственно, createStatement создает этот объект для работы с бд
                    Statement stmt1 = con1.createStatement();

                    //задаем запрос СОЗДАНИЯ, как строку
                    String query = "CREATE TABLE IF NOT EXISTS " + tablename +
                            " (строка varchar(100), индекс_1 int, индекс_2 int, подстрока_по_индексам varchar(100)," +
                            "строка_upper varchar(100), строка_lower varchar(100), подстрока varchar(50)," +
                            "подстрока_в_строке varchar(100), заканчивается_ли_строка_построкой TINYINT)";

                    //отправляем серверу бд sql-выражение
                    //вызваем метод executeQuery объекта Statement и в качестве аргумента передаем скрипт запроса
                    stmt1.executeUpdate(query);

                    //ResultSet - объект java, содержащий результаты выполнения sql запросов
                    ResultSet rs1 = stmt1.executeQuery("Show tables");
                    System.out.println("Таблицы из текущей БД: ");

                    //rs1.next() - построчный вывод названий таблиц в цикле
                    while (rs1.next()) {
                        System.out.print(rs1.getString(1));
                        System.out.println();
                    }
                }

                //если пользователь ввел цифу 3, то...
                case 3 -> {

                    //регистрируем драйвер для дальнейшей работы (управление jdbc)
                    DriverManager.registerDriver(new Driver());

                    //имя драйвера
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    //пытаемся установить соединение с заданным url бд
                    Connection con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "кщще");
                    System.out.println("Успешно законнектились к БД!");

                    //nextLine() читает всю текущую строки и возвращает всё, что было в этой строке
                    scan.nextLine();

                    //вводим с клавиатуры две строки
                    System.out.println("Введите первую строку: ");
                    String str1 = scan.nextLine();
                    System.out.println("Введите вторую строку: ");
                    String str2 = scan.nextLine();
                    System.out.println("Введите начальный индект (включительно): ");
                    int ind1 = scan.nextInt();
                    System.out.println("Введите конечный индекс (не включая): ");
                    int ind2 = scan.nextInt();
                    System.out.println("Введите подстроку: ");
                    scan.nextLine();
                    String substr = scan.nextLine();

                    String res = "";

                    if (str1.contains(substr)) {
                        int ind01 = str1.indexOf(substr);
                        int ind02 = str1.indexOf(substr) + substr.length();
                        res = str1.substring(0, ind01).toLowerCase() + str1.substring(ind01, ind02).toUpperCase() + str1.substring(ind02).toLowerCase();}
                    else if (!str1.contains(substr)) {res = str1 + " не содержит " + substr;}

                    //задаем запрос ЗАПОЛНЕНИЯ, как строку
                    String query2 = "INSERT INTO " + tablename +
                            " (строка, индекс_1, индекс_2, подстрока_по_индексам, строка_upper, строка_lower, подстрока, подстрока_в_строке, заканчивается_ли_строка_построкой)"
                            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

                    //PreparedStatement:
                    //заранее подготавливает запрос с указанием мест, где будут подставляться параметры (знаки '?')
                    //устанавливает параметры определенного типа
                    //и выполняет после этого запрос с уже установленными параметрами
                    PreparedStatement stmt3 = con2.prepareStatement(query2);

                    //установка параметров
                    stmt3.setString(1, str1);
                    stmt3.setInt(2, ind1);
                    stmt3.setInt(3, ind2);
                    stmt3.setString(4, str1.substring(ind1, ind2));
                    stmt3.setString(5, str1.toUpperCase());
                    stmt3.setString(6, str1.toLowerCase());
                    stmt3.setString(7, substr);
                    stmt3.setString(8, res);
                    stmt3.setBoolean(9, str1.endsWith(substr));

                    //выполнение запроса
                    //вызов stmt.executeUpdate() выполняется уже без указания строки запроса
                    stmt3.executeUpdate();

                    System.out.println("Данные в MySQL успешно внесены!");

                    //ResultSet - объект java, содержащий результаты выполнения sql запросов
                    ResultSet rs2 = stmt3.executeQuery("SELECT * FROM " + tablename + "");
                    System.out.println("Введенные данные: ");

                    //statement для выполнения sql запросов
                    Statement statement = con2.createStatement();

                    //ResultSet - объект java, содержащий результаты выполнения sql запросов
                    ResultSet set = statement.executeQuery("SELECT * FROM " + tablename + " LIMIT 0;");

                    //ResultSetMetaData содержит информацию о результирующей таблице
                    //- количество колонок, тип значений колонок и т.д.
                    ResultSetMetaData data = set.getMetaData();

                    //определяем количество колонок
                    int cnt = data.getColumnCount();

                    //выводим названия колонок через пробел
                    for (int i = 1; i <= cnt; i++) {
                        System.out.print(data.getColumnName(i) + " ");
                    }
                    System.out.print("\n");

                    //rs2.next() - построчный вывод введенных данных в цикле
                    while (rs2.next()) {
                        for (int i = 1; i <= cnt; i++) {
                            System.out.print(Arrays.toString(rs2.getString(i).split("   ")));
                        }
                        System.out.println();
                    }

                    ///вывод количества строк в таблице
                    //создаем sql запрос
                    String query = "select count(*) from " + tablename;

                    //пробуем выполнить запрос через try - catch
                    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "кщще");
                         Statement stmt = con.createStatement();
                         ResultSet rs = stmt.executeQuery(query)) {
                        while (rs.next()) {
                            int count = rs.getInt(1);
                            System.out.println("Всего внесено строк в таблицу " + tablename + " : " + count);
                        }
                    } catch (SQLException sqlEx) {
                        sqlEx.printStackTrace();
                    }

                    String res1 = "";

                    if (str2.contains(substr)) {System.out.println(str2 + " содержит " + substr);
                        int ind01 = str2.indexOf(substr);
                        int ind02 = str2.indexOf(substr) + substr.length();
                        res1 = str2.substring(0, ind01).toLowerCase() + str2.substring(ind01, ind02).toUpperCase() + str2.substring(ind02).toLowerCase();}
                    else if (!str2.contains(substr)) {res1 = str2 + " не содержит " + substr;}

                    //задаем запрос ЗАПОЛНЕНИЯ, как строку
                    String query3 = "INSERT INTO " + tablename +
                            " (строка, индекс_1, индекс_2, подстрока_по_индексам, строка_upper, строка_lower, подстрока, подстрока_в_строке, заканчивается_ли_строка_построкой)"
                            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

                    //PreparedStatement:
                    //заранее подготавливает запрос с указанием мест, где будут подставляться параметры (знаки '?')
                    //устанавливает параметры определенного типа
                    //и выполняет после этого запрос с уже установленными параметрами
                    PreparedStatement stmt4 = con2.prepareStatement(query3);

                    //установка параметров
                    stmt4.setString(1, str2);
                    stmt4.setInt(2, ind1);
                    stmt4.setInt(3, ind2);
                    stmt4.setString(4, str2.substring(ind1, ind2));
                    stmt4.setString(5, str2.toUpperCase());
                    stmt4.setString(6, str2.toLowerCase());
                    stmt4.setString(7, substr);
                    stmt4.setString(8, res1);
                    stmt4.setBoolean(9, str2.endsWith(substr));

                    //выполнение запроса
                    //вызов stmt.executeUpdate() выполняется уже без указания строки запроса
                    stmt4.executeUpdate();

                    System.out.println("Данные в MySQL успешно внесены!");

                    //ResultSet - объект java, содержащ
                    // ий результаты выполнения sql запросов
                    ResultSet rs3 = stmt3.executeQuery("SELECT * FROM " + tablename + "");
                    System.out.println("Введенные данные: ");

                    //statement для выполнения sql запросов
                    Statement statement1 = con2.createStatement();

                    //ResultSet - объект java, содержащий результаты выполнения sql запросов
                    ResultSet set1 = statement1.executeQuery("SELECT * FROM " + tablename + " LIMIT 0;");

                    //ResultSetMetaData содержит информацию о результирующей таблице
                    //- количество колонок, тип значений колонок и т.д.
                    ResultSetMetaData data1 = set1.getMetaData();

                    //определяем количество колонок
                    int cnt1 = data1.getColumnCount();

                    //выводим названия колонок через пробел
                    for (int i = 1; i <= cnt1; i++) {
                        System.out.print(data.getColumnName(i) + " ");
                    }
                    System.out.print("\n");

                    //rs3.next() - построчный вывод введенных данных в цикле
                    while (rs3.next()) {
                        for (int i = 1; i <= cnt1; i++) {
                            System.out.print(Arrays.toString(rs3.getString(i).split("   ")));
                        }
                        System.out.println();
                    }

                    ///вывод количества строк в таблице
                    //создаем sql запрос
                    String query1 = "select count(*) from " + tablename;

                    //пробуем выполнить запрос через try - catch
                    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "кщще");
                         Statement stmt = con.createStatement();
                         ResultSet rs = stmt.executeQuery(query1)) {
                        while (rs.next()) {
                            int count = rs.getInt(1);
                            System.out.println("Всего внесено строк в таблицу " + tablename + " : " + count);
                        }
                    } catch (SQLException sqlEx) {
                        sqlEx.printStackTrace();
                    }
                }
                //если пользователь ввел цифру 4, то...
                case 4 -> {

                    //реализуем через try - catch, чтобы программы не руинилась в случае ошибки
                    try{

                        //создаем название excel файла с учетом введеного имени таблицы
                        String filename="c:/" + tablename + ".xls" ;

                        //создаём объект HSSFWorkBook
                        HSSFWorkbook hwb = new HSSFWorkbook();

                        //создаём лист, используя на объекте, созданном в предыдущем шаге, createSheet()
                        HSSFSheet sheet =  hwb.createSheet("new sheet");

                        //создаём на листе строку, используя createRow()
                        HSSFRow rowhead = sheet.createRow((short)0);

                        //создаём в строке ячейку — createCell()
                        //задаём значение ячейки через setCellValue()
                        rowhead.createCell((short) 0).setCellValue("строка");
                        rowhead.createCell((short) 1).setCellValue("индекс_1");
                        rowhead.createCell((short) 2).setCellValue("индекс_2");
                        rowhead.createCell((short) 3).setCellValue("подстрока_по_индексам");
                        rowhead.createCell((short) 4).setCellValue("строка_upper");
                        rowhead.createCell((short) 5).setCellValue("строка_lower");
                        rowhead.createCell((short) 6).setCellValue("подстрока");
                        rowhead.createCell((short) 7).setCellValue("подстрока_в_строке");
                        rowhead.createCell((short) 8).setCellValue("заканчивается_ли_строка_построкой");

                        //имя драйвера
                        Class.forName("com.mysql.cj.jdbc.Driver");

                        //пытаемся установить соединение с заданным url бд
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "кщще");

                        //statement для выполнения sql запросов
                        Statement st = con.createStatement();

                        //ResultSet - объект java, содержащий результаты выполнения sql запросов
                        ResultSet rs = st.executeQuery("Select * from " + tablename);

                        //начальное значение i для while
                        int i = 1;

                        //создаём в строке ячейку — createCell()
                        //задаём значение ячейки через setCellValue()
                        //и всё это через цикл, чтобы заполнить все строчки
                        while(rs.next()){
                            HSSFRow row = sheet.createRow((short) i);
                            row.createCell((short) 0).setCellValue(rs.getString("строка"));
                            row.createCell((short) 1).setCellValue(rs.getString("индекс_1"));
                            row.createCell((short) 2).setCellValue(rs.getString("индекс_2"));
                            row.createCell((short) 3).setCellValue(rs.getString("подстрока_по_индексам"));
                            row.createCell((short) 4).setCellValue(rs.getString("строка_upper"));
                            row.createCell((short) 5).setCellValue(rs.getString("строка_lower"));
                            row.createCell((short) 6).setCellValue(rs.getString("подстрока"));
                            row.createCell((short) 7).setCellValue(rs.getString("подстрока_в_строке"));
                            row.createCell((short) 8).setCellValue(rs.getString("заканчивается_ли_строка_построкой"));
                            i++;
                        }
                        //записываем workbook в file через FileOutputStream
                        FileOutputStream fileOut = new FileOutputStream(filename);

                        //записывает строки в файл
                        hwb.write(fileOut);

                        //закрываем workbook, вызывая close()
                        fileOut.close();
                        System.out.println("Ваш файл Excel успешно сгенерирован!");
                    }

                    //если что-то пойде не так, программа выведет тект ошибки, но не ошибку
                    catch ( Exception ex ) {
                        System.out.println(ex);
                    }
                }
            }
        }
        //если пользователь введет 5, то выйдет из программы
        System.out.println("Вышли из нашей программы.");
    }
}
