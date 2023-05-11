import java.sql.*;

//4- database e baglanma ve database işlemleri için yeni bir repo class olusturalım
//database e bağlanacak olan sınıf
// Connection,Statement,PreparedStatement
public class StudentRepository {
    //database gizli hazinemiz.bu kısma erişimin olabildiğince kısıtlanması lazım.
    // bu yüzden data base e bağlanacak kısmı ayrı bi class olarak olustururum

    private Connection conn;
    private Statement st;
    private PreparedStatement prst;

    //5.adım connection olusturmak için method yazalım. lazım olan baska yerde kullanmak için kolay yoldan
    //methodlar içinde tekrar tekrar kull. gerektiği için method olusturuyoruz

    private void setConnection() {
        try {
            this.conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jdbc_db", "dev_user", "password");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //6.adım statement olusturma için method yazalım

    private void setStatement() {
        try {
            this.st = conn.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //7.adım preparedstatement olusturma için method yazalım
    private void setPreparedStatement(String sql) {
        try {
            this.prst = conn.prepareStatement(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // 8-tablo olusturma
    public void createTable() {
        setConnection();
        setStatement();
        try {
            st.execute("CREATE TABLE IF NOT EXISTS t_student(" +
                    "id SERIAL UNIQUE," +
                    "name VARCHAR(50) NOT NULL CHECK(LENGTH(name)>0)," +//empty ""
                    "lastname VARCHAR(50) NOT NULL CHECK(LENGTH(lastname)>0)," +
                    "city VARCHAR(50) NOT NULL CHECK(LENGTH(city)>0)," +
                    "age INT NOT NULL CHECK(age>0)" +
                    ")");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }


    }

    //12- tabloya kayıt ekleme
    public void save(Student student) {
        String sql = "INSERT INTO t_student(name,lastname,city,age) VALUES(?,?,?,?)";
        setConnection();
        setPreparedStatement(sql);
        try {
            prst.setString(1, student.getName());
            prst.setString(2, student.getLastname());
            prst.setString(3, student.getCity());
            prst.setInt(4, student.getAge());
            prst.executeUpdate();
            System.out.println("Saved successfully...");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                prst.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    //14.Adım tablodaki tüm kayıtları tüm fieldlarıyla getirip yazdırma
    public void findAll() {
        setConnection();
        setStatement();
        String query = "select * from t_student";
        System.out.println("============ ALL STUDENTS ===================");
        try {
            ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                System.out.print("id : " + resultSet.getInt("id"));
                System.out.print("  -ad : " + resultSet.getString("name"));
                System.out.print("  -soyad : " + resultSet.getString("lastname"));
                System.out.print("  -şehir : " + resultSet.getString("city"));
                System.out.print("  -yaş : " + resultSet.getInt("age"));
                System.out.println();

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    //16.adım tablodan id si verilen kaydı silme
    public void delete(int id) {
        setConnection();
     //2.yol
     // String query="delete from t_student where id="+id;
     // st.executeUpdate(query);
        String query="delete from t_student where id=?";
        setPreparedStatement(query);
        try {
            prst.setInt(1,id);
           int deleted= prst.executeUpdate();
           //kayıt bulunursa deleted=1 olur
           if (deleted>0){
               System.out.println("Student Deleted successfully by id: "+id);
           }else {
               System.out.println("Student could not found by id: "+id);
           }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                prst.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }


    }

    //18.adım id si verilen kaydı tablodan getirme
    public Student findById(int id) {
        Student student=null;
        setConnection();
        String query="select * from t_student where id=?";
        setPreparedStatement(query);
        try {
            prst.setInt(1,id);
            ResultSet resultSet=prst.executeQuery();
            if (resultSet.next()){
                student=new Student();
                student.setId(resultSet.getInt("id"));
                student.setName(resultSet.getString("name"));
                student.setLastname(resultSet.getString("lastname"));
                student.setCity(resultSet.getString("city"));
                student.setAge(resultSet.getInt("age"));




            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                prst.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return student;
    }

    public void update(Student foundStudent) {
        setConnection();
        String query="update t_student set name=? , lastname=? , city=? , age=? where id=?";
        setPreparedStatement(query);
        try {
            prst.setString(1,foundStudent.getName());
            prst.setString(2,foundStudent.getLastname());
            prst.setString(3,foundStudent.getCity());
            prst.setInt(4,foundStudent.getAge());
            prst.setInt(5,foundStudent.getId());
            prst.executeUpdate();
            if (prst.executeUpdate()>0){
                System.out.println("Updated successfully...");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            try {
                prst.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }
}
