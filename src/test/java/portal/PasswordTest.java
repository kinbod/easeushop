package portal;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordTest {

	public static void main(String args[]) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println("password:" + encoder.encode("qbq123456"));
	}
}
