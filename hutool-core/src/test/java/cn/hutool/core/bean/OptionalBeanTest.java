package cn.hutool.core.bean;

import org.junit.Assert;
import lombok.Data;
import org.junit.Test;

public class OptionalBeanTest {

    @Test
    public void OptionalBeanTest() {
        User user = new User();
        user.setName("hello");
        String value1 = OptionalBean.ofNullable(user)
                .getBean(User::getSchool)
                .getBean(User.School::getAddress).get();
        Assert.assertEquals(value1, null);

        boolean present = OptionalBean.ofNullable(user)
                .getBean(User::getSchool)
                .getBean(User.School::getAddress).isPresent();
        Assert.assertFalse(present);

        String value2 = OptionalBean.ofNullable(user)
                .getBean(User::getSchool)
                .getBean(User.School::getAddress).orElse("没得地址");
        Assert.assertEquals(value2, "没得地址");
        try {
            OptionalBean.ofNullable(user)
                    .getBean(User::getSchool)
                    .getBean(User.School::getAddress).orElseThrow(() -> new RuntimeException("空指针了"));
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "空指针了");
        }
    }


    @Data
    public class User {
        private String name;
        private String gender;
        private School school;
        @Data
        public class School {
            private String name;
            private String address;
        }
    }

}
