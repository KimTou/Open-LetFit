package com.letfit;

import com.letfit.mapper.*;
import com.letfit.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
class LetFitApplicationTests {

    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private SourceMapper sourceMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 所有模块
     */
    private static final String ALL_MODULE = "all_module";

    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String format = localDateTime.format(formatter);
        System.out.println(format);
    }

    @Test
    void contextLoads() {

    }



}
