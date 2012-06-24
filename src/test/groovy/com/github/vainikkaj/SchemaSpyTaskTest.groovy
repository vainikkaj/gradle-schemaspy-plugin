package com.github.vainikkaj;

import static org.junit.Assert.*
import static org.hamcrest.CoreMatchers.*

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before
import org.junit.Test

class SchemaSpyTaskTest {

	def task

	@Before
	void createTask(){
		Project project = ProjectBuilder.builder().build()
		task = project.task('mytaskname', type: SchemaSpyTask)
	}

	@Test
	void canAddTaskToProject() {
		assertThat task.greeting, is('hello from schemaspy')
	}
}
