package com.kyamran.app.controller;

import com.kyamran.app.model.Writer;
import com.kyamran.app.service.WriterService;

import java.util.List;

public class WriterController {
    WriterService writerService = new WriterService();

    public Writer save(Writer writer) {
        return writerService.save(writer);
    }

    public Writer update(Writer writer) {
        return writerService.update(writer);
    }

    public Writer getById(Long Long) {
        return writerService.getById(Long);
    }

    public void deleteById(Long Long) {
        writerService.deleteById(Long);
    }

    public List<Writer> getAll() {
        return writerService.getAll();
    }
}
