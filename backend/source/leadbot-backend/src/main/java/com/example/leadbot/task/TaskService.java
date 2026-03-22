package com.example.leadbot.task;

import com.example.leadbot.common.BusinessException;
import com.example.leadbot.task.dto.TaskUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> list() {
        return taskRepository.findAll();
    }

    public Task update(Long id, TaskUpdateRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "任务不存在"));

        if (request.getDone() != null) {
            task.setDone(request.getDone());
        }

        return taskRepository.save(task);
    }
}
