package in.ranjitkokare.springbootmongodb.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.ranjitkokare.springbootmongodb.exception.TodoCollectionException;
import in.ranjitkokare.springbootmongodb.model.TodoDTO;
import in.ranjitkokare.springbootmongodb.repository.TodoRepository;
import in.ranjitkokare.springbootmongodb.service.TodoService;

@RestController
public class TodoController {
	
	@Autowired
	private TodoRepository todoRepo;
	
	@Autowired
	private TodoService todoService;
	
	@GetMapping("/todos")
	public ResponseEntity<?> getAllTodos() {
		List<TodoDTO> todos = todoService.getAllTodos();
		return new ResponseEntity<>(todos, todos.size()>0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/todos")
	public ResponseEntity<?> createTodo(@RequestBody TodoDTO todo){
		try {
			todoService.createTodo(todo);
			return new ResponseEntity<TodoDTO>(todo, HttpStatus.OK);
		} catch (ConstraintViolationException e) {
			//if user is sending null values we catch as constraint violation exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		}catch (TodoCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("/todos/{id}")
	public ResponseEntity<?> getSingleTodo(@PathVariable ("id") String id){
		try {
			return new ResponseEntity<>(todoService.getSingleTodo(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/todos/{id}")
	public ResponseEntity<?> updateById(@PathVariable ("id") String id, @RequestBody TodoDTO todo){
		try {
			todoService.updateTodo(id, todo);
			return new ResponseEntity<>("Update Todo with id "+id, HttpStatus.OK);
		} catch (ConstraintViolationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		}catch (TodoCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/todos/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") String id){
		try {
			todoRepo.deleteById(id);
			return new ResponseEntity<>("Successfully deleted with id "+id, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
