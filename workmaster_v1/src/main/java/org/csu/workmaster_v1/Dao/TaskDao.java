package org.csu.workmaster_v1.Dao;

import org.csu.workmaster_v1.Entity.Task;

import java.util.List;

public interface TaskDao {

    public void saveTask(Task task);
    public Task findTaskByTaskName(String TaskName);
    public Task findTaskBytaskid(long taskid);
    public void updateTask(Task task);
    public void deleteTaskById(Long id);
   // public List<Task> findMessageByGroupid(String studentId);
    public List<Task> findTaskByGroupid(Long groupid);

}
