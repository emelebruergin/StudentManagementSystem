import java.util.Scanner;

public class Runner {
    /*
Proje:Student Management System
     -1-Herhangi bir eğitim kurumu için öğrenci yönetim uygulaması geliştiriniz.
     -2-Kullanıcı
               -öğrenci kayıt
               -öğrenci veya öğrencileri görüntüleme
               -id ile öğrenci güncelleme
               -id ile öğrenci silme
       işlemlerini yapabilmeli.
     -3-öğrenci:id,name,lastname,city,age özelliklerine sahiptir.

     //ÖDEV öğrenci silme işleminden sonra silinen öğrencinin bilgilerini göster
     //ÖDEV öğrenci ekleme işleminden sonra eklenen öğrencinin bilgilerini göster
 */
    public static void main(String[] args) {

        start();
    }

    //1.Adım kullanıcıya menu gösterelim
    //2.Adım student class ı olusturma

    public static void start() {

        Scanner inp = new Scanner(System.in);

        //10.adım tablo olusturma
        StudentService service =new StudentService();
        service.createStudentTable();


        int select;
        int id;
        do {
            System.out.println("=====================================");
            System.out.println("Öğrenci Yönetim Paneli");
            System.out.println("1-Öğrenci Kayıt");
            System.out.println("2-Tüm Öğrencileri Listele");
            System.out.println("3-Öğrenci Güncelle");
            System.out.println("4-Öğrenci Sil");
            System.out.println("5-Tek Bir Öğrenciyi Gösterme");
            System.out.println("0-ÇIKIŞ");
            System.out.println("İşlem Seçiniz: ");
            select = inp.nextInt();
            inp.nextLine(); // nextint ten sonra asağı satıra gecme skntısı oluyo.dummy kullanırız nexintten sonra

            switch (select) {
                case 1:
                    service.saveStudent();
                    service.getAllStudent();
                    break;
                case 2:
                    service.getAllStudent();
                    break;
                case 3:
                    id=getId(inp);
                    service.updateStudent(id);
                    break;
                case 4:
                    id=getId(inp);
                    service.deleteStudent(id);
                    service.getAllStudent();
                    break;
                case 5:
                    id=getId(inp);
                    service.displayStudent(id);
                case 0:
                    System.out.printf("Iyi Gunler ...");
                    break;
                default:
                    System.out.printf("Hatalı giriş yaptınız.");
                    break;

            }
        } while (select != 0);
    }


    private static int getId(Scanner inp){
        System.out.printf("Öğrenci id: ");
        int id=inp.nextInt();
        return id;
    }


}
