package pl.rembisz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.rembisz.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
