package com.psdemo.todo

import com.psdemo.todo.data.Todo
import com.psdemo.todo.data.TodoRepository
import com.psdemo.todo.list.ListViewModel
import com.psdemo.todo.utils.TodoTestRepository
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.junit.Rule
import org.junit.rules.ExpectedException

class ListViewModelTest {
    @get:Rule
    val exceptionRule = ExpectedException.none()

    lateinit var repository: TodoRepository
    @Before
    fun setup() {
        val now = System.currentTimeMillis()
        val day = 1000 * 60 * 60 * 24
        val todos = ArrayList<Todo>()
        var todo =Todo("1","Todo 1",null, false, now)
        todos.add(todo)
        todo = Todo("2","Todo 2", now + day, false,now)
        todos.add(todo)
        todo = Todo("3","Todo 3", now + day, false,now)
        todos.add(todo)
        todo = Todo("4","Todo 4", now + day, true,now)
        todos.add(todo)
        todo = Todo("5","Todo 5", now - day, false,now)
        todos.add(todo)
        repository = TodoTestRepository(todos )

    }

    @Test
    fun test_allTodos() {
        val expected = 4
        val model = ListViewModel(repository)
        val todos =  model.allTodos.value


        assertNotNull(todos)
        assertEquals(expected,todos!!.size )

    }


    @Test
    fun test_upcomingTodosCount() {
        val expected = 2
        val model = ListViewModel(repository)

        val todosCount = model.upcomingTodosCount.value

        assertEquals(expected,todosCount)

    }

    @Test
    fun test_toggleTodo() {
        val id = "fake"
        val model = ListViewModel(repository)
        exceptionRule.expect(NotImplementedError::class.java)
        model.toggleTodo(id)
    }
}