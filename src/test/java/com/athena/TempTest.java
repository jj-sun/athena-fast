package com.athena;

import com.athena.common.utils.SpringContextUtils;
import com.athena.modules.sys.repository.SysUserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(value = SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TempTest {

    public static void main(String[] args) {
        System.err.println(Math.floorDiv(13,10));
        System.err.println(Math.floorMod(13,10));
    }

    @Test
    public void test(){
        SysUserRepository baseRepository = SpringContextUtils.getBean("com.athena.modules.sys.repository.SysUserRepository", SysUserRepository.class);
        System.err.println(baseRepository.count());

    }

}
