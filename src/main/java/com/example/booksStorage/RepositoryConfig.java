package com.example.booksStorage;

import com.example.booksStorage.repository.Repository;
import com.example.booksStorage.repository.TreeMapRepository;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig<K, V> {
    public Repository<K, V> repository() {
        return new TreeMapRepository<>();
    }
}
