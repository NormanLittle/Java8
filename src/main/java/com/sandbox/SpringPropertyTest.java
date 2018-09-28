package com.sandbox;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SpringPropertyTest {

    @Autowired
    private SpringBean springBean;

    @Test
    public void testValue() {
        Map<String, String> valueMap = springBean.getValueMap();

        assertEquals(" ", valueMap.get("gb"));
        assertEquals(",", valueMap.get("es"));
        assertNull(valueMap.get("other"));
    }

    @SuppressWarnings("unused")
    @Configuration
    @ComponentScan("com.sandbox")
    static class SpringConfiguration {

        @Bean
        static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
            PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
            propertyPlaceholderConfigurer.setLocation(new ClassPathResource("spring.properties"));
            return propertyPlaceholderConfigurer;
        }
    }

    @Component
    static class SpringBean {

        private final Map<String, String> valueMap;

        @Autowired
        public SpringBean(@Value("#{${property.valueMap}}") Map<String, String> valueMap) {
            this.valueMap = valueMap;
        }

        Map<String, String> getValueMap() {
            return valueMap;
        }
    }
}
