import com.sangeng.TokenApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest(classes = TokenApplication.class)
public class MapperTest {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    public void testss(){
        String encode = passwordEncoder.encode("1234");
        System.out.println(encode);
    }
}
