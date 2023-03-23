import com.sangeng.TokenApplication;
import com.sangeng.mapper.MenuMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest(classes = TokenApplication.class)
public class MapperTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MenuMapper menuMapper;
    @Test
    public void testss(){
        String encode = passwordEncoder.encode("1234");
        System.out.println(encode);
    }

    @Test
    public void testMenu(){
        List<String> list = menuMapper.selectPermsByUserId(1L);
        System.out.println("list = " + list);
    }
}
