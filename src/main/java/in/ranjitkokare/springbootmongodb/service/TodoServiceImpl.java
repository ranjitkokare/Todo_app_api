package in.ranjitkokare.springbootmongodb.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ranjitkokare.springbootmongodb.exception.TodoCollectionException;
import in.ranjitkokare.springbootmongodb.model.TodoDTO;
import in.ranjitkokare.springbootmongodb.repository.TodoRepository;

@Service
public class TodoServiceImpl implements TodoService {

	@Autowired
	private TodoRepository todoRepo;
	
	@Override
	public void createTodo(TodoDTO todo) throws ConstraintViolationException, TodoCollectionException {
		//check for existence
		Optional<TodoDTO> todoOptional = todoRepo.findByTodo(todo.getTodo());
		if(todoOptional.isPresent()) {
			throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
		}else {
			todo.setCreatedAt(new Date(System.currentTimeMillis()));
			todoRepo.save(todo);
		}
		
	}

	@Override
	public List<TodoDTO> getAllTodos() {
		List<TodoDTO> todos = todoRepo.findAll();
		if(todos.size()>0) {
			return todos;
		}else {
			return new ArrayList<>();
		}
	}

	@Override
	public TodoDTO getSingleTodo(String id) throws TodoCollectionException{
		Optional<TodoDTO> optionalTodo = todoRepo.findById(id);
		if(!optionalTodo.isPresent()) {
			throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
		}else {
			return optionalTodo.get();
		}
	}

	@Override
	public void updateTodo(String id, TodoDTO todo) throws TodoCollectionException {
		Optional<TodoDTO> todoWithId = todoRepo.findById(id);
		// to check if duplicate to do is present with same name
		Optional<TodoDTO> todoWithSameName = todoRepo.findByTodo(todo.getTodo());
		
		if(todoWithId.isPresent()) {
			
			if(todoWithSameName.isPresent() && !todoWithSameName.get().getId().equals(id)) {
				throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
			}
			
			TodoDTO todoToUpdate = todoWithId.get();
			
			todoToUpdate.setTodo(todo.getTodo() != null ? todo.getTodo() : todoToUpdate.getTodo());
			todoToUpdate.setDescription(todo.getDescription()!= null ? todo.getDescription() : todoToUpdate.getDescription());
			todoToUpdate.setCompleted(todo.getCompleted() != null ? todo.getCompleted() : todoToUpdate.getCompleted());
			todoToUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));
			
			todoRepo.save(todoToUpdate);
		}else {
			throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
		}
	}

}
