package cn.lenmotion.donutapi;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lenmotion
 */
@Slf4j
public class PasswordTests extends DonutApiApplicationTests {

    @Autowired
    private RSA rsa;

    @Test
    public void testPassword() {
        String encrypt = rsa.encryptHex("qwe@123", KeyType.PublicKey);
        log.info("encrypt: {}", encrypt);
        log.info("encrypt: {}", rsa.encryptHex("admin123", KeyType.PublicKey));

        log.info("encrypt: {}", SaSecureUtil.sha256BySalt("admin123", "admin"));
    }

    public static void main(String[] args) {
        String[] strings = {"A", "A,B", "A,C", "D,E"};

        List<String> result = new ArrayList<>();

        for (int i = 0; i < strings.length; i++) {
            for (int j = i+1; j < strings.length; j++){
                if (strings[j].startsWith(strings[i])) {
                    continue;
                }
                result.add(strings[i]);
            }
        }

        for (String str : strings) {
            boolean found = false;
            for (String s : strings) {
                if (!s.equals(str) && s.contains(str)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                result.add(str);
            }
        }

        String[] filteredStrings = result.toArray(new String[0]);

        for (String s : filteredStrings) {
            System.out.println(s);
        }
    }

}
