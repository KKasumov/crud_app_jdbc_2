package com.kyamran.app.service;

import com.kyamran.app.model.Writer;
import com.kyamran.app.repository.WriterRepository;
import com.kyamran.app.repository.jdbc.WriterRepositoryImpl;

import java.util.List;

public class WriterService {
    WriterRepository writerRepository = WriterRepositoryImpl.getInstance();

    public void setWriterRepository(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    public Writer save(Writer writer) {
        return writerRepository.save(writer);
    }

    public Writer update(Writer writer) {
        return writerRepository.update(writer);
    }

    public Writer getById(Long Long) {
        return writerRepository.getById(Long);
    }

    public void deleteById(Long Long) {
        writerRepository.deleteById(Long);
    }

    public List<Writer> getAll() {
        return writerRepository.getAll();
    }
}
