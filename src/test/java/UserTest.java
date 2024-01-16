import org.example.user.service.User;
import org.example.user.service.impl.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


class UserTest {

    @Test
    void 사용자_등록_테스트() {
        User user = new User();
        user.setUsername("user");
        user.setPassword(new BCryptPasswordEncoder().encode("1234"));
        user.setEnabled(true);

        System.out.println(user.getPassword());

    }
}
