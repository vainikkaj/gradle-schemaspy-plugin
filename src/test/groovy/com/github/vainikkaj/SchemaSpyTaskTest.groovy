package com.github.vainikkaj;

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before
import org.junit.Test

class SchemaSpyTaskTest {

	def task

	@Before
	void createTask(){
		Project project = ProjectBuilder.builder().build()
		project.ext.hello = 'world'
		task = project.task('mytaskname', type: SchemaSpyTask)
	}

	@Test
	void 'task can access project properties'() {
		task.project.properties.sort().each{println it}
		assert task.project.properties.hello == 'world'
	}
}
