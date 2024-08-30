package in.ranjitkokare.springbootmongodb.repository;

import java.util.Optional;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import in.ranjitkokare.springbootmongodb.model.TodoDTO;

@Repository
public interface TodoRepository extends MongoRepository<TodoDTO, String> {
	//Query Method
	@Query("{'todo': ?0}")//'property_name' : parameter_index(starts frm 0th)=1st parameter in the method
	//finding todo from database if user pass any todo namehit the db and retrieve that todo if it is available
	Optional<TodoDTO> findByTodo(String todo);
}
