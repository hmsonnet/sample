import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import org.example.user.service.User;
import org.example.user.service.impl.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


class UserTest {

    //@Test
    void 사용자_등록_테스트() {
        User user = new User();
        user.setUsername("user");
        user.setPassword(new BCryptPasswordEncoder().encode("1234"));
        user.setEnabled(true);

        System.out.println(user.getPassword());

    }

    @Test
    void 사용자_토큰_테스트() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InVzZXIiLCJyb2xlIjoiUk9MRV9VU0VSIiwiZXhwIjoxNzA2NTgwOTMwfQ.Whgpz5LCogiSb7ufLpkay_djVooNq5EoJJAb7s7ma1E";
        Claim claim = JWT.decode(token).getClaim("username");
        String test = claim.asString();
        System.out.println(test);

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("ASDFASFQWER3425243564236423563425GSDFGDFBGFSDFGSDFGDSFGDSFGWERTQQWEAZSXCVX")).build();

        System.out.println(jwtVerifier.verify(token).getExpiresAt());
    }
}
