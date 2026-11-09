package com.example.bookmanager.service.impl;

import com.example.bookmanager.mapper.BookMapper;
import com.example.bookmanager.model.Book;
import com.example.bookmanager.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private BookMapper bookMapper;

    @Override
    @Async("reportExecutor")
    public CompletableFuture<String> generateBookReport() {
        return CompletableFuture.supplyAsync(() -> {
            // 1. 查询所有图书
            List<Book> books = bookMapper.selectList(null);

            // 2. 生成报表内容
            StringBuilder sb = new StringBuilder();
            sb.append("图书库存报表\n");
            sb.append("生成时间: ").append(LocalDateTime.now()).append("\n");
            sb.append("----------------------------------------\n");
            sb.append(String.format("%-6s %-20s %-10s %-8s\n", "ID", "书名", "价格", "库存"));
            sb.append("----------------------------------------\n");
            for (Book book : books) {
                sb.append(String.format("%-6d %-20s %-10.2f %-8d\n",
                        book.getId(), book.getTitle(), book.getPrice(), book.getStock()));
            }
            sb.append("----------------------------------------\n");
            sb.append("总计: ").append(books.size()).append(" 本图书\n");

            //3. 写入文件：txt
            String fileName = "report_" + System.currentTimeMillis() + ".txt";
            File file = new File("./reports/" + fileName);
            file.getParentFile().mkdirs();
            try(FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(sb.toString());
            } catch (IOException e) {
                throw new RuntimeException("报表生成失败" + e);
            }

            return file.getAbsolutePath();
        }).exceptionally(ex -> {
            // 异常时返回错误信息
            return "ERROR: " + ex.getMessage();
        });
    }
}
