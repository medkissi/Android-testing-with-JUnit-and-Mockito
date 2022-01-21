package com.psdemo.todo

import androidx.lifecycle.MutableLiveData
import com.psdemo.todo.data.Todo
import com.psdemo.todo.data.TodoRepository
import com.psdemo.todo.list.ListViewModel
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.junit.Rule
import org.junit.rules.ExpectedException
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify

class ListViewModelTest {
    @get:Rule
    val exceptionRule = ExpectedException.none()
    val now = System.currentTimeMillis()
    val day = 1000 * 60 * 60 * 24


    @Test
    fun test_allTodosEmpty() {
        val expected = 0
        val repository:TodoRepository = mock(TodoRepository::class.java)
        `when`(repository.getAllTodos())
            .thenReturn(MutableLiveData(arrayListOf()))


        val model = ListViewModel(repository)
        val todos =  model.allTodos.value


        assertNotNull(todos)
        assertEquals(expected,todos!!.size )

    }
    @Test
    fun test_allTodosSingle() {
        val expected = 1
        val repository:TodoRepository = mock(TodoRepository::class.java)
        `when`(repository.getAllTodos())
            .thenReturn(MutableLiveData(arrayListOf( Todo("5","Todo 5", now - day, false,now))))


        val model = ListViewModel(repository)
        val todos =  model.allTodos.value


        assertNotNull(todos)
        assertEquals(expected,todos!!.size )

    }
    @Test
    fun test_allTodosMultiple() {
        val expected = 3
        val repository:TodoRepository = mock(TodoRepository::class.java)
        `when`(repository.getAllTodos())
            .thenReturn(MutableLiveData(arrayListOf(
                Todo("5","Todo 5", now - day, false,now),
                Todo("3","Todo 3", now + day, false,now),
                Todo("4","Todo 4", now + day, true,now))))


        val model = ListViewModel(repository)
        val todos =  model.allTodos.value


        assertNotNull(todos)
        assertEquals(expected,todos!!.size )

    }


   @Test
    fun test_upcomingTodosCountEmpty() {
        val expected = 0
        val repository:TodoRepository = mock(TodoRepository::class.java)
       `when`(repository.getUpcomingTodosCount()).thenReturn(MutableLiveData(expected))
        val model = ListViewModel(repository)

        val todosCount = model.upcomingTodosCount.value

        assertEquals(expected,todosCount)

    }
    @Test
    fun test_upcomingTodosCountSingle() {
        val expected = 1
        val repository:TodoRepository = mock(TodoRepository::class.java)
        `when`(repository.getUpcomingTodosCount()).thenReturn(MutableLiveData(expected))
        val model = ListViewModel(repository)

        val todosCount = model.upcomingTodosCount.value

        assertEquals(expected,todosCount)

    }
    @Test
    fun test_upcomingTodosCountMutltiple() {
        val expected = 5
        val repository:TodoRepository = mock(TodoRepository::class.java)
        `when`(repository.getUpcomingTodosCount()).thenReturn(MutableLiveData(expected))
        val model = ListViewModel(repository)

        val todosCount = model.upcomingTodosCount.value

        assertEquals(expected,todosCount)

    }

     @Test
    fun test_toggleTodo() {
        val id = "fake"
        val repository:TodoRepository = mock(TodoRepository::class.java)
        val model = ListViewModel(repository)
        model.toggleTodo(id)
         verify(repository)
             .toggleTodo(id)
    }
    @Test
    fun test_toggleTodoNotFound() {
        val id = "fake"
        val exceptionMessage = "Todo not found"
        val repository:TodoRepository = mock(TodoRepository::class.java)
        `when`(repository.toggleTodo(id)).thenThrow(IllegalArgumentException(exceptionMessage))
        val model = ListViewModel(repository)
        exceptionRule.expect(IllegalArgumentException::class.java)
        exceptionRule.expectMessage(exceptionMessage)
        model.toggleTodo(id)
        verify(repository)
            .toggleTodo(id)
    }
}