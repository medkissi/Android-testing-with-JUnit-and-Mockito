package com.psdemo.todo

import com.psdemo.todo.add.AddViewModel
import com.psdemo.todo.data.TodoRepository

import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.Mockito.mock
import org.mockito.kotlin.argThat
import org.mockito.kotlin.never
import org.mockito.kotlin.verify

class AddViewModelTest {
    @Test
    fun test_save() {
        val repository:TodoRepository = mock(TodoRepository::class.java)
        val model = AddViewModel(repository)
        model.todo.title = "Test Todo"
        val actual = model.save()

        assertNull(actual)
        verify(repository).insert(any())
    }

    @Test
    fun test_saveNoTitle() {
        val repository:TodoRepository = mock(TodoRepository::class.java)
        val model = AddViewModel(repository)
        val expected = "Title is required"
        val actual = model.save()

        assertEquals(expected,actual)
        verify(repository, never()).insert(any())
    }

    @Test
    fun test_saveWithTitle() {
        val repository:TodoRepository = mock(TodoRepository::class.java)
        val model = AddViewModel(repository)
        val actualTitle = "Test Todo"
        val actualDueDate = System.currentTimeMillis()
        model.todo.title =  actualTitle
        model.todo.dueDate = actualDueDate

        val actual = model.save()

        assertNull(actual)
        verify(repository).insert(
            argThat{
                title == actualTitle && dueDate == actualDueDate
            }
        )
    }

    @Test
    fun test_saveWithtoutDate() {
        val repository:TodoRepository = mock(TodoRepository::class.java)
        val model = AddViewModel(repository)
        val actualTitle = "Test Todo"
        model.todo.title =  actualTitle

        val actual = model.save()

        assertNull(actual)
        verify(repository).insert(
            argThat{
                title == actualTitle && dueDate == null
            }
        )
    }
}