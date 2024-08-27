package in.ranjitkokare.springbootmongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import in.ranjitkokare.springbootmongodb.model.TodoDTO;

@Repository
public interface TodoRepository extends MongoRepository<TodoDTO, String> {

}
