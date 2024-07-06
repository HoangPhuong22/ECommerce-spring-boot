package vn.zerocoder.Mart.runner;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import vn.zerocoder.Mart.model.Category;
import vn.zerocoder.Mart.repository.CategoryRepository;

@Component
@Transactional
@RequiredArgsConstructor
public class InitialDataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
    }
}
