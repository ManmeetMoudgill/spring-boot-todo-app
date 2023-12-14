package com.manmeet.moudgill.NewTodoApplication.domains;

import com.manmeet.moudgill.NewTodoApplication.config.ApiResp.PageInfo;
import com.manmeet.moudgill.NewTodoApplication.domains.declaration.TodoDomain;
import com.manmeet.moudgill.NewTodoApplication.dtos.TodoDto;
import com.manmeet.moudgill.NewTodoApplication.dtos.TodoResponseDto;
import com.manmeet.moudgill.NewTodoApplication.dtos.UserDto;
import com.manmeet.moudgill.NewTodoApplication.exceptions.ApplicationException;
import com.manmeet.moudgill.NewTodoApplication.persistance.entities.Todo;
import com.manmeet.moudgill.NewTodoApplication.persistance.entities.User;
import com.manmeet.moudgill.NewTodoApplication.persistance.respositories.TodoRepo;
import com.manmeet.moudgill.NewTodoApplication.persistance.respositories.UserRepo;
import com.manmeet.moudgill.NewTodoApplication.utils.TodoDtoToResponseDtoConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;


@Component
public class TodoDomainImpl implements TodoDomain {

    private final UserRepo userRepo;
    private final TodoRepo todoRepo;


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TodoDtoToResponseDtoConverter todoDtoToResponseDtoConverter;

    @Autowired
    public TodoDomainImpl(UserRepo userRepo, TodoRepo todoRepo) {
        this.userRepo = userRepo;
        this.todoRepo = todoRepo;
    }

    @Override
    public TodoResponseDto createTodo(TodoDto todoDto, Long userId) {

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ApplicationException("User not found!!"));
        todoDto.setTodoUser(this.modelMapper.map(user, UserDto.class));

        Todo todoCreated = this.todoRepo.save(this.modelMapper.map(todoDto, Todo.class));


        return this.todoDtoToResponseDtoConverter.convertFromTodoDtoToTodoResponseDto(this.modelMapper.map(todoCreated, TodoDto.class));

    }

    @Override
    public TodoResponseDto updateTodo(TodoDto todoDto, Long userId) {


        Todo todoToBeUpdated = this.getTodoById(todoDto.getTodoId());
        if (!todoToBeUpdated.getTodoUser().getUserId().equals(userId)) {
            throw new ApplicationException("Unauthorized to do this operation!!");
        }

        todoToBeUpdated.setTodoTitle(todoDto.getTodoTitle());
        todoToBeUpdated.setTodoDescription(todoDto.getTodoDescription());

        this.todoRepo.save(todoToBeUpdated);

        return this.todoDtoToResponseDtoConverter.convertFromTodoDtoToTodoResponseDto(this.modelMapper.map(todoToBeUpdated, TodoDto.class));

    }


    @Override
    public Long deleteTodo(Long todoId, Long loggedUserId) {
        Todo todoToBeDeleted = this.getTodoById(todoId);
        if (!todoToBeDeleted.getTodoUser().getUserId().equals(loggedUserId)) {
            throw new ApplicationException("Unauthorized to do this operation!!");
        }
        todoToBeDeleted.setTodoUser(null);
        this.todoRepo.delete(todoToBeDeleted);
        return todoId;
    }

    @Override
    public TodoResponseDto getTodoOfLoggedUser(Long todoId, Long loggedUserId) {
        System.out.println("TodoId: " + todoId);
        System.out.println("UserId: " + loggedUserId);
        Todo todoOfLoggedUser = this.todoRepo.getSingleTodoOfUserByUserId(todoId, loggedUserId).orElseThrow(() -> new ApplicationException("Todo not found!!"));

        return this.todoDtoToResponseDtoConverter.convertFromTodoDtoToTodoResponseDto(this.modelMapper.map(todoOfLoggedUser, TodoDto.class));
    }

    @Override
    public HashMap<String,Object> getTodosOfLoggedUser(Long loggedUserId,Integer pageNumber,Integer pageSize) {


        Sort sort = Sort.by(Sort.Order.asc("todoId")); // Sorting by todoId in ascending order

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Todo> pagedTodosOfLoggedUser = this.todoRepo.getTodosOfUserByUserId(loggedUserId,pageable);




        List<Todo> todoList = pagedTodosOfLoggedUser.getContent(); //

        List<TodoResponseDto> todosOfUser= todoList.stream().map((el) -> this.todoDtoToResponseDtoConverter.convertFromTodoDtoToTodoResponseDto(this.modelMapper.map(el, TodoDto.class))).toList();

        HashMap<String,Object> todoOfUserHashMap=new HashMap<>();
        todoOfUserHashMap.put("list",todosOfUser);
        todoOfUserHashMap.put("pageInfo", PageInfo.builder().pageNumber(pagedTodosOfLoggedUser.getNumber()).pageSize(pagedTodosOfLoggedUser.getSize()).totalPages(pagedTodosOfLoggedUser.getTotalPages()).build());

        return todoOfUserHashMap;

    }

    @Override
    public Long markTodoCompleted(Long todoId,Long loggedUserId) {
        Todo todoFound=this.getTodoById(todoId);
        if (!todoFound.getTodoUser().getUserId().equals(loggedUserId)) {
            throw new ApplicationException("UnAuthorized to access this resource!!");
        }
        todoFound.setTodoCompletedDate(LocalDateTime.now());
        this.todoRepo.save(todoFound);
        return todoFound.getTodoId();
    }

    /**
     * @param todoId
     * @return
     * @Description TodoService helper method to find the todo by todoId
     */
    private Todo getTodoById(Long todoId) {
        return this.todoRepo.findById(todoId).orElseThrow(() -> new ApplicationException("Todo not found!!"));

    }
}
