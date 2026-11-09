package com.example.bookmanager.batch.faulttolerant;

import com.example.bookmanager.model.Book;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
public class ErrorBookWriter {

    private final FlatFileItemWriter<Book> errorWriter;

    public ErrorBookWriter() {
        this.errorWriter = new FlatFileItemWriterBuilder<Book>()
                .name("errorBookWriter")
                .resource(new FileSystemResource("output/erro_books.csv"))
                .delimited()
                .delimiter(",")
                .names("title", "author", "price", "stock")
                .headerCallback(writer -> writer.write("title,author,price,stock,reason"))
                .build();
    }

    public void writeErrorBook(Book book, String reason) {
        // 简单处理，实际上应该需要设置合理的reason值
        try {
            errorWriter.write(new Chunk<>(Collections.singletonList(book)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
