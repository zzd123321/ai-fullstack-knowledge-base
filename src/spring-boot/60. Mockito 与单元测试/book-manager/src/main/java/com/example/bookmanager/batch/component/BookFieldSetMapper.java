package com.example.bookmanager.batch.component;

import com.example.bookmanager.model.Book;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class BookFieldSetMapper implements FieldSetMapper<Book> {
    @Override
    public Book mapFieldSet(FieldSet fieldSet) throws BindException {
        Book book = new Book();
        book.setTitle(fieldSet.readString(0));
        book.setAuthor(fieldSet.readString(1));
        book.setPrice(fieldSet.readDouble(2));
        book.setStock(fieldSet.readInt(3));

        return book;
    }
}
