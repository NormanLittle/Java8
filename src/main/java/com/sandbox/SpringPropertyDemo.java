package com.sandbox;

import org.joda.time.LocalTime;
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

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SpringPropertyDemo {

    @Autowired
    private SpringBean springBean;

    @Test
    public void testValue() {
        Map<String, String> valueMap = springBean.getValueMap();
        assertEquals(" ", valueMap.get("gb"));
        assertEquals(",", valueMap.get("es"));
        assertNull(valueMap.get("other"));

        List<String> valueList = springBean.getValueList();
        assertEquals(newArrayList("one", "two", "three"), valueList);

        LocalTime valueTime = springBean.getValueTime();
        assertEquals(new LocalTime(8, 0), valueTime);
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
        private final List<String> valueList;
        private final LocalTime valueTime;

        @Autowired
        public SpringBean(@Value("#{${property.valueMap}}") Map<String, String> valueMap,
                          @Value("#{'${property.list}'.split(',')}") List<String> valueList,
                          @Value("#{T(org.joda.time.LocalTime).parse('${property.time}')}") LocalTime valueTime) {
            this.valueMap = valueMap;
            this.valueList = valueList;
            this.valueTime = valueTime;
        }

        Map<String, String> getValueMap() {
            return valueMap;
        }

        List<String> getValueList() {
            return valueList;
        }

        LocalTime getValueTime() {
            return valueTime;
        }
    }
}
