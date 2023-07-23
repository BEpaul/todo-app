package todoapp.core.todo.domain.support;

import java.util.UUID;
import org.springframework.stereotype.Component;
import todoapp.core.todo.domain.TodoId;
import todoapp.core.todo.domain.TodoIdGenerator;

/**
 * UUID 할일 일련번호 생성기
 *
 * @author springrunner.kr@gmail.com
 */
@Component
public class UUIDTodoIdGenerator implements TodoIdGenerator {

  @Override
  public TodoId generateId() {
    return new TodoId(UUID.randomUUID().toString());
  }

}
