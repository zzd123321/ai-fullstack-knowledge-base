package com.example.bookmanager.batch.faulttolerant;

import com.example.bookmanager.model.Book;
import org.springframework.batch.core.SkipListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ErrorBookWriterSkipListener implements SkipListener<Book, Book> {

    @Autowired
    ErrorBookWriter errorBookWriter;

    @Override
    public void onSkipInRead(Throwable t) {
        //读取阶段直接跳过：因为拿不到完整的book对象
    }

    @Override
    public void onSkipInProcess(Book item, Throwable t) {
        errorBookWriter.writeErrorBook(item, t.getMessage());
    }

    @Override
    public void onSkipInWrite(Book item, Throwable t) {
        errorBookWriter.writeErrorBook(item, t.getMessage());
    }
}
