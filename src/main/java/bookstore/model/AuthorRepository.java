package bookstore.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends
        CrudRepository<Author, Long>,
        PagingAndSortingRepository<Author, Long> {

    //@Query("select a from Author a")
//    @Query("select a from Author a left join fetch a.books b left join fetch b.authors aa")
//    @Query("select a from Author a left join fetch a.books b left join fetch b.authors aa")
    Page<Author> findAll(Pageable pageable);
}
