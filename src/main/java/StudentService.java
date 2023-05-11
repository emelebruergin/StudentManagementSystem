//3- student ile ilgili methodları olusturcaz


import java.util.Scanner;

public class StudentService {

    Scanner inp=new Scanner(System.in);

    //repostitory classındaki methodları kullanabilmek için obje olusturalım

    private StudentRepository repo=new StudentRepository();

    //9-student için tablo olusturma
    public void createStudentTable(){
        repo.createTable();
    }

    //11- öğrenciyi kaydetme
    public void saveStudent(){
        System.out.println("AD: ");// length("    ") = 4  girme ihtimaline karsı trim yaparız bas son boşluklar gider boşalır
        String name=inp.nextLine().trim();
        System.out.println("SOYAD: ");
        String lastname=inp.nextLine();
        System.out.println("ŞEHİR: ");
        String city=inp.nextLine();
        System.out.println("YAŞ: ");
        int age= inp.nextInt();
        inp.nextLine();

        Student newStudent=new Student(name,lastname,city,age);
        repo.save(newStudent);
    }
    //13.Adım tüm öğrencileri listeleme
    public void getAllStudent() {
        repo.findAll();

    }

    //15.adım idsi verilen student i silme
    public void deleteStudent(int id) {
        repo.delete(id);

    }

    //17.adım id si verilen öğrenciyi bulma
    public  Student getStudentById(int id){
        Student student=repo.findById(id);
        return  student;
    }


//21. adım id si verilen öğrenciyi gosterme
    public void displayStudent(int id) {
       Student student= getStudentById(id);
       if (student==null){
           System.out.println("Student does not exist by id: "+id);
       }else {
           System.out.println(student);
       }

    }





    //19.adım id si verilen öğrenciyi güncelleme
    public void updateStudent(int id){
        //kullanıcının girmiş oldugu id ile kayıtlı öğrenci mevcutmu?
        Student foundStudent=getStudentById(id);
        if (foundStudent==null){
            System.out.println("Student does not exist by id: "+id);

        }else {
            System.out.println("AD: ");// length("    ") = 4  girme ihtimaline karsı trim yaparız bas son boşluklar gider boşalır
            String name=inp.nextLine().trim();
            System.out.println("SOYAD: ");
            String lastname=inp.nextLine();
            System.out.println("ŞEHİR: ");
            String city=inp.nextLine();
            System.out.println("YAŞ: ");
            int age= inp.nextInt();
            inp.nextLine();

            //bulunan öğrencinin özelliklerini güncelle
            foundStudent.setName(name);
            foundStudent.setLastname(lastname);
            foundStudent.setCity(city);
            foundStudent.setAge(age);

            //id aynı kalacak

            repo.update(foundStudent);
        }


    }



}
