package com.example.booksStorage;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import java.util.Arrays;

@Configuration
public class BeansPrinter {
    private static final Logger logger = Logger.getLogger(BooksStorageApplication.class);

    @Autowired
    public void beansPrinter(ApplicationContext ctx) {
        BasicConfigurator.configure();

        logger.info("--- LIST OF BEANS ---");
        Arrays.stream(ctx.getBeanDefinitionNames())
                .forEach(bean -> logger.info("--- BEAN: " + bean));
    }
}
