package info.cafeit.hibernateonetomany;

import info.cafeit.hibernateonetomany.model.Images;
import info.cafeit.hibernateonetomany.model.Users;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

@SpringBootApplication
public class HibernateOneToManyApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(HibernateOneToManyApplication.class, args);
    }


    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        Users user = new Users("longtran", "tranhuulongcit@gmail.com", "Long", "Tran");

        Images image1 = new Images("maria ozawa", "https://link.vn/maria.jpg", "image/jpg", "2566", new Date());
        image1.setUser(user);

        Images image2 = new Images("ngoc trinh", "https://link.vn/ngoctrinh.jpg", "image/jpg", "2976", new Date());
        image2.setUser(user);

        user.setImages(Arrays.asList(image1, image2));


        //lưu vào database
        //vì chúng ta đang config cascade = CascadeType.ALL nên nó sẻ lưu luôn image
        entityManager.persist(user);

        //select profile và lấy ra tham chiếu của user
        Users p = entityManager.find(Users.class, 1l);

        System.out.println(p);
        p.getImages().forEach(System.out::println);

        //Ngược lại chúng ta có thể biết đc owner của image bằng cách
        Images img = entityManager.find(Images.class, 1l);
        System.out.println(img);

        System.out.println(img.getUser());

    }
}
