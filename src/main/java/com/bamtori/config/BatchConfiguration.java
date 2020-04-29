package com.bamtori.config;

import com.bamtori.coupon.model.Coupon;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Value("classPath:/input/coupons.csv")
    private Resource inputResource;

    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();
        return embeddedDatabaseBuilder.addScript("classpath:org/springframework/batch/core/schema-drop-h2.sql")
                .addScript("classpath:org/springframework/batch/core/schema-h2.sql")
                .addScript("classpath:coupons.sql")
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<Coupon> writer() {
        JdbcBatchItemWriter<Coupon> itemWriter = new JdbcBatchItemWriter<Coupon>();
        itemWriter.setDataSource(dataSource());
        itemWriter.setSql("INSERT INTO COUPON (CODE,STATUS,USED_DATE,EXPIRED_DATE) VALUES (:code, 0, null, null)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Coupon>());
        return itemWriter;
    }

    @Bean
    public LineMapper<Coupon> lineMapper() {
        DefaultLineMapper<Coupon> lineMapper = new DefaultLineMapper<Coupon>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        BeanWrapperFieldSetMapper<Coupon> fieldSetMapper = new BeanWrapperFieldSetMapper<Coupon>();

        lineTokenizer.setNames(new String[]{"code", "status", "used_date", "expired_date"});
        lineTokenizer.setIncludedFields(new int[]{0, 1, 2, 3});
        fieldSetMapper.setTargetType(Coupon.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean
    public Job readCSVFileJob() {
        return jobBuilderFactory
                .get("readCSVFileJob")
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory
                .get("step")
                .<Coupon, Coupon>chunk(5)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public ItemProcessor<Coupon, Coupon> processor() {
        return new DBLogProcessor();
    }

    @Bean
    public FlatFileItemReader<Coupon> reader() {
        FlatFileItemReader<Coupon> itemReader = new FlatFileItemReader<Coupon>();
        itemReader.setLineMapper(lineMapper());
        itemReader.setLinesToSkip(1);
        itemReader.setResource(inputResource);
        return itemReader;
    }
}
