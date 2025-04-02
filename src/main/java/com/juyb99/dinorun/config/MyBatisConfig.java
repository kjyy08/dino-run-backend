package com.juyb99.dinorun.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

@Configuration
public class MyBatisConfig {
    private static final SqlSessionFactory sqlSessionFactory;
    private static final Logger logger = Logger.getLogger(MyBatisConfig.class.getName());

    static {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

        Properties properties = new Properties();
        properties.setProperty("DB_DRIVER", dotenv.get("DB_DRIVER"));
        properties.setProperty("DB_URL", dotenv.get("DB_URL"));
        properties.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        properties.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        logger.info("MyBatisConfig: " + properties);

        String resource = "mybatis-config.xml";
        try (InputStream inputStream = MyBatisConfig.class.getClassLoader().getResourceAsStream(resource)) {
            logger.info("%s".formatted(inputStream));
            logger.info(String.valueOf(MyBatisConfig.class.getClassLoader().getResource(resource)));
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, properties);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 일종의 싱글톤으로 봐야하는데...
    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}